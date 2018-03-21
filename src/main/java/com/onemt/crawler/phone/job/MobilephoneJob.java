package com.onemt.crawler.phone.job;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.onemt.crawler.phone.strategy.list.DealsSouqListStrategy;
import com.onemt.crawler.phone.strategy.list.GsmarenaListStrategy;
import com.onemt.crawler.phone.strategy.list.JumiaListStrategy;
import com.onemt.crawler.phone.strategy.list.SouqListStrategy;
import com.onemt.crawler.phone.strategy.list.WadiListStrategy;

@Component
@DisallowConcurrentExecution
public class MobilephoneJob {
	
	@Autowired
	private WadiListStrategy wadiListStrategy;
	@Autowired 
	private SouqListStrategy souqListStrategy;
	@Autowired 
	private DealsSouqListStrategy dealsSouqListStrategy;
	@Autowired
	private GsmarenaListStrategy gsmarenaListStrategy;
	@Autowired
	private JumiaListStrategy jumiaListStrategy;
	
	
	@Scheduled(cron = "0 */1 * * * ?")
	public void init(){
		wadiListStrategy.saveCrawlerMobilephoneInfos();
		souqListStrategy.saveCrawlerMobilephoneInfos();
		
		//第二批
		dealsSouqListStrategy.saveCrawlerMobilephoneInfos();
		gsmarenaListStrategy.saveCrawlerMobilephoneInfos();
		jumiaListStrategy.saveCrawlerMobilephoneInfos();
		
	}

}
