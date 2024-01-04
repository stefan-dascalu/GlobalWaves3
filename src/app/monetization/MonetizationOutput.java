package app.monetization;

import lombok.Getter;

@Getter
public class MonetizationOutput {
    private final Double merchRevenue;
    private final Double songRevenue;
    private final Integer ranking;
    private final String mostProfitableSong;

    public MonetizationOutput(final Double merchRevenue,
                              final Double songRevenue,
                              final Integer ranking,
                              final String mostProfitableSong) {
        this.merchRevenue = merchRevenue;
        this.songRevenue = songRevenue;
        this.ranking = ranking;
        this.mostProfitableSong = mostProfitableSong;
    }
}
