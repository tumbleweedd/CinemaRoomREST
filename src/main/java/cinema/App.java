package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class App {
    private final CinemaRoom cinemaRoom = new CinemaRoom(9, 9);


    @GetMapping("/seats")
    public CinemaRoom getStudentList() {
        return cinemaRoom;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> addPurchase(@RequestBody Seats seats) {
        int row = seats.getRow();
        int column = seats.getColumn();

        if (row > 9 || row < 0 || column > 9 || column < 0) {
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        } else if (cinemaRoom.isSeatPurchased(row, column)) {
            return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"),
                    HttpStatus.BAD_REQUEST);
        }
        Purchase purchase = new Purchase(cinemaRoom.concreteSeat(row, column));
        cinemaRoom.addTicket(purchase);
        return new ResponseEntity<>(purchase, HttpStatus.OK);


    }

    @PostMapping("/return")
    public ResponseEntity<?> getOrder(@RequestBody Map<String, String> tokenMap) {
        String token = tokenMap.get("token");
        Map<String, Purchase> returnSeat = cinemaRoom.getTokenTicket();
        Map<String, Seats> correctlyOutPut = new HashMap<>();


        if (!cinemaRoom.getTokenTicket().containsKey(token)) {
            return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
        }

        correctlyOutPut.put("returned_ticket", cinemaRoom
                .getTokenTicket()
                .get(token).getSeats());

        returnSeat.get(token).getSeats().setPurchased(false);
        returnSeat.remove(token);

        return new ResponseEntity<>(correctlyOutPut, HttpStatus.OK);
    }

    @PostMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(required = false) String password) {
        if (password == null || !password.equals("super_secret")) {
            return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }

        Map<String, Purchase> purchaseMap = cinemaRoom.getTokenTicket();
        int currentIncome = 0;
        int numberOfAvailableSeats = 81;
        int numberOfPurchasedTickets = 0;

        for (Map.Entry<String, Purchase> entry : purchaseMap.entrySet()) {
            currentIncome += entry.getValue().getSeats().getPrice();
            if (entry.getValue().getSeats().isPurchased()) {
                int count = 0;
                count++;
                numberOfAvailableSeats -= count;
            }
            if (entry.getValue().getSeats().isPurchased()) {
                numberOfPurchasedTickets++;
            }

        }

        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("current_income", currentIncome);
        stat.put("number_of_available_seats", numberOfAvailableSeats);
        stat.put("number_of_purchased_tickets", numberOfPurchasedTickets);

        return new ResponseEntity<>(stat, HttpStatus.OK);
    }
}

