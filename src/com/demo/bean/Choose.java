package com.demo.bean;

import java.util.List;
import java.util.Map;

import com.demo.util.StringUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * choose model.

将表结构放在此，消除记忆负担
mysql> desc tutor_system.choose;
+-----------+--------------+------+-----+---------+----------------+
| Field     | Type         | Null | Key | Default | Extra          |
+-----------+--------------+------+-----+---------+----------------+
| id        | int(11)      | NO   | PRI | NULL    | auto_increment |
| stuId     | varchar(255) | NO   |     |         |                |
| teaId     | varchar(255) | NO   |     |         |                |
| wishNum   | int(11)      | NO   |     | 0       |                |
| stuStatus | tinyint(1)   | NO   |     | 0       |                |
| teaStatus | tinyint(1)   | NO   |     | 0       |                |
+-----------+--------------+------+-----+---------+----------------+
 */
@SuppressWarnings("serial")
public class Choose extends Model<Choose> {
	public static final Choose dao = new Choose();
	
	/**
	 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public List<Choose> findAll() {
		return find("select * from choose");
	}
	
	public Page<Choose> findByPage(int pn,int ps,Map<String,Object> params){
		String where = "from choose where 1=1";
		if(!StringUtil.isEmpty(params.get("stuId"))){
			where += " and stuId = "+ params.get("stuId");
		}
		if(!StringUtil.isEmpty(params.get("teaId"))){
			where += " and teaId = "+ params.get("teaId");
		}
		if(!StringUtil.isEmpty(params.get("stuStatus"))){
			where += " and stuStatus = "+ params.get("stuStatus");
		}
		if(!StringUtil.isEmpty(params.get("teaStatus"))){
			where += " and teaStatus = "+ params.get("teaStatus");
		}
		return Choose.dao.paginate(pn, ps, "select *", where);
	}	
	
	public Page<Choose> findByTeacherCondition(int pn,int ps,Map<String,Object> params){
		String where = "from choose c,teacher t where 1 = 1 and t.id = c.teaId";
		if(!StringUtil.isEmpty(params.get("teaTitle"))){
			where += " and t.teaTitle = "+ params.get("teaTitle");
		}
		return Choose.dao.paginate(pn, ps, "select *", where);
	}
	
	public Page<Choose> findByStudentCondition(int pn,int ps,Map<String,Object> params){
		String where = "from choose c,student s where 1 = 1 and s.id = s.stuId";
		if(!StringUtil.isEmpty(params.get("clazz"))){
			where += " and s.clazz = "+ params.get("clazz");
		}
		if(!StringUtil.isEmpty(params.get("major"))){
			where += " and s.major = "+ params.get("major");
		}
		return Choose.dao.paginate(pn, ps, "select *", where);
	}
}