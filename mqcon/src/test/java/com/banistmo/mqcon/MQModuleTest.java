package com.banistmo.mqcon;

import java.io.IOException;
import java.util.Properties;

import org.mockito.Mockito;
import org.springframework.jms.core.JmsTemplate;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;

public class MQModuleTest  extends AbstractModule {

	@Override
	protected void configure() {
		Properties props = new Properties();
		props.putAll(System.getenv());
		try {
			props.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			System.out.println("application.properties not found");
		}
		Names.bindProperties(binder(), props);
		   bind(MQFactory.class).to(MQFactoryImpl.class);
		   bind(JmsFactoryFactory.class).toInstance(getJmsFactory());
		   bind(JmsTemplate.class).toInstance(getJmsTemplate());
		 //  bind(JmsConnectionFactory.class).toInstance(getConn());		
	}
	private JmsConnectionFactory getConn() {
		return Mockito.mock(JmsConnectionFactory.class);
	}
	private JmsTemplate getJmsTemplate() {
		return Mockito.mock(JmsTemplate.class);
	}

	private JmsFactoryFactory getJmsFactory() {
		return Mockito.mock(JmsFactoryFactory.class);
	}
	
}