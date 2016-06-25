package com.demoorg.demo.action;

import org.apache.sling.api.SlingHttpServletRequest;

import com.demoorg.demo.model.BaseBean;
import com.demoorg.demo.model.HelloWorldBean;
import com.demoorg.demo.model.RegistrationBean;
import com.demoorg.demo.service.IRegistrationService;

public class RegisterAction extends BaseAction {
	
	
	/**
	 * @param args
	 */
	static final String EMAIL = "email";
	static final String FIRSTNAME = "firstName";
	static final String LASTNAME = "lastName";
	static final String PASSWORD = "password";
	static final String PHONE = "phone";


	@Override
	public <T extends BaseBean> T execute() {
		SlingHttpServletRequest request=getSlingRequest();
		String firstName=request.getParameter("firstName");
		String middleName=request.getParameter("middleName");
		String lastName=request.getParameter("lastName");
		String email=request.getParameter("email");
		String phone= request.getParameter("phone");
		String password= request.getParameter("password");
		RegistrationBean registrationBean=new RegistrationBean();
		registrationBean.setFirstName(firstName);
		registrationBean.setLastName(lastName);
		registrationBean.setEmail(email);
		registrationBean.setPassword(password);
		registrationBean.setPhone(phone);
		
		IRegistrationService iRegistrationService = getSling().getService(IRegistrationService.class);
		
		iRegistrationService.submitDetails(registrationBean);
		
		
		return null;
	}

}
