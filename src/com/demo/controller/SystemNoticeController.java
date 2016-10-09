package com.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.bean.Admin;
import com.demo.bean.SystemNotice;
import com.demo.util.StringUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;


/**
 * BlogController
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class SystemNoticeController extends Controller {
	
	public void index() {
		setAttr("systemNotices", SystemNotice.dao.findAll());
		renderJson();
	}
	
	public void systemNoticeResultList() {
		int pn = 1;
		if(!StringUtil.isEmpty(getPara("pageNum"))){
			pn = getParaToInt("pageNum");
		}
		int ps = 10;
		if(!StringUtil.isEmpty(getPara("pageSize"))){
			ps = getParaToInt("pageSize");
		}
		Map<String,Object> systemNotice = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("school"))){
			systemNotice.put("school",getPara("school"));
		}
		Page<SystemNotice> systemNoticePage = SystemNotice.dao.findByPage(pn, ps, systemNotice);
		List<SystemNotice> newList = new ArrayList<SystemNotice>();
		List<SystemNotice> list = systemNoticePage.getList();
		for(SystemNotice c : list){
			c.put("admin", Admin.dao.findById(c.get("adminId")));
			newList.add(c);
		}
		setAttr("status", 0);
		setAttr("value", newList);
		renderJson();
	}
	
	public void saveSystemNotice() {
		Integer id = getParaToInt("id");
		SystemNotice systemNotice = new SystemNotice()
				.set("adminId", getPara("adminId"))
				.set("content", getPara("content"))
				.set("school", getPara("school"))
				.set("title", getPara("title"));
		SystemNotice oldSystemNotice = SystemNotice.dao.findById(id);
		boolean status = false;
		if (oldSystemNotice != null) {
			status = systemNotice.set("id", id).update();
		} else {
			status = systemNotice.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", systemNotice);
		renderJson();
	}
	
	public void resetInfo(){
		String id = getPara("id");
		SystemNotice systemNotice = new SystemNotice();
		if(!StringUtil.isEmpty(getPara("title"))){
			systemNotice.set("title",getPara("title"));
		}
		if(!StringUtil.isEmpty(getPara("content"))){
			systemNotice.set("content",getPara("content"));
		}
		SystemNotice oldSystemNotice = SystemNotice.dao.findById(id);
		boolean status = false;
		if (oldSystemNotice!= null) {
			status = systemNotice.set("id", id).update();
		} else {
			status = systemNotice.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", systemNotice);
		renderJson();
	}
}