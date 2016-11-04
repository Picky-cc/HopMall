/**
 * 
 */
package com.zufangbao.earth.web.listener;

import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



/**
 * @author Downpour
 */
public class ContextPathInitializer implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		Properties config = (Properties) applicationContext.getBean("config");
		servletContext.setAttribute("ctx", new ContextPath(config));
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	
	}
	
	/**
	 * 
	 * @author downpour
	 */
	public static class ContextPath {
		
		private Properties properties;
		
		ContextPath(Properties properties) {
			this.properties = properties;
		}
		
		/**
		 * 
		 * @return
		 */
		public String getResource() {
			return this.properties.getProperty("ctx.resource");
		}
		
		/**
		 * @return
		 */
		public String getUpload() {
			return this.properties.getProperty("ctx.upload");
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return this.properties.getProperty("ctx");
		}
		public String getProduction(){
			
			return this.properties.getProperty("ctx.production");
		}
		
	}

	
}
