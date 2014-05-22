package com.subwaydevs.messenger;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageProducer {

	
	public static void main(String[] args) throws NamingException {
		sendMessage();
	}
	
	public static void sendMessage() throws NamingException {
		InitialContext context = configureContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) context
				.lookup("jms/RemoteConnectionFactory");
		Queue messages = (Queue) context.lookup("java:jms/queue/messagesQueue");
		try (JMSContext jmsContext = connectionFactory.createContext("sdevs", "sdevs")) {
			JMSProducer producer = jmsContext.createProducer();
			for (int i = 1; i < 10; i++) {
				producer.setPriority(i).send(messages, "Mssg nr "+ i + " with prio " + i );
			}
		}
	}
	
	private static InitialContext configureContext() throws NamingException {
		final Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		p.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
		p.put(Context.SECURITY_PRINCIPAL, "sdevs");
		p.put(Context.SECURITY_CREDENTIALS, "sdevs");
		InitialContext context = new InitialContext(p);
		return context;
	}
}
