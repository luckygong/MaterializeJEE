package report;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.materialize.jee.platform.utils.ContextUtils;
import com.materialize.jee.web.hbase.test.service.PhoenixTestService;

public class PhoenixTest {
	private static ApplicationContext context;
	
	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("spring/spring-context-common.xml");
		ContextUtils.setContext(context);
		PhoenixTestService pService = ContextUtils.getBean(PhoenixTestService.class);
		Date start = new Date();
		
//		pService.init(3026554L,5000000L);//3026554
		pService.init(8031439L,10000000L);//8031439
		
//		//查询
//		Alarm alarm = pService.get("G004999999");
//		System.out.println(alarm.toString());
		Date end = new Date();
		System.out.println("finish. "+(end.getTime()-start.getTime()));
	}
}
