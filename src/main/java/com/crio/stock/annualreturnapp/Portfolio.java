package com.crio.stock.annualreturnapp;

import com.crio.warmup.stock.dto.PortfolioTrade;
import java.util.List;
import lombok.Data;
import lombok.Getter;

@Data
public class Portfolio {

  private String name;
  private List<PortfolioTrade> portfolioTrades;

}
