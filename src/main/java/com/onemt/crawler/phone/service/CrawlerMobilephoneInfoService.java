package com.onemt.crawler.phone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onemt.crawler.phone.bean.CrawlerMobilephoneInfo;
import com.onemt.crawler.phone.dao.CrawlerMobilephoneInfoDao;

@Service
public class CrawlerMobilephoneInfoService {
	
	@Autowired
	private CrawlerMobilephoneInfoDao crawlerMobilephoneInfoDao;
	
	public void insertBatch(List<CrawlerMobilephoneInfo> list){
		crawlerMobilephoneInfoDao.insertBatch(list);
	}

	
	public void insertTemplate(CrawlerMobilephoneInfo entity){
		crawlerMobilephoneInfoDao.insertTemplate(entity);
	}
}
