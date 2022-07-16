package znu.visum.components.statistics.domain;

import znu.visum.core.models.common.Pair;

import java.time.Year;
import java.util.List;

public record MovieCount(
        List<Pair<Year, Integer>> perYear,
        List<Pair<String, Integer>> perGenre,
        List<Pair<String, Integer>> perOriginalLanguage
) { }
