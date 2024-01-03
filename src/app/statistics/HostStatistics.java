package app.statistics;

import app.user.Host;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type HostStatistics.
 */
public final class HostStatistics extends Statistics {
    private final List<UserStatistics> userStatistics;

    public HostStatistics(final Host host, final List<UserStatistics> userStatistics) {
        userAbstract = host;
        this.userStatistics = userStatistics;
    }

    @Override
    public StatisticsOutput wrapped() {
        Host host = (Host) userAbstract;

        Map<String, Integer> episodeListens = userStatistics.stream()
                .flatMap(s -> s.getEpisodeListens().entrySet().stream())
                .filter(e -> host.getAllEpisodes().contains(e.getKey()))
                .collect(Collectors.toMap(e -> e.getKey().getName(), Map.Entry::getValue,
                        Integer::sum));

        LinkedHashMap<String, Integer> topEpisodes = episodeListens.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey))
                .limit(maxLimit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1,
                        LinkedHashMap::new));

        long listeners = episodeListens.values().stream().distinct().count();

        return new HostStatisticsOutput(topEpisodes, (int) listeners);
    }
}
