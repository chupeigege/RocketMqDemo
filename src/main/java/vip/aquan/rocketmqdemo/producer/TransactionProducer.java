package vip.aquan.rocketmqdemo.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

/**
 * @author: wcp
 * @date: 2023/6/27 00:56
 * @Description: 事务消息Producer
 */
public class TransactionProducer {
    public static void main(String[] args) throws MQClientException {
        //创建事务消息生产者
        TransactionMQProducer producer = new TransactionMQProducer("java_transaction_group");
        //指定NameServer
        producer.setNamesrvAddr("127.0.0.1:9876");
        //启动
        producer.start();
        //创建事务监听器
        TransactionListener listener = new TransactionListenerImpl();
        producer.setTransactionListener(listener);
        //创建线程池
        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                5,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = newThread(r);
                thread.setName("client-tanscation-msg-check-thread");
                return thread;
            }
        });
        producer.setExecutorService(executorService);
        //创建消息
        Message message = new Message(
                "Topic_T",       //主题
                "Tags_T",            //标记 过滤条件
                "Key_T",             // key 可以根据key获取具体的消息
                "hello!-transaction".getBytes(StandardCharsets.UTF_8)); //消息主体
        //发送事务消息
        TransactionSendResult result = producer.sendMessageInTransaction(message, "hello-transaction");
        System.out.println("消息结果：" + result);
        //关闭资源
        producer.shutdown();
    }
}
