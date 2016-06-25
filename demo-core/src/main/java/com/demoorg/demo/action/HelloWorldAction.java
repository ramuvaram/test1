package com.demoorg.demo.action;

import com.demoorg.demo.model.BaseBean;
import com.demoorg.demo.model.HelloWorldBean;

public class HelloWorldAction extends BaseAction {

	@Override
	public <T extends BaseBean> T execute() {
		String name = getProperty("name");
		HelloWorldBean bean = new HelloWorldBean();
		if (name == null) {
			name = "Virtusa";
		}
		bean.setName(name);
		return (T) bean;
	}

}
