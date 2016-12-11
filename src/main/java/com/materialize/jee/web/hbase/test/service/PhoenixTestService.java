package com.materialize.jee.web.hbase.test.service;

import com.materialize.jee.platform.hbase.phoenix.PhoenixPage;
import com.materialize.jee.platform.hbase.phoenix.PhoenixPagination;
import com.materialize.jee.web.hbase.test.domain.Alarm;

public interface PhoenixTestService {
	Alarm get(String id);
	
	Long init(Long start, Long end);
	
	PhoenixPage<Alarm> findPage(PhoenixPagination pagination);
}