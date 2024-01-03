package app.statistics;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Getter
public class ArtistStatisticsOutput implements StatisticsOutput {
    private final LinkedHashMap<String, Integer> topAlbums;
    private final LinkedHashMap<String, Integer> topSongs;
    private final ArrayList<String> topFans;
    private final Integer listeners;

    public ArtistStatisticsOutput(final LinkedHashMap<String, Integer> topAlbums,
                                  final LinkedHashMap<String, Integer> topSongs,
                                  final ArrayList<String> topFans, Integer listeners) {
        this.topAlbums = topAlbums;
        this.topSongs = topSongs;
        this.topFans = topFans;
        this.listeners = listeners;
    }
}
