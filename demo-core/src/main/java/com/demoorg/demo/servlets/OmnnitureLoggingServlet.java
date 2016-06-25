package com.demoorg.demo.servlets;

import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
 
@SlingServlet(
    paths={"/services/unicom/v1"}
)

public class OmnnitureLoggingServlet extends SlingAllMethodsServlet
{
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException
    {
        //Do something fun here
    	doPost(request, response);
    }
 
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException
    {
    	 //Do something fun hereStrre
    	String name =(String) request.getParameter("uname");
    	String firstname =(String) request.getParameter("firstname");
        PrintWriter out = response.getWriter();
        out.println(firstname+"Hello world"+name);
    }
}