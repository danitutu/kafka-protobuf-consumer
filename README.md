# kafka-protobuf-consumer

A containerized Spring Boot app which uses a Kafka listener to consume messages from a topic and to log messages data.

This project is only meant for testing purposes and should not be used in production.

Be aware that if your Kafka messages contain sensitive data then the sensitive data will be logged.

The container doesn't require a protobuf schema.

&#x26A0;&#x26A0;&#x26A0; **!!! Make sure you understand the configuration you pass to the application. Misconfiguration might cause irreparable harm or irreparable damage. See LICENSE file and the Agreement section.** 

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

## Agreement

In addition to the LICENSE file, by using this tool, you, the user of the tool, understand and agree with LICENSE and with the following:
1. Misconfiguration of the tool might cause irreparable harm or irreparable damage.
2. You are responsible for the way you use the tool and for any potential harm or damage that might appear as a consequence of using this tool.
3. Authors and contributors cannot be held responsible, in any way, for any type of damage or harm, including and without limiting to data loss, data leaks, data privacy, that was caused by the usage of this tool.

## Getting Started

Spring Boot, Kafka connectivity, consumer, logging and others can be configured.

&#x26A0; Always make sure that the data you log from the topics does not contain sensitive data (ex.: any data that can be used to identify a real person, usernames, passwords, emails, phone numbers, etc.). Beware of the deployment environment and infrastructure as there might be side effects that happen without your knowledge (like automatically taking the application standard output and logging it in another system).

By default, the application logs to the standard output.

Usage with docker-compose:

```yaml
kafka-protobuf-consumer:
  image: ghcr.io/danitutu/kafka-protobuf-consumer:1.0.1
  environment:
    - SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9093
    - KAFKA_TOPIC_NAME=<topic-name>
    - SPRING_KAFKA_CONSUMER_AUTO_OFFSET_RESET=latest
  depends_on:
    - kafka
```

For the listed parameters possible values and default values please consult the appropriate docs.

- https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#application-properties
- https://kafka.apache.org/documentation/

The Default column shows only the values that are set by this application in case the parameter value was not provided. It doesn't refer to the values set by the other tools.

| Name                                             | Required | Sample Value                         | Default     | Description                                                                                                                                                                                                          |
|--------------------------------------------------|----------|--------------------------------------|-------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| SPRING_KAFKA_BOOTSTRAP_SERVERS                   | Yes      | localhost:29092                      |             | Kafka broker address                                                                                                                                                                                                 |
| SPRING_KAFKA_TOPIC_NAME                          | Yes      | events.user                          |             | The topic from which messages will be consumed                                                                                                                                                                       |
| SPRING_KAFKA_CONSUMER_GROUP_ID &#x26A0;          |          | bf2f1933-de86-44c6-b654-ac57d20bf003 | Random UUID | The consumer group ID. Never set this value to an existing consumer group ID. Doing that will cause the messages to be consumed by the test consumer which might end up with data loss.                              |
| SPRING_KAFKA_CONSUMER_AUTO_OFFSET_RESET &#x26A0; |          | latest                               |             | The way the consumer starts consuming messages when it joins Kafka. Depending on the value that is set and on other Kafka parameters, this might consume a high amount of messages and produce huge amounts of logs. |

### Log to file

Set `logging.file.name=/home/<user>/kafka/logs/kafka-messages.log`. After this, the app will log to both the standard output and the file. If you want to disable the standard output logging and keep only the file logging, then set `logging.pattern.console=`.

### Kubernetes pod deployment



## Development

### docker-compose

In the project directory:

1. Run `./mvnw spring-boot:build-image`
2. Run `./docker-compose up`
