import java.util.*;

class UseCase8BookingHistory {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        history.addBooking(new Reservation("S1", "Lokesh", "Single"));
        history.addBooking(new Reservation("D2", "Rahul", "Double"));
        history.addBooking(new Reservation("S3", "Anita", "Suite"));

        BookingReportService reportService = new BookingReportService();

        reportService.showAllBookings(history);
        reportService.showTotalBookings(history);
        reportService.showBookingsByRoomType(history);
    }
}

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

class BookingHistory {

    private List<Reservation> bookings = new ArrayList<>();
    public void addBooking(Reservation reservation) {
        bookings.add(reservation);
    }
    public List<Reservation> getAllBookings() {
        return bookings;
    }
}
class BookingReportService {
    public void showAllBookings(BookingHistory history) {
        System.out.println("All Bookings:");
        for (Reservation r : history.getAllBookings()) {
            System.out.println(r);
        }
        System.out.println();
    }
    public void showTotalBookings(BookingHistory history) {
        System.out.println("Total Bookings: " + history.getAllBookings().size());
        System.out.println();
    }
    public void showBookingsByRoomType(BookingHistory history) {
        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : history.getAllBookings()) {
            countMap.put(
                    r.getRoomType(),
                    countMap.getOrDefault(r.getRoomType(), 0) + 1
            );
        }
        System.out.println("Bookings by Room Type:");

        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}git