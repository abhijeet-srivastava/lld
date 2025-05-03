import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Scheduler {

    Map<Integer, TreeMap<Long, Long>> rooms;

    public Scheduler(List<Integer> rooms) {
        this.rooms = new HashMap<>();
        for(int roomNum: rooms) {
            this.rooms.put(roomNum, new TreeMap<>());
        }
    }
    public Integer scheduleMeeting(long startTime, long endTime) {
        Integer nextAvailableRoom = null;
        for(var room: this.rooms.keySet()) {
            if(isRoomAvailable(room,  startTime,  endTime)) {
                nextAvailableRoom = room;
                break;
            }
        }
        if(nextAvailableRoom == null) {
            throw new AssertionError("No room is available for window : (startTime- "+ startTime + ", " + endTime + ")");
        }
        reserveRoom(nextAvailableRoom, startTime,  endTime);
        return nextAvailableRoom;
    }
    private void reserveRoom(Integer roomId, long startTime, long endTime) {
        this.rooms.get(roomId).put(startTime, endTime);
    }

    private boolean isRoomAvailable(Integer roomId, long startTime, long endTime) {
        TreeMap<Long, Long> roomSchedule = this.rooms.get(roomId);

        var floorEntryEnd = roomSchedule.floorEntry(endTime);
        return   floorEntryEnd == null || startTime  >= floorEntryEnd.getValue();

    }

    public static void main(String[] args) {
        List<Integer> rooms = List.of(1,2);
        Scheduler scheduler = new Scheduler(rooms);
        int nextRoom = scheduler.scheduleMeeting(1, 3);
        System.out.printf("Assigned room: %d\n", nextRoom);
        nextRoom = scheduler.scheduleMeeting(2, 7);
        System.out.printf("Assigned room: %d\n", nextRoom);
        nextRoom = scheduler.scheduleMeeting(3, 6);
        System.out.printf("Assigned room: %d\n", nextRoom);
        nextRoom = scheduler.scheduleMeeting(4, 9);
        System.out.printf("Assigned room: %d\n", nextRoom);
    }
}
