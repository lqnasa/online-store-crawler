package com.onemt.crawler.phone.strategy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.jayway.jsonpath.JsonPath;

@Component
public class SouqMobilephoneStrategy extends AbstractMobilephoneStrategy {

	private static final Pattern compile = Pattern.compile("var globalBucket =([^<]*)");
	private static final String cssText = "dt:containsOwn(%s) + dd";

	public String getText(Document content, String jsonPath) {
		String str = content.body().toString();
		Matcher matcher = compile.matcher(str);
		String data = "";
		while (matcher.find()) {
			data = matcher.group(1);
			break;
		}

		//System.out.println(data);

		Assert.hasText(data, "数据未抽取到");

		Object text = null;
		try {
			text = JsonPath.read(data, jsonPath);
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getMessage()+"\n"+data);
			text = "";
		}
		return String.valueOf(text);
	}

	public String getTextByCss(Document content, String cssQuery) {
		Elements select = content.select(String.format(cssText, cssQuery));
		if (!select.isEmpty()) {
			return select.first().text();
		}
		return "";
	}

	@Override
	public String getBrand(Document content) {
		return getText(content, "$.Page_Data.product.brand");
	}

	@Override
	public String getModelNumber(Document content) {
		return getText(content, "$.Page_Data.product.attributes.Model_Number");
	}

	@Override
	public String getOperatingSystem(Document content) {
		return getText(content, "$.Page_Data.product.attributes.Operating_System_Type");
	}

	@Override
	public String getDisplayResolution(Document content) {
		return getTextByCss(content, "Display Resolution");
	}

	@Override
	public String getRom(Document content) {
		return getText(content, "$.Page_Data.product.attributes.Storage_Capacity");
	}

	@Override
	public String getRam(Document content) {
		return getTextByCss(content, "Memory RAM");
	}

	@Override
	public String getDisplaySize(Document content) {
		return getText(content, "$.Page_Data.product.attributes['Display_Size_(Inch)']");
	}

	@Override
	public String getCellularNetworkTechnology(Document content) {
		return getTextByCss(content, "Cellular Network Technology");
	}

	@Override
	public String getColor(Document content) {
		return getText(content, "$.Page_Data.product.attributes.Color");
	}

	@Override
	public String getListDate(Document content) {
		return "";
	}

	@Override
	public String getPrices(Document content) {
		return String.valueOf(getText(content, "$.Page_Data.product.price"));
	}

}
