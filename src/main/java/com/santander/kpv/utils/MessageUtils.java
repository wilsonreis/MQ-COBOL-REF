package com.santander.kpv.utils;

import jakarta.jms.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageUtils {
    protected static final Log logger = LogFactory.getLog(MessageUtils.class);

    private MessageUtils() {}

    public static void checkMessageType(Message message) {
        try {
            if (message instanceof TextMessage) {
                logger.info("Message matches TextMessage");
                logger.info("message payload is " + ((TextMessage) message).getText());
            } else if (message instanceof BytesMessage) {
                logger.info("Message matches BytesMessage");
            } else if (message instanceof MapMessage) {
                logger.info("Message matches MapMessage");
            } else if (message instanceof StreamMessage) {
                logger.info("Message matches StreamMessage");
            }
        } catch (JMSException e) {
            logger.warn("Unable to process JMS message");
        }
    }
}
