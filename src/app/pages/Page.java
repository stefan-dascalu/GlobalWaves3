package app.pages;

import app.user.UserAbstract;
import lombok.Getter;

/**
 * The Page abstract type.
 */
@Getter
public abstract class Page {

    protected final UserAbstract user;

    public Page(final UserAbstract user) {
        this.user = user;
    }


    /**
     * Print current page string.
     *
     * @return the current page string
     */
    public abstract String printCurrentPage();
}
