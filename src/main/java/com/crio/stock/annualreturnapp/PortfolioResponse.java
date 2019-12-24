package com.crio.stock.annualreturnapp;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PortfolioResponse {

  private final String name;
  private final List<AnnualizedReturn> annualizedReturns;
  private final LocalDate calculationsDate;

}
