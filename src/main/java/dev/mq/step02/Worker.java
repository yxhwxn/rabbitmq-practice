package dev.mq.step02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
    작업 큐에서 꺼낸 작업의 메시지를 처리하는 역할
 */
public class Worker {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
        System.out.println("[Consumer(Worker)] 메시지를 기다리는 중..");

        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            // 메시지 추출
            String message = new String(delivery.getBody(), "UTF-8");

            System.out.println(" [Consumer(Worker)] Received '" + message + "'");
            try {
                doWork(message); // 받은 메시지 처리 작업 수행
            } finally {
                System.out.println(" [Consumer(Worker)] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };

        boolean autoAcknowledgement = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAcknowledgement, deliverCallback, consumerTag -> { });
    }

    // Task 처리 메서드, Hello...일 경우 .하나당 1초씩 지연해서 처리해야함
    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') { // .(dot) 한 개당 1초씩 지연
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
