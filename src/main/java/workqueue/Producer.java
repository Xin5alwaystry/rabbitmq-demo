package workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author xin5
 * @date 2021年01月23日 23:21:16
 */
public class Producer {
    @Test
    public void produceMessage() throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("hello", true, false, false, null);
        for (int i = 0; i < 20; i++) {
            channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, ("hello rabbitmq" + i).getBytes());
        }

        RabbitMQUtils.closeChannelAndConnection(channel, connection);
    }
}
