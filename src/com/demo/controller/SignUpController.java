package com.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.bean.SignUp;
import com.demo.bean.Student;
import com.demo.bean.Task;
import com.demo.bean.Teacher;
import com.demo.util.StringUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;


/**
 * BlogController
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class SignUpController extends Controller {
	
	public void index() {
		setAttr("signUps", SignUp.dao.findAll());
		renderJson();
	}
	
	public void enterListResultList() {
		int pn = 1;
		if(!StringUtil.isEmpty(getPara("pageNum"))){
			pn = getParaToInt("pageNum");
		}
		int ps = 10;
		if(!StringUtil.isEmpty(getPara("pageSize"))){
			ps = getParaToInt("pageSize");
		}
		Map<String,Object> enterList = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("tasId"))){
			enterList.put("tasId",getPara("tasId"));
		}
		if(!StringUtil.isEmpty(getPara("stuId"))){
			enterList.put("stuId",getPara("stuId"));
		}
		if(!StringUtil.isEmpty(getPara("type"))){
			enterList.put("type",getPara("type"));
		}
		if(!StringUtil.isEmpty(getPara("flag"))){
			enterList.put("flag",getPara("flag"));
		}
		if(!StringUtil.isEmpty(getPara("isFinish"))){
			enterList.put("isFinish",getPara("isFinish"));
		}
		Page<SignUp> enterListPage = SignUp.dao.findByPage(pn, ps, enterList);
		List<SignUp> newList = new ArrayList<SignUp>();
		List<SignUp> list = enterListPage.getList();
		for(SignUp c : list){
			c.put("task", Task.dao.findById(c.get("tasId")));
			c.put("student", Student.dao.findById(c.get("stuId")));
			newList.add(c);
		}
		setAttr("status", 0);
		setAttr("value", newList);
		renderJson();
	}
	
	public void getCount(){
		Map<String,Object> enterList = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("tasId"))){
			enterList.put("tasId",getPara("tasId"));
		}
		if(!StringUtil.isEmpty(getPara("stuId"))){
			enterList.put("stuId",getPara("stuId"));
		}
		if(!StringUtil.isEmpty(getPara("type"))){
			enterList.put("type",getPara("type"));
		}
		if(!StringUtil.isEmpty(getPara("flag"))){
			enterList.put("flag",getPara("flag"));
		}
		if(!StringUtil.isEmpty(getPara("isFinish"))){
			enterList.put("isFinish",getPara("isFinish"));
		}
		setAttr("status", 0);
		setAttr("value", SignUp.dao.count(enterList));
		renderJson();
	}
	
	public void saveEnterList() {
		Integer id = getParaToInt("id");
		SignUp signUp = new SignUp()
				.set("tasId", getPara("tasId"))
				.set("stuId", getPara("stuId"))
				.set("type", getParaToInt("type"))
				.set("flag", getParaToInt("flag"))
				.set("isFinish", getParaToInt("isFinish"));
		SignUp oldEnterList = SignUp.dao.findById(id);
		boolean status = false;
		if (oldEnterList != null) {
			status = signUp.set("id", id).update();
		} else {
			status = signUp.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", signUp);
		renderJson();
	}
}