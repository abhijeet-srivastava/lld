import com.salseforce.mt.parking_lot.ParkingCash;
import com.salseforce.mt.parking_lot.ParkingStats;
import com.salseforce.mt.parking_lot.Sensor;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ParkingCash parkingCash = new ParkingCash();
        Object lockCars = new Object();
        Object lockMoto = new Object();
        ParkingStats stats = new ParkingStats(parkingCash, lockCars, lockMoto);
        System.out.printf("Parking Simulator\n");

        int numberSensors=2 * Runtime.getRuntime().availableProcessors();
        System.out.println("Num of threads: " + numberSensors);

        Thread threads[]=new Thread[numberSensors];
        for (int i = 0; i<numberSensors; i++) {
            Sensor sensor=new Sensor(stats);
            Thread thread=new Thread(sensor);
            thread.start();
            threads[i]=thread;
        }

        for (int i=0; i < numberSensors; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Number of cars: %d\n", stats.getNumOfCars());
        System.out.printf("Number of motorcycles: %d\n", stats.getNumOfMoto());
        parkingCash.close();

    }
}