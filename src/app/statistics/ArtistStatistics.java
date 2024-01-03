package app.statistics;

import app.user.Artist;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type ArtistStatistics.
 */
public final class ArtistStatistics extends Statistics {
    private final List<UserStatistics> userStatistics;

    public ArtistStatistics(final Artist artist, final List<UserStatistics> userStatistics) {
        userAbstract = artist;
        this.userStatistics = userStatistics;
    }

    /**
     * Computes monetization statistics
     *
     * @return the monetization output
     */
    public MonetizationOutput getMonetization() {
        long listens = userStatistics.stream().filter(s -> s.getArtistListens()
                        .getOrDefault(userAbstract.getUsername(), 0) > 0).count();

        if (listens == 0) {
            return null;
        }

        return new MonetizationOutput();
    }

    @Override
    public StatisticsOutput wrapped() {
        Artist artist = (Artist) userAbstract;

        Map<String, Integer> albumListens = userStatistics.stream()
                .flatMap(s -> s.getAlbumListens().entrySet().stream())
                .filter(e -> artist.getAlbums().contains(e.getKey()))
                .collect(Collectors.toMap(e -> e.getKey().getName(),
                        Map.Entry::getValue, Integer::sum));

        Map<String, Integer> songListens = userStatistics.stream()
                .flatMap(s -> s.getSongListens().entrySet().stream())
                .filter(e -> artist.getAllSongs().contains(e.getKey()))
                .collect(Collectors.toMap(e -> e.getKey().getName(),
                        Map.Entry::getValue, Integer::sum));

        LinkedHashMap<String, Integer> topAlbums = albumListens.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey))
                .limit(maxLimit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        LinkedHashMap<String, Integer> topSongs = songListens.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey))
                .limit(maxLimit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        Map<String, Integer> fanListens = userStatistics.stream()
                .filter(s -> s.getArtistListens().getOrDefault(artist.getUsername(), 0) > 0)
                .collect(Collectors.toMap(s -> s.userAbstract.getUsername(),
                        s -> s.getArtistListens().getOrDefault(artist.getUsername(), 0),
                        (e1, e2) -> e1));

        ArrayList<String> topFans = fanListens.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey))
                .limit(maxLimit).map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayList::new));

        return new ArtistStatisticsOutput(topAlbums, topSongs, topFans, fanListens.size());
    }
}
