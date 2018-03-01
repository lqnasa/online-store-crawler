package com.onemt.crawler.phone.bean;

import java.io.Serializable;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

@Table(name = "crawler_mobilephone_info")
public class CrawlerMobilephoneInfo implements Serializable {

	private static final long serialVersionUID = 1400573545318809394L;

	@AutoID
	private Long id;
	private String brand; // 品牌
	private String modelNumber; // 型号
	private String operatingSystem; // 系统版本
	private String displayResolution; // 分辨率
	private String rom; // 存储内存
	private String ram; // 运行内存
	private String displaySize; // 屏幕尺寸
	private String cellularNetworkTechnology; // 网络
	private String color; // 颜色
	private String listDate; // 上市日期
	private String prices; // 当前售价
	private String sourceUrl; // 详情页地址
	private String createTime;
	private String is_delete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getDisplayResolution() {
		return displayResolution;
	}

	public void setDisplayResolution(String displayResolution) {
		this.displayResolution = displayResolution;
	}

	public String getRom() {
		return rom;
	}

	public void setRom(String rom) {
		this.rom = rom;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getDisplaySize() {
		return displaySize;
	}

	public void setDisplaySize(String displaySize) {
		this.displaySize = displaySize;
	}

	public String getCellularNetworkTechnology() {
		return cellularNetworkTechnology;
	}

	public void setCellularNetworkTechnology(String cellularNetworkTechnology) {
		this.cellularNetworkTechnology = cellularNetworkTechnology;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getListDate() {
		return listDate;
	}

	public void setListDate(String listDate) {
		this.listDate = listDate;
	}

	public String getPrices() {
		return prices;
	}

	public void setPrices(String prices) {
		this.prices = prices;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}

}
