package br.dev.ltres.poc.integrateams.infrastructure.config;

import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphClientConfig {

    @Value("${microsoft.graph.tenant-id}")
    private String tenantId;

    @Value("${microsoft.graph.client-id}")
    private String clientId;

    @Value("${microsoft.graph.client-secret}")
    private String clientSecret;

    @Bean
    public GraphServiceClient graphServiceClient() {
        var credentialBuilder = new ClientSecretCredentialBuilder()
                .tenantId(tenantId)
                .clientId(clientId)
                .clientSecret(clientSecret);
        var credential = credentialBuilder.build();

        var scopes = new String[] { "https://graph.microsoft.com/.default" };
        return new GraphServiceClient(credential, scopes);
    }
}