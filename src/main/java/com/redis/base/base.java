package com.redis.base;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.*;

public class base {
    public static void main(String[] args) {
        //连接Redis 服务
        Jedis jedis = new Jedis("192.168.1.207",6379);
        //jedis.auth("密码");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        // String操作
        jedis.set("runoobkey", "www.runoob.com");
        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
        //List入队
        jedis.lpush("list","a");
        jedis.lpush("list","b");
        jedis.lpush("list","c");
        //List出队
        System.out.println( jedis.lpop("list"));
        //List长度
        System.out.println(jedis.llen("list"));
        //List遍历
        List<String> list = jedis.lrange("list", 0 ,10);
        for(int i=0; i<list.size(); i++) {
            System.out.println("List为: "+list.get(i));
        }
        //Set操作
        jedis.sadd("set", "set1", "set2","set3");
        Set<String> smembers = jedis.smembers("set");
        System.out.println(smembers);
        //Hash操作
        jedis.hset("hash", "name", "zx");
        Map<String, String> map = new HashMap<String, String>();
        map.put("name0", "zx0");
        map.put("name1", "zx1");
        map.put("name2", "zx2");
        jedis.hmset("hash", map);
        Map<String, String> resmap = jedis.hgetAll("hash");
        System.out.println(resmap);
        //SortedSet操作
        Map<String, Double> sourceMember = new HashMap<String, Double>();
        for (int i = 0; i < 3; i++) {
            double score = i;
            sourceMember.put("stu" + score, score);
        }
        jedis.zadd("sortedset", sourceMember);
        Set<Tuple> tuples = jedis.zrangeWithScores("sortedset", 0, -1);
        for (Tuple tuple : tuples) {
            System.out.println(tuple.getElement() + ":" + tuple.getScore());
        }
    }
}

