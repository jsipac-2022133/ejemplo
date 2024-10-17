package com.jamessipac.bookingSystem.service;

import com.jamessipac.bookingSystem.model.Usuario;
import com.jamessipac.bookingSystem.repository.usuario.UsuarioRepository;
import com.jamessipac.bookingSystem.service.usuario.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    public void testFindAll() {
        Usuario usuario1 = new Usuario("1", "James", "james@gmail.com", "password1", "123456");
        Usuario usuario2 = new Usuario("2", "Melisa", "melisa@gmail.com", "password2", "789012");

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        List<Usuario> usuarios = usuarioService.findAll();

        assertEquals(2, usuarios.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    public void testFindById_UsuarioExiste() {
        Usuario usuario = new Usuario("1", "James", "james@gmail.com", "password1", "123456");

        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));

        Usuario encontrado = usuarioService.findById("1");

        assertNotNull(encontrado);
        assertEquals("James", encontrado.getNombre());
        verify(usuarioRepository, times(1)).findById("1");
    }

    @Test
    public void testFindById_UsuarioNoExiste() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.empty());

        Usuario encontrado = usuarioService.findById("1");

        assertNull(encontrado);
        verify(usuarioRepository, times(1)).findById("1");
    }

    @Test
    public void testSave() {
        Usuario usuario = new Usuario("1", "James", "james@gmail.com", "password1", "123456");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario guardado = usuarioService.save(usuario);

        assertNotNull(guardado);
        assertEquals("James", guardado.getNombre());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    // Test para el método update
    @Test
    public void testUpdate_UsuarioExiste() {
        Usuario usuarioExistente = new Usuario("1", "James", "james@gmail.com", "password1", "123456");
        Usuario usuarioActualizado = new Usuario("1", "James Updated", "james.updated@gmail.com", "passwordUpdated", "654321");

        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(usuarioExistente)).thenReturn(usuarioExistente);

        Usuario resultado = usuarioService.update("1", usuarioActualizado);

        assertNotNull(resultado);
        assertEquals("James Updated", resultado.getNombre());
        assertEquals("james.updated@gmail.com", resultado.getEmail());
        assertEquals("654321", resultado.getTelefono());
        verify(usuarioRepository, times(1)).findById("1");
        verify(usuarioRepository, times(1)).save(usuarioExistente);
    }

    @Test
    public void testUpdate_UsuarioNoExiste() {
        Usuario usuarioActualizado = new Usuario("1", "James Updated", "james.updated@gmail.com", "paswordUpdated", "654321");

        when(usuarioRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.update("1", usuarioActualizado);
        });

        verify(usuarioRepository, times(1)).findById("1");
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }

    @Test
    public void testDeleteById() {
        doNothing().when(usuarioRepository).deleteById("1");

        usuarioService.deleteById("1");

        verify(usuarioRepository, times(1)).deleteById("1");
    }
}

