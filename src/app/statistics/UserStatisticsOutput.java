package app.statistics;

import lombok.Getter;

import java.util.LinkedHashMap;

@Getter
public class UserStatisticsOutput implements StatisticsOutput {
    private final LinkedHashMap<String, Integer> topArtists;
    private final LinkedHashMap<String, Integer> topGenres;
    private final LinkedHashMap<String, Integer> topSongs;
    private final LinkedHashMap<String, Integer> topAlbums;
    private final LinkedHashMap<String, Integer> topEpisodes;

    public UserStatisticsOutput(final LinkedHashMap<String, Integer> topArtists,
                                final LinkedHashMap<String, Integer> topGenres,
                                final LinkedHashMap<String, Integer> topSongs,
                                final LinkedHashMap<String, Integer> topAlbums,
                                final LinkedHashMap<String, Integer> topEpisodes) {
        this.topArtists = topArtists;
        this.topGenres = topGenres;
        this.topSongs = topSongs;
        this.topAlbums = topAlbums;
        this.topEpisodes = topEpisodes;
    }
}
