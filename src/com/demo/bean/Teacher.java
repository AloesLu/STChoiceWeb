package com.demo.bean;

import java.util.List;
import java.util.Map;

import com.demo.util.StringUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Item model.

将表结构放在此，消除记忆负担
mysql> desc Item;
+-------------+--------------+-------+-----+---------+----------------+
| Field       | Type         | Null | Key | Default | Extra          |
+-------------+--------------+-------+-----+---------+----------------+
| teaId       | varchar(20)  | NO    | PRI | NULL    | auto_increment |
| pwd         | varchar(50)  | NO    |     | NULL    |                |
| avatar      | varchar(100) | YES   |     | NULL    |                |
| teaName     | varchar(50)  | YES   |     | NULL    |                |
| sex         | Int          | YES   |     | NULL    |                |
| teaTitle    | varchar(10)  | YES   |     | NULL    |                |
| direction   | varchar(10)  | YES   |     | NULL    |                |
| major       | varchar(10)  | YES   |     | NULL    |                |
| contact     | varchar(20)  | YES   |     | NULL    |                |
| email       | varchar(50)  | YES   |     | NULL    |                |
| QQ          | varchar(20)  | YES   |     | NULL    |                |
| teaPaper    | varchar(400) | YES   |     | NULL    |                |
| teaProject  | varchar(400) | YES   |     | NULL    |                |
| honour      | varchar(400) | YES   |     | NULL    |                |
| stuRequire  | varchar(400) | YES   |     | NULL    |                |
| remarks     | varchar(400) | YES   |     | NULL    |                |
| createTime  | varchar(30)  | YES   |     | NULL    |                |
+-------------+--------------+-------+-----+---------+----------------+

数据库字段名建议使用驼峰命名规则，便于与 java 代码保持一致，如字段名： userId
 */
@SuppressWarnings("serial")
public class Teacher extends Model<Teacher> {
	public static final Teacher dao = new Teacher();
	
	/**
	 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public List<Teacher> findAll() {
		return find("select * from teacher");
	}

	public Page<Teacher> findByPage(int pn, int ps, Map<String, Object> params) {
		String where = "from teacher where 1=1";
		if (!StringUtil.isEmpty(params.get("teaTitle"))) {
			where += " and teaTitle = " + params.get("teaTitle");
		}
		if (!StringUtil.isEmpty(params.get("direction"))) {
			where += " and direction = " + params.get("direction");
		}
		if (!StringUtil.isEmpty(params.get("school"))) {
			where += " and school = " + params.get("school");
		}
		return Teacher.dao.paginate(pn, ps, "select *", where);
	}
	
	public Teacher findByPwd(Map<String, Object> params) {
		String where = "select * from teacher where 1=1";
		if (!StringUtil.isEmpty(params.get("id"))) {
			where += " and id = " + params.get("id");
		}
		if (!StringUtil.isEmpty(params.get("pwd"))) {
			where += " and pwd = '" + params.get("pwd")+"'";
		}
		return findFirst(where);
	}
}