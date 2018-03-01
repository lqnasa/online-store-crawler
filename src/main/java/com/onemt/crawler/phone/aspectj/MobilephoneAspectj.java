package com.onemt.crawler.phone.aspectj;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MobilephoneAspectj {
	private static final Logger logger = LoggerFactory.getLogger(MobilephoneAspectj.class);

	private static final String storagedUrls = "crawler:mobilephone:storaged_urls";
	
	@Resource(name = "jedisTemplate")
	private SetOperations<String, String> setOperations;
	
	@Pointcut("execution(* com.onemt.crawler.phone.strategy..*.saveCrawlerMobilephoneInfo(java.lang.String))&& args(url)")
	public void pointCut(String url) {
	}

	@Around(value = "pointCut(url)", argNames = "url")
	public void around(ProceedingJoinPoint joinPoint, String url){
		if (setOperations.add(storagedUrls, url) == 0) {
			System.out.println("================= around pointCut 该Url:"+url+" 已经入库,执行过滤.==================");
			return;
		}
		try {
			joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			setOperations.remove(storagedUrls, url);
			System.out.println("================= pointCut 该Url:"+url+" 入库异常,移除redis中已记入状态.============= ");
		}
	}

}
