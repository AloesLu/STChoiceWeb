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
| id          | int(11)      | NO    | PRI | NULL    | auto_increment |
| teaId       | varchar(20)  | NO    |     | NULL    |                |
| stuId       | varchar(20)  | NO    |     | NULL    |                |
| content     | varchar(200) | NO    |     | NULL    |                |
| wishNum     | Int          | NO    |     | NULL    |                |
+-------------+--------------+-------+-----+---------+----------------+

数据库字段名建议使用驼峰命名规则，便于与 java 代码保持一致，如字段名： userId
 */
@SuppressWarnings("serial")
public class Wish extends Model<Wish> {
	public static final Wish dao = new Wish();
	
	/**
	 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public List<Wish> findAll() {
		return find("select * from wish");
	}	

	public Page<Wish> findByPage(int pn, int ps, Map<String, Object> params) {
		String where = "from wish where 1=1";
		if (!StringUtil.isEmpty(params.get("stuId"))) {
			where += " and stuId = " + params.get("stuId");
		}
		if (!StringUtil.isEmpty(params.get("teaId"))) {
			where += " and teaId = " + params.get("teaId");
		}
		if (!StringUtil.isEmpty(params.get("wishNum"))) {
			where += " and wishNum = " + params.get("wishNum");
		}
		return Wish.dao.paginate(pn, ps, "select *", where);
	}
}