package com.demoorg.demo.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.http.Cookie;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.scripting.jsp.util.JspSlingHttpServletResponseWrapper;
import org.apache.sling.scripting.jsp.util.TagUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.LanguageManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.WCMMode;
import com.day.cq.wcm.api.designer.Style;
import com.demoorg.demo.model.BaseBean;

/**
 * All actions should extend this abstract base action. This action class is
 * used for separating business logic from JSP file.
 * 
 */
public abstract class BaseAction {
	/**
	 * logger object for handling log messages.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(BaseAction.class);
	/**
	 * To hold the page context.
	 */
	private transient PageContext pageContext;
	/**
	 * To hold sling request.
	 */
	private transient SlingHttpServletRequest slingRequest;
	/**
	 * To hold the sling response.
	 */
	private transient SlingHttpServletResponse slingResponse;

	/**
	 * Returns the page context.
	 * 
	 * @return PageContext
	 */
	public final PageContext getPageContext() {
		return this.pageContext;
	}

	/**
	 * @param pageContextArg
	 *            - pageContext.
	 */
	public final void init(final PageContext pageContextArg) {
		this.pageContext = pageContextArg;
		slingRequest = TagUtil.getRequest(this.pageContext);
		slingResponse = new JspSlingHttpServletResponseWrapper(this.pageContext);
	}

	/**
	 * Write the business logic in execute method.
	 * 
	 * @return - Derived type of AbstractBaseModel.
	 * @param <T>
	 *            - generic Type.
	 */
	public abstract <T extends BaseBean> T execute();

	/**
	 * This method will return component property values.
	 * 
	 * @param propertyName
	 *            - The property name of the component.
	 * @return - Property value.
	 */
	public final String getProperty(final String propertyName) {
		return getProperty(propertyName, null);
	}

	/**
	 * Returns SURFERINFO from SessionPersistence cookie.
	 * 
	 * @return Map containing key/values from SURFERINFO.
	 */
	public Map<String, String> getSurferInfoCookieValues() {
		Map<String, String> surferInfoMap = new HashMap<String, String>();
		String PERSISTENT_COOKIE = "SessionPersistence";
		SlingHttpServletRequest request = getSlingRequest();

		Cookie cookie = request.getCookie(PERSISTENT_COOKIE);
		if (cookie == null) {
			LOG.debug(PERSISTENT_COOKIE + " not found.");
			return null;
		}
		String cookieValue = cookie.getValue();
		try {
			cookieValue = URLDecoder.decode(cookieValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOG.error("error in getCookieValues", e);
		}
		if (StringUtils.isEmpty(cookieValue)) {
			return null;
		}
		String surferInfoStr = "SURFERINFO:=";
		int indexOfSurferInfo = cookieValue.indexOf(surferInfoStr);
		if (indexOfSurferInfo > 0) {
			indexOfSurferInfo = indexOfSurferInfo + surferInfoStr.length();
		}
		String surferInfo = cookieValue.substring(indexOfSurferInfo);
		int indexOfPipe = surferInfo.indexOf("|");
		if (indexOfPipe > 0) {
			surferInfo = surferInfo.substring(0, indexOfPipe);
		}

		String[] surferInfoSplit = surferInfo.split(",");
		if (surferInfoSplit.length > 0) {
			for (int splitIndex = 0; splitIndex < surferInfoSplit.length; splitIndex++) {
				String[] surferInfoPair = surferInfoSplit[splitIndex]
						.split("=");
				if (surferInfoPair.length == 2) {
					String key = surferInfoPair[0];
					String value = surferInfoPair[1];
					surferInfoMap.put(key, value);

				}
			}
		}
		return surferInfoMap;
	}

	/**
	 * This method will return component property values.
	 * 
	 * @param propertyName
	 *            - The property name of the component.
	 * @param mockValues
	 *            - A map containing mock property values.
	 * @return - Property value.
	 */
	public final String getProperty(final String propertyName,
			Map<String, String> mockValues) {
		String propertyValue;

		try {
			Locale locale = getLocale(this.getSlingRequest());
			final Resource resource = slingRequest.getResource();
			propertyValue = ((String) ResourceUtil.getValueMap(resource).get(
					propertyName, String.class));
			boolean isEditMode = false;
			WCMMode mode = WCMMode.fromRequest(slingRequest);
			if (mode.compareTo(WCMMode.EDIT) == 0) {
				isEditMode = true;
			}
			return propertyValue;
		} catch (Exception e) {
			LOG.error("Error in BaseAction ", e);
		}
		return null;
	}

	/**
	 * Get the current resource object.
	 * 
	 * @return Resource object.
	 */
	public final Resource getCurrentResource() {
		return slingRequest.getResource();
	}

	/**
	 * to get the sling request.
	 * 
	 * @return slingRequest
	 */
	public final SlingHttpServletRequest getSlingRequest() {
		return slingRequest;
	}

	/**
	 * to get the sling response.
	 * 
	 * @return slingResponse.
	 */
	public final SlingHttpServletResponse getSlingResponse() {
		return slingResponse;
	}

	/***
	 * to get the current page.
	 * 
	 * @return currentPage
	 */
	public final Page getCurrentPage() {
		return (Page) pageContext.getAttribute("currentPage");

	}

	/**
	 * To get the currentStyle.
	 * 
	 * @return currentStyle
	 */
	public final Style getCurrentStyle() {
		return (Style) pageContext.getAttribute("currentStyle");

	}

	/**
	 * To get the currentNode.
	 * 
	 * @return currentNode
	 */
	public final Node getCurrentNode() {
		return (Node) pageContext.getAttribute("currentNode");

	}

	/**
	 * to get the propertyValues.
	 * 
	 * @param propertyName
	 *            - propertyName.
	 * @return propertyValues.
	 */
	public final String[] getPropertyValues(final String propertyName) {
		final Resource resource = slingRequest.getResource();
		final String[] propertyValues = ((String[]) ResourceUtil.getValueMap(
				resource).get(propertyName, String[].class));
		return propertyValues;
	}

	/**
	 * Returns sling script helper class.
	 * 
	 * @return SlingScriptHelper.
	 */
	public final SlingScriptHelper getSling() {
		return (SlingScriptHelper) getPageContext().getAttribute("sling");
	}

	/**
	 * Gets the locale.
	 * 
	 * @param request
	 *            the request
	 * @return the locale
	 */
	private Locale getLocale(SlingHttpServletRequest request) {
		Locale locale = getPageLocale(request.getResource());
		;

		if (locale != null) {
			return locale;
		}
		return request.getLocale();

	}

	/**
	 * Gets the page locale.
	 * 
	 * @param resource
	 *            the resource
	 * @return the page locale
	 */
	private Locale getPageLocale(Resource resource) {
		LanguageManager langMgr = (LanguageManager) getSling().getService(
				LanguageManager.class);
		if (langMgr == null) {
			return null;
		}
		return langMgr.getLanguage(resource);
	}

	/**
	 * Sanitizes an URl, if url contains jcr:content it is replaced with
	 * _jcr_content.
	 * 
	 * @param url
	 *            - The url to sanitize.
	 * @return - Sanitized url.
	 */
	public String sanitizeUrl(String url) {
		url = url.replaceAll("jcr:content", "_jcr_content");
		return url;
	}

	public String getPropertyValue(final Node node, final String property)
			throws RepositoryException {
		ValueMap valMap = getSlingRequest().getResourceResolver()
				.getResource(node.getPath()).adaptTo(ValueMap.class);
		return (String) valMap.get(property);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getService(Class clazz) {
		return getSling().getService(clazz);
	}

}
