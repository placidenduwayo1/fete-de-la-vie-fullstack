package com.fdlv.gmd.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Go My Defi.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {}
