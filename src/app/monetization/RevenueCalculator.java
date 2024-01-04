package app.monetization;

import app.audio.Files.Song;

import java.util.List;

/**
 * The type RevenueCalculator abstract.
 */
public abstract class RevenueCalculator {
    protected final List<Song> songHistory;

    protected RevenueCalculator(final List<Song> songHistory) {
        this.songHistory = songHistory;
    }

    public abstract void calculateRevenue(Integer adPrice);

    public final void addSong(final Song song) {
        songHistory.add(song);
    }
}
