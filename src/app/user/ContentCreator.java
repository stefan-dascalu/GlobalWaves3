package app.user;

import app.pages.Page;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Content creator.
 */
@Getter
@Setter
public abstract class ContentCreator extends UserAbstract {
    protected List<User> subscribers;
    protected String description;
    protected Page page;

    /**
     * Instantiates a new Content creator.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public ContentCreator(final String username, final int age, final String city) {
        super(username, age, city);
        subscribers = new ArrayList<>();
    }

    /**
     * Adds a new user as subscriber.
     *
     * @param user the user
     * @return the string
     */
    public String subscribe(final User user) {
        boolean contained = subscribers.remove(user);

        if (contained) {
            return "%s unsubscribed from %s successfully."
                    .formatted(user.getUsername(), getUsername());
        }

        subscribers.add(user);
        return "%s subscribed to %s successfully.".formatted(user.getUsername(), getUsername());
    }
}
