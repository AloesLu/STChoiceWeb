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
+------------+---------------+------+-----+-------------------+-------+
| Field      | Type          | Null | Key | Default           | Extra |
+------------+---------------+------+-----+-------------------+-------+
| id         | varchar(255)  | NO   | PRI |                   |       |
| name       | varchar(255)  | NO   |     |                   |       |
| pwd        | varchar(255)  | NO   |     | 123456            |       |
| sex        | tinyint(1)    | NO   |     | 0                 |       |
| avatar     | varchar(2000) | NO   |     |                   |       |
| clazz      | varchar(255)  | NO   |     |                   |       |
| school     | varchar(255)  | NO   |     |                   |       |
| major      | varchar(255)  | NO   |     |                   |       |
| grade      | varchar(255)  | NO   |     |                   |       |
| contact    | varchar(255)  | NO   |     |                   |       |
| email      | varchar(255)  | NO   |     |                   |       |
| QQ         | varchar(255)  | NO   |     |                   |       |
| rank       | int(11)       | NO   |     | 0                 |       |
| honour     | varchar(2000) | NO   |     |                   |       |
| interst    | varchar(2000) | NO   |     |                   |       |
| works      | varchar(2000) | NO   |     |                   |       |
| experience | text          | YES  |     | NULL              |       |
| remarks    | text          | YES  |     | NULL              |       |
| createTime | timestamp     | NO   |     | CURRENT_TIMESTAMP |       |
+------------+---------------+------+-----+-------------------+-------+

数据库字段名建议使用驼峰命名规则，便于与 java 代码保持一致，如字段名： userId
 */
@SuppressWarnings("serial")
public class Student extends Model<Student> {
	public static final Student dao = new Student();

	/**
	 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public List<Student> findAll() {
		return find("select * from student");
	}

	public Page<Student> findByPage(int pn, int ps, Map<String, Object> params) {
		String where = "from student where 1=1";
		if (!StringUtil.isEmpty(params.get("major"))) {
			where += " and major = " + params.get("major");
		}
		if (!StringUtil.isEmpty(params.get("grade"))) {
			where += " and grade = " + params.get("grade");
		}
		if (!StringUtil.isEmpty(params.get("school"))) {
			where += " and school = " + params.get("school");
		}
		if (!StringUtil.isEmpty(params.get("clazz"))) {
			where += " and clazz = " + params.get("clazz");
		}
		return Student.dao.paginate(pn, ps, "select *", where);
	}
	
	public Student findByPwd(Map<String, Object> params) {
		String where = "select * from student where 1=1";
		if (!StringUtil.isEmpty(params.get("id"))) {
			where += " and id = " + params.get("id");
		}
		if (!StringUtil.isEmpty(params.get("pwd"))) {
			where += " and pwd = '" + params.get("pwd")+"'";
		}
		return findFirst(where);
	}
}
