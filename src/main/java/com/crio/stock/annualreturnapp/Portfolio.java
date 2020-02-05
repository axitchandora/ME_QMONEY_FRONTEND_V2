package com.crio.stock.annualreturnapp;

import com.crio.warmup.stock.dto.PortfolioTrade;
import java.util.List;
import lombok.Data;
import lombok.Getter;

public class Portfolio {

  private String name;
  private List<PortfolioTrade> portfolioTrades;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<PortfolioTrade> getPortfolioTrades() {
    return portfolioTrades;
  }

  public void setPortfolioTrades(List<PortfolioTrade> portfolioTrades) {
    this.portfolioTrades = portfolioTrades;
  }
}
