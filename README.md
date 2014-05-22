Messenger
=========
Application to be deployed on Wildfly 8

How to configure WildFly ?
1. Configuration has been done based on these tutorials:
 https://docs.jboss.org/author/display/WFLY8/Messaging+configuration
 
 https://github.com/wildfly/quickstart/tree/master/helloworld-jms

2. Enter destination name in standalone-full.xml, example:
		<jms-destinations>
			<jms-queue name="testQueue">
				<entry name="queue/test"/>
				<entry name="java:jboss/exported/jms/queue/test"/>
			</jms-queue>
		</jms-destinations>

3. Create application user according to following description:
 https://github.com/wildfly/quickstart/blob/master/README.md#add-an-application-user
 * credentials used in exemplary queue/topic is sdevs/sdevs (role = guest)
		
4. Start the server with the full profile : JBOSS_HOME\bin\standalone.bat -c standalone-full.xml



