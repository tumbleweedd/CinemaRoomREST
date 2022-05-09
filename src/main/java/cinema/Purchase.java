package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Purchase {
    private final String token;
    @JsonProperty("ticket")
    private final Seats seats;

    public Seats getSeats() {
        return seats;
    }

    public String getToken() {
        return token;
    }

    public Purchase(Seats seats) {
        this.seats = seats;
        this.token = UUID.randomUUID().toString();
        this.seats.setPurchased(true);
    }


}