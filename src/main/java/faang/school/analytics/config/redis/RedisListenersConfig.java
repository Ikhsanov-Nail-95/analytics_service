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

        Map<String, Object> annotatedBeans = beanFactory.getBeansWithAnnotation(EventTopic.class);
        for (Object annotatedListener : annotatedBeans.values()) {
            Class<?> targetClass = annotatedListener.getClass();
            String channelKey = targetClass.getAnnotation(EventTopic.class).value();
            String channelPattern = redisChannelsProperties.getChannels().get(channelKey);

            if (channelPattern == null) {
                throw new MissingRedisTopicException(channelKey);
            }

            MessageListenerAdapter adapter = new MessageListenerAdapter(annotatedListener, "handleMessage");
            adapter.setSerializer(new StringRedisSerializer());
            adapter.afterPropertiesSet();

            container.addMessageListener(adapter, new PatternTopic(channelPattern));
        }

        return container;
    }

}