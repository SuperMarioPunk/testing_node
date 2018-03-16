package com.banistmo.mqcon;

import java.util.Map;

import javax.jms.JMSException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Lambda function that simply prints "Hello World" if the input String is not provided,
 * otherwise, print "Hello " with the provided input String.
 */

public class MQConHandler implements RequestHandler<Map<String,String>, String> {
	
	private MQManager mqm;
	
	private Injector injector = Guice.createInjector(new MQModule());		

		
    public MQManager getMqm() {
		return mqm;
	}

	public void setMqm(MQManager mqm) {
		this.mqm = mqm;
	}

	@Override
    public String handleRequest(Map<String,String>  input, Context context) {
        String output = String.format("url: %s, text:%s length:%s",input.get("destUrl"),input.get("text"), input.get("text").length());
        try {
        	if (getMqm() == null) {setMqm(injector.getInstance(MQManager.class)); }
			getMqm().send(input.get("destUrl"),input.get("text"));
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
        context.getLogger().log(output);
        return output;
    }
}