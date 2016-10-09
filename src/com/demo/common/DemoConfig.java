 package com.demo.common;

import com.demo.bean.Admin;
import com.demo.bean.Choose;
import com.demo.bean.SignUp;
import com.demo.bean.Student;
import com.demo.bean.SystemNotice;
import com.demo.bean.Tag;
import com.demo.bean.Task;
import com.demo.bean.Teacher;
import com.demo.bean.Time;
import com.demo.bean.Wish;
import com.demo.controller.AdminController;
import com.demo.controller.ChooseController;
import com.demo.controller.SignUpController;
import com.demo.controller.StudentController;
import com.demo.controller.SystemNoticeController;
import com.demo.controller.TagController;
import com.demo.controller.TaskController;
import com.demo.controller.TeacherController;
import com.demo.controller.TimeController;
import com.demo.controller.VoteController;
import com.demo.controller.WishController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

/**
 * API引导式配置
 */
public class DemoConfig extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		loadPropertyFile("a_little_config.txt");				// 加载少量必要配置，随后可用getProperty(...)获取值
		me.setDevMode(getPropertyToBoolean("devMode", false));
		me.setViewType(ViewType.JSP); 							// 设置视图类型为Jsp，否则默认为FreeMarker
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", CommonController.class);
		me.add("/admin", AdminController.class);
		me.add("/choose", ChooseController.class);
		me.add("/signUp", SignUpController.class);
		me.add("/student", StudentController.class);
		me.add("/systemNotice", SystemNoticeController.class);
		me.add("/tag", TagController.class);
		me.add("/task", TaskController.class);
		me.add("/teacher", TeacherController.class);
		me.add("/time", TimeController.class);
		me.add("/wish", WishController.class);
		me.add("/vote", VoteController.class);
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
		me.add(c3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		arp.addMapping("admin", Admin.class);	// 映射Admin 表到 Admin模型
		arp.addMapping("choose", Choose.class);
		arp.addMapping("signUp", SignUp.class);
		arp.addMapping("student", Student.class);
		arp.addMapping("systemNotice", SystemNotice.class);
		arp.addMapping("tag", Tag.class);
		arp.addMapping("task", Task.class);
		arp.addMapping("teacher", Teacher.class);
		arp.addMapping("time", Time.class);
		arp.addMapping("wish", Wish.class);
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 8181, "/", 5);
	}
}
