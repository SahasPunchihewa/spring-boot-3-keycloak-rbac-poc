package com.sahas.springboot3keycloakrbacpoc.config.properties;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppPropertiesConfig
{
    public String KEYCLOAK_CLIENT_ID;
    public Boolean KEYCLOAK_RESOURCE_ROLE_MAPPING;

    @Value("${keycloak.client-id}")
    public void setKeycloakClientId(String clientId)
    {
        this.KEYCLOAK_CLIENT_ID = clientId;
    }

    @Value("${keycloak.resource-role-mappings}")
    public void setKeycloakResourceRoleMapping(Boolean roleMapping)
    {
        this.KEYCLOAK_RESOURCE_ROLE_MAPPING = roleMapping;
    }

    @PostConstruct
    public void init()
    {
        AppProperties.appProperties.setKeycloakClientId(this.KEYCLOAK_CLIENT_ID);
        AppProperties.appProperties.setKeycloakResourceRoleMapping(this.KEYCLOAK_RESOURCE_ROLE_MAPPING);
    }
}