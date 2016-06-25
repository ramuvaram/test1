package com.demoorg.demo.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.ServerException;
import java.util.Dictionary;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;

@SlingServlet(
paths={"/bin/customservlet/hashim"}
)
public class MyServlet extends SlingAllMethodsServlet
{
	 BufferedReader reader = null;
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException
	{
	//Write your custom code herer
		doPost(request,response);
	}
	 
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException
	{
	//Write your custom code here
		System.out.println("posttttttttttttttttttt");
		URL url = null;
		 url = new URL("http://ip.jsontest.com/");
	      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	      // read the output from the server
	      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	      System.out.println(reader);
	      StringBuilder responseStrBuilder = new StringBuilder();

	      String inputStr;
	      while ((inputStr = reader.readLine()) != null)
	          responseStrBuilder.append(inputStr);

	      try {
			JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
			System.out.println(jsonObject.toString());
			response.getWriter().write(jsonObject.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
