package com.luo.jedis;
 
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
 
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
 
/**
 * @author huang: 
 * @version 创建时间：2018-8-30 下午3:23:21 
 * @introduction
 */
public class JedisDemo {
 
	/**
	 * 单例测试demo1
	 * @param args
	 */
	@Test
	public void demo1(){
		//1.设置ip地址和端口
		Jedis jedis=new Jedis("127.0.0.1",6379);//ip地址，端口号
		//2.保存数据
		for(int i=0;i<2000000;i++) {
			jedis.set("i",""+i);
		}
		//3.获取数据
		double start=System.currentTimeMillis();
		for(int i=0;i<2000000;i++) {
			jedis.get("i");
		}
		System.out.println(System.currentTimeMillis()-start);
		//String value1=jedis.get("1");
		//System.out.println(value1+"guo");
		//4.释放资源
		jedis.close();
	}
	@Test
	public void demo3(){
		Map map=new HashMap();
		for(int i=0;i<2000000;i++) {
			map.put(i, i);
		}
		double start=System.currentTimeMillis();
		for(int i=0;i<2000000;i++) {
			map.get(i);
		}
		System.out.println(System.currentTimeMillis()-start);
		
		
	}
	/*
	 * 使用连接池的方式
	 */
	@Test
	public void demo2(){
		//获取连接池配置对象
		JedisPoolConfig config=new JedisPoolConfig();
		//设置最大连接数
		config.setMaxTotal(30);
		//设置最大空闲连接
		config.setMaxIdle(10);
		//获得连接池
		JedisPool jedisPool=new JedisPool(config,"127.0.0.1",6379);//ip地址，端口号
		Jedis jedis=null;
		try{
			//通过连接池获得连接
			jedis=jedisPool.getResource();
			//设置数据
			jedis.set("name", "今天下雨啊啊啊");
			//获取数据
			String value=jedis.get("name");
			System.out.println(value+"liuo");
			//释放资源
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
			if(jedisPool!=null){
				jedisPool.close();
			}
		}
	}
}
