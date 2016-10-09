package com.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.bean.Vote;
import com.demo.service.VoteInterceptor;
import com.demo.service.VoteValidator;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * BlogController
 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@Before(VoteInterceptor.class)
public class VoteController extends Controller {
	
	public void index() {
		setAttr("votes", Vote.dao.findAll());
		renderJson();

	}
	
	
	public void add() {
		Vote vote=getModel(Vote.class);
		vote.set("name", getPara("name"));
		vote.set("score", getPara("score"));
		vote.save();
		renderJson();
	}
	
	
	@Before(VoteValidator.class)
	public void save() {
		getModel(Vote.class).save();
	}
	
	public void edit() {
		setAttr("vote", Vote.dao.findById(getParaToInt()));
		renderJson();
	}
	
	@Before(VoteValidator.class)
	public void update() {
		Vote vote=getModel(Vote.class);
		vote.set("id", getPara("id"));
		vote.set("name", getPara("name"));
		vote.set("score", getPara("score"));
	
		JSONObject json = new JSONObject();
		json.put("code", vote.update());
		json.put("msg", "success");
		json.put("values", vote.getAttrValues());
		renderJson(json.toJSONString());
	}

	public void batchCrud() {
		String myRs= "{'params':" + getPara("params") + "}";
	      // 将字符串转为json对象，使用fastjson
      JSONObject obj = JSON.parseObject(myRs);
     // Object[] Votes=null;
      JSONArray Votes;
      try{
    	   Votes = obj.getJSONArray("params");
          for (int idx= 0; idx < Votes.size(); idx++) {
        	  Integer myId = Votes.getJSONObject(idx).getInteger("id");
        	  String myTitle = Votes.getJSONObject(idx).getString("title");
        	  String myDes = Votes.getJSONObject(idx).getString("des");
        	  String myStatus = Votes.getJSONObject(idx).getString("myStatus");
        	  System.out.println("id:"+myId);
        	  System.out.println("title:"+myTitle);
        	  System.out.println("Descripton:"+myDes);
        	  System.out.println("myStatus:"+myStatus);
        	  System.out.println("Record "+ idx +"----");
        	  if (myStatus.equals("D")){
        		  Db.deleteById("Vote", Votes.getJSONObject(idx).getInteger("id"));
        	  }        	  
        	  if (myStatus.equals("U")){
        		  if(myId!=null){
        			  System.out.println("进入update");
        			  Vote.dao.findById(myId).set("des", myDes).set("title", myTitle).update();
		        	  }
        		  else{
        			  System.out.println("进入New");
		        	  Record Vote = new Record().set("des", myDes).set("title", myTitle);
		        	  Db.save("vote", Vote);        			  

        		  }
        		  
        	  }

        	  
          }
      }catch(NullPointerException ne){
    	  Votes=null;
    	 // Votes=new Object[]{};
      }
		//redirect("/");
		setAttr("votes", Vote.dao.findAll());
		renderJson();
	}

	public void delete() {
		Integer id = getParaToInt();
		Vote.dao.deleteById(getParaToInt());
		redirect("/vote");
	}
}


