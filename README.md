# Spring RabbitMQ Chat

A simple chat application based on **Spring Boot** and **RabbitMQ**. 

## Command Line Interface

Make sure that the `cli` profile is turned on. You can spawn several chat clients by passing a distinct queue name on the command line:

    session1> ./gradlew -Dapp.settings.rabbit.queue=chat.Q.dev.MessageSubscriber1.Message run
    session2> ./gradlew -Dapp.settings.rabbit.queue=chat.Q.dev.MessageSubscriber2.Message run

Don't forget to [setup](src/main/resources/application.yml) your **RabbitMQ** connection first.

