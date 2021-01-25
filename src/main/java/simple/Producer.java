package simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;
import utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xin5
 * @date 2021年01月12日 23:41:01
 */
public class Producer {
    @Test
    public void produceMessage() throws IOException, TimeoutException {
//        //新建一个创建mq连接的工厂
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        //设置mq服务端的主机地址
//        connectionFactory.setHost("localhost");
//        //设置端口
//        connectionFactory.setPort(5672);
//        //设置virtual host
//        connectionFactory.setVirtualHost("/linxw");
//        //设置拥有上述虚拟主机的账号
//        connectionFactory.setUsername("linxw");
//        //设置密码
//        connectionFactory.setPassword("123");
//
//        //获取连接对象
//        Connection connection = connectionFactory.newConnection();

        Connection connection = RabbitMQUtils.getConnection();

        if (connection == null) {
            System.out.println("Connection is null.");
            return;
        }

        //获取信道
        Channel channel = connection.createChannel();

        //信道绑定对应的消息队列（声明）
        //申明队列不是必须的，可在已有的队列中发布消息
        //参数1：队列名称，如果队列不存在则自动创建
        //参数2：定义队列是否持久化, rabbitmq重启后队列是否存在
        //参数3：定义该连接是否独占队列
        //参数4：定义是否在消费队列后自动删除队列
        //参数5：额外附加的参数
        channel.queueDeclare("hello", true, false, false, null);

        //发布消息
        //参数1：指定交换机
        //参数2：指定队列名称
        //参数3：传递消息额外设置
        //参数4：消息的具体内容
        //MessageProperties.PERSISTENT_TEXT_PLAIN 未消费消息可持久化
        channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, "hello rabbitmq".getBytes());

//        //关闭信道
//        channel.close();
//        //关闭
//        connection.close();
        RabbitMQUtils.closeChannelAndConnection(channel, connection);
    }
}
