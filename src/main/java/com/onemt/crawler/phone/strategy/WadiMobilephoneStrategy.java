package com.onemt.crawler.phone.strategy;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jayway.jsonpath.JsonPath;

@Component
public class WadiMobilephoneStrategy extends AbstractMobilephoneStrategy {

	public String getText(Document content, String jsonPath) {
		String str = content.body().text();
		Object text = null;
		try {
			text = JsonPath.read(str, jsonPath);
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getMessage()+"\n"+str);
			text = "";
		}
		return String.valueOf(text);
	}

	@Override
	public String getBrand(Document content) {
		return getText(content, "$.data.brand.name");
	}

	@Override
	public String getModelNumber(Document content) {
		String text = getText(content, "$.data.attributes.model_name");
		if (StringUtils.isEmpty(text)) {
			text = getText(content, "$.data.name_desc.name");
		}
		return text;
	}

	@Override
	public String getOperatingSystem(Document content) {
		return getText(content, "$.data.attributes.operating_system");
	}

	@Override
	public String getDisplayResolution(Document content) {
		return getText(content, "$.data.attributes.display_resolution");
	}

	@Override
	public String getRom(Document content) {
		return getText(content, "$.data.attributes.internal_memory");
	}

	@Override
	public String getRam(Document content) {
		return getText(content, "$.data.attributes.ram");
	}

	@Override
	public String getDisplaySize(Document content) {
		return getText(content,
				"$.data.attributesMap.groups['General Features'].attributes.screen_size_in_inches.value");
	}

	@Override
	public String getCellularNetworkTechnology(Document content) {
		return getText(content, "$.data.attributes.mobile_data");
	}

	@Override
	public String getColor(Document content) {
		return getText(content, "$.data.attributes.color");
	}

	@Override
	public String getListDate(Document content) {
		return getText(content, "$.data.simples[0].suppliers[0].delivered_by_date");
	}

	@Override
	public String getPrices(Document content) {
		return String.valueOf(getText(content, "$.data.price"));
	}

}
