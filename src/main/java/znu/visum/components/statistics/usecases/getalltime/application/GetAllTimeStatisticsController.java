package znu.visum.components.statistics.usecases.getalltime.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.statistics.usecases.getalltime.domain.GetAllTimeStatisticsService;

@RestController
@RequestMapping(value = "/api/statistics/years", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetAllTimeStatisticsResponse.class)
public class GetAllTimeStatisticsController {
  private final GetAllTimeStatisticsService getAllTimeStatisticsService;

  @Autowired
  public GetAllTimeStatisticsController(GetAllTimeStatisticsService getAllTimeStatisticsService) {
    this.getAllTimeStatisticsService = getAllTimeStatisticsService;
  }

  @ApiOperation("Get all-time statistics.")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public GetAllTimeStatisticsResponse getAllTimeStatistics() {
    return GetAllTimeStatisticsResponse.from(getAllTimeStatisticsService.getAllTimeStatistics());
  }
}
