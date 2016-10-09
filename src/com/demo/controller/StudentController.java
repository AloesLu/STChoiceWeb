package com.demo.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.demo.bean.Admin;
import com.demo.bean.Student;
import com.demo.util.RSASecurityHelper;
import com.demo.util.StringUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;


/**
 * BlogController
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class StudentController extends Controller {
	
	public void index() {
		setAttr("students", Student.dao.findAll());
		renderJson();

	}
	
	public void studentList(){
		int pn = 1;
		if(!StringUtil.isEmpty(getPara("pageNum"))){
			pn = getParaToInt("pageNum");
		}
		int ps = 10;
		if(!StringUtil.isEmpty(getPara("pageSize"))){
			ps = getParaToInt("pageSize");
		}
		Map<String,Object> student = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("major"))){
			student.put("major",getPara("major"));
		}
		if(!StringUtil.isEmpty(getPara("school"))){
			student.put("school",getPara("school"));
		}
		if(!StringUtil.isEmpty(getPara("grade"))){
			student.put("grade",getPara("grade"));
		}
		if(!StringUtil.isEmpty(getPara("clazz"))){
			student.put("clazz",getPara("clazz"));
		}
		Page<Student> studentPage = Student.dao.findByPage(pn, ps, student);
		setAttr("status", 0);
		setAttr("value", studentPage.getList());
		renderJson();
	}
	
	public void studentDetail(){
		Integer status = 0;
		Student student = Student.dao.findById(getPara("id"));
		if(StringUtil.isEmpty(student)){
			status = 404;
		}
		setAttr("status", status);
		setAttr("value", student);
		renderJson();
	}
	
	public void saveStudent() {
		String id = getPara("id");
		Student student = new Student()
				.set("avatar", getPara("avatar"))
				.set("name", getPara("name"))
				.set("clazz", getPara("clazz"))
				.set("major", getPara("major"))
				.set("grade", getPara("grade"))
				.set("contact", getPara("contact"))
				.set("email", getPara("email"))
				.set("QQ", getPara("QQ"))
				.set("honour", getPara("honour"))
				.set("interst", getPara("interst"))
				.set("works", getPara("works"))
				.set("experience", getPara("experience"))
				.set("remarks", getPara("remarks"))				
				.set("sex", getParaToInt("sex"))
				.set("rank", getParaToInt("rank"));
		Student oldStudent = Student.dao.findById(id);
		boolean status = false;
		if (oldStudent != null) {
			status = student.set("id", id).update();
		} else {
			status = student.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", student);
		renderJson();
	}
	
	public void resetInfo(){
		String id = getPara("id");
		Student student = new Student();
		if(!StringUtil.isEmpty(getPara("name"))){
			student.set("name",getPara("name"));
		}
		if(!StringUtil.isEmpty(getPara("clazz"))){
			student.set("clazz",getPara("clazz"));
		}
		if(!StringUtil.isEmpty(getPara("major"))){
			student.set("major",getPara("major"));
		}
		if(!StringUtil.isEmpty(getPara("contact"))){
			student.set("contact",getPara("contact"));
		}
		if(!StringUtil.isEmpty(getPara("email"))){
			student.set("email",getPara("email"));
		}
		if(!StringUtil.isEmpty(getPara("QQ"))){
			student.set("QQ",getPara("QQ"));
		}
		if(!StringUtil.isEmpty(getPara("sex"))){
			student.set("sex",getPara("sex"));
		}
		Student oldStudent = Student.dao.findById(id);
		boolean status = false;
		if (oldStudent != null) {
			status = student.set("id", id).update();
		} else {
			status = student.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", student);
		renderJson();
	}
	
	public void addStudent() throws Exception{
		String id = getPara("id");
		String pwd = RSASecurityHelper.toHexValue(RSASecurityHelper.encryptMD5(getPara("pwd").getBytes(Charset.forName("utf-8"))));;
		Student student = new Student()
		.set("id", id)
		.set("name", getPara("name"))
		.set("avatar", getPara("avatar"))
		.set("school", getPara("school"))
		.set("pwd", pwd);
		Student oldStudent = Student.dao.findById(id);
		boolean status = false;
		if (oldStudent != null) {
			setAttr("status", 111);
		} else {
			status = student.save();
			if (status) {
				setAttr("status", 0);
			}
		}
		setAttr("value", student);
		renderJson();
	}
	
	public void studentLogin() throws Exception{
		Map<String,Object> params = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("id"))){
			params.put("id",getPara("id"));
		}
		if(!StringUtil.isEmpty(getPara("pwd"))){
			params.put("pwd",RSASecurityHelper.toHexValue(RSASecurityHelper.encryptMD5(getPara("pwd").getBytes(Charset.forName("utf-8")))));
		}
		Student student = Student.dao.findByPwd(params);
		if(student!=null){
			setAttr("status", 0);
			setAttr("value", student);
			renderJson();
		}else{
			setAttr("status", 404);
			renderJson();
		}
	}
	
	public void modifyStudentPwd() throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("id"))){
			params.put("id",getPara("id"));
		}
		if(!StringUtil.isEmpty(getPara("pwd"))){
			params.put("pwd",RSASecurityHelper.toHexValue(RSASecurityHelper.encryptMD5(getPara("pwd").getBytes(Charset.forName("utf-8")))));
		}
		Student student = Student.dao.findByPwd(params);
		if(student!=null){
			boolean status=student.set("pwd", RSASecurityHelper.toHexValue(RSASecurityHelper.encryptMD5(getPara("newPwd").getBytes(Charset.forName("utf-8"))))).update();
			if (status) {
				setAttr("status", 0);
			}else{
				setAttr("status", 404);
			}
			setAttr("value", student);
			renderJson();
		}else{
			setAttr("status", 404);
			renderJson();
		}
	}
	
	public void resetStudentPwd() throws Exception{
		String id = getPara("id");
		Student student = new Student()
				.set("pwd", RSASecurityHelper.toHexValue(RSASecurityHelper.encryptMD5(getPara("pwd").getBytes(Charset.forName("utf-8")))));
		Student oldStudent = Student.dao.findById(id);
		boolean status = false;
		if (oldStudent != null) {
			status = student.set("id", id).update();
		} else {
			status = student.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", student);
		renderJson();
	}
}