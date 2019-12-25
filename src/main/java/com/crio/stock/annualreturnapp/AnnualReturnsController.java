package com.crio.stock.annualreturnapp;

import com.crio.warmup.stock.quotes.StockQuoteServiceFactory;
import com.crio.warmup.stock.quotes.StockQuotesService;
import com.crio.warmup.stock.service.PortfolioManagerService;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/portfolio")
public class AnnualReturnsController {

  private PortfolioManagerService portfolioManagerService;

  @Autowired
  public AnnualReturnsController(RestTemplate restTemplate) {
    StockQuotesService stockQuotesService = new StockQuoteServiceFactory().getService(null, restTemplate);
    portfolioManagerService = new PortfolioManagerService(stockQuotesService);
  }

  @PostMapping("/analyze")
  @ResponseBody
  public PortfolioResponse calculateReturns(@RequestBody Portfolio portfolio) {
    LocalDate endDate = LocalDate.now().minus(1, ChronoUnit.DAYS);
    return PortfolioResponse.builder()
        .annualizedReturns(portfolioManagerService.calculateAnnualizedReturn(portfolio.getPortfolioTrades(), endDate))
        .calculationsDate(endDate)
        .name(portfolio.getName())
        .build();
  }
}
