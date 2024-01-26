package javaguru.by.gateway.configs;

import javaguru.by.gateway.testData.TestData;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestGatewayConfig implements BeforeAllCallback {

    @Bean
    public RouteLocator testRouteLocator(RouteLocatorBuilder builder, AuthenticationFilter filter) {

        return builder.routes()
                .route("test-service", r -> r.path(TestData.SECURED_ENDPOINT)
                        .filters(f -> f.filter(filter.apply(filter.newConfig())))
                        .uri("lb://test"))
                .build();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        System.setProperty("JWT_SECRET_PHRASE", TestData.JWT_SECRET_PHRASE);
    }
}
