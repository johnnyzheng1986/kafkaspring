package com.unionpay.datacenter.kafka.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DayBatchDaoImpl implements DayBatchDao {
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Resource
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> queryDayBatchJob(String dayend) {
		String sql = "select file_name,file_path,status from tbl_chhis_file_import_job_log where date=?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, new Object[]{dayend});
		return resultList;
	}

}
