package com.github.danitutu.protobufkafkaconsumer;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class Listener {

    private static final Logger logger = LoggerFactory.getLogger(Listener.class);

    @KafkaListener(topics = "${KAFKA_TOPIC_NAME}")
    public void listener(@Payload byte[] message, @Headers Map<String, Object> headers) throws Descriptors.DescriptorValidationException, IOException {
        try {
            String data = DescriptorProtos.FileOptions.parseFrom(message).toString().indent(4);

            logger.info(
                    """
                            Message received
                            - Headers:
                                - Timestamp Type: {}
                                - Timestamp: {}
                                - Kafka Offset: {}
                                - Partition: {}
                            - Payload:
                            {}""",
                    headers.get("kafka_timestampType"),
                    headers.get("kafka_receivedTimestamp"),
                    headers.get("kafka_offset"),
                    headers.get("kafka_receivedPartitionId"),
                    data
            );
        } catch (InvalidProtocolBufferException e) {
            logger.warn("Failed to parse the message", e);
        }
    }

}
