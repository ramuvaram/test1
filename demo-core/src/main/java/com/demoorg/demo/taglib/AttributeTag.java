package com.demoorg.demo.taglib;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.scripting.jsp.util.TagUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AttributeTag class Will be invoked while passing.
 *         parameters to JSP
 */
public class AttributeTag extends TagSupport {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * name.
     */
    private String name;
    /**
     * value.
     */
    private String value;
    /**
     * logger object to set the log messages.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(AttributeTag.class);

    /**
     * @return name.
     */
    public final String getName() {
        return name;
    }

    /**
     * @param nameArg
     *            - name.
     */
    public final void setName(final String nameArg) {
        this.name = nameArg;
    }

    /**
     * @return value.
     */
    public final String getValue() {
        return value;
    }

    /**
     * @param valueArg
     *            - value.
     */
    public final void setValue(final String valueArg) {
        this.value = valueArg;
    }

    /**
     * this will be called at the end of the tag for setting the params.
     *
     * @return true/false.
     */
    public final int doEndTag() {
        final SlingHttpServletRequest slingRequest = TagUtil
                .getRequest(this.pageContext);
        LOG.debug("inside end of paramstaglib class");

        slingRequest.setAttribute(this.name, this.value);

        return 0;
    }

    /**
     * @param pageContext
     *            - pageContext. setPageContext - To set the page context.
     */
    public final void setPageContext(final PageContext pageContext) {
        super.setPageContext(pageContext);
    }

}
