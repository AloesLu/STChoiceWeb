package com.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.bean.Choose;
import com.demo.bean.Student;
import com.demo.bean.Teacher;
import com.demo.util.StringUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;


/**
 * BlogController
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class ChooseController extends Controller {
	
	public void index() {
		setAttr("chooses", Choose.dao.findAll());
		renderJson();
	}
	
	public void chooseResultList() {
		int pn = 1;
		if(!StringUtil.isEmpty(getPara("pageNum"))){
			pn = getParaToInt("pageNum");
		}
		int ps = 10;
		if(!StringUtil.isEmpty(getPara("pageSize"))){
			ps = getParaToInt("pageSize");
		}
		Map<String,Object> choose = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("teaId"))){
			choose.put("teaId",getPara("teaId"));
		}
		if(!StringUtil.isEmpty(getPara("stuId"))){
			choose.put("stuId",getPara("stuId"));
		}
		if(!StringUtil.isEmpty(getPara("teaStatus"))){
			choose.put("teaStatus",getPara("teaStatus"));
		}
		if(!StringUtil.isEmpty(getPara("stuStatus"))){
			choose.put("stuStatus",getPara("stuStatus"));
		}
		Page<Choose> choosePage = Choose.dao.findByPage(pn, ps, choose);
		List<Choose> newList = new ArrayList<Choose>();
		List<Choose> list = choosePage.getList();
		for(Choose c : list){
			c.put("student", Student.dao.findById(c.get("stuId")));
			c.put("teacher", Teacher.dao.findById(c.get("teaId")));
			newList.add(c);
		}
		setAttr("status", 0);
		setAttr("value", newList);
		renderJson();
	}
	
	public void saveChoose() {
		Integer id = getParaToInt("id");
		Choose choose = new Choose()
				.set("stuId", getPara("stuId"))
				.set("teaId", getPara("teaId"))
				.set("wishNum", getParaToInt("wishNum"))
				.set("stuStatus", getParaToInt("stuStatus"))
				.set("teaStatus", getParaToInt("teaStatus"));
		Choose oldChoose = Choose.dao.findById(id);
		boolean status = false;
		if (oldChoose != null) {
			status = choose.set("id", id).update();
		} else {
			status = choose.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", choose);
		renderJson();
	}
	
	public void chooseTeacherResultList() {
		int pn = 1;
		if(!StringUtil.isEmpty(getPara("pageNum"))){
			pn = getParaToInt("pageNum");
		}
		int ps = 10;
		if(!StringUtil.isEmpty(getPara("pageSize"))){
			ps = getParaToInt("pageSize");
		}
		Map<String,Object> choose = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("teaTitle"))){
			choose.put("teaTitle",getPara("teaTitle"));
		}
		Page<Choose> choosePage = Choose.dao.findByPage(pn, ps, choose);
		List<Choose> newList = new ArrayList<Choose>();
		List<Choose> list = choosePage.getList();
		for(Choose c : list){
			c.put("student", Student.dao.findById(c.get("stuId")));
			c.put("teacher", Teacher.dao.findById(c.get("teaId")));
			newList.add(c);
		}
		setAttr("status", 0);
		setAttr("value", newList);
		renderJson();
	}
	
	public void chooseStudentResultList() {
		int pn = 1;
		if(!StringUtil.isEmpty(getPara("pageNum"))){
			pn = getParaToInt("pageNum");
		}
		int ps = 10;
		if(!StringUtil.isEmpty(getPara("pageSize"))){
			ps = getParaToInt("pageSize");
		}
		Map<String,Object> choose = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("clazz"))){
			choose.put("clazz",getPara("clazz"));
		}
		if(!StringUtil.isEmpty(getPara("major"))){
			choose.put("major",getPara("major"));
		}
		Page<Choose> choosePage = Choose.dao.findByPage(pn, ps, choose);
		List<Choose> newList = new ArrayList<Choose>();
		List<Choose> list = choosePage.getList();
		for(Choose c : list){
			c.put("student", Student.dao.findById(c.get("stuId")));
			c.put("teacher", Teacher.dao.findById(c.get("teaId")));
			newList.add(c);
		}
		setAttr("status", 0);
		setAttr("value", newList);
		renderJson();
	}
	
	public void saveFenPeiChoose() {
		int pn = 1;
		int ps = 60;
		int selectNum=0;
		int successNum=0;
		if(!StringUtil.isEmpty(getPara("selectNum"))){
			selectNum = getParaToInt("selectNum");
		}
		Map<String,Object> student = new HashMap<String,Object>();
		Page<Student> studentPage = Student.dao.findByPage(pn, ps, student);
		List<Student> teachreList=studentPage.getList();
		for(Student t : teachreList){
			Map<String,Object> chooseMap = new HashMap<String,Object>();
			if(!StringUtil.isEmpty(t.get("id"))){
				chooseMap.put("stuId",t.get("id"));
			}
			Page<Choose> choosePage = Choose.dao.findByPage(pn, ps, chooseMap);
			if(choosePage.getList().size()==0&&selectNum>successNum){
				Choose choose = new Choose()
				.set("stuId", t.get("id"))
				.set("teaId", getPara("teaId"))
				.set("wishNum", 0)
				.set("stuStatus", 0)
				.set("teaStatus", 0);
				Choose oldChoose = Choose.dao.findById(getPara("id"));
				if (oldChoose != null) {
					choose.set("id", getPara("id")).update();
				} else {
					choose.save();
				}
				successNum++;
			}
		}
		setAttr("status", 0);
		setAttr("value", successNum);
		renderJson();
	}
}