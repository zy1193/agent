package com.keicei.agent.domain.manager;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@ContextConfiguration(locations = { "classpath:spring-jdbc.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class AgentManagerTest {

	@Resource
	private AgentManager agentManager;

	@Test
	public void testSwitchStatus() {
		agentManager.switchCardStatus("zhy", "0");
	}

}
