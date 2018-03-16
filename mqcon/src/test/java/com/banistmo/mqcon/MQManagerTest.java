package com.banistmo.mqcon;

import javax.jms.JMSException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.inject.Guice;
import com.google.inject.Injector;

@RunWith(MockitoJUnitRunner.class)
public class MQManagerTest {

	
	private MQManager manager;
	
	@Before 
	public void init() {
		Injector injector = Guice.createInjector(new MQModuleTest());	
		manager = injector.getInstance(MQManager.class);
	}
	
	

	@Test(expected=IllegalArgumentException.class)
	public void destNameNull() throws JMSException {
		manager.send(null, "Mensaje de prueba");
	} 
	
	@Test(expected=IllegalArgumentException.class)
	public void destNameEmpty() throws JMSException {
		manager.send("", "Mensaje de prueba");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void textNull() throws JMSException {
		manager.send("QL.PPRUEBA_AWS_SENTINEL", null);
	} 
	
	@Test(expected=IllegalArgumentException.class)
	public void textEmpty() throws JMSException {
		manager.send("QL.PPRUEBA_AWS_SENTINEL", "");
	}
	
	@Test
	public void validMsgQueue() throws JMSException {
		manager.send("QL.PPRUEBA_AWS_SENTINEL", "Mensaje de prueba");
	}
	
	@Test
	public void validMsgTopic() throws JMSException {
		manager.send("topic://POST_AUTH", "Mensaje de prueba");
	}
	
}
