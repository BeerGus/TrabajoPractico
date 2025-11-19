package dev.danvega.h2demo.repository;

import dev.danvega.h2demo.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    // Reservas activas de una sala (no canceladas y no vencidas)
    @Query("SELECT r FROM Reserva r " +
            "WHERE r.idSala = :idSala " +
            "  AND r.estadoSala <> 'CANCELADA' " +
            "  AND r.fechaHoraFin >= :ahora")
    List<Reserva> findReservasActivasPorSala(@Param("idSala") Integer idSala,
                                             @Param("ahora") LocalDateTime ahora);

    // Cantidad de reservas activas de una sala (para la columna extra)
    @Query("SELECT COUNT(r) FROM Reserva r " +
            "WHERE r.idSala = :idSala " +
            "  AND r.estadoSala <> 'CANCELADA' " +
            "  AND r.fechaHoraFin >= :ahora")
    long countReservasActivasPorSala(@Param("idSala") Integer idSala,
                                     @Param("ahora") LocalDateTime ahora);

    // Reservas que se solapan en horario en una sala (para validar nuevas reservas)
    @Query("SELECT r FROM Reserva r " +
            "WHERE r.idSala = :idSala " +
            "  AND r.estadoSala <> 'CANCELADA' " +
            "  AND r.fechaHoraFin > :inicio " +
            "  AND r.fechaHoraInicio < :fin")
    List<Reserva> findSolapadas(@Param("idSala") Integer idSala,
                                @Param("inicio") LocalDateTime inicio,
                                @Param("fin") LocalDateTime fin);
}
