<%@include file="/libs/foundation/global.jsp"%>

<%@page session="false" import="com.day.cq.commons.Doctype,
    com.day.cq.commons.inherit.InheritanceValueMap,
    com.day.cq.wcm.api.components.DropTarget,
    com.day.cq.wcm.api.WCMMode,
    com.day.cq.wcm.foundation.Image,
    com.day.cq.wcm.foundation.List,
    org.apache.commons.lang3.StringEscapeUtils,
    org.apache.sling.api.resource.ResourceResolver,
    org.apache.sling.api.SlingHttpServletRequest,
    com.day.text.Text" %><%
%>
<%@taglib prefix="action" uri="http://com.demoorg.demo/taglibs/action/1.0" %><%
%><%@taglib prefix="func" uri="http://com.demoorg.demo/taglibs/func/1.0" %><%
%><cq:defineObjects />