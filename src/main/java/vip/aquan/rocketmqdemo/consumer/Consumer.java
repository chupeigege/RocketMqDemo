package vip.aquan.rocketmqdemo.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

import static org.apache.rocketmq.remoting.common.RemotingHelper.DEFAULT_CHARSET;

/**
 * @author: wcp
 * @date: 2023/6/27 00:31
 * @Description: 普通消息Consumer
 */
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        //创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("java-group");
        //指定nameserver
        consumer.setNamesrvAddr("127.0.0.1:9876");
        //设置消息最大拉取数
        consumer.setConsumeMessageBatchMaxSize(2);
        //消息订阅
        consumer.subscribe("Topic_Demo", "*");
        //创建监听
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt messageExt : list) {
                    try {
                        System.out.println("消息id：" + messageExt.getMsgId());
                        //消息主题
                        String topic = messageExt.getTopic();
                        //标记
                        String tags = messageExt.getTags();
                        //消息主体
                        String msg = new java.lang.String(messageExt.getBody(), DEFAULT_CHARSET);
                        System.out.println("消费消息：" + "主题：" + topic + "==" + "标记：" + tags + "==" + "消息主体" + msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
