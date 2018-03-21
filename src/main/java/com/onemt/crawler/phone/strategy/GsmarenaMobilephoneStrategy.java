package com.onemt.crawler.phone.strategy;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class GsmarenaMobilephoneStrategy extends AbstractMobilephoneStrategy {

	
	private static final String cssText = "#specs-list td.ttl:has(a:contains(%s)) + td.nfo";
	
	public String getTextByCss(Document content, String cssQuery) {
		Elements select = content.select(String.format(cssText, cssQuery));
		if (!select.isEmpty()) {
			return select.first().text().trim();
		}
		return "";
	}
	
	@Override
	public String getBrand(Document content) {
		String text = content.selectFirst("h1.specs-phone-name-title").text();
		String[] split = text.split(" ");
		return split[0];
	}

	@Override
	public String getModelNumber(Document content) {
		return content.selectFirst("h1.specs-phone-name-title").text();
	}

	@Override
	public String getOperatingSystem(Document content) {
		return getTextByCss(content ,"OS");
	}

	@Override
	public String getDisplayResolution(Document content) {
		return getTextByCss(content ,"Resolution");
	}

	@Override
	public String getRom(Document content) {
		String text = content.selectFirst(".accent-expansion").text();
		return text;
	}

	@Override
	public String getRam(Document content) {
		String text = content.selectFirst("span[data-spec='storage-hl']").text();
		return text;
	}

	@Override
	public String getDisplaySize(Document content) {
		return getTextByCss(content ,"Size");
	}

	@Override
	public String getCellularNetworkTechnology(Document content) {
		return getTextByCss(content ,"Technology");
	}

	@Override
	public String getColor(Document content) {
		return getTextByCss(content ,"Colors");
	}

	@Override
	public String getPrice(Document content) {
		return getTextByCss(content ,"Price");
	}

	@Override
	public String getOriginalPrice(Document content) {
		return getTextByCss(content ,"Price");
	}

	@Override
	public String getCurrencyCode(Document content) {
		String textByCss = getTextByCss(content ,"Price");
		String[] split = textByCss.split(" ");
		return split[split.length-1];
	}

}
