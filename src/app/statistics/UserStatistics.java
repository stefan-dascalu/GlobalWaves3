package app.statistics;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.user.User;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type UserStatistics.
 */
@Getter
public final class UserStatistics extends Statistics {
    private final HashMap<Song, Integer> songListens;
    private final HashMap<Episode, Integer> episodeListens;

    private final HashMap<Album, Integer> albumListens;
    private final HashMap<String, Integer> genreListens;
    private final HashMap<String, Integer> artistListens;
    private final HashMap<String, Integer> hostListens;

    public UserStatistics(final User user) {
        userAbstract = user;

        songListens = new HashMap<>();
        episodeListens = new HashMap<>();

        albumListens = new HashMap<>();
        genreListens = new HashMap<>();
        artistListens = new HashMap<>();
        hostListens = new HashMap<>();
    }

    public void increaseListenCount(final LibraryEntry entry) {
        if (entry instanceof Song song) {
            songListens.put(song, songListens.getOrDefault(song, 0) + 1);
            genreListens.put(song.getGenre(),
                    genreListens.getOrDefault(song.getGenre(), 0) + 1);
            artistListens.put(song.getArtist(),
                    artistListens.getOrDefault(song.getArtist(), 0) + 1);

            Album album = Admin.getInstance().getArtist(song.getArtist()).getAlbum(song.getAlbum());
            albumListens.put(album, albumListens.getOrDefault(album, 0) + 1);
        } else if (entry instanceof Episode episode) {
            episodeListens.put(episode, episodeListens.getOrDefault(episode, 0) + 1);
        }
    }

    @Override
    public StatisticsOutput wrapped() throws EmptyStatisticsException {
        if (songListens.isEmpty() && episodeListens.isEmpty()) {
            throw new EmptyStatisticsException("No data to show for user %s."
                    .formatted(userAbstract.getUsername()));
        }

        Map<String, Integer> combinedSongListens = songListens.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().getArtist() + " - "
                        + e.getKey().getName(), Map.Entry::getValue, Integer::sum));

        LinkedHashMap<String, Integer> topSongs = combinedSongListens.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().substring(e.getKey().indexOf(" - ") + 3),
                        Map.Entry::getValue, Integer::sum)).entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey))
                .limit(maxLimit).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        Integer::sum, LinkedHashMap::new));


        LinkedHashMap<String, Integer> topArtists = artistListens.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey))
                .limit(maxLimit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        Integer::sum, LinkedHashMap::new));

        LinkedHashMap<String, Integer> topGenres = genreListens.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey))
                .limit(maxLimit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        Integer::sum, LinkedHashMap::new));


        LinkedHashMap<String, Integer> topAlbums = albumListens.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().getName(), Map.Entry::getValue,
                        Integer::sum))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey))
                .limit(maxLimit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum,
                        LinkedHashMap::new));

        LinkedHashMap<String, Integer> topEpisodes = episodeListens.entrySet().stream()
                .sorted(Map.Entry.<Episode, Integer>comparingByValue().reversed()
                        .thenComparing(e -> e.getKey().getName()))
                .limit(maxLimit)
                .collect(Collectors.toMap(e -> e.getKey().getName(), Map.Entry::getValue,
                        Integer::sum, LinkedHashMap::new));

        return new UserStatisticsOutput(topArtists, topGenres, topSongs, topAlbums, topEpisodes);
    }
}
