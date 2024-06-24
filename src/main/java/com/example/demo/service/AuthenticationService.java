package com.example.demo.service;

import com.example.demo.Dto.AuthenticationDto;
import com.example.demo.Dto.IntrospectDto;
import com.example.demo.Model.Customer;
import com.example.demo.Response.AuthenticationResponse;
import com.example.demo.Response.IntrospectResponse;
import com.example.demo.repository.AuthenticationRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;

@Service
public class AuthenticationService implements IAuthentication{
    @Autowired
    AuthenticationRepository authenticationRepository;
    protected static final String key = "YKY3JkEvUlB+uz1yBkSgoHkE1Q9TGf9O/vTEn/rk8NfqOaSPITzaRr/ZAyY6Wd7S";

    @Override
    public AuthenticationResponse authenticateCheck(AuthenticationDto authenticationDto) {
        Optional<Customer> customer = Optional.ofNullable(authenticationRepository.findByEmail(authenticationDto.getEmail()).orElseThrow(() -> new RuntimeException("Email Not Found")));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(authenticationDto.getPassword(), customer.get().getPassword());

        if (!authenticated) {
            throw new RuntimeException("Invalid Password");
        }
        try {
            String token = generatedToken(customer.get());
            return new AuthenticationResponse(true, token);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String generatedToken(Customer customer) throws JOSEException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(customer.getEmail())
                .issuer("Bao dep trai")
                .issueTime(new Date())
                .claim("scope", buildScope(customer))
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        jwsObject.sign(new MACSigner(key.getBytes()));
        return jwsObject.serialize();
    }

    public IntrospectResponse introspectResponse(IntrospectDto introspectDto) throws JOSEException, ParseException {
        String token = introspectDto.getToken();
        JWSVerifier verifier = new MACVerifier(key.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean isTokenValid = signedJWT.verify(verifier);
        Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean check = expirationDate.after(new Date()) && isTokenValid;
        return new IntrospectResponse(check);
    }

    private String buildScope(Customer user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(stringJoiner::add);
        return stringJoiner.toString();
    }
}