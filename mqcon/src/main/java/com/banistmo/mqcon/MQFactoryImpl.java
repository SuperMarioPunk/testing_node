package com.banistmo.mqcon;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.springframework.jms.core.MessageCreator;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.ibm.msg.client.jms.JmsFactoryFactory;


public class MQFactoryImpl implements MQFactory {
	
	@Inject
	private JmsFactoryFactory jmsFactory;
//	private String qm;
//	private String qhost;
//	private Integer qp;
//	private String qchannel;
//	private String userid;
//	private JmsConnectionFactory cf;
//	private JmsFactoryFactory jmsFactory;
//	
//	@Inject
//	public MQFactoryImpl(@Named("MQM_QM") String qm,
//			@Named("MQM_QP") Integer qp,
//			@Named("MQM_QHOST") String qhost,
//			@Named("MQM_QCHANNEL")  String qchannel,
//			@Named("MQM_USERID") String userid,
//			JmsFactoryFactory jmsFactory
//			) throws JMSException {
//		this.qm = qm;
//		this.qp = qp;
//		this.qhost = qhost;
//		this.qchannel = qchannel;
//		this.userid = userid;
//		this.jmsFactory = jmsFactory;
//		this.cf = getConnectionFactory();
//	}
	

	
	
	public Destination createDestination(@Assisted String destUrl) throws JMSException {
		Destination dest;
		if (destUrl.startsWith("topic://")) {
			Topic topic = jmsFactory.createTopic(destUrl);
			dest = topic;
		} else {
			Queue queue = jmsFactory.createQueue(destUrl);
			dest = queue;
		}
		return dest;
	}
		
	public MessageCreator createMessageCreator(String text) {
		return new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(text);
			}};
	}

	
}
