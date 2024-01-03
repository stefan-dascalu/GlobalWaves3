package app.statistics;

import lombok.Getter;

import java.util.LinkedHashMap;

@Getter
public class HostStatisticsOutput implements StatisticsOutput {
    private final LinkedHashMap<String, Integer> topEpisodes;
    private final Integer listeners;

    public HostStatisticsOutput(final LinkedHashMap<String, Integer> topEpisodes,
                                final Integer listeners) {
        this.topEpisodes = topEpisodes;
        this.listeners = listeners;
    }
}
