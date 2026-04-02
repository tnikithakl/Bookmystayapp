/**
 * ---------------------------------------------------------
 * MAIN CLASS - UseCase5BookingQueue
 * ---------------------------------------------------------
 *
 * Use Case 5: Booking Request Queue (FIFO)
 *
 * Description:
 * Demonstrates fair handling of booking requests using Queue.
 */

import java.util.LinkedList;
import java.util.Queue;

public class UseCase5BookingQueue {

    public static void main(String[] args) {

        System.out.println("Booking Request System (FIFO)\n");

        // Create booking queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Guests submit booking requests
        queue.addRequest(new Reservation("Lokesh", "Single"));
        queue.addRequest(new Reservation("Rahul", "Double"));
        queue.addRequest(new Reservation("Anita", "Suite"));
        queue.addRequest(new Reservation("Priya", "Single"));

        // Display queued requests
        queue.displayQueue();
    }
}

/**
 * ---------------------------------------------------------
 * CLASS - Reservation
 * ---------------------------------------------------------
 *
 * Represents a guest booking request
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return guestName + " -> " + roomType;
    }
}

/**
 * ---------------------------------------------------------
 * CLASS - BookingRequestQueue
 * ---------------------------------------------------------
 *
 * Manages booking requests using FIFO Queue
 */
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    /**
     * Add booking request to queue
     */
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Request added: " + reservation);
    }

    /**
     * View next request (without removing)
     */
    public Reservation peekRequest() {
        return requestQueue.peek();
    }

    /**
     * Process next request (removes from queue)
     * NOTE: No inventory logic yet (important!)
     */
    public Reservation processNextRequest() {
        return requestQueue.poll();
    }

    /**
     * Display all queued requests
     */
    public void displayQueue() {
        System.out.println("\nCurrent Booking Queue:");

        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : requestQueue) {
            System.out.println(r);
        }
    }
}
