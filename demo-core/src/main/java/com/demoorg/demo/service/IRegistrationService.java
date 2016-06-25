package com.demoorg.demo.service;

import com.demoorg.demo.model.RegistrationBean;

public interface IRegistrationService {

	 void submitDetails(RegistrationBean registrationBean);
	 
	 RegistrationBean getUserDetails(String emailId);
	
}
