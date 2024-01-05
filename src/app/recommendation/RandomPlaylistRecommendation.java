package app.recommendation;

import app.Admin;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;

import java.util.*;

public final class RandomPlaylistRecommendation extends Recommendation {
    public RandomPlaylistRecommendation(final User user) {
        super(user);
    }

    @Override
    public String execute() {
        Set<Song> userSongs = new HashSet<>(user.getLikedSongs());
        for (Playlist playlist : user.getPlaylists()) {
            userSongs.addAll(playlist.getSongs());
        }
        for (Playlist playlist : user.getFollowedPlaylists()) {
            userSongs.addAll(playlist.getSongs());
        }

        Map<String, Integer> genreListens = new HashMap<>();
        for (Song song : userSongs) {
            genreListens.put(song.getGenre(), 1 + genreListens.getOrDefault(song.getGenre(), 0));
        }

        List<String> topGenres = genreListens.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3).map(Map.Entry::getKey).toList();

        Playlist playlist = new Playlist("%s's recommendations".formatted(user.getUsername()),
                user.getUsername());

        Set<Song> songs = new HashSet<>();
        for (int i = 0; i < topGenres.size(); i++) {
            String genre = topGenres.get(i);
            List<Song> genreSongs = Admin.getInstance().getSongs().stream()
                    .filter(song -> song.getGenre().equals(genre))
                    .distinct()
                    .sorted(Comparator.comparingInt(Song::getLikes).reversed())
                    .toList();

            int limit = (i == 0) ? 5 : (i == 1) ? 3 : 2;

            int added = 0;
            for (Song song : genreSongs) {
                if (added == limit) {
                    break;
                }

                if (!songs.contains(song)) {
                    songs.add(song);
                    added++;
                }
            }
        }

        if (songs.isEmpty()) {
            return "No new recommendations were found";
        }

        playlist.getSongs().addAll(songs);
        user.getPlaylistRecommendations().add(playlist);
        user.setLastRecommendation(playlist);

        return "The recommendations for user %s have been updated successfully.".formatted(user
                .getUsername());
    }
}
