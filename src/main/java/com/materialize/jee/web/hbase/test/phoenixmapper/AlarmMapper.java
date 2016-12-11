/**
 * @Company：
 * @Title: AlarmMapper.java
 * @Description：描述
 * @Author： 97802
 * @Date：2016-06-04 08:54:57
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.web.hbase.test.phoenixmapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.materialize.jee.platform.hbase.phoenix.PhoenixPagination;
import com.materialize.jee.web.hbase.test.domain.Alarm;

@Repository
public interface AlarmMapper {

	Alarm get(@Param(value="id") String id);
	
	Integer save(Alarm alarm);
	
	List<Alarm> findPage(PhoenixPagination pagination);

}
