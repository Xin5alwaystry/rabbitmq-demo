package fanout;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author xin5
 * @date 2021年01月24日 17:03:14
 */
public class Consumer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs", "fanout");

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, "logs", "");

        channel.basicConsume("", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumer2 : " + new String(body));
            }
        });
    }
}
