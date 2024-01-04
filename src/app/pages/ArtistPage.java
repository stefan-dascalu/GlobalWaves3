package app.pages;

import app.audio.Collections.Album;
import app.user.Artist;
import app.user.UserAbstract;
import lombok.Getter;

/**
 * The type Artist page.
 */
@Getter
public final class ArtistPage extends Page {

    /**
     * Instantiates a new Artist page.
     *
     * @param user the user
     */
    public ArtistPage(final UserAbstract user) {
        super(user);
    }

    @Override
    public String printCurrentPage() {
        Artist artist = (Artist) user;

        return "Albums:\n\t%s\n\nMerch:\n\t%s\n\nEvents:\n\t%s"
                .formatted(artist.getAlbums().stream().map(Album::getName).toList(),
                        artist.getMerch().stream().map(merchItem -> "%s - %d:\n\t%s"
                                        .formatted(merchItem.getName(),
                                                merchItem.getPrice(),
                                                merchItem.getDescription()))
                                .toList(),
                        artist.getEvents().stream().map(event -> "%s - %s:\n\t%s"
                                        .formatted(event.getName(),
                                                event.getDate(),
                                                event.getDescription()))
                                .toList());
    }
}
