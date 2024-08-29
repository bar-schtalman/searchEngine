package com.handson.searchengine.sqs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handson.searchengine.crawler.Crawler;
import com.handson.searchengine.model.CrawlerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.io.IOException;
import java.util.List;

@Component
public class SqsConsumer {

    @Autowired
    private ObjectMapper om;

    @Autowired
    private Crawler crawler;

    @Autowired
    private SqsClient sqsClient;

    @Value("${aws.sqs.queueUrl}")
    private String queueUrl;

    @Scheduled(fixedRate = 5000) // Adjust the rate as needed
    public void pollMessages() throws IOException, InterruptedException {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(20)
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

        for (Message message : messages) {
            CrawlerRecord rec = om.readValue(message.body(), CrawlerRecord.class);
            crawler.crawlOneREcord(rec.getCrawlId(), rec);

            // Delete the message from the queue after processing
            sqsClient.deleteMessage(DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle())
                    .build());
        }
    }
}
