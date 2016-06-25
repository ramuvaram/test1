<%--

  Hello World Component component.

  

--%><%
%><%@include file="/apps/demo/components/page/global.jsp"%>

<action:action className="com.demoorg.demo.action.HelloWorldAction" bean="helloWorld"/>
Hello ${helloWorld.name}! welcome to template site...

