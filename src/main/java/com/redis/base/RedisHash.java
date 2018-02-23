package com.redis.base;
import redis.clients.jedis.Jedis;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisHash {

    private Jedis jedis = new Jedis("192.168.1.207",6379);
    private static final String KEY = "hash";
    private static final String FIELD = "name";
    private static final String VALUE = "layman";

    /**
     * HSET key field value
     * 将哈希表 key 中的域 field 的值设为 value 。
     * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
     * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
     * <p/>
     * HMSET key field value [field value ...]
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
     * 此命令会覆盖哈希表中已存在的域。
     * 如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作。
     * <p/>
     * HSETNX key field value
     * 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。
     * 若域 field 已经存在，该操作无效。
     * 如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。
     */
    @Test
    public void HSET() {
        jedis.hset(KEY, FIELD, VALUE);
        HGETALL();
        Map<String, String> map = new HashMap<String, String>();
        map.put(FIELD + 0, VALUE + 0);
        map.put(FIELD + 1, VALUE + 1);
        map.put(FIELD + 2, VALUE + 2);
        jedis.hmset(KEY, map);
        HGETALL();
    }

    /**
     * HGET key field
     * 返回哈希表 key 中给定域 field 的值。
     * <p/>
     * HMGET key field [field ...]
     * 返回哈希表 key 中，一个或多个给定域的值。
     * 如果给定的域不存在于哈希表，那么返回一个 nil 值。
     * 因为不存在的 key 被当作一个空哈希表来处理，所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表。
     * <p/>
     * HGETALL key
     * 返回哈希表 key 中，所有的域和值。
     * 在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。
     */
    @Test
    public void HGETALL() {
        Map<String, String> map = jedis.hgetAll(KEY);
        System.out.println(map);
    }

    /**
     * HKEYS key
     * 返回哈希表 key 中的所有域。
     */
    @Test
    public void HKEYS() {
        Set<String> hkeys = jedis.hkeys(KEY);
        System.out.println(hkeys);
    }

    /**
     * HVALS key
     * 返回哈希表 key 中所有域的值。
     */
    @Test
    public void HVALS() {
        HKEYS();
        List<String> hvals = jedis.hvals(KEY);
        System.out.println(hvals);
    }

    /**
     * HEXISTS key field
     * 查看哈希表 key 中，给定域 field 是否存在。
     */
    @Test
    public void HEXISTS() {
        System.out.println(jedis.hexists(KEY, FIELD));
        System.out.println(jedis.hexists(KEY, FIELD + 4));
    }

    /**
     * HDEL key field [field ...]
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
     */
    @Test
    public void HDEL() {
        HGETALL();
        jedis.hdel(KEY, FIELD + 0);
        HGETALL();
    }

    /**
     * HINCRBY key field increment
     * 为哈希表 key 中的域 field 的值加上增量 increment 。
     * 增量也可以为负数，相当于对给定域进行减法操作。
     * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。
     * 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
     * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。
     * 本操作的值被限制在 64 位(bit)有符号数字表示之内。
     * <p/>
     * HINCRBYFLOAT key field increment
     * 为哈希表 key 中的域 field 加上浮点数增量 increment 。
     * 如果哈希表中没有域 field ，那么 HINCRBYFLOAT 会先将域 field 的值设为 0 ，然后再执行加法操作。
     * 如果键 key 不存在，那么 HINCRBYFLOAT 会先创建一个哈希表，再创建域 field ，最后再执行加法操作。
     * 当以下任意一个条件发生时，返回一个错误：
     * 域 field 的值不是字符串类型(因为 redis 中的数字和浮点数都以字符串的形式保存，所以它们都属于字符串类型）
     * 域 field 当前的值或给定的增量 increment 不能解释(parse)为双精度浮点数(double precision floating point number)
     */
    @Test
    public void HINCRBY() {
        jedis.hset(KEY, FIELD + 0, "0");
        HGETALL();
        jedis.hincrBy(KEY, FIELD + 0, 24);
        HGETALL();
    }

    /**
     * HLEN key
     * 返回哈希表 key 中域的数量。
     */
    @Test
    public void HLEN() {
        System.out.println(jedis.hlen(KEY));
    }

    /**
     * HSTRLEN key field         3.2以后才有
     * 返回哈希表 key 中， 与给定域 field 相关联的值的字符串长度（string length）。
     * 如果给定的键或者域不存在， 那么命令返回 0 。
     */
    @Test
    public void HSTRLEN() {
    }
}
