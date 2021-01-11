package com.pruebacc.redis.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
/*
*This class creates the configuration for the redis plugin called Spring data redis. This class defines the host and port of the database.
*Also, creates a template for the type of objects that are stored inside the database.
*/
@Configuration
@EnableRedisRepositories
public class RedisConfig {
    /*This method configures the host and port in which the database is running and listening to calls
     *Returns the Jedis configuration created. I used Jedis which is one of the options to create the configuration with Lettuce.
     */
    @Bean
    public JedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(System.getProperty("HOST"));
        configuration.setPort(Integer.parseInt(System.getProperty("PORT")));

        return new JedisConnectionFactory(configuration);
    }
    /*This method creates a template for the objects that are going to be stored inside the database
     */
    @Bean
    public RedisTemplate<String, Object> template() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();

        return template;
    }
}
