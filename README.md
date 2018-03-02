# online-store-crawler
协助数据组抓取网上商城数据

# 使用技术
spring + quartz + beetlsql + jsoup + jsonPath + redis + 正则 

# 表:
```
CREATE TABLE `crawler_mobilephone_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识(主键)',
  `brand` varchar(200) DEFAULT '' COMMENT '品牌',
  `model_number` varchar(200) DEFAULT '' COMMENT '型号',
  `operating_system` varchar(200) DEFAULT '' COMMENT '系统版本',
  `display_resolution` varchar(200) DEFAULT '' COMMENT '分辨率',
  `rom` varchar(200) DEFAULT '' COMMENT '存储内存',
  `ram` varchar(200) DEFAULT '' COMMENT '运行内存',
  `display_size` varchar(200) DEFAULT '' COMMENT '屏幕尺寸',
  `cellular_network_technology` varchar(200) DEFAULT '' COMMENT '网络',
  `color` varchar(200) DEFAULT '' COMMENT '颜色',
  `price` varchar(50) DEFAULT '' COMMENT '当前售价',
  `original_price` varchar(50) DEFAULT '' COMMENT '原价',
  `currency_code` varchar(50) DEFAULT '' COMMENT '货币代码',
  `source_url` varchar(255) DEFAULT '' COMMENT '详情页地址',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_delete` enum('0','1') DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8101 DEFAULT CHARSET=utf8mb4;

```
