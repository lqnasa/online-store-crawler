package com.onemt.crawler.phone.strategy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.jayway.jsonpath.JsonPath;

@Component
public class JumiaMobilephoneStrategy extends AbstractMobilephoneStrategy {

	private static final Pattern compile = Pattern.compile("jsTrackingStore.data =([^;]*);");
	private static final String cssText = "div.-head:containsOwn(%s) + div";

	public String getText(Document content, String jsonPath) {
		String str = content.html();
		Matcher matcher = compile.matcher(str);
		String data = "";
		while (matcher.find()) {
			data = matcher.group(1);
			break;
		}
		// System.out.println(data);
		Assert.hasText(data, "数据未抽取到");
		Object text = null;
		try {
			text = JsonPath.read(data, jsonPath);
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println(e.getMessage() + "\n" + data);
			text = "";
		}
		return String.valueOf(text).trim();
	}

	public String getTextByCss(Document content, String cssQuery) {
		Elements select = content.select(String.format(cssText, cssQuery));
		if (!select.isEmpty()) {
			return select.first().text().trim();
		}
		return "";
	}

	@Override
	public String getBrand(Document content) {
		return getText(content, "$.dataLayer.ecommerce.detail.products[0].brand");
	}

	@Override
	public String getModelNumber(Document content) {
		return getText(content, "$.dataLayer.productModel");
	}

	@Override
	public String getOperatingSystem(Document content) {
		return getTextByCss(content, "Operating System");
	}

	@Override
	public String getDisplayResolution(Document content) {
		return null;
	}

	@Override
	public String getRom(Document content) {
		return getTextByCss(content, "Capacity (GB)");
	}

	@Override
	public String getRam(Document content) {
		return getTextByCss(content, "RAM (GB)");
	}

	@Override
	public String getDisplaySize(Document content) {
		return getTextByCss(content, "Display Size (inches)");
	}

	@Override
	public String getCellularNetworkTechnology(Document content) {
		return getTextByCss(content, "Connectivity");
	}

	@Override
	public String getColor(Document content) {
		return getTextByCss(content, "Color");
	}

	@Override
	public String getPrice(Document content) {
		Element selectFirst = content.selectFirst(".details-footer div.price-box span.price span:eq(1)");
		if(selectFirst==null){
			return null;
		}
		return selectFirst.attr("data-price");
	}

	@Override
	public String getOriginalPrice(Document content) {
		Element selectFirst = content.selectFirst(".details-footer div.price-box span.-old span:eq(1)");
		if(selectFirst==null){
			return null;
		}
		return selectFirst.attr("data-price");
	}

	@Override
	public String getCurrencyCode(Document content) {
		Element selectFirst = content.selectFirst(".details-footer div.price-box span.price span:eq(0)");
		if(selectFirst==null){
			return null;
		}
		return selectFirst.attr("data-currency-iso");
	}

}
