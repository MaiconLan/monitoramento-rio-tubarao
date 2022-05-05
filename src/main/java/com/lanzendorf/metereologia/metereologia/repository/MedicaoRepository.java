package com.lanzendorf.metereologia.metereologia.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lanzendorf.metereologia.metereologia.model.Medicao;

/**
 * Class
 *
 * @author Maicon Lanzendorf
 */
public interface MedicaoRepository extends JpaRepository<Medicao, Long> {

  Optional<Medicao> findByData(LocalDateTime data);
}
