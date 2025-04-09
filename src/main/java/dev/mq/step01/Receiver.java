package dev.mq.step01;

import com.rabbitmq.client.*;

/**
 * Receiver - 메시지 소비자(Consumer)
 * Sender - 메시지 발행자(Publisher)
 *
 * Recevier는 RabbitMQ와 연결, 메시지를 전송하는 처리 수행
 * Sencder는 메시지 큐로부터 퍼블리셔(Receiver가 적재한 메시지를 소비하는 처리 수행
 */
public class Receiver {

    private final static String QUEUE_NAME = "hello-queue";

    public static void main(String[] argv) throws Exception {
        // ConnectionFactory: RabbitMQ 서버 프로세스로의 연결 수행
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        // 연결 및 채널 생성
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // 메시지 수신 처리 로직
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };

        // 메시지 소비 시작
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}
