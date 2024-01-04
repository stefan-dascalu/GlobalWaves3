package app.user;

import lombok.Getter;

/**
 * The type Notification.
 */
@Getter
public final class Notification {
    private final String name;
    private final String description;


    public Notification(final String name, final String description) {
        this.name = name;
        this.description = description;
    }
}
