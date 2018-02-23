package com.redis.base;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.Set;

public class RedisSet {

    private Jedis jedis = new Jedis("192.168.1.207",6379);
    private static final String KEY = "set";
    private static final String VALUE = "layman";

    /**
     * SADD key member [member ...]
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
     * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
     * 当 key 不是集合类型时，返回一个错误。
     */
    @Test
    public void SADD() {
        jedis.sadd(KEY, VALUE + 2, VALUE + 0, VALUE + 1);
        SMEMBERS();
    }

    /**
     * SCARD key
     * 返回集合 key 的基数(集合中元素的数量)。
     */
    @Test
    public void SCARD() {
        System.out.println(jedis.scard(KEY));
    }

    /**
     * SDIFF key [key ...]
     * 返回一个集合的全部成员，该集合是所有给定集合之间的差集。
     * 不存在的 key 被视为空集。
     * <p/>
     * SDIFFSTORE destination key [key ...]
     * 这个命令的作用和 SDIFF 类似，但它将结果保存到 destination 集合，而不是简单地返回结果集。
     * 如果 destination 集合已经存在，则将其覆盖。
     * destination 可以是 key 本身。
     */
    @Test
    public void SDIFF() {
        SMEMBERS();
        jedis.sadd(KEY + 0, VALUE + 1, VALUE + 3);
        System.out.println(jedis.smembers(KEY + 0));
        System.out.println(jedis.sdiff(KEY, KEY + 0));//返回值为KEY-KEY0的值
    }

    /**
     * SINTER key [key ...]
     * 返回一个集合的全部成员，该集合是所有给定集合的交集。
     * 不存在的 key 被视为空集。
     * 当给定集合当中有一个空集时，结果也为空集(根据集合运算定律)。
     * <p/>
     * SINTERSTORE destination key [key ...]
     * 这个命令类似于 SINTER 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。
     * 如果 destination 集合已经存在，则将其覆盖。
     * destination 可以是 key 本身。
     */
    @Test
    public void SINTER() {
        System.out.println(jedis.sinter(KEY, KEY + 0));
    }

    /**
     * SISMEMBER key member
     * 判断 member 元素是否集合 key 的成员。
     */
    @Test
    public void SISMEMBER() {
        System.out.println(jedis.sismember(KEY, VALUE + 0));
        System.out.println(jedis.sismember(KEY, VALUE + 5));
    }

    /**
     * SMEMBERS key
     * 返回集合 key 中的所有成员。
     * 不存在的 key 被视为空集合。
     */
    @Test
    public void SMEMBERS() {
        Set<String> smembers = jedis.smembers(KEY);
        System.out.println(smembers);
    }

    /**
     * SMOVE source destination member
     * 将 member 元素从 source 集合移动到 destination 集合。
     * SMOVE 是原子性操作。
     * 如果source集合不存在或不包含指定的member元素，则SMOVE命令不执行任何操作，仅返回0。否则,member元素从source集合中被移除，并添加到destination 集合中去。
     * 当 destination 集合已经包含 member 元素时， SMOVE 命令只是简单地将 source 集合中的 member 元素删除。
     * 当 source 或 destination 不是集合类型时，返回一个错误。
     */
    @Test
    public void SMOVE() {
        System.out.println(jedis.smembers(KEY));
        System.out.println(jedis.smembers(KEY + 0));
        jedis.smove(KEY, KEY + 0, "layman0");
        System.out.println(jedis.smembers(KEY));
        System.out.println(jedis.smembers(KEY + 0));
    }

    /**
     * SPOP key
     * 移除并返回集合中的一个随机元素。
     * 如果只想获取一个随机元素，但不想该元素从集合中被移除的话，可以使用 SRANDMEMBER 命令。
     */
    @Test
    public void SPOP() {
        SMEMBERS();
        System.out.println(jedis.spop(KEY));
        SMEMBERS();
    }

    /**
     * SRANDMEMBER key [count]
     * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素。
     * 从 Redis 2.6 版本开始， SRANDMEMBER 命令接受可选的 count 参数：
     * 如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count 大于等于集合基数，那么返回整个集合。
     * 如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
     * 该操作和 SPOP 相似，但 SPOP 将随机元素从集合中移除并返回，而 SRANDMEMBER 则仅仅返回随机元素，而不对集合进行任何改动。
     */
    @Test
    public void SRANDMEMBER() {
        System.out.println(jedis.smembers(KEY + 0));
        System.out.println(jedis.srandmember(KEY + 0, 2));
        System.out.println(jedis.srandmember(KEY + 0, -2));
    }

    /**
     * SREM key member [member ...]
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
     * 当 key 不是集合类型，返回一个错误。
     */
    @Test
    public void SREM() {
        SMEMBERS();
        jedis.srem(KEY, "layman1");
        SMEMBERS();
    }

    /**
     * SUNION key [key ...]
     * 返回一个集合的全部成员，该集合是所有给定集合的并集。
     * 不存在的 key 被视为空集。
     * <p/>
     * SUNIONSTORE destination key [key ...]
     * 这个命令类似于 SUNION 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。
     * 如果 destination 已经存在，则将其覆盖。
     * destination 可以是 key 本身。
     */
    @Test
    public void SUNION() {
        SMEMBERS();
        jedis.sadd(KEY + 0, "layman" + new Random().nextInt(50));
        System.out.println(jedis.sunion(KEY, KEY + 0));
    }
}
