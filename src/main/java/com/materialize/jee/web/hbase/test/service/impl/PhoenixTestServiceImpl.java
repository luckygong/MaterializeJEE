package com.materialize.jee.web.hbase.test.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.materialize.jee.platform.hbase.phoenix.BasePhoenixService;
import com.materialize.jee.platform.hbase.phoenix.PhoenixPage;
import com.materialize.jee.platform.hbase.phoenix.PhoenixPagination;
import com.materialize.jee.web.hbase.test.domain.Alarm;
import com.materialize.jee.web.hbase.test.phoenixmapper.AlarmMapper;
import com.materialize.jee.web.hbase.test.service.PhoenixTestService;

@Service
public class PhoenixTestServiceImpl extends BasePhoenixService<Alarm> implements PhoenixTestService{
	@Autowired
	private AlarmMapper alarmMapper;
	
	public Alarm get(String id){
		return alarmMapper.get(id);
	}
	
	@Override
	public PhoenixPage<Alarm> findPage(PhoenixPagination pagination) {
		List<Alarm> list = alarmMapper.findPage(pagination);
		PhoenixPage<Alarm> page = super.createPhoenixPage(pagination, list);
		return page;
	}
	
	public Long init(Long start, Long end) {
		Random random = new Random();
		for(Long i=start;i<=end;i++){
			Alarm alarm = new Alarm();
			alarm.setRowKey(Chars[(int)((i-1)%8)]+frontCompWithZore(i,9));
			alarm.setMpuId("48"+frontCompWithZore(Long.valueOf(getRandom(random,100)),3));
			alarm.setDvcCode("4U"+frontCompWithZore(Long.valueOf(getRandom(random, 78821)),5));
			alarm.setAlmType(getRandom(random, 100));
			alarm.setAlmFrom(getRandom(random, 2).toString());
			alarm.setCallEmp(seaters[getRandom(random,8)]);
			Integer target = alarmMapper.save(alarm);
			System.out.println(i+", target:"+target);
		}
		return null;
	}
	
	public static final String[] Chars = new String[]{"A","B","C","D","E","F","G","H"};
	public static final String[] seaters = new String[]{"36101","36102","36103","36104","36105","36106","36107","36108"};
	
	public Integer getRandom(Random random, Integer max){
		return random.nextInt(max);
	}
	public String frontCompWithZore(Long sourceData,int formatLength) { 
		/* * 0 指前面补充零 * formatLength 字符总长度为 formatLength * d 代表为正数。 */ 
		String newString = String.format("%0"+formatLength+"d", sourceData); 
		return newString; 
	} 
}