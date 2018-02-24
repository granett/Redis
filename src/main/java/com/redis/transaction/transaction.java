package com.redis.transaction;

import java.util.List;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class transaction {

    private Jedis jedis = new Jedis("192.168.1.207",6379);

    /**
     * MULTI
     * 标记一个事务块的开始，事务块内的多条命令会按照先后顺序被放进一个队列当中，最后由 EXEC命令原子性(atomic)地执行。
     * 返回值：总是返回 OK 。
     */
    @Test
    public void MULTI(){
    	Transaction multi = jedis.multi(); 
    	multi.set("a", "1");  
    	multi.set("b", "2");  
    	multi.del("c");
    }
    
    /**
     * EXEC
     * 执行所有事务块内的命令。
     * 返回值：事务块内所有命令的返回值，按命令执行的先后顺序排列。
     * 当操作被打断时，返回空值 nil 。
     */
    @Test
    public void EXEC(){
    	Transaction multi = jedis.multi();  
        multi.set("testabcd", "125");  
        multi.del("c");
        List<Object> exec = multi.exec();  
    }
    
    /**
     * WATCH key [key ...]
     * 监视一个(或多个) key ，如果在事务执行之前这个(或这些) key 被其他命令所改动，那么事务将被打断。
     * 返回值：总是返回 OK 。
     */
    @Test
    public void WATCH(){
    	String watch = jedis.watch("testabcd2");  
        System.out.println(watch);  
        Transaction multi = jedis.multi();  
        multi.set("testabcd", "125");  
        List<Object> exec = multi.exec();  
        System.out.println(exec);  
    }
    
    /**
     * UNWATCH
     * 取消 WATCH命令对所有 key 的监视。
     * 返回值：总是返回 OK 。
     */
    @Test
    public void UNWATCH(){
    	String watch = jedis.watch("testabcd2");  
        System.out.println(watch);  
        String unwatch = jedis.unwatch();
        System.out.println(unwatch);
    }
    /**
     * DISCARD
     * 取消事务，放弃执行事务块内的所有命令。
     * 返回值：总是返回 OK 。
     */
    @Test
    public void DISCARD(){
    	Transaction multi = jedis.multi();  
        multi.set("testabcd", "125");
        System.out.println(multi.discard());
        List<Object> exec = multi.exec();  
        System.out.println(exec);  
    }
   
}
