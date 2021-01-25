package workqueue;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author xin5
 * @date 2021年01月23日 23:39:43
 */
public class Consumer1 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);
        channel.queueDeclare("hello", true, false, false, null);

        //参数2：是否自动确认，如填是，可能出现消息未被真正读取的情况下消费者宕机；通常不建议填是
        channel.basicConsume("hello", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Consumer1 : " + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
