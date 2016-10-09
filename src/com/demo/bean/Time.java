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
| startTime   | varchar(200) | NO    |     | NULL    |                |
| endTime     | varchar(200) | NO    |     | NULL    |                |
| timeStatus  | Int          | NO    |     | NULL    |                |
| timeType    | Int          | NO    |     | NULL    |                |
+-------------+--------------+-------+-----+---------+----------------+

数据库字段名建议使用驼峰命名规则，便于与 java 代码保持一致，如字段名： userId
 */
@SuppressWarnings("serial")
public class Time extends Model<Time> {
	public static final Time dao = new Time();
	
	/**
	 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public List<Time> findAll() {
		return find("select * from time");
	}
	
	public Page<Time> findByPage(int pn,int ps,Map<String,Object> params){
		String where = "from time where 1=1";
		if(!StringUtil.isEmpty(params.get("timeStatus"))){
			where += " and timeStatus = "+ params.get("timeStatus");
		}
		return Time.dao.paginate(pn, ps, "select *", where);
	}
}