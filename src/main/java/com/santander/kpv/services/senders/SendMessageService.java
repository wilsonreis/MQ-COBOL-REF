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

package com.santander.kpv.services.senders;

import jakarta.jms.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SendMessageService {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private ConnectionFactory connectionFactory;


    public void reply(Destination replyDest, String msg, String correlation) {
        try {
            JMSContext context = connectionFactory.createContext();

            TextMessage message = context.createTextMessage(msg);
            message.setJMSCorrelationID(correlation);
            logger.info(correlation);
            JMSProducer producer = context.createProducer();
            // Make sure message put on a reply queue is non-persistent so non XMS/JMS apps
            // can get the message off the temp reply queue
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.send(replyDest, message);
        } catch (JMSException e) {
            logger.warn("JMS Exception attempting to create and send reply");
            logger.warn(e.getMessage());
        }

    }


}



