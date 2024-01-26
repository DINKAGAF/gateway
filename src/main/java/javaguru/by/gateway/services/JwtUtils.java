package javaguru.by.gateway.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;


    public String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getOrEmpty("Authorization").get(0);

        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                log.info("IN validateToken - JWT token has expired");
                return false;
            }
            log.info("IN validateToken - JWT token valid");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("IN validateToken  - expired or invalid JWT token");
            return false;
        }
    }
}

