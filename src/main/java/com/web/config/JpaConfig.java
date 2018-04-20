package com.web.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by alex on 20.04.18.
 */
@Configuration
@EntityScan("com.web.entity")
@ComponentScan({"com.web.persistence", "com.web.service", "com.web.security"})
@EnableJpaRepositories(basePackages = {"com.web.persistence"})
@EnableTransactionManagement(proxyTargetClass = true)
public class JpaConfig {
}
