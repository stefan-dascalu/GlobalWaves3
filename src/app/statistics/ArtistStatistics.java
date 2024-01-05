package app.statistics;

import app.Admin;
import app.user.Artist;
import app.user.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type ArtistStatistics.
 */
public final class ArtistStatistics extends Statistics {
    public ArtistStatistics(final Artist artist) {
        userAbstract = artist;
    }

    /**
     * Gets number of listens for artist.
     *
     * @return the number of listens
     */
    public int getListens() {
        Artist artist = (Artist) userAbstract;
        List<UserStatistics> userStatistics = Admin.getInstance().getUserStatistics();

        return userStatistics.stream()
                .flatMap(s -> s.getAlbumListens().entrySet().stream())
                .filter(e -> artist.getAlbums().contains(e.getKey()))
                .mapToInt(Map.Entry::getValue).sum();
    }

    /**
     * Gets the top fans.
     *
     * @return the top fans
     */
    public List<User> topFans() {
        Artist artist = (Artist) userAbstract;
        List<UserStatistics> userStatistics = Admin.getInstance().getUserStatistics();

        Map<User, Integer> fanListens = userStatistics.stream()
                .filter(s -> s.getArtistListens().getOrDefault(artist.getUsername(), 0) > 0)
                .collect(Collectors.toMap(s -> (User) s.userAbstract,
                        s -> s.getArtistListens().getOrDefault(artist.getUsername(), 0),
                        (e1, e2) -> e1));

        List<User> topFans = fanListens.entrySet().stream()
                .sorted(Map.Entry.<User, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(e -> e.getKey().getUsername()))
                .limit(maxLimit).map(Map.Entry::getKey).toList();

        return topFans;
    }

    @Override
    public StatisticsOutput wrapped() throws EmptyStatisticsException {
        if (getListens() == 0) {
            throw new EmptyStatisticsException("No data to show for artist %s."
                    .formatted(userAbstract.getUsername()));
        }

        Artist artist = (Artist) userAbstract;
        List<UserStatistics> userStatistics = Admin.getInstance().getUserStatistics();

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
