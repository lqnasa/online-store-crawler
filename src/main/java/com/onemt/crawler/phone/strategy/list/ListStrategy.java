package com.onemt.crawler.phone.strategy.list;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public interface ListStrategy {
	
	/**
	 * 配置抓取栏目url
	 * @return
	 */
	String setCategroyUrl();
	
	void saveCrawlerMobilephoneInfos();
	
	default Document getContent(String url) {
		try {
			Document document = Jsoup.connect(url)
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36")
					.followRedirects(true).timeout(80000).ignoreContentType(true).validateTLSCertificates(false).get();
			return document;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
