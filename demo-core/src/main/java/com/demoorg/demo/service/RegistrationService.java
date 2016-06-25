package com.demoorg.demo.service;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demoorg.demo.model.RegistrationBean;

@Component(label = "Registration Service", description = "Facade which gets list of values from the backend service", immediate = true, metatype = false)
@Properties({
		@Property(name = "service.pid", value = "com.demoorg.demo.service.RegistrationService", propertyPrivate = false),
		@Property(name = "service.description", value = "Demo Registration Service", propertyPrivate = false),
		@Property(name = "service.vendor", value = "Demo Organisation", propertyPrivate = false) })
@Service(value = IRegistrationService.class)
public class RegistrationService extends BaseService implements IRegistrationService {
Logger log=LoggerFactory.getLogger(RegistrationService.class);
	@Override
	public void submitDetails(RegistrationBean registrationBean) {
		Session session = null;
		try {
			session = getSession();
			String pagePath = "/tmp";

			Node node = session.getNode(pagePath);
			Node node1;
			if (node.hasNode("demo")) {
				node1 = node.getNode("demo");
			} else {
				node.addNode("demo", "nt:unstructured");
				node1 = node.getNode("demo");
			}
			if (node1.hasNode("users")) {
				node = node1.getNode("users");
			} else {
				node1.addNode("users", "nt:unstructured");
				node = node1.getNode("users");
			}
			node.addNode(registrationBean.getEmail());
			Node emailNode = node.getNode(registrationBean.getEmail());
			emailNode.setProperty("firstName", registrationBean.getFirstName());
			emailNode.setProperty("lastName", registrationBean.getLastName());
			emailNode.setProperty("phone", registrationBean.getPhone());
			emailNode.setProperty("password", registrationBean.getPassword());
			session.save();
		} catch (RepositoryException e) {
			log.error("Repository Exception occared");
		}
		
	}

	@Override
	public RegistrationBean getUserDetails(String emailId) {
		Session session = null;
		String firstName = null;
		String lastName = null;
		String password = null;
		try {
			session = getSession();
			String pagePath = "/tmp/demo/users";
			if (session.getNode(pagePath) != null) {
				Node node = session.getNode(pagePath);

				if (node.hasNode(emailId)) {
					node = node.getNode(emailId);
					firstName = node.getProperty("firstName").getString();
					lastName = node.getProperty("lastName").getString();
					password = node.getProperty("password").getString();
					
				}
			}
		} catch (RepositoryException e) {
			log.error("Repository Exception occared");
		}
		return null;
	}

}
