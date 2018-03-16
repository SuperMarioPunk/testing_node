package com.banistmo.mqcon;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.inject.Guice;
import com.google.inject.Injector;

@RunWith(MockitoJUnitRunner.class)
public class MQConHandlerTest {
	
	private MQConHandler handler;
	
	private Map<String,String> validData;
	
	@Before
	public  void setup() {
		Injector injector = Guice.createInjector(new MQModuleTest());	
		handler = injector.getInstance(MQConHandler.class);
		validData = new HashMap<>();
		validData.put("destUrl", "QL.PPRUEBA_AWS_SENTINEL");
		validData.put("text", "Mensaje de prueba");
	}
	
	@Test
	public void validMsgQueue() {
		Context ctx = Mockito.mock(Context.class);
		LambdaLogger logger = Mockito.mock(LambdaLogger.class);
		Mockito.when(ctx.getLogger()).thenReturn(logger);
		MQManager manager = Mockito.mock(MQManager.class);
		handler.setMqm(manager);
		handler.handleRequest(validData,ctx);
	} 
	
//	@Test
//	public void nullManager() {
//		Context ctx = Mockito.mock(Context.class);
//		handler.setMqm(null);
//		handler.handleRequest(validData,ctx);
//	} 
	
	
}
