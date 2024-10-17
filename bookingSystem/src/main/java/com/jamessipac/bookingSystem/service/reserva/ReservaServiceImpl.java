package com.jamessipac.bookingSystem.service.reserva;

import com.jamessipac.bookingSystem.dto.ReservaResponseDto;
import com.jamessipac.bookingSystem.model.Reserva;
import com.jamessipac.bookingSystem.model.Usuario;
import com.jamessipac.bookingSystem.repository.reserva.ReservaRepository;
import com.jamessipac.bookingSystem.repository.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservaServiceImpl implements ReservaService{

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Override
    public Reserva findById(String id) {
        return reservaRepository.findById(id).orElse(null);
    }

    @Override
    public Reserva save(Reserva reserva) {

        // Asignar la fecha de reserva si no se ha proporcionado
        if (reserva.getFechaReserva() == null) {
            reserva.setFechaReserva(LocalDateTime.now());
        }

        Optional<Usuario> usuario = usuarioRepository.findById(reserva.getUsuarioId());
        if (usuario.isPresent()) {
            // Guardar la reserva si el usuario existe
            return reservaRepository.save(reserva);
        } else {
            // Lanzar una excepción si el usuario no existe
            throw new IllegalArgumentException("El usuario con ID " + reserva.getUsuarioId() + " no existe.");
        }
    }


    @Override
    public Reserva update(String id, Reserva reserva) {
        // Buscar la reserva existente por su ID
        Reserva existingReserva = reservaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Reserva con ID " + id + " no encontrada.")
        );

        // Validar si se ha proporcionado un nuevo usuarioId y si el usuario existe en la BD
        if (reserva.getUsuarioId() != null) {
            Optional<Usuario> usuario = usuarioRepository.findById(reserva.getUsuarioId());
            if (usuario.isPresent()) {
                existingReserva.setUsuarioId(reserva.getUsuarioId());
            } else {
                throw new IllegalArgumentException("El usuario con ID " + reserva.getUsuarioId() + " no existe.");
            }
        }

        // Actualizar otros campos si es necesario (puedes añadir más lógica aquí)
        if (reserva.getFechaReserva() != null) {
            existingReserva.setFechaReserva(reserva.getFechaReserva());
        }

        // Guardar la reserva actualizada
        return reservaRepository.save(existingReserva);
    }


    @Override
    public void deleteById(String id) {
        reservaRepository.deleteById(id);
    }



    public ReservaResponseDto getReservaById(String reservaId) {
        // Buscar la reserva por su ID
        Optional<Reserva> reservaOpt = reservaRepository.findById(reservaId);
        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();

            // Buscar el usuario por el usuarioId en la reserva
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(reserva.getUsuarioId());
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();

                // Crear y devolver el DTO de respuesta con los datos del usuario
                return new ReservaResponseDto(reserva.getId(), usuario, reserva.getFechaReserva());
            } else {
                throw new IllegalArgumentException("Usuario no encontrado para la reserva.");
            }
        } else {
            throw new IllegalArgumentException("Reserva no encontrada.");
        }
    }



}
