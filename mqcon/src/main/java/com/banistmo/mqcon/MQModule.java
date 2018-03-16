package com.banistmo.mqcon;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jms.JMSException;

import org.springframework.jms.core.JmsTemplate;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsConstants;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;



public class MQModule  extends AbstractModule {
	
	@Override
	protected void configure() {
		Properties props = new Properties();
		props.putAll(System.getenv());
		try {
			InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
			if (input != null)	props.load(input);
		} catch (IOException e) {
			System.out.println("application.properties not found");
		}
		Names.bindProperties(binder(), props);
		   try {
			   bind(MQFactory.class).to(MQFactoryImpl.class);

			bind(JmsFactoryFactory.class).toInstance(getJmsFactory());
		} catch (JMSException e) {
			//System.out.println("#Error JmsFactory");
			e.printStackTrace();
		}

	}

	public JmsFactoryFactory getJmsFactory() throws JMSException {
		return JmsFactoryFactory.getInstance(JmsConstants.WMQ_PROVIDER);
	}
	
	@Provides
	@Singleton
	public JmsConnectionFactory getConnectionFactory(@Named("MQM_QM") String qm,
			@Named("MQM_QP") Integer qp,
			@Named("MQM_QHOST") String qhost,
			@Named("MQM_QCHANNEL")  String qchannel,
			@Named("MQM_USERID") String userid,
			JmsFactoryFactory jmsFactory) throws JMSException {
		JmsConnectionFactory connFact = jmsFactory.createConnectionFactory();
		connFact.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
		connFact.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, qm);
		connFact.setStringProperty(WMQConstants.WMQ_HOST_NAME, qhost);
		connFact.setObjectProperty(WMQConstants.WMQ_PORT, qp);
		connFact.setStringProperty(WMQConstants.WMQ_CHANNEL, qchannel);
		connFact.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, userid);
		connFact.setStringProperty(WMQConstants.USERID, userid);
	return connFact;
}
	@Provides
	public JmsTemplate getJmsTemplate(JmsConnectionFactory cf) throws JMSException {
		//System.out.println("getTemplate con CF:" + String.valueOf(cf));
		JmsTemplate template = new JmsTemplate(cf);
		template.setReceiveTimeout(10000);
		return template;
	}
}
