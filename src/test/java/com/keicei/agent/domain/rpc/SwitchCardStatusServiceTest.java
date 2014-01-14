package com.keicei.agent.domain.rpc;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@ContextConfiguration(locations = { "classpath:spring-jdbc.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SwitchCardStatusServiceTest {

	String brandid = "kc";

	@Resource
	private SwitchCardStatusService switchCardStatusService;

	@Test
	public void testSwitchCardStatus() {
		String passwords = "187124353524";
		boolean result = switchCardStatusService.switchCardStatus(brandid,
				passwords, "0");
		Assert.assertTrue(result);
	}

}
