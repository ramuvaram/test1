package com.demoorg.demo.taglib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * ActionTag class that extends TagSupport.
 *
 */
public class ActionTag extends TagSupport {
     /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * bean.
     */
    private String bean;

    /**
     * className.
     */
    private String className;

    /**
     * logger object to set the LOG messages.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ActionTag.class);


    /**
     * This method will be executed at the end of the tag. it will call the base
     * action class execute method.
     *
     * @return true/false
     */
    public final int doEndTag() {
        LOG.debug("class name is===" + this.className);
        try {
            final ClassLoader tccl = Thread.currentThread()
                    .getContextClassLoader();
            Class<?> cls;
            cls = Class.forName(this.className, true, tccl);
            final Class<?>[] params = new Class[1];
            params[0] = PageContext.class;
            Object obj;
            obj = cls.newInstance();
            final Object[] arglist = new Object[1];
            arglist[0] = this.pageContext;
            Method initMethod;
            initMethod = cls.getMethod("init", params);
            initMethod.invoke(obj, arglist);
            Method meth;
            meth = cls.getMethod("execute");
            Object retobj;
            retobj = meth.invoke(obj);
            pageContext.setAttribute(this.bean, retobj);
        }
        catch (ClassNotFoundException e) {
            LOG.error("ClassNotFoundException : ", e);
        }
        catch (IllegalArgumentException e) {
            LOG.error("IllegalArgumentException : ", e);
        }
        catch (IllegalAccessException e) {
            LOG.error("IllegalAccessException : ", e);
        }
        catch (InvocationTargetException e) {
            LOG.error("InvocationTargetException : ", e);
        }
        catch (SecurityException e) {
            LOG.error("SecurityException : ", e);
        }
        catch (NoSuchMethodException e) {
            LOG.error("NoSuchMethodException : ", e);
        }
        catch (InstantiationException e) {
            LOG.error("InstantiationException : ", e);
        }

        return 0;
    }

    /**
     * to set the bean.
     *
     * @param beanArg
     *            - bean.
     */
    public final void setBean(final String beanArg) {
        this.bean = beanArg;
    }

    /**
     * to get the bean.
     *
     * @return bean.
     */
    public final String getBean() {
        return bean;
    }

    /**
     * To set the class name.
     *
     * @param classNameArg
     *            - className.
     */
    public final void setClassName(final String classNameArg) {
        this.className = classNameArg;
    }

    /**
     * To get the className.
     *
     * @return className.
     */
    public final String getClassName() {
        return className;
    }

}
