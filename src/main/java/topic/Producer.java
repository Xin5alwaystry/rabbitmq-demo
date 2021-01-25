package topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author xin5
 * @date 2021年01月25日 21:39:49
 */
public class Producer {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "topics";
        //topic：动态路由模型，消费者绑定的routingKey可使用通配符
        //'*'可替代一个单词，'#'可替代多个单词
        channel.exchangeDeclare(exchangeName, "topic");

        String routingKey = "save.user";
        channel.basicPublish(exchangeName, routingKey, null, String.format("来自routingkey[%s]的消息", routingKey).getBytes());

        RabbitMQUtils.closeChannelAndConnection(channel, connection);
    }
}
