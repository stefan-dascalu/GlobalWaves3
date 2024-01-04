package app.pages;

import app.user.Host;
import app.user.UserAbstract;

/**
 * The type Host page.
 */
public final class HostPage extends Page {

    /**
     * Instantiates a new Host page.
     *
     * @param user the user
     */
    public HostPage(final UserAbstract user) {
        super(user);
    }

    @Override
    public String printCurrentPage() {
        Host host = (Host) user;

        return "Podcasts:\n\t%s\n\nAnnouncements:\n\t%s".formatted(host.getPodcasts().
                        stream().map(podcast -> "%s:\n\t%s\n".formatted(podcast.getName(),
                                podcast.getEpisodes().stream()
                                        .map(episode -> "%s - %s".formatted(
                                                episode.getName(),
                                                episode.getDescription())).toList())).toList(),
                host.getAnnouncements().stream().map(announcement -> "%s:\n\t%s\n"
                                .formatted(announcement.getName(),
                                        announcement.getDescription())).toList());
    }
}
