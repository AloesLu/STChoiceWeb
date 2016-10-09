package com.demo.bean;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * Item model.

将表结构放在此，消除记忆负担
mysql> desc Item;
+-------------+--------------+-------+-----+---------+----------------+
| Field       | Type         | Null  | Key | Default | Extra          |
+-------------+--------------+-------+-----+---------+----------------+
| wishId      | varchar(20)  | NO    | PRI | NULL    | auto_increment |
| teaId       | varchar(20)  | NO    |     | NULL    |                |
| stuId       | varchar(20)  | NO    |     | NULL    |                |
| content     | varchar(200) | NO    |     | NULL    |                |
| wishNum     | Int          | NO    |     | NULL    |                |
+-------------+--------------+-------+-----+---------+----------------+

数据库字段名建议使用驼峰命名规则，便于与 java 代码保持一致，如字段名： userId
 */
@SuppressWarnings("serial")
public class Vote extends Model<Vote> {
	public static final Wish dao = new Wish();
	
	/**
	 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public List<Vote> findAll() {
		return find("select * from vote");
	}
}