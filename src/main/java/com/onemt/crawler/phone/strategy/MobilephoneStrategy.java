package com.onemt.crawler.phone.strategy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public interface MobilephoneStrategy {

	/**
	 * 获取品牌
	 * 
	 * @param content
	 * @return
	 */
	String getBrand(Document content);

	/**
	 * 获取 型号
	 * 
	 * @return
	 */
	String getModelNumber(Document content);

	/**
	 * 系统版本
	 * 
	 * @param content
	 * @return
	 */
	String getOperatingSystem(Document content);

	/**
	 * 分辨率
	 * 
	 * @param content
	 * @return
	 */
	String getDisplayResolution(Document content);

	/**
	 * 存储内存
	 * 
	 * @param content
	 * @return
	 */
	String getRom(Document content);

	/**
	 * 运行内存
	 * 
	 * @param content
	 * @return
	 */
	String getRam(Document content);

	/**
	 * 屏幕尺寸
	 * 
	 * @param content
	 * @return
	 */
	String getDisplaySize(Document content);

	/**
	 * 网络
	 * 
	 * @param content
	 * @return
	 */
	String getCellularNetworkTechnology(Document content);

	/**
	 * 颜色
	 * 
	 * @param content
	 * @return
	 */
	String getColor(Document content);

	/**
	 * 当前售价
	 * 
	 * @param content
	 * @return
	 */
	String getPrice(Document content);
	
	/**
	 * 原价
	 * @param content
	 * @return
	 */
	String getOriginalPrice(Document content); 
	
	/**
	 * 货币代码
	 * @param content
	 * @return
	 */
	String getCurrencyCode(Document content); 

	default Document getContent(String url) {
		try {
			Document document = Jsoup.connect(url)
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36")
					.followRedirects(true).timeout(80000).ignoreContentType(true).get();
			return document;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
