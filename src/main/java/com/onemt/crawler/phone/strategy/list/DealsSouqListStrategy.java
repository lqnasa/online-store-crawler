package com.onemt.crawler.phone.strategy.list;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;
import com.onemt.crawler.phone.strategy.SouqMobilephoneStrategy;

@Component
public class DealsSouqListStrategy implements ListStrategy {

	private static final String url = "https://deals.souq.com/eg-en/search?campaign_id=6370&filters[id_type_item][]=33&sort=best&page=%d";

	@Autowired
	private SouqMobilephoneStrategy souqMobilephoneStrategy;

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml",
				"spring/applicationContext-datasource.xml", "spring/applicationContext-redis.xml");
		context.start();
		Arrays.asList(context.getBeanDefinitionNames()).forEach(System.out::println);

		DealsSouqListStrategy bean = (DealsSouqListStrategy) context.getBean("dealsSouqListStrategy");
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
		return url;
	}

	@Override
	public void saveCrawlerMobilephoneInfos() {
		savePage(setCategroyUrl(), 1);

	}

	public void savePage(String pageUrl, Integer pageNum) {
		Document contentStr = getContent(String.format(pageUrl, pageNum));
		List<String> links = JsonPath.read(contentStr.body().text(), "$.data[*].url");
		links.parallelStream().forEach(link -> {
			souqMobilephoneStrategy.saveCrawlerMobilephoneInfo(link);
		});

		// 翻页处理
		Integer totalPages = JsonPath.read(contentStr.body().text(), "$.metadata.total_pages");
		if (pageNum + 1 > totalPages) {
			return;
		}
		savePage(url, pageNum + 1);

	}

}
