package dev.mq.step01;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * Receiver - 메시지 소비자(Consumer)
 * Sender - 메시지 발행자(Publisher)
 *
 * Receiver는 RabbitMQ와 연결, 메시지를 전송하는 처리 수행
 * Sender는 메시지 큐로부터 퍼블리셔(Receiver)가 적재한 메시지를 소비하는 처리 수행
 */
public class Sender {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // RabbitMQ 서버 프로세스로의 연결 수행
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        // RabbitMQ 서버(5672)와 커넥션 연결 수행
        Connection connection = factory.newConnection();
        // 통신 채널 생성
        Channel channel = connection.createChannel();

        // 큐 생성
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello world!";

        // 메시지 발행
        channel
                .basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [Publisher] Sent " + message);
    }
}
