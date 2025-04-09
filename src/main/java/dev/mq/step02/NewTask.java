package dev.mq.step02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
    작업(Task)을 작업 큐(Work queue)에 스케줄링하는 프로그램
    문자열값이 어떤 복잡한 작업이라고 가정할 때,
    ex. Hello...이라고 하면
    . 하나는 1초가 소요되는 작업이라고 가정

    따라서 Hello...는 3초가 걸리는 작업이라고 가정

    실행 인자값(args) 활용해서 Hello...과 같은 값 입력
 */
public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 서버 연결
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // 큐 생성
            // TODO: durable=true로 변경
            channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);

            // 실행 옵션으로 메시지를 생성, ex. Hello...
            String message = String.join(" ", args);

            // 메시지 적재
            channel.basicPublish("", TASK_QUEUE_NAME,
                    null,
                    message.getBytes("UTF-8"));

            System.out.println(" [Publisher] Sent " + message);
        }

    }
}
