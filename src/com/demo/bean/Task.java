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
| id          | varchar(20)  | NO    | PRI | NULL    | auto_increment |
| title       | varchar(100) | NO    |     | NULL    |                |
| teaId       | varchar(20)  | NO    |     | NULL    |                |
| createTime  | varchar(20)  | NO    |     | NULL    |                |
| endTime     | varchar(20)  | NO    |     | NULL    |                |
| major       | varchar(10)  | YES   |     | NULL    |                |
| type        | Int          | YES   |     | NULL    |                |
| content     | varchar(1000)| YES   |     | NULL    |                |
+-------------+--------------+-------+-----+---------+----------------+

数据库字段名建议使用驼峰命名规则，便于与 java 代码保持一致，如字段名： userId
 */
@SuppressWarnings("serial")
public class Task extends Model<Task> {
	public static final Task dao = new Task();
	
	/**
	 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public List<Task> findAll() {
		return find("select * from task");
	}

	public Page<Task> findByPage(int pn, int ps, Map<String, Object> params) {
		String where = "from task where 1=1";
		if (!StringUtil.isEmpty(params.get("stuId"))) {
			where += " and stuId = " + params.get("stuId");
		}
		if (!StringUtil.isEmpty(params.get("type"))) {
			where += " and type = " + params.get("type");
		}
		if (!StringUtil.isEmpty(params.get("flag"))) {
			where += " and flag = '" + params.get("flag") +"'";
		}
		if (!StringUtil.isEmpty(params.get("school"))) {
			where += " and school = '" + params.get("school") + "'";
		}
		return Task.dao.paginate(pn, ps, "select *", where);
	}
}
