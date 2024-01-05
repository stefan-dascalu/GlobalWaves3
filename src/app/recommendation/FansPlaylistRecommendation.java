package app.recommendation;

import app.Admin;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.Artist;
import app.user.User;

import java.util.*;

public final class FansPlaylistRecommendation extends Recommendation {
    public FansPlaylistRecommendation(final User user) {
        super(user);
    }

    @Override
    public String execute() {
        Artist artist = Admin.getInstance().getArtist(((Song) user.getPlayer()
                .getCurrentAudioFile()).getArtist());
        List<User> topFans = artist.getStatistics().topFans();

        Set<Song> topSongs = new HashSet<>();
        for (User user : topFans) {
            List<Song> likedSong = user.getLikedSongs().stream()
                    .sorted(Comparator.comparingInt(Song::getLikes).reversed()).toList();
            int added = 0;
            for (Song song : likedSong) {
                if (added == 5) {
                    break;
                }
                if (!topSongs.contains(song)) {
                    topSongs.add(song);
                    added++;
                }
            }

        }
        if (topSongs.isEmpty()) {
            return "No new recommendations were found";
        }

        Playlist playlist = new Playlist("%s Fan Club recommendations"
                .formatted(artist.getUsername()),
                user.getUsername());
        playlist.getSongs().addAll(topSongs);

        user.getPlaylistRecommendations().add(playlist);
        user.setLastRecommendation(playlist);

        return "The recommendations for user %s have been updated successfully.".formatted(user
                .getUsername());
    }
}
