package app.statistics;

import app.user.Artist;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonetizationOutput {
    private Double merchRevenue;
    private Double songRevenue;
    private Integer ranking;
    private String mostProfitableSong;

    public MonetizationOutput() {
        songRevenue = 0.0;
        merchRevenue = 0.0;
        ranking = 0;
        mostProfitableSong = "N/A";
    }

    public MonetizationOutput(Artist artist) {
        this();
    }
}
