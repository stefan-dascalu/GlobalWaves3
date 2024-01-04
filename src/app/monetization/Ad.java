package app.monetization;

import lombok.Getter;

@Getter
public class Ad {
    private final int price;

    public Ad(final int price) {
        this.price = price;
    }
}
