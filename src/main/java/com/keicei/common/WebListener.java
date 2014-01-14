package com.keicei.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.logicalcobwebs.proxool.ProxoolFacade;

public class WebListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		ProxoolFacade.shutdown();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
	}

}
