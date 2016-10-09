package com.demo.service;

import com.demo.bean.Vote;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * BlogValidator.
 */
public class VoteValidator extends Validator {
	
	protected void validate(Controller controller) {
//		validateRequiredString("vote.score", "titleMsg", "请输入Vote标题!");
//		validateRequiredString("vote.name", "desMsg", "请输入Vote内容!");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Vote.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/vote/save"))
			controller.renderJson("message", "name不对");
		else if (actionKey.equals("/vote/update"))
			controller.renderJson("message", "name不对");
	}
}
