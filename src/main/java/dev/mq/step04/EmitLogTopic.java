package dev.mq.step04;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // topic 타입의 익스체인지 선언
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            // 인자 0: routing key, 나머지: 메시지
            String routingKey = argv.length > 0 ? argv[0] : "anonymous.info";
            String message = argv.length > 1 ? String.join(" ", argv).substring(routingKey.length() + 1) : "Hello World!";

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        }
    }
}
