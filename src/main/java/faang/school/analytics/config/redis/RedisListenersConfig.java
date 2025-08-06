package faang.school.analytics.config.redis;

import faang.school.analytics.exception.MissingRedisTopicException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(RedisChannelsProperties.class)
public class RedisListenersConfig {

    private final RedisChannelsProperties redisChannelsProperties;

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisMessageListenerContainer container(ListableBeanFactory beanFactory,
                                                   LettuceConnectionFactory redisConnectionFactory,
                                                   @Qualifier("redisTaskExecutor") ThreadPoolTaskExecutor redisTaskExecutor
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.setTaskExecutor(redisTaskExecutor);

        Map<String, Object> listeners = beanFactory.getBeansWithAnnotation(EventTopic.class);
        for (Object listenerBean : listeners.values()) {
            Class<?> listenerClass = listenerBean.getClass();
            String key = listenerClass.getAnnotation(EventTopic.class).value();
            String topicName = redisChannelsProperties.getChannels().get(key);

            if (topicName == null) {
                throw new MissingRedisTopicException(key);
            }

            MessageListenerAdapter adapter = new MessageListenerAdapter(listenerBean, "handleMessage");
            adapter.setSerializer(new StringRedisSerializer());
            adapter.afterPropertiesSet();

            container.addMessageListener(adapter, new PatternTopic(topicName));
        }

        return container;
    }

}