package com.lanzendorf.metereologia.metereologia.resource.dto;

import java.time.LocalDateTime;
import com.lanzendorf.metereologia.metereologia.model.Medicao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class
 *
 * @author Maicon Lanzendorf
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicaoDto {

  private LocalDateTime data;
  private Double altura;
  private String status;

  private void setStatus(Double altura) {
    if (altura == null)
      this.status = "Falha";
    else if (altura >= 5.4)
      this.status = "Emergência";
    else if (altura >= 3.5)
      this.status = "Alerta";
    else if (altura >= 2.5)
      this.status = "Atenção";
    else if (altura <= 1)
      this.status = "Baixo";
    else
      this.status = "Normal";
  }

  public MedicaoDto(Medicao medicao) {
    this.data = medicao.getData();
    this.altura = medicao.getAltura();
    setStatus(medicao.getAltura());
  }
}
