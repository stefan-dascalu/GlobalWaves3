package app.monetization;

import app.Admin;
import app.audio.Files.Song;

import java.util.List;

public final class PremiumUserRevenueCalculator extends RevenueCalculator {
    private static final double SUBSCRIPTION_FEE = 1000000;

    public PremiumUserRevenueCalculator(final List<Song> songHistory) {
        super(songHistory);
    }

    @Override
    public void calculateRevenue(final Integer adPrice) {
        double revenuePerSong = SUBSCRIPTION_FEE / songHistory.size();

        songHistory.forEach(song -> {
            Admin.getInstance().getArtist(song.getArtist())
                    .increaseSongRevenue(song, revenuePerSong);
        });

        songHistory.clear();
    }
}
