**Java Project**
1. Update the custom-handler.properties file according to your needs.

2. Compile and build the package MessageHandlerHTTPToJMS.jar from the Java project MessageHandlerHTTPToJMS using IntelliJ
   Copy the MessageHandlerHTTPToJMS.jar from the target folder.

**API Manager 3.2.0**
3. Create two sequences in the following location:
<APIM_HOME>/repository/deployment/server/synapse-configs/default/sequences/

    _api_logging_request_handler_.xml
    _api_logging_response_handler_.xml

4. If the API is already created, navigate to the API folder under this location:
    <APIM_HOME>/repository/deployment/server/synapse-configs/default/api/

    For example:
    admin--<apiname>_v1.0.xml
    E.g., admin--abctest_v1.0.xml
   Update the handler configration as follows
   <handlers>
        <handler class="<<customhandler class name with the package>>"/>
   </handlers>
        eg., : <handlers>
        <handler class="<<org.wso2.custom.APILoggingHandler>>"/>
   </handlers>
Note: Engage the handler in velocity_template.xml is the best practice
5. Paste the MessageHandlerHTTPToJMS.jar (from step 2) into the <APIM_HOME>/repository/components/lib/ folder.

**ActiveMQ**
6. Download Apache ActiveMQ 5.8.0.
7. Copy the following client libraries from the ACTIVEMQ_HOME/lib directory to the APIM_HOME/lib directory:

    activemq-broker-5.8.0.jar
    activemq-client-5.8.0.jar
    activemq-kahadb-store-5.8.0.jar
    geronimo-jms_1.1_spec-1.1.1.jar
    geronimo-j2ee-management_1.1_spec-1.0.1.jar
    geronimo-jta_1.0.1B_spec-1.0.1.jar
    hawtbuf-1.9.jar
    slf4j-api-1.6.6.jar
    activeio-core-3.1.4.jar (available in the ACTIVEMQ_HOME/lib/optional directory)

For Earlier Versions of ActiveMQ:

    activemq-core-5.5.1.jar
    geronimo-j2ee-management_1.0_spec-1.0.jar
    geronimo-jms_1.1_spec-1.1.1.jar

8. Update the deployment.toml file located in <APIM_HOME>/repository/conf/ with the following configuration:

   ActiveMQ Configuration
    [transport.jms]
    sender_enable = true
    [[transport.jms.sender]]
    name = "myQueueSender"
    parameter.'java.naming.factory.initial' = "org.apache.activemq.jndi.ActiveMQInitialContextFactory"
    parameter.'java.naming.provider.url' = "tcp://localhost:61617"
    parameter.'transport.jms.ConnectionFactoryJNDIName' = "QueueConnectionFactory"
    parameter.'transport.jms.ConnectionFactoryType' = "queue"
    parameter.'transport.jms.CacheLevel' = "producer"

    Note: The ActiveMQ default port is 61616 and the admin port is 8161. The administrative interface can be accessed at:
    http://localhost:8161
    Login: admin
    Password: admin

9. Start the ActiveMQ server:
        Mac (using Homebrew): brew services start activemq
        Manual (from the extracted ActiveMQ folder):
        Navigate to the bin folder and run:
        sh activemq.sh start

10. Open the administrative interface:
        URL: http://127.0.0.1:8161/admin/
        Login: admin
        Password: admin

11. Navigate to Queues and create two queues:
        requestqueue
        responsequeue

        These queues will be created and displayed under the Queues tab.

12. Update the requestqueue and responsequeue names in the _api_logging_request_handler_.xml and _api_logging_response_handler_.xml files, where the JMS endpoints are defined.

**Mockoon API**

13. Connect with Mockoon to create your resource methods (GET and POST). Add the logic and get the URLs of the endpoints.

**API Manager Configuration**

14. Please refer to the point 3, point 4, point 5, point 7, and point 8
    Restart the API Manager.

    Create APIs in the API Manager using the Publisher, add resource methods, and define API endpoints. Then, publish the API.

    Create an application in the Developer Portal and subscribe to the API.

    Generate the keys (ConsumerKey and ConsumerSecret) and invoke the API using the token.

    Verify that the request and response of the API are being published to the ActiveMQ queues (requestqueue and responsequeue)
    



