package vip.aquan.rocketmqdemo.producer;


import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * @author: wcp
 * @date: 2023/6/27 00:22
 * @Description: 普通消息Producer
 */

public class Producer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("java-group");
        //启动nameserver
        producer.setNamesrvAddr("127.0.0.1:9876");
        //启动生产者
        producer.start();
        //创建消息
        Message message = new Message(
                "Topic_Demo",       //主题
                "Tags_1",            //标记 过滤条件
                "Key_1",             // key 可以根据key获取具体的消息
                "hello2".getBytes(StandardCharsets.UTF_8)); //消息主体
        //发送消息
        SendResult result = producer.send(message);
        System.out.println("消息发送成功：" + result);
        //关闭消息
        producer.shutdown();
    }

}
