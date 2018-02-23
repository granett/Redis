package com.redis.pubsub;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

public class Subscribe {

    private Jedis jedis = new Jedis("192.168.1.207",6379);

    /**
     * SUBSCRIBE channel [channel ...]
     * 订阅给定的一个或多个频道的信息
     */
    @Test
    public void subscribe(){
        final PubSubListener listener = new PubSubListener();
        jedis.subscribe(listener, "channel");
    }

    /**
     * UNSUBSCRIBE [channel [channel ...]]
     * 指示客户端退订给定的频道
     * 如果没有频道被指定，即一个无参数的 UNSUBSCRIBE 调用被执行，
     * 那么客户端使用 SUBSCRIBE 命令订阅的所有频道都会被退订。
     * 在这种情况下，命令会返回一个信息，告知客户端所有被退订的频道。
     */
    @Test
    public void unsubscribe(){
        final PubSubListener listener = new PubSubListener();
        listener.unsubscribe("channel");
    }

    /**
     * PSUBSCRIBE pattern [pattern ...]
     * 订阅一个或多个符合给定模式的频道
     * 每个模式以 * 作为匹配符，比如 it* 匹配所有以 it 开头的频道( it.news 、 it.blog 、 it.tweets 等等)，
     * news.* 匹配所有以 news. 开头的频道( news.it 、 news.global.today 等等)，诸如此类。
     */
    @Test
    public void psubscribe(){
        final PubSubListener listener = new PubSubListener();
        jedis.psubscribe(listener, "ch*");
    }

    /**
     * PUNSUBSCRIBE [pattern [pattern ...]]
     * 指示客户端退订所有给定模式
     * 如果没有模式被指定，即一个无参数的 PUNSUBSCRIBE 调用被执行，
     * 那么客户端使用 PSUBSCRIBE 命令订阅的所有模式都会被退订。
     * 在这种情况下，命令会返回一个信息，告知客户端所有被退订的模式。
     */
    @Test
    public void punsubscribe(){
        final PubSubListener listener = new PubSubListener();
        listener.punsubscribe("ch*");
    }

    /**
     * PUBLISH channel message
     * 将信息 message 发送到指定的频道 channel
     * 返回值：接收到信息 message 的订阅者数量
     */
    @Test
    public void publish(){
        jedis.publish("channel", "bar123");
        System.out.println("发布消息");
    }

    /**
     * PUBSUB CHANNELS [pattern]
     * 列出当前的活跃频道。 活跃频道指的是那些至少有一个订阅者的频道， 订阅模式的客户端不计算在内。
     * pattern 参数是可选的：
     *    如果不给出 pattern 参数，那么列出订阅与发布系统中的所有活跃频道。
     *    如果给出 pattern 参数，那么只列出和给定模式 pattern 相匹配的那些活跃频道。
     */
    @Test
    public void PUBSUB(){
        List<String> list = jedis.pubsubChannels("*");
        System.out.println(list);
    }

    /**
     * PUBSUB NUMSUB [channel-1 ... channel-N]
     * 返回给定频道的订阅者数量， 订阅模式的客户端不计算在内。
     */
    @Test
    public void pubsubNumSub(){
        Map<String,String> map = jedis.pubsubNumSub();
        System.out.println(map);
    }

    /**
     * PUBSUB NUMPAT
     * 返回订阅模式的数量。
     * 注意， 这个命令返回的不是订阅模式的客户端的数量， 而是客户端订阅的所有模式的数量总和。
     */
    @Test
    public void pubsubNumPat(){
        Long count = jedis.pubsubNumPat();
        System.out.println(count);
    }

}
