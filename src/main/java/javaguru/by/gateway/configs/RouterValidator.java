package javaguru.by.gateway.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.function.Predicate;

@Service
public class RouterValidator {

    private List<String> openEndpoints;

    public RouterValidator(@Value("${app.open-endpoints}") List<String> openEndpoints) {
        this.openEndpoints = openEndpoints;
    }

    public Predicate<ServerHttpRequest> isSecured =
            request -> openEndpoints.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}