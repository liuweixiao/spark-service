package com.liu.annotation;

import com.google.common.collect.Lists;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

@Aspect
@Component
public class MyRedisLimiterApect {
    // https://www.cnblogs.com/binghe001/p/13412885.html
    private final Logger logger = LoggerFactory.getLogger(MyRedisLimiter.class);
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private RedisTemplate<String, Serializable> limitRedisTemplate;

    private DefaultRedisScript<List> redisScript;

    @PostConstruct
    public void init(){
        redisScript = new DefaultRedisScript<List>();
        redisScript.setResultType(List.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(("limit.lua"))));
    }

    @Autowired
    public MyRedisLimiterApect(RedisTemplate<String, Serializable> limitRedisTemplate) {
        this.limitRedisTemplate = limitRedisTemplate;
    }

    @Pointcut("execution(public * com.liu.controller.*.*(..))")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object process(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //使用反射获取MyRedisLimiter注解
        MyRedisLimiter myRedisLimiter = signature.getMethod().getDeclaredAnnotation(MyRedisLimiter.class);
        if(myRedisLimiter == null){
            //正常执行方法
            return proceedingJoinPoint.proceed();
        }
        //获取注解上的参数，获取配置的速率
        double value = myRedisLimiter.limitNum();
        //List设置Lua的KEYS[1]
        String key = myRedisLimiter.limitParamName() + System.currentTimeMillis() / 1000;
        List<String> keyList = Lists.newArrayList(key);

        //List设置Lua的ARGV[1]
        List<String> argvList = Lists.newArrayList(String.valueOf(value));

        //调用Lua脚本并执行
        List result = limitRedisTemplate.execute(redisScript, keyList, String.valueOf(value));
        logger.info("Lua脚本的执行结果：" + result);

        //Lua脚本返回0，表示超出流量大小，返回1表示没有超出流量大小。
        if("0".equals(result.get(0).toString())){
            fullBack();
            return null;
        }

        //获取到令牌，继续向下执行
        return proceedingJoinPoint.proceed();
    }

    private void fullBack() {
        System.out.println("开小差了，请稍后再试");
//        response.setHeader("Content-Type" ,"text/html;charset=UTF8");
//        PrintWriter writer = null;
//        try{
//            writer = response.getWriter();
//            writer.println("回退失败，请稍后阅读。。。");
//            writer.flush();
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            if(writer != null){
//                writer.close();
//            }
//        }
    }
}
