package rabbit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.support.Transformers;

@Configuration
public class RabbitInboundFlow {
    private static final Logger logger = LoggerFactory.getLogger(RabbitInboundFlow.class);

    @Autowired
    private Queue sampleQueue;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(this.connectionFactory);
        listenerContainer.setQueues(this.sampleQueue);
        listenerContainer.setConcurrentConsumers(1);
        listenerContainer.setExclusive(true);
        return listenerContainer;
    }

    @Bean
    public IntegrationFlow inboundFlow() {
        return IntegrationFlows.from(Amqp.inboundAdapter(simpleMessageListenerContainer()))
                .transform(Transformers.objectToString())
                .handle((m) -> {
                    logger.info("Received payload {}", m.getPayload());
                })
                .get();
    }
}
