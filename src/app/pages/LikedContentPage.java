package app.pages;

import app.user.User;
import app.user.UserAbstract;

/**
 * The type Liked content page.
 */
public final class LikedContentPage extends Page {

    /**
     * Instantiates a new Liked content page.
     *
     * @param user the user
     */
    public LikedContentPage(final UserAbstract user) {
        super(user);
    }

    @Override
    public String printCurrentPage() {
        User u = (User) user;

        return "Liked songs:\n\t%s\n\nFollowed playlists:\n\t%s"
                .formatted(u.getLikedSongs().stream().map(song -> "%s - %s"
                                .formatted(song.getName(), song.getArtist())).toList(),
                        u.getFollowedPlaylists().stream().map(playlist -> "%s - %s"
                                .formatted(playlist.getName(), playlist.getOwner())).toList());
    }
}
