import Algorithms.FCFS;
import Algorithms.SRTF;
import Algorithms.ShortestJobFirst;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfProcess = scanner.nextInt();

        int[] arrivalTime = new int[numberOfProcess];
        for(int i=0; i<numberOfProcess; i++) arrivalTime[i] = scanner.nextInt();

        int[] burstTime = new int[numberOfProcess];
        for(int i=0; i<numberOfProcess; i++) burstTime[i] = scanner.nextInt();

        FCFS.simulateFCFS(arrivalTime, burstTime);
    }
}