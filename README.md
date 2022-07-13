# toolkit-jedis 插件使用

## 支持以下数据结构

|  1| 数据类型 | 使用方式                         |
|  ----  | ----  |------------------------------|
| 1 | String | jedisService.opsForValue().set(...) |
| 2 | List | jedisService.opsForList().lpush(...) |
| 3 | Map | jedisService.opsForHash().set(...)  |
| 4 | Set | jedisService.opsForSet().set(...)  |
| 5 | Zet | jedisService.opsForZSet().set(...) |

## 使用说明

```xml

<dependencies>
    <dependency>
        <groupId>cn.vimor.jedis</groupId>
        <artifactId>toolkit-jedis</artifactId>
        <version>1.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```

## 支持自定义配置

```yml
#Redis配置
jedis:
  host: 127.0.0.1 #ip地址
  port: 6379 #端口号
  auth: a23456 #认证密码
  maxActive: 1024 #最大连接数
  maxIdle: 200 #最大闲置连接数
  maxWait: 10000 #最长等待时间
  timeOut: 10000 #连接超时时间
  testOnBorrow: true #连接验证
  defaultDataBase: 0 #默认db
  clientName: jedis-client #jedis客户端名
```

> jedisPool使用重量级锁保证线程安全，全局调用jedisPool生成jedis实例的地方并不多，并发不会太多，不会影响效率

## 锁实现

基于redis源生指令的全局分布式锁

```java
   public class RedisApplicationTest {
    @Resource
    GlobalLock globalLock;

    @Test
    void contextLoads() {
        /* 参照CAS的轻量锁，尝试取锁，取不到锁时内部会自旋等待下一次取锁,实现类 GlobalLockImpl*/
        //获取锁
        globalLock.lock("test-demo01");
        jedisService.opsForString().set("test01", "test01String");
        //释放锁
        globalLock.unLock("test-demo01");
    }
}
```
# toolkit-jedis
