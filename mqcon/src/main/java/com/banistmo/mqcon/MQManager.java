package com.banistmo.mqcon;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.jms.Destination;
import javax.jms.JMSException;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.google.inject.name.Named;


@Singleton
public class MQManager {
	
	private MQFactory factory;		

	private Integer mqmttl;
	
	@Inject
	private JmsTemplate template;
	
	@Inject
	public MQManager(MQFactory factory, @Named("MQM_TTL") Integer ttl) throws JMSException {
		this.factory = factory;
		this.mqmttl = ttl;
	}
	
	
	
	public void send(String destName, String text,Integer ttl) throws JMSException {
		requireNotEmptyNotNull(destName,"destName is required");
		requireNotEmptyNotNull(text,"text is required");
		requireNotEmptyNotNull(ttl,"ttl is required");
		
		Destination destination = factory.createDestination(destName);
		MessageCreator message =factory.createMessageCreator(text);
		template.setTimeToLive(ttl);
		template.send(destination,message);
	}

	private void requireNotEmptyNotNull(Object value, String text) {
		if (value == null || "".equals(value) ) throw new IllegalArgumentException(text);
	}

	public void send(String destName, String text) throws JMSException {
		send(destName, text, mqmttl);
	}
	
}
