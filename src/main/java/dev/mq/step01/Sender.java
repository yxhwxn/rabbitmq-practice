package dev.mq.step01;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Receiver - 메시지 소비자(Consumer)
 * Sender - 메시지 발행자(Publisher)
 *
 * Recevier는 RabbitMQ와 연결, 메시지를 전송하는 처리 수행
 * Sencder는 메시지 큐로부터 퍼블리셔(Receiver가 적재한 메시지를 소비하는 처리 수행
 */
public class Sender {

    private final static String QUEUE_NAME = "hello-queue";

    public static void main(String[] args) throws Exception {
        // ConnectionFactory: RabbitMQ 서버 프로세스로의 연결 수행
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        // 연결 및 채널 생성
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // 큐 선언
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello RabbitMQ!";

            // 메시지 전송
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
