package com.jamessipac.bookingSystem.service.usuario;

import com.jamessipac.bookingSystem.model.Usuario;
import com.jamessipac.bookingSystem.repository.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findById(String id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(String id, Usuario usuario) {
        Usuario existingUsuario = usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (usuario.getNombre() != null) {
            existingUsuario.setNombre(usuario.getNombre());
        }
        if (usuario.getEmail() != null) {
            existingUsuario.setEmail(usuario.getEmail());
        }
        if (usuario.getTelefono() != null) {
            existingUsuario.setTelefono(usuario.getTelefono());
        }

        return usuarioRepository.save(existingUsuario);
    }



    @Override
    public void deleteById(String id) {
        usuarioRepository.deleteById(id);
    }
}
