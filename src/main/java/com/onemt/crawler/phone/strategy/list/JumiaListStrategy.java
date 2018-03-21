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

import com.onemt.crawler.phone.strategy.JumiaMobilephoneStrategy;

@Component
public class JumiaListStrategy implements ListStrategy {

	@Autowired
	private JumiaMobilephoneStrategy jumiaMobilephoneStrategy;

	public static void main(String[] args) throws UnsupportedEncodingException {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml",
				"spring/applicationContext-datasource.xml", "spring/applicationContext-redis.xml");
		context.start();
		Arrays.asList(context.getBeanDefinitionNames()).forEach(System.out::println);

		JumiaListStrategy bean = (JumiaListStrategy) context.getBean("jumiaListStrategy");
		bean.saveCrawlerMobilephoneInfos();

		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (context != null) {
				context.close();
			}
		}

	}

	@Override
	public String setCategroyUrl() {
		return "https://www.jumia.com.eg/mobiles/";
	}

	@Override
	public void saveCrawlerMobilephoneInfos() {
		savePage(setCategroyUrl());
	}

	public void savePage(String pageUrl) {
		Document contentStr = getContent(pageUrl);
		List<String> links = contentStr.select("section.products a.link").eachAttr("href");
		links.parallelStream().forEach(link -> {
			jumiaMobilephoneStrategy.saveCrawlerMobilephoneInfo(link);
		});

		Elements nextEl = contentStr.select("section.pagination  a[title='Next']");
		if (nextEl.isEmpty()) {
			return;
		}

		String nextPage = nextEl.first().attr("href");
		savePage(nextPage);

	}

}
