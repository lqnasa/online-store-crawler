package com.onemt.crawler.phone.strategy;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.onemt.crawler.phone.bean.CrawlerMobilephoneInfo;
import com.onemt.crawler.phone.service.CrawlerMobilephoneInfoService;

public abstract class AbstractMobilephoneStrategy implements MobilephoneStrategy {

	@Autowired
	private CrawlerMobilephoneInfoService crawlerMobilephoneInfoService;

	public void saveCrawlerMobilephoneInfo(String url) {
		Document detailContent = getContent(url);
		if (detailContent != null && detailContent.hasText()) {
			CrawlerMobilephoneInfo crawlerMobilephoneInfo = new CrawlerMobilephoneInfo();
			crawlerMobilephoneInfo.setBrand(getBrand(detailContent));
			crawlerMobilephoneInfo.setModelNumber(getModelNumber(detailContent));
			crawlerMobilephoneInfo.setOperatingSystem(getOperatingSystem(detailContent));
			crawlerMobilephoneInfo.setDisplayResolution(getDisplayResolution(detailContent));
			crawlerMobilephoneInfo.setRom(getRom(detailContent));
			crawlerMobilephoneInfo.setRam(getRam(detailContent));
			crawlerMobilephoneInfo.setDisplaySize(getDisplaySize(detailContent));
			crawlerMobilephoneInfo.setCellularNetworkTechnology(getCellularNetworkTechnology(detailContent));
			crawlerMobilephoneInfo.setColor(getColor(detailContent));
			crawlerMobilephoneInfo.setPrice(getPrice(detailContent));
			crawlerMobilephoneInfo.setOriginalPrice(getOriginalPrice(detailContent));
			crawlerMobilephoneInfo.setCurrencyCode(getCurrencyCode(detailContent));
			crawlerMobilephoneInfo.setSourceUrl(url);
			crawlerMobilephoneInfoService.insertTemplate(crawlerMobilephoneInfo);
		}
	}


}
