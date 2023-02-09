package com.hyj.sta.service;

import com.hyj.sta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author test.java
 * @since 2023-02-08
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

	void createStatisticsByDay(String day);

	Map<String, Object> getChartData(String begin, String end, String type);
}
