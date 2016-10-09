package com.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.bean.Wish;
import com.demo.bean.Student;
import com.demo.bean.Teacher;
import com.demo.util.StringUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;


/**
 * BlogController
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class WishController extends Controller {
	
	public void index() {
		setAttr("wishs", Wish.dao.findAll());
		renderJson();
	}
	
	public void wishResultList() {
		int pn = 1;
		if(!StringUtil.isEmpty(getPara("pageNum"))){
			pn = getParaToInt("pageNum");
		}
		int ps = 10;
		if(!StringUtil.isEmpty(getPara("pageSize"))){
			ps = getParaToInt("pageSize");
		}
		Map<String,Object> wish = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("teaId"))){
			wish.put("teaId",getPara("teaId"));
		}
		if(!StringUtil.isEmpty(getPara("stuId"))){
			wish.put("stuId",getPara("stuId"));
		}
		if(!StringUtil.isEmpty(getPara("wishNum"))){
			wish.put("wishNum",getPara("wishNum"));
		}
		Page<Wish> wishPage = Wish.dao.findByPage(pn, ps, wish);
		List<Wish> newList = new ArrayList<Wish>();
		List<Wish> list = wishPage.getList();
		for(Wish c : list){
			c.put("student", Student.dao.findById(c.get("stuId")));
			c.put("teacher", Teacher.dao.findById(c.get("teaId")));
			newList.add(c);
		}
		setAttr("status", 0);
		setAttr("value", newList);
		renderJson();
	}
	
	public void saveWish() {
		Integer id = getParaToInt("id");
		Wish wish = new Wish()
				.set("stuId", getPara("stuId"))
				.set("teaId", getPara("teaId"))
				.set("wishNum", getParaToInt("wishNum"))
				.set("content", getPara("content"));
		Wish oldWish = Wish.dao.findById(id);
		boolean status = false;
		if (oldWish != null) {
			status = wish.set("id", id).update();
		} else {
			status = wish.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", wish);
		renderJson();
	}
}