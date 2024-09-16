package com.emazon.msvc_stock.domain.spi;


public interface ITokenServicePort {
    String extractUsername(String token);
    String extractSpecificClaim(String token, String claimName);
    boolean isValidToken(String token);
}
