package rabbit.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SampleDataTest {
    @Autowired
    private SampleGateway sampleGateway;

    @Test
    public void populateTestData() throws Exception {
        for (int i = 0; i < 10; i++) {
            this.sampleGateway.sendMessage("Test Message " + i);
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan("rabbit")
    public static class TestConfig {

    }
}
