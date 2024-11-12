package com.sahas.springboot3keycloakrbacpoc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sahas.springboot3keycloakrbacpoc.config.properties.AppProperties.appProperties;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@DependsOn("appPropertiesConfig")
public class SecurityConfig
{
    @Bean
    public CorsFilter corsFilter()
    {
        return new CorsFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults()
    {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter()
    {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt ->
        {
            Collection<String> kayaRoles = extractRoles(jwt.getClaims());

            return kayaRoles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        });
        return jwtAuthenticationConverter;
    }

    private Collection<String> extractRoles(Map<String, Object> claims)
    {
        Map<String, Object> access;
        if (appProperties.KEYCLOAK_RESOURCE_ROLE_MAPPING) // Use realm roles or client roles based on the configuration
        {
            @SuppressWarnings("unchecked")
            Map<String, Object> resourceAccess = (Map<String, Object>)claims.get("resource_access");
            access = (Map<String, Object>)resourceAccess.get(appProperties.KEYCLOAK_CLIENT_ID);
        }
        else
        {
            access = (Map<String, Object>)claims.get("realm_access");
        }

        return (Collection<String>)access.get("roles");
    }
}