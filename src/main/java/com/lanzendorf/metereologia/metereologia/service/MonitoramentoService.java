package com.lanzendorf.metereologia.metereologia.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import com.lanzendorf.metereologia.metereologia.model.Medicao;
import com.lanzendorf.metereologia.metereologia.repository.MedicaoRepository;

/**
 * Class
 *
 * @author Maicon Lanzendorf
 */
@Service
public class MonitoramentoService {

  private String server = "https://plantaragronomia.eng.br";
  private String uri = "/nivelFTP.txt";
  private RestTemplate rest;
  private HttpHeaders headers;
  private HttpStatus status;

  @Autowired
  private MedicaoRepository medicaoRepository;

  public void realizarMedicao() {
    initialize();

    System.out.println("Rodando!");

    try {
      Medicao medicao = getMedicao();
      if (this.status == HttpStatus.OK) {
        salvar(medicao);
      } else {
        System.err.println("Erro ao chamar servidor, status: " + this.status);
      }
    } catch (ResourceAccessException e) {
      System.err.println("Não foi possível conectar no servidor!");
      e.printStackTrace();
    }
  }

  private void salvar(Medicao medicao) {
    Optional<Medicao> medicaoOptional = medicaoRepository.findByData(medicao.getData());

    if (medicaoOptional.isEmpty()) {
      mostrarMedicao(medicao);
      medicaoRepository.save(medicao);
    }
  }

  private void mostrarMedicao(Medicao medicao) {
    System.out.println("Data: " + medicao.getData() + " - Medição: " + medicao.getAltura());
  }


  private void initialize() {
    this.rest = new RestTemplate();
    this.headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain");
    headers.add("Accept", "*/*");
  }

  private Medicao getMedicao() {
    System.out.println("Chamando servidor");
    return convert(get(uri));
  }

  private String get(String uri) {
    HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
    ResponseEntity<String> responseEntity = rest
        .exchange(server + uri, HttpMethod.GET, requestEntity, String.class);
    this.status = responseEntity.getStatusCode();

    System.out.println("Retorno: " + this.status.value());
    System.out.println("Body: " + responseEntity.getBody());
    return responseEntity.getBody();
  }


  private Medicao convert(String body) {
    String[] split = body.split(";");

    Medicao medicao = new Medicao();

    String altura = split[1].replace(",", ".")
        .replace("m", "")
        .trim();

    String data = split[0];

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    LocalDateTime dataHora = LocalDateTime.parse(data, formatter);

    medicao.setAltura(Double.valueOf(altura));
    medicao.setData(dataHora);
    return medicao;
  }

  public List<Medicao> obterMedicoes() {
    return medicaoRepository.findAll(Sort.by("data").descending());
  }
}
