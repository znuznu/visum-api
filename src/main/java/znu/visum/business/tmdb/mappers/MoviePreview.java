package znu.visum.business.tmdb.mappers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoviePreview {
    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    private LocalDate releaseDate;

    public MoviePreview() {
    }

    @JsonProperty("releaseDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    @JsonProperty("release_date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
