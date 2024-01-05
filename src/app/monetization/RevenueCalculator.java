package app.monetization;

import app.Admin;
import app.audio.Files.Song;
import app.user.User;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type RevenueCalculator.
 */
public final class RevenueCalculator {
    private static final int SUBSCRIPTION_FEE = 1000000;

    private final List<Song> freeSongHistory;
    private final List<Song> premiumSongHistory;

    @Setter
    private int adPrice;
    private final User user;

    public RevenueCalculator(final User user) {
        this.user = user;
        freeSongHistory = new ArrayList<>();
        premiumSongHistory = new ArrayList<>();

        adPrice = 0;
    }

    /**
     * Generate revenue for artists.
     */
    public void calculateRevenue() {
        if (user.isPremium()) {
            calculateMembershipRevenue(SUBSCRIPTION_FEE, premiumSongHistory);
        } else {
            calculateMembershipRevenue(adPrice, freeSongHistory);
        }
    }

    /**
     * Generate revenue for artists.
     *
     * @param price the price
     * @param songHistory the song history
     */
    private void calculateMembershipRevenue(final int price, final List<Song> songHistory) {
        double revenuePerSong = (double) price / songHistory.size();

        songHistory.forEach(song -> {
            Admin.getInstance().getArtist(song.getArtist())
                    .increaseSongRevenue(song, revenuePerSong);
        });

        songHistory.clear();
    }

    /**
     * Adds song to history
     *
     * @param song the song
     */
    public void addSong(final Song song) {
        if (user.isPremium()) {
            premiumSongHistory.add(song);
        } else {
            freeSongHistory.add(song);
        }
    }
}
