package com.subwaydevs.messenger.example;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageSubscriberNonDurable {

	public static void main(String[] args) throws NamingException {
		subscribeMessage(1);
	}
	
	private static void subscribeMessage(int subscriberNumber) throws NamingException {
		InitialContext context = configureContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) context
				.lookup("jms/RemoteConnectionFactory");
		Topic messages = (Topic) context.lookup("java:jms/topic/messagesTopic");
		try (JMSContext jmsContext = connectionFactory.createContext("sdevs", "sdevs")) {
			JMSConsumer consumer = jmsContext.createConsumer(messages);
			System.out.println("Subscriber : " + subscriberNumber + " recieved message " + consumer.receiveBody(String.class));
		}
		
	}
	
	
	private static InitialContext configureContext() throws NamingException {
		final Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jboss.naming.remote.client.InitialContextFactory");
		p.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
		InitialContext context = new InitialContext(p);
		return context;
	}
}
