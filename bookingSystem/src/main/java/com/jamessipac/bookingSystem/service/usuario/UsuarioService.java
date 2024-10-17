package com.jamessipac.bookingSystem.service.usuario;

import com.jamessipac.bookingSystem.model.Usuario;
import java.util.List;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario findById(String id);
    Usuario save(Usuario usuario);
    Usuario update(String id, Usuario usuario);
    void deleteById(String id);
}
