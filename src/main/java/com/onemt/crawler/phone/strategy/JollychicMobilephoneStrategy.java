package com.onemt.crawler.phone.strategy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Component
public class JollychicMobilephoneStrategy extends AbstractMobilephoneStrategy {

	private static final Pattern compile = Pattern.compile("<div class=\"men-3-left\">Description:</div> <div class=\"men-3-right\">([\\S\\s]*)(?=</div> </div> <span class=\"view-more-btn J-view-more-btn fn-right fn-hide\">)");
	private static final String regex = "%s(.*)%s";

	public String getText(Document content, String prefix,String suffix) {
		String str = content.body().toString();
		Matcher matcher = compile.matcher(str);
		String data = "";
		if(matcher.find()){
			data = matcher.group(1);
		}

		System.out.println(data);
		Assert.hasText(data, "数据未抽取到");

		Pattern textP = Pattern.compile(String.format(regex, prefix,suffix));
		Matcher textM = textP.matcher(data);
		if(textM.find()){
			return textM.group(1);
		}
		return "";
	}


	@Override
	public String getBrand(Document content) {
		return content.selectFirst("meta[property='og:title']").attr("content");
	}

	@Override
	public String getModelNumber(Document content) {
		return "";
	}

	@Override
	public String getOperatingSystem(Document content) {
		return getText(content, "Operating system\\s*:\\s*","<br />");
	}

	@Override
	public String getDisplayResolution(Document content) {
		String text = getText(content, "Home screen resolution\\s*:\\s*","<br />");
		if(StringUtils.isEmpty(text)){
			text = getText(content, "Resolution\\s*:\\s*","<br />");
		}
		return text;
	}

	@Override
	public String getRom(Document content) {
		return getText(content, "ROM.*?:\\s*","<br />");
	}

	@Override
	public String getRam(Document content) {
		return getText(content, "RAM.*?:\\s","<br />");
	}

	@Override
	public String getDisplaySize(Document content) {
		return getText(content, "Main screen size\\s*:\\s*","<br />");
	}

	@Override
	public String getCellularNetworkTechnology(Document content) {
		return "";
	}

	@Override
	public String getColor(Document content) {
		return content.selectFirst("span[data-key='COLOR']").attr("data-value");
	}

	@Override
	public String getListDate(Document content) {
		return "";
	}

	@Override
	public String getPrices(Document content) {
		return content.selectFirst("span.jolly-price").text();
	}

}
