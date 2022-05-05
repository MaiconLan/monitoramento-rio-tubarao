package com.lanzendorf.metereologia.metereologia.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Class
 *
 * @author Maicon Lanzendorf
 */

@Getter
@Setter
@Table(name = "medicao")
@Entity
public class Medicao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime data;
  private Double altura;

}
