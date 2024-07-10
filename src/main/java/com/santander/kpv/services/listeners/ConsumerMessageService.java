/*
 * (c) Copyright IBM Corporation 2021, 2023
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.santander.kpv.services.listeners;

import com.santander.kpv.services.senders.SendMessageService;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Component
public class ConsumerMessageService {
    protected final Log logger = LogFactory.getLog(getClass());

    final private SendMessageService service;

    ConsumerMessageService(SendMessageService service) {
        this.service = service;
    }

    @JmsListener(destination = "${app.queue.name1}")
    public void receiveRequest(Message message,
                               @Header("JMSXDeliveryCount") Integer deliveryCount) {
        logger.info("");
        logger.info( this.getClass().getSimpleName());
        logger.info("Received message of type: " + message.getClass().getSimpleName());
        logger.info("Received message :" + message);
        try {
            Destination replyDest = message.getJMSReplyTo();
            String correlation = message.getJMSCorrelationID();
            logger.info("Attempting Json parsing");
            // If the deliveryCount >=3 then perhaps the temp reply queue is broken,
            // ideally should dead letter queue the request.
            if (3 <= deliveryCount) {
                logger.warn("Message delivered " + deliveryCount + " times.");
                logger.warn("Message should be dead letter queued, as it might be poisoned");
            }else {
                createResponse(replyDest, "Retornando com valor tratado", correlation);
            }
        } catch (JMSException e) {
            logger.warn("JMSException processing request");
        }
    }

    private void createResponse(Destination replyDest, String data, String correlation) {
        if (null == replyDest) {
            logger.warn("No Reply destination");
        } else {
            logger.info("Sending reply with correlation id : " + correlation);
            service.reply(replyDest, data, correlation);
        }
    }

}
