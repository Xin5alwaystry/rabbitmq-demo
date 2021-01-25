package direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author xin5
 * @date 2021年01月24日 21:05:05
 */
public class Producer {
    @Test
    public void produceMessage() throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "logs_direct";

        channel.exchangeDeclare(exchangeName, "direct");

        //info, warning, error
        String routingKey = "warning";
        channel.basicPublish(exchangeName, routingKey, null, String.format("基于direct模型发布的routingKey为[%s]的消息", routingKey).getBytes());
        RabbitMQUtils.closeChannelAndConnection(channel, connection);
    }
}
