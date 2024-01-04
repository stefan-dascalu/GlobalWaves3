package app.statistics;

import app.user.UserAbstract;

/**
 * The type Statistics abstract.
 */
public abstract class Statistics {
    protected UserAbstract userAbstract;

    protected final int maxLimit = 5;

    public abstract StatisticsOutput wrapped() throws EmptyStatisticsException;
}
