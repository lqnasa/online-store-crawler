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

import com.onemt.crawler.phone.strategy.JollychicMobilephoneStrategy;

@Component
public class JollychicListStrategy implements ListStrategy {
	
	@Autowired
	private JollychicMobilephoneStrategy jollychicMobilephoneStrategy;

	public static void main(String[] args) throws UnsupportedEncodingException {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml",
				"spring/applicationContext-datasource.xml", "spring/applicationContext-redis.xml");
		context.start();
		Arrays.asList(context.getBeanDefinitionNames()).forEach(System.out::println);

		JollychicListStrategy bean = (JollychicListStrategy) context.getBean("jollychicListStrategy");
		bean.saveCrawlerMobilephoneInfos();

		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(context!=null){
				context.close();
			}
		}

	}

	@Override
	public String setCategroyUrl() {
		return "https://www.jollychic.com/cell-phones-c744";
	}

	@Override
	public void saveCrawlerMobilephoneInfos() {
		savePage(setCategroyUrl());

	}

	public void savePage(String pageUrl) {
		
		Document contentStr = getContent(pageUrl);
		List<String> links = contentStr.select("ul#J-pro-list li > a[href]").eachAttr("href");
		links.stream().forEach(link->{
			jollychicMobilephoneStrategy.saveCrawlerMobilephoneInfo(link);
		});

		Elements nextEl = contentStr.select("a.ui-page-next");
		if (nextEl.isEmpty()) {
			return;
		}
		String nextPage = nextEl.first().attr("href");
		savePage(nextPage);

	}

}
