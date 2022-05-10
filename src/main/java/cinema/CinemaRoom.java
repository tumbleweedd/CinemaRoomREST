package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaRoom {
    @JsonProperty("available_seats")
    private List<Seats> seats = new ArrayList<>();
    private int totalRows;
    private int totalColumns;

    public CinemaRoom(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        availableSeats();
    }

    @JsonProperty("available_seats")
    public List<Seats> getAvailableSeats() {
        return getSeats().stream()
                .filter(e -> !e.isPurchased())
                .collect(Collectors.toList());
    }




    @JsonIgnore
    public Map<String, Purchase> getTokenTicket() {
        return tokenTicket;
    }

    public void addTicket(Purchase purchase) {
        this.tokenTicket.put(purchase.getToken(), purchase);
    }

    private final Map<String, Purchase> tokenTicket = new LinkedHashMap<>();


    public List<Seats> getSeats() {
        return seats;
    }

    private void availableSeats() {
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                Seats availableSeats = new Seats(i + 1, j + 1);
                availableSeats.setPrice(i < 4 ? 10 : 8);
                seats.add(availableSeats);
            }
        }
        getAvailableSeats();
    }

    public Seats concreteSeat(int row, int column) {
        return seats.get((row - 1) * totalColumns + column - 1);
    }

    public boolean isSeatPurchased(int row, int column) {
        return concreteSeat(row, column).purchased;
    }
}