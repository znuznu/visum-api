package znu.visum.components.statistics.usecases.getperyear.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.statistics.usecases.getperyear.domain.GetPerYearStatisticsService;

import javax.validation.constraints.Min;
import java.time.Year;

@Validated
@RestController
@RequestMapping(value = "/api/statistics/years", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetPerYearStatisticsController.class)
public class GetPerYearStatisticsController {
  private final GetPerYearStatisticsService GetPerYearStatisticsService;

  @Autowired
  public GetPerYearStatisticsController(GetPerYearStatisticsService getPerYearStatisticsService) {
    this.GetPerYearStatisticsService = getPerYearStatisticsService;
  }

  @ApiOperation("Get per year statistics.")
  @GetMapping("/{year}")
  @ResponseStatus(HttpStatus.OK)
  public GetPerYearStatisticsResponse getAllTimeStatistics(@PathVariable @Min(1900) int year) {
    return GetPerYearStatisticsResponse.from(
        GetPerYearStatisticsService.getStatisticsForYear(Year.of(year)));
  }
}
