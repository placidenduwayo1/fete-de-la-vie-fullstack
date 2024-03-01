package com.fdlv.gmd.api.config;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import com.fdlv.gmd.api.aop.logging.LoggingAspect;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(Constants.PROFIL_DEV)
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
