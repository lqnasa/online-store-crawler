package com.onemt.crawler.phone.strategy.list;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.onemt.crawler.phone.strategy.SouqMobilephoneStrategy;

@Component
public class SouqListStrategy implements ListStrategy {

	@Autowired
	private SouqMobilephoneStrategy souqMobilephoneStrategy;

	public static void main(String[] args) throws UnsupportedEncodingException {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml",
				"spring/applicationContext-datasource.xml", "spring/applicationContext-redis.xml");
		context.start();
		Arrays.asList(context.getBeanDefinitionNames()).forEach(System.out::println);

		SouqListStrategy bean = (SouqListStrategy) context.getBean("souqListStrategy");
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
		return "https://uae.souq.com/ae-en/mobile-phone/l/?ref=nav&section=2&page=1";
	}

	@Override
	public void saveCrawlerMobilephoneInfos() {
		savePage(setCategroyUrl());
	}

	public void savePage(String pageUrl) {

		// souqMobilephoneStrategy.saveCrawlerMobilephoneInfo("https://uae.souq.com/ae-en/apple-iphone-6s-plus-with-facetime-64gb-4g-lte-silver-9000887/i/");

		Document contentStr = getContent(pageUrl);
		List<String> links = contentStr.select("div.list-view div.item-content a[href^=http]").eachAttr("href");
		links.parallelStream().forEach(link -> {
			souqMobilephoneStrategy.saveCrawlerMobilephoneInfo(link);
		});

		Elements nextEl = contentStr.select(".pagination-next > a[href]");
		if (nextEl.isEmpty()) {
			return;
		}

		String nextPage = nextEl.first().attr("href");
		savePage(nextPage + "&section=2");

	}

}
