package com.example.springappnewssecure.service.security.impl;

import com.example.springappnewssecure.applicationparametr.TokenParameter;
import com.example.springappnewssecure.entity.User;
import com.example.springappnewssecure.security.UserDetailsService;
import com.example.springappnewssecure.service.security.JwtTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {

    private final TokenParameter tokenParameter;



    @Override
    public String generateToken(UserDetailsService userDetailsService) {
        log.info("Generate token");
        return Jwts.builder()
                .issuer(userDetailsService.getUsername())
                .subject(userDetailsService.getPassword())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + tokenParameter.getTokenExpiration().toMillis()))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public Claims decoderToken(String token) {
        log.info("Decoder token");
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public String[] injectUserNameAndPasswordFromToken(String token) {
        Claims claims = decoderToken(token);
        return new String[]{claims.getIssuer(), claims.getSubject()};
    }


    @Override
    public boolean isValidToken(String token) {
        try {
            log.info("Check valid token");
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            log.info("Token is valid");
            return true;
        }catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: {}", e.getMessage());
        }catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(tokenParameter.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

//    public String getUsernameFromToken(String token) {
//        Claims claims = getAllClaimsFromToken(token);
//        return claims.getSubject();
//    }
//
//    // Метод для извлечения ролей из токена
//    public List<String> getRolesFromToken(String token) {
//        Claims claims = getAllClaimsFromToken(token);
//        return (List<String>) claims.get("roles");
//    }


}
