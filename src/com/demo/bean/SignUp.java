package com.demo.bean;

import java.util.List;
import java.util.Map;

import com.demo.util.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * signUp model.
 * 
 * 将表结构放在此，消除记忆负担 mysql> desc tutor_system.signUp;
 * +------------+--------------+--
 * ----+-----+-------------------+----------------+ | Field | Type | Null | Key
 * | Default | Extra |
 * +------------+--------------+------+-----+----------------
 * ---+----------------+ | id | int(11) | NO | PRI | NULL | auto_increment | |
 * tasId | int(11) | NO | | NULL | | | stuId | varchar(255) | NO | | NULL | | |
 * createTime | timestamp | NO | | CURRENT_TIMESTAMP | | | flag | tinyint(1) |
 * NO | | 0 | | | type | tinyint(1) | NO | | 0 | | | isFinish | tinyint(1) | NO
 * | | 0 | |
 * +------------+--------------+------+-----+-------------------+------
 * ----------+
 * 
 * 
 * 数据库字段名建议使用驼峰命名规则，便于与 java 代码保持一致，如字段名： userId
 */
@SuppressWarnings("serial")
public class SignUp extends Model<SignUp> {
	public static final SignUp dao = new SignUp();

	/**
	 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public List<SignUp> findAll() {
		return find("select * from signUp");
	}

	public Page<SignUp> findByPage(int pn, int ps, Map<String, Object> params) {
		String where = "from signUp where 1=1";
		if (!StringUtil.isEmpty(params.get("stuId"))) {
			where += " and stuId = " + params.get("stuId");
		}
		
		if (!StringUtil.isEmpty(params.get("tasId"))) {
			where += " and tasId = " + params.get("tasId");
		}
		
		if (!StringUtil.isEmpty(params.get("type"))) {
			where += " and type = " + params.get("type");
		}
		if (!StringUtil.isEmpty(params.get("flag"))) {
			where += " and flag = " + params.get("flag");
		}
		if (!StringUtil.isEmpty(params.get("isFinish"))) {
			where += " and isFinish = " + params.get("isFinish");
		}
		return SignUp.dao.paginate(pn, ps, "select *", where);

	}

	public Long count(Map<String, Object> params) {
		String sql = "select count(*) from signUp where 1 = 1";
		if (!StringUtil.isEmpty(params.get("tasId"))) {
			sql += " and tasId = " + params.get("tasId");
		}
		if (!StringUtil.isEmpty(params.get("stuId"))) {
			sql += " and stuId = " + params.get("stuId");
		}
		if (!StringUtil.isEmpty(params.get("type"))) {
			sql += " and type = " + params.get("type");
		}
		if (!StringUtil.isEmpty(params.get("flag"))) {
			sql += " and flag = " + params.get("flag");
		}
		if (!StringUtil.isEmpty(params.get("isFinish"))) {
			sql += " and isFinish = " + params.get("isFinish");
		}

		return Db.queryLong(sql);

	}

}
