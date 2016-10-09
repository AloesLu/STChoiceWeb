package com.demo.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.demo.bean.Admin;
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
public class AdminController extends Controller {
	
	public void index() {
		setAttr("admins", Admin.dao.findAll());
		renderJson();

	}
	public void adminDetail(){
		Integer status = 0;
		Admin admin = Admin.dao.findById(getPara("id"));
		if(StringUtil.isEmpty(admin)){
			status = 404;
		}
		setAttr("status", status);
		setAttr("value", admin);
		renderJson();
	}
	
	public void adminList(){
		int pn = 1;
		if(!StringUtil.isEmpty(getPara("pageNum"))){
			pn = getParaToInt("pageNum");
		}
		int ps = 10;
		if(!StringUtil.isEmpty(getPara("pageSize"))){
			ps = getParaToInt("pageSize");
		}
		Map<String,Object> admin = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("school"))){
			admin.put("school",getPara("school"));
		}
		Page<Admin> adminPage = Admin.dao.findByPage(pn, ps, admin);
		setAttr("status", 0);
		setAttr("value", adminPage.getList());
		renderJson();
	}
	
	public void addAdmin() throws Exception{
		String id = getPara("id");
		String pwd = RSASecurityHelper.toHexValue(RSASecurityHelper.encryptMD5(getPara("pwd").getBytes(Charset.forName("utf-8"))));;
		Admin admin = new Admin()
		.set("id", id)
		.set("avatar", getPara("avatar"))
		.set("pwd", pwd)
		.set("school", getPara("school"));
		Admin oldAdmin = Admin.dao.findById(id);
		boolean status = false;
		if (oldAdmin != null) {
			setAttr("status", 111);
		} else {
			status = admin.save();
			if (status) {
				setAttr("status", 0);
			}
		}
		setAttr("value", admin);
		renderJson();
	}
	
	
	public void adminLogin() throws Exception{
		Map<String,Object> params = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("id"))){
			params.put("id",getPara("id"));
		}
		if(!StringUtil.isEmpty(getPara("pwd"))){
			params.put("pwd",RSASecurityHelper.toHexValue(RSASecurityHelper.encryptMD5(getPara("pwd").getBytes(Charset.forName("utf-8")))));
		}
		Admin admin = Admin.dao.findByPwd(params);
		if(admin!=null){
			setAttr("status", 0);
			setAttr("value", admin);
			renderJson();
		}else{
			setAttr("status", 404);
			renderJson();
		}
	}
	
	public void modifyAdminPwd() throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		if(!StringUtil.isEmpty(getPara("id"))){
			params.put("id",getPara("id"));
		}
		if(!StringUtil.isEmpty(getPara("pwd"))){
			params.put("pwd",RSASecurityHelper.toHexValue(RSASecurityHelper.encryptMD5(getPara("pwd").getBytes(Charset.forName("utf-8")))));
		}
		Admin admin = Admin.dao.findByPwd(params);
		if(admin!=null){
			boolean status=admin.set("pwd", RSASecurityHelper.toHexValue(RSASecurityHelper.encryptMD5(getPara("newPwd").getBytes(Charset.forName("utf-8"))))).update();
			if (status) {
				setAttr("status", 0);
			}else{
				setAttr("status", 404);
			}
			setAttr("value", admin);
			renderJson();
		}else{
			setAttr("status", 404);
			renderJson();
		}
	}
	
	public void resetAdminPwd() throws Exception{
		String id = getPara("id");
		Admin admin = new Admin()
				.set("pwd", RSASecurityHelper.toHexValue(RSASecurityHelper.encryptMD5(getPara("pwd").getBytes(Charset.forName("utf-8")))));
		Admin oldAdmin = Admin.dao.findById(id);
		boolean status = false;
		if (oldAdmin != null) {
			status = admin.set("id", id).update();
		} else {
			status = admin.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", admin);
		renderJson();
	}
	
	public void resetInfo(){
		String id = getPara("id");
		Admin admin = new Admin();
		if(!StringUtil.isEmpty(getPara("name"))){
			admin.set("name",getPara("name"));
		}
		if(!StringUtil.isEmpty(getPara("contact"))){
			admin.set("contact",getPara("contact"));
		}
		if(!StringUtil.isEmpty(getPara("sex"))){
			admin.set("sex",getPara("sex"));
		}
		Admin oldAdmin = Admin.dao.findById(id);
		boolean status = false;
		if (oldAdmin!= null) {
			status = admin.set("id", id).update();
		} else {
			status = admin.save();
		}
		if (status) {
			setAttr("status", 0);
		}
		setAttr("value", admin);
		renderJson();
	}

}
