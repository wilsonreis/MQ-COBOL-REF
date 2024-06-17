package com.santander.kpv.utils;

import jakarta.jms.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

public class MessageUtilsTest {

    private Log logger;

    @BeforeEach
    public void setUp() {
        logger = LogFactory.getLog(MessageUtils.class);
    }

    @Test
    public void testCheckMessageTypeWithTextMessage() throws JMSException {
        TextMessage message = mock(TextMessage.class);
        when(message.getText()).thenReturn("This is a text message");

        MessageUtils.checkMessageType(message);

        verify(logger).info("Message matches TextMessage");
        verify(logger).info("message payload is This is a text message");
    }

    @Test
    public void testCheckMessageTypeWithBytesMessage() throws JMSException {
        BytesMessage message = mock(BytesMessage.class);

        MessageUtils.checkMessageType(message);

        verify(logger).info("Message matches BytesMessage");
    }

    @Test
    public void testCheckMessageTypeWithMapMessage() throws JMSException {
        MapMessage message = mock(MapMessage.class);

        MessageUtils.checkMessageType(message);

        verify(logger).info("Message matches MapMessage");
    }

    @Test
    public void testCheckMessageTypeWithStreamMessage() throws JMSException {
        StreamMessage message = mock(StreamMessage.class);

        MessageUtils.checkMessageType(message);

        verify(logger).info("Message matches StreamMessage");
    }

    @Test
    public void testCheckMessageTypeWithException() throws JMSException {
        TextMessage message = mock(TextMessage.class);
        when(message.getText()).thenThrow(new JMSException("Error"));

        MessageUtils.checkMessageType(message);

        verify(logger).warn("Unable to process JMS message");
    }
}
