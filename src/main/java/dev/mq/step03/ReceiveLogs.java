package dev.mq.step03;

import com.rabbitmq.client.*;

public class ReceiveLogs {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // fanout 타입의 익스체인지 선언
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // 서버가 이름을 정해주는 임시 큐 생성
        String queueName = channel.queueDeclare().getQueue();

        // logs 익스체인지에 바인딩
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };

        // 메시지 수신 시작
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}
