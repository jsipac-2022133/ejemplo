package com.jamessipac.bookingSystem.service.reserva;

import com.jamessipac.bookingSystem.dto.ReservaResponseDto;
import com.jamessipac.bookingSystem.model.Reserva;

import java.util.List;

public interface ReservaService {
    List<Reserva> findAll();
    Reserva findById(String id);
    Reserva save(Reserva reserva);
    Reserva update(String id, Reserva reserva);
    void deleteById(String id);
    ReservaResponseDto getReservaById(String reservaId);
}
