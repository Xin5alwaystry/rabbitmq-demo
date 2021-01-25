package fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author xin5
 * @date 2021年01月24日 16:56:48
 */
public class Producer {
    /**
     * fanout 广播模式
     * @throws IOException
     */
    @Test
    public void produceMessage() throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //申明交换机
        channel.exchangeDeclare("logs", "fanout");

        channel.basicPublish("logs", "", null, "hello fanout!".getBytes());

        RabbitMQUtils.closeChannelAndConnection(channel, connection);
    }
}
