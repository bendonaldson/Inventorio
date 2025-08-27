package dev.bendonaldson.inventorio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * A type-safe configuration properties class for JWT settings.
 * This record is bound to properties prefixed with "jwt" in the application.properties file.
 *
 * @param secretKey The Base64 encoded secret key used for signing JWTs.
 * @param expirationMs The expiration time for JWTs in milliseconds.
 */
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secretKey,
        long expirationMs
) {}
