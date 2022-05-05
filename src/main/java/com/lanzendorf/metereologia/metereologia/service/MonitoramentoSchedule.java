package com.lanzendorf.metereologia.metereologia.service;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Class
 *
 * @author Maicon Lanzendorf
 */
@Component
@EnableScheduling
public class MonitoramentoSchedule {

  @Autowired
  private MonitoramentoService monitoramentoService;

  @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.MINUTES)
  public void verificaPorMinuto() {
    monitoramentoService.realizarMedicao();
  }

}
