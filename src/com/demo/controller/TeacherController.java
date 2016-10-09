package com.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.demo.bean.Admin;
import com.demo.bean.Choose;
import com.demo.bean.Student;
import com.demo.bean.Teacher;
import com.demo.util.RSASecurityHelper;
import com.demo.util.StringUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;


/**
 * BlogController
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class TeacherController extends Controller {
	
	public void index() {
		setAttr("teachers", Teacher.dao.findAll());
		renderJson();
	}
	
	
	public void teacherList(){
		int pn = 1;
		if(!StringUtil.isEmpty(getPara("pageNum"))){
			pn = getParaToInt("pageNum");
		}
		int ps = 10;
		if(!StringUtil.isEmpty(getPara("pageSize"))){
			ps = getParaToInt("pageSize");
		}
		Map<String,Object> teacher = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("teaTitle"))){
			teacher.put("teaTitle",getPara("teaTitle"));
		}
		if(!StringUtil.isEmpty(getPara("direction"))){
			teacher.put("direction",getPara("direction"));
		}
		if(!StringUtil.isEmpty(getPara("school"))){
			teacher.put("school",getPara("school"));
		}
		Page<Teacher> teacherPage = Teacher.dao.findByPage(pn, ps, teacher);
		setAttr("status", 0);
		setAttr("value", teacherPage.getList());
		renderJson();
	}
	
	public void teacherDetail(){
		Integer status = 0;
		Teacher teacher = Teacher.dao.findById(getPara("id"));
		if(StringUtil.isEmpty(teacher)){
			status = 404;
		}
		setAttr("status", status);
		setAttr("value", teacher);
		renderJson();
	}
	
	public void saveTeacher() {
		String id = getPara("id");
		Teacher teacher = new Teacher()
				.set("avatar", getPara("avatar"))
				.set("name", getPara("name"))
				.set("sex", getPara("sex"))
				.set("teaTitle", getPara("teaTitle"))
				.set("direction", getPara("direction"))
				.set("contact", getPara("contact"))
				.set("email", getPara("email"))
				.set("QQ", getPara("QQ"))
				.set("honour", getPara("honour"))
				.set("school", getPara("school"))
				.set("teaPaper", getPara("teaPaper"))
				.set("teaProject", getPara("teaProject"))
				.set("stuRequire", getPara("stuRequire"))				
				.set("remarks", getPara("remarks"));
		Teacher oldTeacher = Teacher.dao.findById(id);
		boolean status = false;
		if (oldTeacher != null) {
			status = teacher.set("id", id).update();
		} else {
			status = teacher.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", teacher);
		renderJson();
	}
	
	public void addTeacher() throws Exception{
		String id = getPara("id");
		String pwd = RSASecurityHelper.toHexValue(RSASecurityHelper.encryptMD5(getPara("pwd").getBytes(Charset.forName("utf-8"))));;
		Teacher teacher = new Teacher()
		.set("id", id)
		.set("name", getPara("name"))
		.set("avatar", getPara("avatar"))
		.set("pwd", pwd)
		.set("school", getPara("school"));
		Teacher oldTeacher = Teacher.dao.findById(id);
		boolean status = false;
		if (oldTeacher != null) {
			setAttr("status", 111);
		} else {
			status = teacher.save();
			if (status) {
				setAttr("status", 0);
			}
		}
		setAttr("value", teacher);
		renderJson();
	}
	
	public void teacherLogin() throws Exception{
		Map<String,Object> params = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("id"))){
			params.put("id",getPara("id"));
		}
		if(!StringUtil.isEmpty(getPara("pwd"))){
			params.put("pwd",RSASecurityHelper.toHexValue(RSASecurityHelper.encryptMD5(getPara("pwd").getBytes(Charset.forName("utf-8")))));
		}
		Teacher teacher = Teacher.dao.findByPwd(params);
		if(teacher!=null){
			setAttr("status", 0);
			setAttr("value", teacher);
			renderJson();
		}else{
			setAttr("status", 404);
			renderJson();
		}
	}
	
	public void modifyTeacherPwd() throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("id"))){
			params.put("id",getPara("id"));
		}
		if(!StringUtil.isEmpty(getPara("pwd"))){
			params.put("pwd",RSASecurityHelper.toHexValue(RSASecurityHelper.encryptMD5(getPara("pwd").getBytes(Charset.forName("utf-8")))));
		}
		Teacher teacher = Teacher.dao.findByPwd(params);
		if(teacher!=null){
			boolean status=teacher.set("pwd", RSASecurityHelper.toHexValue(RSASecurityHelper.encryptMD5(getPara("newPwd").getBytes(Charset.forName("utf-8"))))).update();
			if (status) {
				setAttr("status", 0);
			}else{
				setAttr("status", 404);
			}
			setAttr("value", teacher);
			renderJson();
		}else{
			setAttr("status", 404);
			renderJson();
		}
	}
	
	public void resetTeacherPwd() throws Exception{
		String id = getPara("id");
		Teacher teacher = new Teacher()
				.set("pwd", RSASecurityHelper.toHexValue(RSASecurityHelper.encryptMD5(getPara("pwd").getBytes(Charset.forName("utf-8")))));
		Teacher oldTeacher = Teacher.dao.findById(id);
		boolean status = false;
		if (oldTeacher != null) {
			status = teacher.set("id", id).update();
		} else {
			status = teacher.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", teacher);
		renderJson();
	}
	
	public void teacherFenPeiList(){
		int pn = 1;
		int ps = 60;
		Map<String,Object> teacher = new HashMap<String,Object>();
		Page<Teacher> teacherPage = Teacher.dao.findByPage(pn, ps, teacher);
		List<Teacher> teachreList=teacherPage.getList();
		List<Teacher> newList = new ArrayList<Teacher>();
		for(Teacher t : teachreList){
			Map<String,Object> choose = new HashMap<String,Object>();
			if(!StringUtil.isEmpty(t.get("id"))){
				choose.put("teaId",t.get("id"));
			}
			Page<Choose> choosePage = Choose.dao.findByPage(pn, ps, choose);
			if(choosePage.getList().size()<8){
				t.put("selectNum", choosePage.getList().size());
				newList.add(t);
			}
		}
		setAttr("status", 0);
		setAttr("value", newList);
		renderJson();
	}
	
	public void resetInfo(){
		String id = getPara("id");
		Teacher teacher = new Teacher();
		if(!StringUtil.isEmpty(getPara("name"))){
			teacher.set("name",getPara("name"));
		}
		if(!StringUtil.isEmpty(getPara("contact"))){
			teacher.set("contact",getPara("contact"));
		}
		if(!StringUtil.isEmpty(getPara("email"))){
			teacher.set("email",getPara("email"));
		}
		if(!StringUtil.isEmpty(getPara("QQ"))){
			teacher.set("QQ",getPara("QQ"));
		}
		if(!StringUtil.isEmpty(getPara("sex"))){
			teacher.set("sex",getPara("sex"));
		}
		if(!StringUtil.isEmpty(getPara("direction"))){
			teacher.set("direction",getPara("direction"));
		}
		if(!StringUtil.isEmpty(getPara("teaTitle"))){
			teacher.set("teaTitle",getPara("teaTitle"));
		}
		Teacher oldTeacher = Teacher.dao.findById(id);
		boolean status = false;
		if (oldTeacher != null) {
			status = teacher.set("id", id).update();
		} else {
			status = teacher.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", teacher);
		renderJson();
	}
}