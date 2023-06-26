package vip.aquan.rocketmqdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Rocketmq简单demo，链接：https://blog.csdn.net/QQ_hoverer/article/details/120115732
 * 下载Rocketmq安装包， 启动mqnamesrv.cmd、启动mqbroker.cmd
 * 普通消息、顺序消息、事务消息、广播消息
 */
@SpringBootApplication
public class RocketmqdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketmqdemoApplication.class, args);
    }

}
