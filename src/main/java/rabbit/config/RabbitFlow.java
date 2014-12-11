package rabbit.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@EnableIntegration
@IntegrationComponentScan
@EnableAutoConfiguration
@Import({RabbitConfig.class, RabbitInboundFlow.class, RabbitSampleOutbound.class})
public class RabbitFlow {
}
