package com.fdlv.gmd.api.service.mail;

import com.azure.identity.UsernamePasswordCredential;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GraphAuthenticatorService {

    @Value("${mail.graph.clientId}")
    private String clientId;

    @Value("${mail.graph.tenantId}")
    private String tenantId;

    @Value("${mail.graph.username}")
    private String username;

    @Value("${mail.graph.password}")
    private String password;

    public GraphAuthenticatorService() {
        super();
    }

    public GraphServiceClient<Request> getAuthenticationProvider() {
        // Permissions from Graph Api
        final List<String> scopes = new ArrayList<>();
        scopes.add("Mail.Send");
        scopes.add("User.Read");

        // Auth method
        final UsernamePasswordCredential clientSecretCredential = new UsernamePasswordCredentialBuilder()
            .clientId(clientId)
            .username(username)
            .password(password)
            .tenantId(tenantId)
            .build();

        final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(scopes, clientSecretCredential);

        return GraphServiceClient.builder().authenticationProvider(tokenCredentialAuthProvider).buildClient();
    }
}
