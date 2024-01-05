package app.pages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;
import app.user.UserAbstract;

import java.util.Comparator;

/**
 * The type Home page.
 */
public final class HomePage extends Page {
    private final int limit = 5;

    /**
     * Instantiates a new Home page.
     *
     * @param user the user
     */
    public HomePage(final UserAbstract user) {
        super(user);
    }

    @Override
    public String printCurrentPage() {
        User u = (User) user;

        return ("Liked songs:\n\t%s\n\nFollowed playlists:\n\t%s\n\n"
                + "Song recommendations:\n\t%s\n\nPlaylists recommendations:\n\t%s")
                .formatted(u.getLikedSongs().stream()
                                .sorted(Comparator.comparing(Song::getLikes)
                                        .reversed()).limit(limit).map(Song::getName)
                                .toList(),
                        u.getFollowedPlaylists().stream().sorted((o1, o2) ->
                                        o2.getSongs().stream().map(Song::getLikes)
                                                .reduce(Integer::sum).orElse(0)
                                                - o1.getSongs().stream()
                                                .map(Song::getLikes).reduce(Integer::sum)
                                                .orElse(0)).limit(limit).map(Playlist::getName)
                                .toList(),
                        u.getSongRecommendations().stream().map(Song::getName).toList(),
                        u.getPlaylistRecommendations().stream().map(Playlist::getName).toList());
    }
}
