package app.monetization;

import app.Admin;
import app.audio.Files.Song;

import java.util.List;

public final class FreeUserRevenueCalculator extends RevenueCalculator {

    public FreeUserRevenueCalculator(final List<Song> songHistory) {
        super(songHistory);
    }

    @Override
    public void calculateRevenue(final Integer adPrice) {
        double revenuePerSong = (double) adPrice / songHistory.size();

        songHistory.forEach(song -> Admin.getInstance().getArtist(song.getArtist())
                .increaseSongRevenue(song, revenuePerSong));

        songHistory.clear();
    }
}
