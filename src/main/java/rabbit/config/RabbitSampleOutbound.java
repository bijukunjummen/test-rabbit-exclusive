package rabbit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.channel.MessageChannels;

@Configuration
public class RabbitSampleOutbound {
    private static final Logger logger = LoggerFactory.getLogger(RabbitSampleOutbound.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public IntegrationFlow outBoundFlow() {
        return IntegrationFlows.from("message.input")
                .handle(Amqp.outboundAdapter(rabbitTemplate))
                .get();
    }
}
