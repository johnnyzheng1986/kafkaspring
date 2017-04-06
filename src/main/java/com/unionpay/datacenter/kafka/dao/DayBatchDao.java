package com.unionpay.datacenter.kafka.dao;

import java.util.List;
import java.util.Map;

public interface DayBatchDao {
	/**
	 * 查询每日批量任务
	 * @param dayend 日终日期
	 * @return 任务列表
	 */
	public List<Map<String, Object>> queryDayBatchJob(String dayend);
}
