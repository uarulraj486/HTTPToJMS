package org.wso2.carbon.apimgt.gateway.handlers.httptojms;

import org.apache.synapse.ManagedLifecycle;
import org.apache.synapse.Mediator;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.rest.AbstractHandler;
import org.apache.synapse.MessageContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class APILoggingHandler extends AbstractHandler implements ManagedLifecycle {
    private static final Log log = LogFactory.getLog(APILoggingHandler.class);
    Properties loadHandlerProperties;

    public APILoggingHandler() {
        loadHandlerProperties();
    }

    @Override
    public void init(SynapseEnvironment synapseEnvironment) {
        if (log.isDebugEnabled()) {
            log.debug("Initializing APILogging Handler Request Handler instance");
        }

    }

    @Override
    public void destroy() {
        if (log.isDebugEnabled()) {
            log.debug("Destroying APILogging Handler instance");
        }

    }


    @Override
    public boolean handleRequest(MessageContext requestMessageContext) {
        log.debug("APILoggingHandler handleRequest Method......");
        log.info("APILoggingHandler handleRequest Method......");
        String reqSequenceName = loadHandlerProperties.getProperty("HANDLER.API_LOGGING_REQUEST_SEQUENCE_NAME");
        Mediator handleRequestSequence = requestMessageContext.getSequence(reqSequenceName);
        if (handleRequestSequence != null) {
            log.debug("APILoggingHandler handleRequestSequence Inside the If Condition......");
            handleRequestSequence.mediate(requestMessageContext);

        }
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext responseMessageContext) {

        log.debug("APILoggingHandler handleResponse Method......");
        log.info("APILoggingHandler handleResponse Method......");
        String respSequenceName = loadHandlerProperties.getProperty("HANDLER.API_LOGGING_RESPONSE_SEQUENCE_NAME");
        Mediator handleResponseSequence = responseMessageContext.getSequence(respSequenceName);
        if (handleResponseSequence != null) {
            log.debug("APILoggingHandler handleResponse Inside the If Condition......");
            handleResponseSequence.mediate(responseMessageContext);
        }
        return true;
    }

    private void loadHandlerProperties() {
        loadHandlerProperties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream
                    ("custom-handler.properties");
            {
                if (inputStream != null) {
                    loadHandlerProperties.load(inputStream);
                } else {
                    log.debug("Properties file not found in resources folder!");
                }
            }
        } catch (IOException ioe) {
            log.debug("Error" + ioe.getMessage());
        }
    }


}