package com.banistmo.mqcon;

import javax.jms.Destination;
import javax.jms.JMSException;

import org.springframework.jms.core.MessageCreator;

public interface MQFactory {
	public Destination createDestination(String destUrl) throws JMSException;
	public MessageCreator createMessageCreator(String text);
}
