package com.lanzendorf.metereologia.metereologia.resource;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lanzendorf.metereologia.metereologia.resource.dto.MedicaoDto;
import com.lanzendorf.metereologia.metereologia.service.MonitoramentoService;

/**
 * Class
 *
 * @author Maicon Lanzendorf
 */
@RestController
@RequestMapping("/monitoramento")
public class MonitoramentoEndpoint {

  @Autowired
  private MonitoramentoService monitoramentoService;

  @GetMapping
  public List<MedicaoDto> obterMedicoes() {
    return monitoramentoService.obterMedicoes()
        .stream()
        .map(MedicaoDto::new)
        .collect(Collectors.toList());
  }

}
