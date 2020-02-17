package com.crio.stock.annualreturnapp;

import com.crio.warmup.stock.portfolio.PortfolioManager;
import com.crio.warmup.stock.portfolio.PortfolioManagerFactory;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/portfolio")
public class AnnualReturnsController {

  private RestTemplate restTemplate;
  private int numThreads = 10;

  @Autowired
  public AnnualReturnsController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @PostMapping("/analyze")
  @ResponseBody
  public PortfolioResponse calculateReturns(@RequestBody Portfolio portfolio)
      throws InterruptedException {
    PortfolioManager portfolioManager = PortfolioManagerFactory.getPortfolioManager(restTemplate);
    LocalDate endDate = LocalDate.now().minus(1, ChronoUnit.DAYS);
    try {
      return PortfolioResponse.builder()
          .annualizedReturns(portfolioManager
              .calculateAnnualizedReturn(portfolio.getPortfolioTrades(), endDate))
          .calculationsDate(endDate)
          .name(portfolio.getName())
          .build();
    } catch (Throwable e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Provider error", e);
    }
  }
}
