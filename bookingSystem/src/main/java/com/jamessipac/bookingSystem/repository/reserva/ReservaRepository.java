package com.jamessipac.bookingSystem.repository.reserva;

import com.jamessipac.bookingSystem.model.Reserva;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.Repository;

public interface ReservaRepository extends MongoRepository<Reserva, String> {
}
