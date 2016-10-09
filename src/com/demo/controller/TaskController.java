package com.demo.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.bean.SystemNotice;
import com.demo.bean.Task;
import com.demo.bean.Teacher;
import com.demo.util.StringUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;


/**
 * BlogController
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class TaskController extends Controller {
	
	public void index() {
		setAttr("tasks", Task.dao.findAll());
		renderJson();
	}
	
	public void taskResultList() {
		int pn = 1;
		if(!StringUtil.isEmpty(getPara("pageNum"))){
			pn = getParaToInt("pageNum");
		}
		int ps = 10;
		if(!StringUtil.isEmpty(getPara("pageSize"))){
			ps = getParaToInt("pageSize");
		}
		Map<String,Object> task = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("teaId"))){
			task.put("teaId",getPara("teaId"));
		}
		if(!StringUtil.isEmpty(getPara("type"))){
			task.put("type",getPara("type"));
		}
		if(!StringUtil.isEmpty(getPara("flag"))){
			task.put("flag",getPara("flag"));
		}
		if(!StringUtil.isEmpty(getPara("school"))){
			task.put("school",getPara("school"));
		}
		Page<Task> taskPage = Task.dao.findByPage(pn, ps, task);
		List<Task> newList = new ArrayList<Task>();
		List<Task> list = taskPage.getList();
		for(Task c : list){
			c.put("teacher", Teacher.dao.findById(c.get("teaId")));
			newList.add(c);
		}
		setAttr("status", 0);
		setAttr("value", newList);
		renderJson();
	}
	
	public void saveTask() {
		Integer id = getParaToInt("id");
		Task task = new Task()
				.set("title", getPara("title"))
				.set("teaId", getPara("teaId"))
				.set("school", getPara("school"))
				.set("type", getParaToInt("type"))
				.set("flag", getParaToInt("flag"))
				.set("content", getPara("content"))
				.set("endTime", getPara("endTime"));
		Task oldTask = Task.dao.findById(id);
		boolean status = false;
		if (oldTask != null) {
			status = task.set("id", id).update();
		} else {
			status = task.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", task);
		renderJson();
	}
	
	public void resetInfo(){
		String id = getPara("id");
		Task task = new Task();
		if(!StringUtil.isEmpty(getPara("title"))){
			task.set("title",getPara("title"));
		}
		if(!StringUtil.isEmpty(getPara("content"))){
			task.set("content",getPara("content"));
		}
		Task oldTask = Task.dao.findById(id);
		boolean status = false;
		if (oldTask!= null) {
			status = task.set("id", id).update();
		} else {
			status = task.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", task);
		renderJson();
	}
}