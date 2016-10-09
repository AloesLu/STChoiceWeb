package com.demo.common;

import com.jfinal.core.Controller;

/**
 * CommonController
 */
public class CommonController extends Controller {
	
	public void index() {
		render("/vote");
		//render("/common/index.jsp");
	}
}
