/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dcarlidev.testsqspollinglambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.internal.SQSRequestHandler;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.ListQueuesRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */
public class MyHandler {

    private LambdaLogger logger;
    private int invocation = 0;
    private final String SQS_URL = "";

    public MyHandler() {
    }

    public void handler(SQSEvent event, Context context) throws Exception {
        logger = context.getLogger();
        AmazonSQS sqs = AmazonSQSClientBuilder.standard().build();
        ReceiveMessageRequest r = new ReceiveMessageRequest()
                .withQueueUrl(SQS_URL)
                .withMaxNumberOfMessages(2);
        ReceiveMessageResult result = sqs.receiveMessage(r);
        result.getMessages().forEach(m -> System.out.println("Message: " + m.getBody()));
        logger = context.getLogger();
        logger.log("Starting with extraction of data from SQSEvent " + invocation);
        invocation++;
        List<SQSMessage> messages = event.getRecords();
        logger.log("Size of records: " + messages.size());
        messages.stream().forEach(message -> this.processMessage(message, logger));
//        for (SQSMessage message : messages) {
//            logger.log("MessageGetReceiptHandle: " + message.getReceiptHandle());
//            logger.log("MessageId: " + message.getMessageId());
//            logger.log("MessageBody " + message.getBody());
//            processMessage(message);
//        }
    }

    private void processMessage(SQSMessage message, LambdaLogger loggerr) {
        String body = message.getBody();
        loggerr.log("Throwing the exception....");
        try {
            Thread.sleep(100000);
//        throw new Exception("");
        } catch (InterruptedException ex) {
            Logger.getLogger(MyHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String... args) throws Exception {
        MyHandler h = new MyHandler();
        h.handler(null, null);
    }

}
