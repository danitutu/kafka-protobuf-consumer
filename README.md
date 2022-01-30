# kafka-protobuf-consumer

A containerized Spring Boot app which uses a Kafka listener to consume messages from a topic and to log messages data.

This project is only meant for testing purposes and should not be used in production.

Be aware that if your Kafka messages contain sensitive data then the sensitive data will be logged.

The container doesn't require a schema.

A logged message sample:

```
- Headers:
    - Timestamp Type: CREATE_TIME
    - Timestamp: 1643532455489
    - Kafka Offset: 1834
    - Partition: 0
- Payload:
    java_package: "person name"
    2: 15
    3: {
      1: 1643530908
      2: 337135974
    }
    4: {
      1: "street"
      2: "city"
      4: {
        1: "protuf oneOf field value"
      }
    }
```

This should be correlated with a protobuf schema.

## Getting Started

### Environment Variables

| Name                             | Sample Value       | Default     | Description                                                                                                          |
|----------------------------------|--------------------|-------------|----------------------------------------------------------------------------------------------------------------------|
| KAFKA_BOOTSTRAP_SERVERS          | localhost:29092    | N/A         | Kafka broker address                                                                                                 |
| KAFKA_CONSUMER_GROUP_ID          | test-user-consumer | Random UUID | The consumer group ID. If not provided each instance will get a new UUID.                                            |
| KAFKA_TOPIC_NAME                 | events.user        | N/A         | The topic from which messages will be consumed                                                                       |
| KAFKA_CONSUMER_AUTO_OFFSET_RESET | earliest           | latest      | The way the consumer should consume messages when it joins Kafka. It should be one of: `latest`, `earliest`, `none`. |

## Development

### docker-compose

In the project directory:

1. Run `./mvnw spring-boot:build-image`
2. Run `./docker-compose up`