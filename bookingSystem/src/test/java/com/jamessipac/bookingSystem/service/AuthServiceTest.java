package com.jamessipac.bookingSystem.service;

import com.jamessipac.bookingSystem.model.Usuario;
import com.jamessipac.bookingSystem.repository.usuario.UsuarioRepository;
import com.jamessipac.bookingSystem.security.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;


    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario("1", "testUser", "test@example.com", "password", "98980909");
    }

    @Test
    void testLogin_ValidCredentials() {
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(usuario);
        when(jwtTokenUtil.generateToken("1")).thenReturn("token123");

        String token = authService.login("test@example.com", "password");
        assertEquals("token123", token);
        verify(usuarioRepository).findByEmail("test@example.com");
        verify(jwtTokenUtil).generateToken("1");
    }

    @Test
    void testLogin_InvalidEmail() {
        when(usuarioRepository.findByEmail("invalid@example.com")).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login("invalid@example.com", "password");
        });
        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void testLogin_InvalidPassword() {
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(usuario);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login("test@example.com", "wrongpassword");
        });
        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void testLogin_UserWithNullEmail() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(null, "password");
        });
        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void testLogin_UserWithNullPassword() {
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(usuario);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login("test@example.com", null);
        });
        assertEquals("Invalid credentials", exception.getMessage());
    }
}
