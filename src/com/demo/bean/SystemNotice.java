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
| Field       | Type         | Null  | Key | Default | Extra          |
+-------------+--------------+-------+-----+---------+----------------+
| id          | varchar(20)  | NO    | PRI | NULL    | auto_increment |
| adminId     | varchar(20)  | NO    |     | NULL    |                |
| content     | varchar(1000)| NO    |     | NULL    |                |
| createTime  | varchar(30)  | NO    |     | NULL    |                |
| major       | varchar(10)  | NO    |     | NULL    |                |
+-------------+--------------+-------+-----+---------+----------------+

数据库字段名建议使用驼峰命名规则，便于与 java 代码保持一致，如字段名： userId
 */
@SuppressWarnings("serial")
public class SystemNotice extends Model<SystemNotice> {
	public static final SystemNotice dao = new SystemNotice();
	
	/**
	 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public List<SystemNotice> findAll() {
		return find("select * from systemNotice");
	}

	public Page<SystemNotice> findByPage(int pn, int ps, Map<String, Object> params) {
		String where = "from systemNotice where 1=1";
		if (!StringUtil.isEmpty(params.get("school"))) {
			where += " and school = '" + params.get("school") + "'";
		}
		return SystemNotice.dao.paginate(pn, ps, "select *", where);
	}
}