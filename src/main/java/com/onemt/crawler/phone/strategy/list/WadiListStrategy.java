package com.onemt.crawler.phone.strategy.list;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;
import com.onemt.crawler.phone.strategy.WadiMobilephoneStrategy;

@Component
public class WadiListStrategy implements ListStrategy {

	private static final String detailUrlPrefix = "https://en-sa.wadi.com/api/sawa/v1/u/%s";
	@Autowired
	private WadiMobilephoneStrategy wadiMobilephoneStrategy;

	public static void main(String[] args) throws UnsupportedEncodingException {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml",
				"spring/applicationContext-datasource.xml", "spring/applicationContext-redis.xml");
		context.start();
		Arrays.asList(context.getBeanDefinitionNames()).forEach(System.out::println);

		WadiListStrategy bean = (WadiListStrategy) context.getBean("wadiListStrategy");
		bean.saveCrawlerMobilephoneInfos();

		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String setCategroyUrl() {
		return "https://en-sa.wadi.com/api/sawa/v1/u/mobiles_and_accessories-mobile_phones";
	}

	@Override
	public void saveCrawlerMobilephoneInfos() {
		String contentStr = getContent(setCategroyUrl()).body().text();
		Integer pages = JsonPath.read(contentStr, "$.pages");
		for (int i = 1; i <= pages; i++) {
			String otherpage = setCategroyUrl() + "/?page=" + i;
			Document otherContentDoc = getContent(otherpage);
			String otherContent = otherContentDoc.body().text();
			List<String> links = JsonPath.read(otherContent, "$.data[*].groups.color[*].link");
			links.stream().map(link -> String.format(detailUrlPrefix, link)).distinct().parallel().forEach(link -> {
				wadiMobilephoneStrategy.saveCrawlerMobilephoneInfo(link);
			});
		}
		//wadiMobilephoneStrategy.saveCrawlerMobilephoneInfo("https://en-sa.wadi.com/api/sawa/v1/u/samsung-galaxy-s9-limited-edition-4gb-256gb-4g-lte-hybrid-dual-sim-black-2725888.html");
	}

}
