package com.demo.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.bean.Choose;
import com.demo.bean.Student;
import com.demo.bean.Teacher;
import com.demo.bean.Time;
import com.demo.util.StringUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * BlogController 所有 sql 写在 Model 或 Service 中，不要写在 Controller
 * 中，养成好习惯，有利于大型项目的开发与维护
 */
public class TimeController extends Controller {

	public void index() {
		setAttr("status", 0);
		setAttr("values", Time.dao.findAll());
		renderJson();
	}

	public void timeList() {
		int pn = 1;
		if(!StringUtil.isEmpty(getPara("pageNum"))){
			pn = getParaToInt("pageNum");
		}
		int ps = 10;
		if(!StringUtil.isEmpty(getPara("pageSize"))){
			ps = getParaToInt("pageSize");
		}
		Map<String,Object> time = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("timeStatus"))){
			time.put("timeStatus",getPara("timeStatus"));
		}
		Page<Time> timePage = Time.dao.findByPage(pn, ps, time);
		setAttr("status", 0);
		setAttr("values", timePage.getList());
		renderJson();
	}

	public void saveTime() {
		Integer id = getParaToInt("id");
		Time time = new Time()
				.set("startTime", new Timestamp(getParaToLong("startTime")))
				.set("endTime", new Timestamp(getParaToLong("endTime")))
				.set("timeStatus", getParaToInt("timeStatus"))
				.set("timeType", getParaToInt("timeType"));
		Time oldTime = Time.dao.findById(id);
		boolean status = false;
		if (oldTime != null) {
			status = time.set("id", id).update();
		} else {
			status = time.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", time);
		renderJson();
	}
}