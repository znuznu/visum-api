package znu.visum.components.statistics.usecases.getperyear.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.statistics.usecases.getperyear.domain.GetStatisticsPerYear;

import javax.validation.constraints.Min;
import java.time.Year;

@Validated
@RestController
@RequestMapping(value = "/api/statistics/years", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetStatisticsPerYearController.class)
public class GetStatisticsPerYearController {

  private final GetStatisticsPerYear getStatisticsPerYear;

  @Autowired
  public GetStatisticsPerYearController(GetStatisticsPerYear getStatisticsPerYear) {
    this.getStatisticsPerYear = getStatisticsPerYear;
  }

  @Operation(summary = "Get per year statistics.")
  @GetMapping("/{year}")
  @ResponseStatus(HttpStatus.OK)
  public GetStatisticsPerYearResponse getAllTimeStatistics(@PathVariable @Min(1900) int year) {
    return GetStatisticsPerYearResponse.from(getStatisticsPerYear.process(Year.of(year)));
  }
}
