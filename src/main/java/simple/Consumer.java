package simple;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xin5
 * @date 2021年01月12日 23:41:55
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtils.getConnection();

        if (connection == null) {
            System.out.println("Connection is null.");
            return;
        }
        //获取信道
        Channel channel = connection.createChannel();

        //信道绑定对应的消息队列（声明）
        //参数1：队列名称，如果队列不存在则自动创建
        //参数2：定义队列是否持久化
        //参数3：定义该连接是否独占队列
        //参数4：定义是否在消费队列后自动删除队列
        //参数5：额外附加的参数
        //channel.queueDeclare("hello", false, false, false, null);

        //参数1：要消费的队列名称
        //参数2：开启消息的自动确认机制
        //参数3：重写消费消息的回调方法
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Consuming message : " + new String(body));
            }
        });
    }
}
