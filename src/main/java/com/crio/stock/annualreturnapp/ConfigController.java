package com.crio.stock.annualreturnapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/config")
public class ConfigController {

  private RestTemplate restTemplate;
  private int numThreads = 10;

  @Autowired
  public ConfigController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @GetMapping("/json")
  @ResponseBody
  public Object calculateReturns()
      throws IOException {
    return new ObjectMapper()
        .readValue(
            new ClassPathResource("jsons/users.json").getInputStream(), Object.class);
  }
}
