package com.crio.stock.annualreturnapp;

import com.crio.warmup.stock.exception.RateLimitException;
import com.crio.warmup.stock.quotes.StockQuoteServiceFactory;
import com.crio.warmup.stock.quotes.StockQuotesService;
import com.crio.warmup.stock.service.PortfolioManagerService;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/portfolio")
public class AnnualReturnsController {

  private PortfolioManagerService portfolioManagerService;
  private int numThreads = 10;

  @Autowired
  public AnnualReturnsController(RestTemplate restTemplate) {
    StockQuotesService stockQuotesService = new StockQuoteServiceFactory()
        .getService(null, restTemplate);
    portfolioManagerService = new PortfolioManagerService(stockQuotesService);
  }

  @PostMapping("/analyze")
  @ResponseBody
  public PortfolioResponse calculateReturns(@RequestBody Portfolio portfolio)
      throws InterruptedException {
    LocalDate endDate = LocalDate.now().minus(1, ChronoUnit.DAYS);
    try {
      return PortfolioResponse.builder()
          .annualizedReturns(portfolioManagerService
              .calculateAnnualizedReturnParallel(portfolio.getPortfolioTrades(), endDate,
                  numThreads))
          .calculationsDate(endDate)
          .name(portfolio.getName())
          .build();
    } catch (RateLimitException e) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Provider error", e);
    }
  }
}
