package znu.visum.components.externals.tmdb.usecases.getmoviesbydirectorid.domain;

public record GetTmdbMoviesByDirectorIdQuery(long directorId, boolean notSavedOnly) {
}
