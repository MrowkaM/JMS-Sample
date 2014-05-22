package com.subwaydevs.messenger;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MesssageConsumer {
	
	public static void main(String[] args) throws NamingException {
		consumeMessage();
	}
	
	public static void consumeMessage() throws NamingException {
		InitialContext context = configureContext();
		Queue messages = (Queue) context.lookup("java:jms/queue/messagesQueue");
		ConnectionFactory connectionFactory = (ConnectionFactory) context
				.lookup("jms/RemoteConnectionFactory");
		try (JMSContext jmsContext = connectionFactory.createContext("sdevs", "sdevs")) {
			JMSConsumer consumer = jmsContext.createConsumer(messages,"special = 'XXX'");
			String mssg = consumer.receiveBody(String.class);
			while(mssg != null) {
				System.out.println(mssg);
				mssg = consumer.receiveBody(String.class);
			}
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
