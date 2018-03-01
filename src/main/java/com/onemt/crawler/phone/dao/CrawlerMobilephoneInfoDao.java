package com.onemt.crawler.phone.dao;

import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;

import com.onemt.crawler.phone.bean.CrawlerMobilephoneInfo;

@SqlResource("crawlerMobilephoneInfo")
public interface CrawlerMobilephoneInfoDao extends BaseMapper<CrawlerMobilephoneInfo> {

}
