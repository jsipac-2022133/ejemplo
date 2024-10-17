package com.jamessipac.bookingSystem.controller.reserva;

import com.jamessipac.bookingSystem.dto.ReservaResponseDto;
import com.jamessipac.bookingSystem.model.Reserva;
import com.jamessipac.bookingSystem.service.reserva.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public List<Reserva> findAll(){
        return reservaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDto> getReservaById(@PathVariable String id) {
        try {
            ReservaResponseDto reservaDto = reservaService.getReservaById(id);
            return ResponseEntity.ok(reservaDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping
    public ResponseEntity<Reserva> save(@RequestBody Reserva reserva) {
        try {
            // Intentar guardar la reserva
            Reserva nuevaReserva = reservaService.save(reserva);
            return ResponseEntity.ok(nuevaReserva);
        } catch (IllegalArgumentException e) {
            // Si el usuario no existe, devolver un 400 Bad Request con el mensaje de error
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PutMapping("/{idReserva}")
    public ResponseEntity<Reserva> update(@PathVariable String idReserva, @RequestBody Reserva reserva){
        try {
            Reserva actualizarReserva=reservaService.update(idReserva, reserva);
            return ResponseEntity.ok(actualizarReserva);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }

    }

    @DeleteMapping("{idReserva}")
    public void deleteById(@PathVariable String idReserva){
        reservaService.deleteById(idReserva);
    }
}
