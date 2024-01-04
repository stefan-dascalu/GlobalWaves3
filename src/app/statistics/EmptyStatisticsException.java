package app.statistics;

import lombok.Getter;

@Getter
public final class EmptyStatisticsException extends Exception {
    private final String message;

    public EmptyStatisticsException(final String message) {
        this.message = message;
    }
}
