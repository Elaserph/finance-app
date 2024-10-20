package com.finance.app.fundstransfer.wrapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for creating and configuring a {@link RestTemplate} bean.
 * <p>
 * Provides a centrally configured {@link RestTemplate} bean that can be used for making HTTP requests.
 * </p>
 *
 * @author
 * <a href="https://github.com/Elaserph">elaserph</a>
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Creates and configures a {@link RestTemplate} bean.
     *
     * @return a configured {@link RestTemplate} instance.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
