package app.recommendation;

import app.user.User;

public class RecommendationFactory {
    private RecommendationFactory() {

    }

    /**
     * Creates recommendation command.
     *
     * @param user the user
     * @param type the type
     * @return the Recommendation command
     */
    public static Recommendation createRecommendation(final User user, final String type) {
        return switch (type) {
            case "random_song" -> new RandomSongRecommendation(user);
            case "random_playlist" -> new RandomPlaylistRecommendation(user);
            case "fans_playlist" -> new FansPlaylistRecommendation(user);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}
