package app.recommendation;

import app.user.User;

public abstract class Recommendation {
    protected final User user;

    protected Recommendation(final User user) {
        this.user = user;
    }


    public abstract String execute();
}
