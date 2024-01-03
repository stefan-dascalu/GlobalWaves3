package app.user;

import app.statistics.EmptyStatisticsException;
import app.statistics.Statistics;
import app.statistics.StatisticsOutput;
import lombok.Getter;
import lombok.Setter;

/**
 * The type User abstract.
 */
@Getter
@Setter
public abstract class UserAbstract {
    private String username;
    private int age;
    private String city;
    private Statistics statistics;

    /**
     * Instantiates a new User abstract.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public UserAbstract(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
    }

    /**
     * User type string.
     *
     * @return the string
     */
    public abstract String userType();

    /**
     * Statistics output.
     *
     * @return the statistics
     */
    public StatisticsOutput wrapped() throws EmptyStatisticsException {
        return statistics.wrapped();
    }
}
