package com;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.plugin.spacerimport.returns.RPCResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * @author LK
 * @date 2018-03-28 15:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/config/spring/spring-mvc.xml"})
public class MsgPushServerTest {

	@Autowired
	private ServiceDemo serviceDemo;

	@Before
	public void init() {

	}

	@Test
	public void shouldAddModule() {
		List<String> ls = new ArrayList<String>();
		ls.add("asd");
		RPCResponse<List<String>> listRPCResponse = serviceDemo.Demo2(123, (short) 1,2L,new demo().setD("123").setD2("asd").setD3(ls));
		List<String> list = listRPCResponse.getModel();
		System.out.println();
	}

}