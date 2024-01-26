package javaguru.by.gateway;

import javaguru.by.gateway.configs.TestGatewayConfig;
import javaguru.by.gateway.testData.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestGatewayConfig.class)
@ExtendWith(TestGatewayConfig.class)

class AuthenticationFilterIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnUnauthorizedWhenAuthorizationHeaderIsMissing() {
        webTestClient.get()
                .uri(TestData.SECURED_ENDPOINT)  // Исправленный URI
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Authorization header is missing");
    }

    @Test
    void shouldReturnUnauthorizedWhenTokenIsInvalid() {
        webTestClient.get()
                .uri(TestData.SECURED_ENDPOINT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + TestData.BEARER_INVALID_TOKEN)  // Исправленный токен
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Invalid or expired token");
    }

//    @Test
//    void shouldAllowAccessWithValidToken() {
//
//        String validToken = "ValidToken";
//
//        webTestClient.get()
//                .uri("/your-secured-endpoint")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken)
//                .exchange()
//                .expectStatus().isOk();
//    }
}