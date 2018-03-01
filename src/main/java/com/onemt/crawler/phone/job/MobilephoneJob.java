package com.onemt.crawler.phone.job;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.onemt.crawler.phone.strategy.list.SouqListStrategy;
import com.onemt.crawler.phone.strategy.list.WadiListStrategy;

@Component
@DisallowConcurrentExecution
public class MobilephoneJob {
	
	@Autowired
	private WadiListStrategy wadiListStrategy;
	@Autowired 
	private SouqListStrategy souqListStrategy;
	
	@Scheduled(cron = "0 */1 * * * ?")
	public void init(){
		wadiListStrategy.saveCrawlerMobilephoneInfos();
		souqListStrategy.saveCrawlerMobilephoneInfos();
	}

}
