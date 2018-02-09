package com.redis.base;

import redis.clients.jedis.Jedis;
import org.junit.Test;

public class RedisString {

    private Jedis jedis = new Jedis("192.168.1.207",6379);
    private static final String KEY = "key";
    private static final String VALUE = "zx";

    /**
     * 	set(String key, String value, String nxxx, String expx, long time)
     * 	必填参数
     *  key
     *  value
     * 可选参数
     * nxxx - NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist.
                NX ：只在键不存在时，才对键进行设置操作。 SET key value NX 效果等同于 SETNX key value
                XX ：只在键已经存在时，才对键进行设置操作。
     * expx EX|PX, expire time units: EX = seconds; PX = milliseconds
                EX second ：设置键的过期时间为 second 秒； SET key value EX second 效果等同于 SETEX key second value 。
                PX second ：设置键的过期时间为 milliseconds 毫秒； SET key value PX millisecond 效果等同于 PSETEX key millisecond
     * time - expire time in the units of expx
     */
    @Test
    public void SET() {
        //设置不存在key为name时设置其值为layman并在15秒后过期
        jedis.set(KEY, VALUE, "NX", "EX", 15);
        out(jedis.get(KEY));
    }

    /**
     * MSET [key value ...]  同时设置一个或多个 key-value 对，如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，如果这不是你所希望的效果，请考虑使用 MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作。
     * MGET [key ...]批量返回所有给定 key 的值。如果给定的key里面,有某个key不存在，那么这个key返回null
     *MSET 是一个原子性(atomic)操作，所有给定 key 都会在同一时间内被设置，某些给定 key 被更新而另一些给定 key 没有改变的情况，不可能发生。
     */
    @Test
    public void MSET() {
        jedis.mset("name", "zx", "age", "18");
        out(jedis.mget("name", "age"));
    }

    /**
     * GET key
     * 返回 key 所关联的字符串值
     * key 不存在那么返回null
     * key 储存的值不是字符串类型，返回一个错误
     */
    @Test
    public void GET() {
        out(jedis.get(KEY));
    }

    /**
     * GETSET key value
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     */
    @Test
    public void GETSET() {
        jedis.getSet(KEY, "aaa");
        out(jedis.getSet(KEY, "leo"));
        out(jedis.get(KEY));
    }

    /**
     * APPEND key value
     * 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。
     * 如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key value 一样。
     */
    @Test
    public void APPEND() {
        jedis.getSet(KEY, "aaa");
        jedis.append("name", "APPEND");
        out(jedis.get(KEY));
    }

    /**
     * SETRANGE key offset value
     * 用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始。
     * 不存在的 key 当作空白字符串处理。
     * SETRANGE 命令会确保字符串足够长以便将 value 设置在指定的偏移量上，
     * 如果给定key原来储存的字符串长度比偏移量小(比如字符串只有 5 个字符长，但你设置的 offset 是 10 )，
     * 那么原字符和偏移量之间的空白将用零字节(zerobytes, "\x00" )来填充。
     */
    @Test
    public void SETRANGE() {
        SET();
        jedis.setrange(KEY, 4L, "leo");
        GET();
    }

    /**
     * GETRANGE key start end
     * 返回 key 中字符串值的子字符串，字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
     * 负数偏移量表示从字符串最后开始计数， -1 表示最后一个字符， -2 表示倒数第二个，以此类推。
     */
    @Test
    public void GETRANGE() {
        SET();
        out(jedis.getrange(KEY, 2, -3));
    }

    /**
     * 返回 key 所储存的字符串值的长度。
     */
    @Test
    public void STRLEN() {
        SET();
        out(jedis.strlen(KEY));
    }

    /**
     * INCR key
     * 将 key 中储存的数字值增一。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     */
    @Test
    public void INCR() {
        jedis.incr(KEY);
        GET();
    }

    /**
     * INCRBY key increment     INCRBYFLOAT key increment
     * 将 key 所储存的值加上增量 increment 。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     */
    @Test
    public void INCRBY() {
        jedis.incrBy(KEY, 24);
        GET();
        jedis.incrByFloat(KEY, 0.22225);
        GET();
    }

    private void out(Object msg) {
        System.out.println(msg);
    }
}
