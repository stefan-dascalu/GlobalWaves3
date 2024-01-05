package app.user;

import java.util.*;

import app.audio.Collections.Album;
import app.audio.Collections.AlbumOutput;
import app.audio.Files.Song;
import app.pages.ArtistPage;
import app.statistics.ArtistStatistics;
import lombok.Getter;

/**
 * The type Artist.
 */
@Getter
public final class Artist extends ContentCreator {
    private final ArrayList<Album> albums;
    private final ArrayList<Merchandise> merch;
    private final ArrayList<Event> events;

    private final HashMap<Song, Double> revenuePerSong;
    private Double merchRevenue;

    /**
     * Instantiates a new Artist.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
        albums = new ArrayList<>();
        merch = new ArrayList<>();
        events = new ArrayList<>();
        revenuePerSong = new HashMap<>();

        merchRevenue = 0.0;

        super.setPage(new ArtistPage(this));
        super.setStatistics(new ArtistStatistics(this));
    }

    /**
     * Gets event.
     *
     * @param eventName the event name
     * @return the event
     */
    public Event getEvent(final String eventName) {
        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                return event;
            }
        }

        return null;
    }

    /**
     * Gets album.
     *
     * @param albumName the album name
     * @return the album
     */
    public Album getAlbum(final String albumName) {
        for (Album album : albums) {
            if (album.getName().equals(albumName)) {
                return album;
            }
        }

        return null;
    }

    /**
     * Adds a new album.
     *
     * @param album the album
     */
    public void addAlbum(final Album album) {
        albums.add(album);
        subscribers.forEach(s -> s.receiveNotification(new Notification("New Album",
                "New Album from %s.".formatted(getUsername()))));
    }

    /**
     * Adds a new merchandise.
     *
     * @param merchandise the merchandise
     */
    public void addMerch(final Merchandise merchandise) {
        merch.add(merchandise);
        subscribers.forEach(s -> s.receiveNotification(new Notification("New Merchandise",
                "New Merchandise from %s.".formatted(getUsername()))));
    }

    /**
     * Adds a new event.
     *
     * @param event the event
     */
    public void addEvent(final Event event) {
        events.add(event);
        subscribers.forEach(s -> s.receiveNotification(new Notification("New Event",
                "New Event from %s.".formatted(getUsername()))));
    }

    /**
     * Gets all songs.
     *
     * @return the all songs
     */
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        albums.forEach(album -> songs.addAll(album.getSongs()));

        return songs;
    }

    /**
     * Show albums array list.
     *
     * @return the array list
     */
    public ArrayList<AlbumOutput> showAlbums() {
        ArrayList<AlbumOutput> albumOutput = new ArrayList<>();
        for (Album album : albums) {
            albumOutput.add(new AlbumOutput(album));
        }

        return albumOutput;
    }

    /**
     * Get user type
     *
     * @return user type string
     */
    public String userType() {
        return "artist";
    }

    /**
     * Increases revenue for given song.
     *
     * @param song te song
     * @param amount the amount
     */
    public void increaseSongRevenue(final Song song, final double amount) {
        revenuePerSong.put(song, amount + revenuePerSong.getOrDefault(song, 0.0));
    }

    /**
     * Gets total revenue for songs.
     *
     * @return the revenue
     */
    public double getTotalSongRevenue() {
        return revenuePerSong.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    /**
     * Gets song with the highest revenue.
     *
     * @return the song
     */
    public Optional<Map.Entry<Song, Double>> getHighestRevenueSong() {
        return revenuePerSong.entrySet().stream()
                .min(Map.Entry.<Song, Double>comparingByValue().reversed()
                        .thenComparing((Map.Entry<Song, Double> e) -> e.getKey().getName()));
    }

    /**
     * Increases merch revenue amount.
     *
     * @param amount the amount
     */
    public void increaseMerchRevenue(final double amount) {
        merchRevenue += amount;
    }

    @Override
    public ArtistStatistics getStatistics() {
        return (ArtistStatistics) super.getStatistics();
    }
}
