package app.recommendation;

import app.Admin;
import app.audio.Files.Song;
import app.user.User;

import java.util.List;
import java.util.Random;

public final class RandomSongRecommendation extends Recommendation {
    public RandomSongRecommendation(final User user) {
        super(user);
    }

    @Override
    public String execute() {
        int duration = user.getPlayer().getCurrentAudioFile().getDuration();
        int remaining = user.getPlayer().getStats().getRemainedTime();
        int listened = duration - remaining;

        if (listened >= 30) {
            String genre = ((Song) user.getPlayer().getCurrentAudioFile()).getGenre();
            List<Song> songs = Admin.getInstance().getSongs().stream()
                    .filter(s -> s.matchesGenre(genre)).toList();
            Random random = new Random();
            random.setSeed(listened);
            Song randomSong = songs.get(random.nextInt(songs.size()));
            user.getSongRecommendations().add(randomSong);
            user.setLastRecommendation(randomSong);
        }

        return "The recommendations for user %s have been updated successfully.".formatted(user
                .getUsername());
    }
}
