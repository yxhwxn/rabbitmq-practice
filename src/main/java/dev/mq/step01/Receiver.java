package dev.mq.step01;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Receiver - 메시지 소비자(Consumer)
 * Sender - 메시지 발행자(Publisher)
 *
 * Receiver는 RabbitMQ와 연결, 메시지를 전송하는 처리 수행
 * Sender는 메시지 큐로부터 퍼블리셔(Receiver)가 적재한 메시지를 소비하는 처리 수행
 */
public class Receiver {

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
        System.out.println(" [Receiver] 메시지 기다리는 중.");

        // 메시지를 소비하기 위한 콜백 함수 정의
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [Receiver] 메시지 받음: " + message);
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
