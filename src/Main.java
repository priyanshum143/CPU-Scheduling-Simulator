import Algorithms.*;
import Utils.ProcessInfo;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to my CPU Scheduling Simulator.\n");

        Scanner scanner = new Scanner(System.in);
        int algo = -1;

        ProcessInfo process;

        try {
            System.out.println("Press 1 to simulate First Come First Serve algorithm");
            System.out.println("Press 2 to simulate Shortest Job First (non-preemptive) algorithm");
            System.out.println("Press 3 to simulate Shortest Remaining Time First algorithm");
            System.out.println("Press 4 to simulate Round Robin algorithm");
            System.out.println();

            algo = scanner.nextInt();

            if(algo < 1 || algo > 4) {
                throw new IllegalArgumentException("Invalid choice. Please enter a number between 1 and 4.");
            }

            System.out.print("Enter the number of processes -> ");
            int numberOfProcess = scanner.nextInt();

            System.out.print("Enter the respective arrival time of the processes -> ");
            int[] arrivalTime = new int[numberOfProcess];
            for(int i=0; i<numberOfProcess; i++) arrivalTime[i] = scanner.nextInt();

            System.out.print("Enter the respective burst time of the processes -> ");
            int[] burstTime = new int[numberOfProcess];
            for(int i=0; i<numberOfProcess; i++) burstTime[i] = scanner.nextInt();

            switch(algo) {
                case 1:
                    process = FCFS.simulateFCFS(arrivalTime, burstTime);
                    printDetails(process);
                    break;

                case 2:
                    process = ShortestJobFirst.simulateSJF(arrivalTime, burstTime);
                    printDetails(process);
                    break;

                case 3:
                    process = SRTF.simulateSRTF(arrivalTime, burstTime);
                    printDetails(process);
                    break;

                case 4:
                    System.out.print("Enter the time Quantum -> ");
                    int timeQuantum = scanner.nextInt();

                    process = RoundRobin.simulateRR(arrivalTime, burstTime, timeQuantum);
                    printDetails(process);
                    break;
            }
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch(Exception e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Clear the input buffer
        } finally {
            scanner.close();
        }
    }

    public static void printDetails(ProcessInfo process) {
        System.out.println();
        System.out.println("Arrival Time -> " + Arrays.toString(process.arrivalTime));
        System.out.println("Burst Time -> " + Arrays.toString(process.burstTime));
        System.out.println("Waiting Time -> " + Arrays.toString(process.waitingTime));
        System.out.println("Turn Around Time -> " + Arrays.toString(process.turnAroundTime));
        System.out.println("Average wait time -> " + process.avgWaitTime);
        System.out.println("Average turn around time -> " + process.avgTAT);
        System.out.println("Throughput -> " + process.throughput);
        System.out.println("Context Switches -> " + process.contextSwitches);
        System.out.println();
    }
}