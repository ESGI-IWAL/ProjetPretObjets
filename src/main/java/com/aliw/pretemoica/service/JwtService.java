package com.aliw.pretemoica.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	private final SecretKey secretKey;
	private final long expirationMs;

	public JwtService(
			@Value("${jwt.secret}") String secret,
			@Value("${jwt.expiration-ms:3600000}") long expirationMs) {
		this.secretKey = buildKey(secret);
		this.expirationMs = expirationMs;
	}


	public String generateToken(String username) {
		Instant now = Instant.now();
		JWTClaimsSet claims = new JWTClaimsSet.Builder()
				.subject(username)
				.issueTime(Date.from(now))
				.expirationTime(Date.from(now.plusMillis(expirationMs)))
				.build();

		try {
			SignedJWT signedJWT = new SignedJWT(
					new JWSHeader(JWSAlgorithm.HS256),
					claims);
			signedJWT.sign(new MACSigner(secretKey.getEncoded()));
			return signedJWT.serialize();
		} catch (JOSEException ex) {
			throw new IllegalStateException("Unable to generate JWT", ex);
		}
	}

	public String extractUsername(String token) {
		try {
			return SignedJWT.parse(token).getJWTClaimsSet().getSubject();
		} catch (ParseException ex) {
			throw new IllegalArgumentException("Invalid JWT", ex);
		}
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);
			if (!signedJWT.verify(new MACVerifier(secretKey.getEncoded()))) {
				return false;
			}

			JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
			Date expiration = claims.getExpirationTime();
			return claims.getSubject() != null
					&& claims.getSubject().equals(userDetails.getUsername())
					&& expiration != null
					&& expiration.toInstant().isAfter(Instant.now());
		} catch (ParseException | JOSEException ex) {
			return false;
		}
	}

	private SecretKey buildKey(String secret) {
		byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		if (keyBytes.length < 32) {
			byte[] expanded = new byte[32];
			System.arraycopy(keyBytes, 0, expanded, 0, keyBytes.length);
			keyBytes = expanded;
		}
		return new SecretKeySpec(keyBytes, "HmacSHA256");
	}
}

