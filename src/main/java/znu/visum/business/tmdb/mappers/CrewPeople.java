package znu.visum.business.tmdb.mappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrewPeople extends CreditPeople {
    @JsonProperty("job")
    private String job;

    public CrewPeople() {
        super();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public boolean isDirector() {
        return this.job.equals("Director");
    }
}
