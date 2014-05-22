package com.subwaydevs.messenger;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessagePublisher {
	
	public static void main(String[] args) throws NamingException {
		publishMessage();
	}
	
	private static void publishMessage() throws NamingException {
		 InitialContext context = configureContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) context
				.lookup("jms/RemoteConnectionFactory");
		Topic messages = (Topic) context.lookup("java:jms/topic/messagesTopic");
		try (JMSContext jmsContext = connectionFactory.createContext("sdevs", "sdevs")) {
			JMSProducer producer = jmsContext.createProducer();
			producer.send(messages, "Hello");
		}
		
	}

	private static InitialContext configureContext() throws NamingException {
		final Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		p.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
		InitialContext context = new InitialContext(p);
		return context;
	}

}
