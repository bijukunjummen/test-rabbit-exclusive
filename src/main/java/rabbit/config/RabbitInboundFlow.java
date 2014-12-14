package rabbit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.handler.advice.RequestHandlerRetryAdvice;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import rabbit.Utils;

@Configuration
public class RabbitInboundFlow {
    private static final Logger logger = LoggerFactory.getLogger(RabbitInboundFlow.class);

    @Autowired
    private RabbitConfig rabbitConfig;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(this.connectionFactory);
        listenerContainer.setQueues(this.rabbitConfig.sampleQueue());
        listenerContainer.setConcurrentConsumers(1);
        listenerContainer.setExclusive(true);
        return listenerContainer;
    }

    @Bean
    public IntegrationFlow inboundFlow() {
        return IntegrationFlows.from(Amqp.inboundAdapter(simpleMessageListenerContainer()))
                .transform(Transformers.objectToString())
                .handle((m) -> {
                    logger.info("Received  {}", m.getPayload());
                    Utils.sleep(3000);
                    Utils.throwExceptionsPercentOfTime(40);
                    logger.info("Processed {}", m.getPayload());
                }, c -> c.advice(this.retryAdvice()))
                .get();
    }

    @Bean
    public RequestHandlerRetryAdvice retryAdvice() {
        RequestHandlerRetryAdvice retryAdvice = new RequestHandlerRetryAdvice();
        retryAdvice.setRetryTemplate(retryTemplate());
        return retryAdvice;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(retryPolicy);

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(2000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }


}
