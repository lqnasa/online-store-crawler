package com.onemt.crawler.phone.strategy.list;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.onemt.crawler.phone.strategy.GsmarenaMobilephoneStrategy;

@Component
public class GsmarenaListStrategy implements ListStrategy {

	@Autowired
	private GsmarenaMobilephoneStrategy gsmarenaMobilephoneStrategy;

	public static void main(String[] args) throws UnsupportedEncodingException {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml",
				"spring/applicationContext-datasource.xml", "spring/applicationContext-redis.xml");
		context.start();
		Arrays.asList(context.getBeanDefinitionNames()).forEach(System.out::println);

		GsmarenaListStrategy bean = (GsmarenaListStrategy) context.getBean("gsmarenaListStrategy");
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
		return "https://www.gsmarena.com/makers.php3";
	}

	@Override
	public void saveCrawlerMobilephoneInfos() {
		Document contentStr = getContent(setCategroyUrl());
		List<String> urlList = contentStr.select("div.st-text a").eachAttr("abs:href");
		urlList.forEach(url -> {
			savePage(url);
		});
	}

	public void savePage(String pageUrl) {
		Document contentStr = getContent(pageUrl);
		List<String> links = contentStr.select(".makers a").eachAttr("abs:href");
		links.parallelStream().forEach(link -> {
			gsmarenaMobilephoneStrategy.saveCrawlerMobilephoneInfo(link);
		});

		Element nextPageEl = contentStr.selectFirst("a.pages-next");
		if (nextPageEl==null || nextPageEl.absUrl("href").contains("#1")) {
			return;
		}
		savePage(nextPageEl.absUrl("href"));

	}

}
