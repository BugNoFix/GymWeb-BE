package com.marcominaudo.gymweb.unitTest.security;

import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.exception.exceptions.JWTException;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.security.jwt.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    UtilsTest utilsTest = new UtilsTest();

    String secretKey = "3fcff1b4f217030ee29aa693b6baece2b726593b03f421f4209a785d077a812d";

    JWTUtil jwtUtil = new JWTUtil(secretKey, 64000);

    @Test
    void generateToken(){
        User user = utilsTest.getUser();
        assertDoesNotThrow(() ->  jwtUtil.generateToken(user));
    }

    @Test
    void extractEmail(){
        User user = utilsTest.getUser();
        String jwt = jwtUtil.generateToken(user);

        // Test
        String email = assertDoesNotThrow(() ->  jwtUtil.extractEmail(jwt));
        assertEquals("marco.minaudo@gmail.com",email);
    }

    // ------- Check token valid ------------
    @Test
    void isTokenValidSuccess(){
        User user = utilsTest.getUser();
        String jwt = jwtUtil.generateToken(user);

        // Test
        assertDoesNotThrow(() ->  jwtUtil.isTokenValid(jwt));
    }

    @Test
    void isTokenValidThrowInvalidJWT(){
        try (MockedStatic<Jwts> utilities = Mockito.mockStatic(Jwts.class)) {
            // Mock
            utilities.when(() -> Jwts.parser()).thenThrow(new MalformedJwtException("Invalid token"));

            // Test
            JWTException thrown = assertThrows(JWTException.class, () -> jwtUtil.isTokenValid("token"));
            assertEquals(JWTException.ExceptionCodes.INVALID_JWT_SIGNATURE.name(), thrown.getMessage());

        }
    }

    @Test
    void isTokenValidThrowExpiredJWT(){
        try (MockedStatic<Jwts> utilities = Mockito.mockStatic(Jwts.class)) {
            // Mock
            utilities.when(() -> Jwts.parser()).thenThrow(new ExpiredJwtException(null, null, "Expired"));

            // Test
            JWTException thrown = assertThrows(JWTException.class, () -> jwtUtil.isTokenValid("token"));
            assertEquals(JWTException.ExceptionCodes.EXPIRED_JWT_TOKEN.name(), thrown.getMessage());

        }
    }

    @Test
    void isTokenValidThrowUnsupportedJWT(){
        try (MockedStatic<Jwts> utilities = Mockito.mockStatic(Jwts.class)) {
            // Mock
            utilities.when(() -> Jwts.parser()).thenThrow(new UnsupportedJwtException("Unsupported token"));

            // Test
            JWTException thrown = assertThrows(JWTException.class, () -> jwtUtil.isTokenValid("token"));
            assertEquals(JWTException.ExceptionCodes.UNSUPPORTED_JWT_TOKEN.name(), thrown.getMessage());

        }
    }
    // -------------------------------
}
