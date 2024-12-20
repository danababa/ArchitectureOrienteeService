package com.aos.tp.userService.config;


import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class JwtTokenProvider {

    private final String secretKey = "MySecretKey";
    private final long validityInMilliseconds = 3600000; // Token validity duration (1 hour)

    /**
     * Generate JWT token
     * @param username of user
     * @return JWT token
     */
    public String generateToken(String username) {
        long now = System.currentTimeMillis();
        long expiry = now + validityInMilliseconds;

        // Token Payload
        String payload = "{\"sub\":\"" + username + "\",\"iat\":" + now + ",\"exp\":" + expiry + "}";

        // Base64 encode the header and payload
        String header = Base64.getEncoder().encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes());
        String encodedPayload = Base64.getEncoder().encodeToString(payload.getBytes());

        // Generate the signature (HMAC-SHA256)
        String signature = sign(header + "." + encodedPayload, secretKey);

        // Return the complete token
        return header + "." + encodedPayload + "." + signature;
    }

    /**
     * Validate the JWT token
     * @param token of user
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return false;

            String header = parts[0];
            String payload = parts[1];
            String signature = parts[2];

            // Validate the signature
            String expectedSignature = sign(header + "." + payload, secretKey);
            if (!expectedSignature.equals(signature)) return false;

            // Validate expiration
            String decodedPayload = new String(Base64.getDecoder().decode(payload));
            long expiry = Long.parseLong(decodedPayload.replaceAll(".*\"exp\":(\\d+).*", "$1"));
            return expiry > System.currentTimeMillis();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the username from the token
     * @param token of user
     * @return username
     */
    public String getUsernameFromToken(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) throw new IllegalArgumentException("Invalid token");

        String payload = new String(Base64.getDecoder().decode(parts[1]));
        return payload.replaceAll(".*\"sub\":\"([^\"]+)\".*", "$1");
    }

    /**
     * Method to generate HMAC-SHA256 signature
     * @param data to sign
     * @param secret key
     * @return signature
     */
    private String sign(String data, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Error signing the token", e);
        }
    }
}
