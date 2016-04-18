package com.synyx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.synyx.syscontrol.ControlApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ControlApplication.class)
@WebAppConfiguration
public class ControlApplicationTests {

	@Test
	public void contextLoads() {
	}

}
