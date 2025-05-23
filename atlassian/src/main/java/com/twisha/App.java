package com.twisha;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Hello world!
 *
 */
public class App {
    /**
     * Implement a function that given a list of tennis court bookings with start and finish times, returns a plan assigning each booking to a specific court, ensuring each court is used by only one booking at a time and using the minimum amount of courts with unlimited number of courts available.
     * An example of the booking record might look like:
     *
     * class BookingRecord:
     *
     *   id: int // ID of the booking.
     *
     *   start_time: int
     *
     *   finish_time: int
     *
     * and our function is going to look like:
     *
     * List<Court> assignCourts(List<BookingRecord> bookingRecords)
     * @param args
     */
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        App app = new App();
        app.validateCourtBookings();
        //app.validateCourtBookingsCase1();
    }

    private void validateCourtBookingsCase1() {
        List<BookingRecord> bookingRecords = Arrays.asList(
                new BookingRecord(1, 1, 4),
                new BookingRecord(2, 2, 7),
                new BookingRecord(3, 3, 6)
        );
        List<Court> bookingSchedule = this.assignCourts(bookingRecords, 0);
        bookingSchedule.forEach(System.out::println);
    }

    private void validateCourtBookings() {
        List<BookingRecord> bookingRecords = Arrays.asList(
                new BookingRecord(1, 1, 3),
                new BookingRecord(2, 2, 7),
                new BookingRecord(3, 4, 6)
        );
        List<Court> bookingSchedule = this.assignCourts(bookingRecords, 2);
        bookingSchedule.forEach(System.out::println);
    }

    // period: 1, (1, 3), (2, 7), (4, 6), : 3

    // Size of Input n = O(nlogn) + O(n *long)
    //(n)log(n)
    public List<Court> assignCourts(List<BookingRecord> bookingRecords, int maintenancePeriod) {
        int len = bookingRecords.size();
        Map<Integer, Court> result = new HashMap<>();
        int assignedCortId = 1;
        Collections.sort(bookingRecords,
                (b1, b2) -> b1.startTime == b2.startTime
                        ? Integer.compare(b1.endTime, b2.endTime): Integer.compare(b1.startTime, b2.startTime));
        //int[][] timeseries = new int[]
        // 1:s ->1    3:s -> 2   , 4:e->1 removed from
        PriorityQueue<int[]> assignedCourts = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));//0 - courtId, 1: Busy Up till

        for(BookingRecord br: bookingRecords) {
            if(!assignedCourts.isEmpty() && (assignedCourts.peek()[1] +  maintenancePeriod) <= br.startTime) {
                Integer courtId = assignedCourts.poll()[0];
                Court court = result.get(courtId);
                court.getBookings().add(br);
            } else {
                Court createdCourt = new Court(assignedCortId++);
                createdCourt.getBookings().add(br);
                assignedCourts.offer(new int[]{createdCourt.getId(), br.endTime});
                result.put(createdCourt.getId(), createdCourt);

            }
        }
        return new ArrayList<>(result.values());
    }

    class Court {
        private Integer id;
        private List<BookingRecord> bookings;

        public Court(int id) {
            this.id = id;
            this.bookings = new ArrayList<>();
        }

        public Integer getId() {
            return id;
        }

        public List<BookingRecord> getBookings() {
            return bookings;
        }

        @Override
        public String toString() {
            return "Court{" +
                    "id=" + id +
                    ", bookings=" + bookings +
                    '}';
        }
    }

    class BookingRecord {
        private Integer id;
        private int startTime;
        private int endTime;

        public BookingRecord(Integer id, int startTime, int endTime) {
            this.id = id;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public Integer getId() {
            return id;
        }

        public int getStartTime() {
            return startTime;
        }

        public int getEndTime() {
            return endTime;
        }

        @Override
        public String toString() {
            return "BookingRecord{" +
                    "id=" + id +
                    ", startTime=" + startTime +
                    ", endTime=" + endTime +
                    '}';
        }
    }
}
