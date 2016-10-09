package com.demo.bean;

import java.util.List;
import java.util.Map;

import com.demo.util.StringUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * admin model.

将表结构放在此，消除记忆负担
mysql> desc tutor_system.admin;
+------------+---------------+------+-----+-------------------+----------------+
| Field      | Type          | Null | Key | Default           | Extra          |
+------------+---------------+------+-----+-------------------+----------------+
| id         | int(11)       | NO   | PRI | NULL              | auto_increment |
| name       | varchar(255)  | NO   |     |                   |                |
| pwd        | varchar(255)  | NO   |     | NULL              |                |
| sex        | tinyint(1)    | NO   |     | 0                 |                |
| avatar     | varchar(2000) | NO   |     |                   |                |
| contact    | varchar(255)  | NO   |     |                   |                |
| major      | varchar(255)  | NO   |     |                   |                |
| createTime | timestamp     | NO   |     | CURRENT_TIMESTAMP |                |
| role       | tinyint(1)    | NO   |     | 0                 |                |
+------------+---------------+------+-----+-------------------+----------------+
 */
@SuppressWarnings("serial")
public class Admin extends Model<Admin> {
	public static final Admin dao = new Admin();
	
	/**
	 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public List<Admin> findAll() {
		return find("select * from admin");
	}
	
	public Page<Admin> findByPage(int pn, int ps, Map<String, Object> params) {
		String where = "from admin where 1=1";
		if (!StringUtil.isEmpty(params.get("school"))) {
			where += " and school = " + params.get("school");
		}
		return Admin.dao.paginate(pn, ps, "select *", where);
	}
	
	public Admin findByPwd(Map<String, Object> params) {
		String where = "select * from admin where 1=1";
		if (!StringUtil.isEmpty(params.get("id"))) {
			where += " and id = '" + params.get("id")+"'";
		}
		if (!StringUtil.isEmpty(params.get("pwd"))) {
			where += " and pwd = '" + params.get("pwd")+"'";
		}
		return findFirst(where);
	}
}