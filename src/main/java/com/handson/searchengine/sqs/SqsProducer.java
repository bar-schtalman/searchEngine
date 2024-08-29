package com.handson.searchengine.sqs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
public class SqsProducer {

    @Autowired
    private SqsClient sqsClient;

    @Autowired
    private ObjectMapper om;

    @Value("${aws.sqs.queueUrl}")
    private String queueUrl;

    public void send(Object message) throws JsonProcessingException {
        String messageBody = om.writeValueAsString(message);
        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build();
        sqsClient.sendMessage(sendMessageRequest);
    }
}
