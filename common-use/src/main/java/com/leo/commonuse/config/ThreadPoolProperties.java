package com.leo.commonuse.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Liu
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "blog.mainthreadpool")
public class ThreadPoolProperties {


    int corePoolSize;

    int maximumPoolSize;

    long keepAliveTime;

    int queueSize;
}
