package com.onemt.crawler.phone.strategy;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

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
		return getText(content, "$.Page_Data.product.brand");
	}

	@Override
	public String getModelNumber(Document content) {
		String text = getText(content, "$.Page_Data.product.attributes.Model_Number");
		if(StringUtils.isEmpty(text)){
			text = getTextByCss(content, "Model Number");
		}
		if(StringUtils.isEmpty(text)){
			text = getText(content, "$.Page_Data.product.name");
		}
		return text;
	}

	@Override
	public String getOperatingSystem(Document content) {
		String text = getTextByCss(content, "Operating System Version");
		if(StringUtils.isEmpty(text)){
			text = getTextByCss(content, "Operating System Type");
		}
		if(StringUtils.isEmpty(text)){
			text = getText(content, "$.Page_Data.product.attributes.Operating_System_Type");
		}
		return text;
	}

	@Override
	public String getDisplayResolution(Document content) {
		return getTextByCss(content, "Display Resolution");
	}

	@Override
	public String getRom(Document content) {
		String text = getText(content, "$.Page_Data.product.attributes.Storage_Capacity");
		if(StringUtils.isEmpty(text)){
			text = getTextByCss(content, "Storage Capacity");
		}
		return text;
	}

	@Override
	public String getRam(Document content) {
		String text = getTextByCss(content, "RAM Memory");
		if(StringUtils.isEmpty(text)){
			text = getTextByCss(content, "Memory RAM");
		}
		return text;
	}

	@Override
	public String getDisplaySize(Document content) {
		String text = getText(content, "$.Page_Data.product.attributes['Display_Size_(Inch)']");
		if(StringUtils.isEmpty(text)){
			text = getTextByCss(content, "Display Size (Inch)");
		}
		return text;
	}

	@Override
	public String getCellularNetworkTechnology(Document content) {
		return getTextByCss(content, "Cellular Network Technology");
	}

	@Override
	public String getColor(Document content) {
		String text = getText(content, "$.Page_Data.product.attributes.Color");
		if(StringUtils.isEmpty(text)){
			text = getTextByCss(content, "Color");
		}
		return text;
	}

	@Override
	public String getPrice(Document content) {
		return getText(content, "$.Page_Data.product.price");
	}

	@Override
	public String getOriginalPrice(Document content) {
		String price = getPrice(content);
		if("null".equals(price)||StringUtils.isEmpty(price)){
			return getPrice(content);
		}
		Double _price = Double.valueOf(price);
		Double discount = Double.valueOf(getText(content, "$.Page_Data.product.discount"));
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(_price*(1-discount));
	}

	@Override
	public String getCurrencyCode(Document content) {
		return getText(content, "$.Page_Data.product.currencyCode");
	}

}
