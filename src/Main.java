import Algorithms.*;
import Utils.ProcessInfo;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfProcess = scanner.nextInt();

        int[] arrivalTime = new int[numberOfProcess];
        for(int i=0; i<numberOfProcess; i++) arrivalTime[i] = scanner.nextInt();

        int[] burstTime = new int[numberOfProcess];
        for(int i=0; i<numberOfProcess; i++) burstTime[i] = scanner.nextInt();

        ProcessInfo process = SRTF.simulateSRTF(arrivalTime, burstTime);
        System.out.println("Arrival Time -> " + Arrays.toString(process.arrivalTime));
        System.out.println("Burst Time -> " + Arrays.toString(process.burstTime));
        System.out.println("Waiting Time -> " + Arrays.toString(process.waitingTime));
        System.out.println("Turn Around Time -> " + Arrays.toString(process.turnAroundTime));
        System.out.println("Average wait time -> " + process.avgWaitTime);
        System.out.println("Average turn around time -> " + process.avgTAT);
        System.out.println("Throughput -> " + process.throughput);
        System.out.println("Context Switches -> " + process.contextSwitches);
    }
}