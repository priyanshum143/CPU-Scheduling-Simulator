package Algorithms;

import Utils.ProcessInfo;

import java.util.*;

public class RoundRobin {
    public static ProcessInfo simulateRR(int[] arrivalTime, int[] burstTime, int timeQuantum) {
        ProcessInfo process = new ProcessInfo(arrivalTime, burstTime);
        calculateMetrics(process, timeQuantum);
        return process;
    }

    private static void calculateMetrics(ProcessInfo process, int timeQuantum) {
        int numberOfProcess = process.arrivalTime.length;

        // Making a copy of arrivalTime because we will be updating arrival time of added processes
        int[] tempArrivalTime = process.arrivalTime.clone();

        // Initially the current time will be equal to 0
        int currTime = 0;
        double totalWaitTime = 0, totalTAT = 0;

        // Starting the context switches from -1 because we have to ignore the case where last process gets terminated
        int contextSwitches = -1;

        int currProcess = findMinAT(process.arrivalTime, currTime);
        tempArrivalTime[currProcess] = Integer.MAX_VALUE;

        // Flag = false will determine that all the processes has already been added to the CPU
        boolean Flag = true;

        // This queue will contain the burst time of processes
        Queue<Integer> CPU = new ArrayDeque<>();
        CPU.add(process.burstTime[currProcess]);

        // This queue will contain the process Ids(index) of processes
        Queue<Integer> processID = new ArrayDeque<>();
        processID.add(currProcess);

        while(!CPU.isEmpty()) {
            int remainingTime = CPU.peek();
            currTime += Math.min(remainingTime, timeQuantum);

            // Add all the new processes whose arrival time is less than the current time
            if(Flag) Flag = scheduler(tempArrivalTime, process.burstTime, currTime, CPU, processID);

            if(remainingTime <= timeQuantum) {
                process.waitingTime[processID.peek()] = currTime - process.arrivalTime[processID.peek()] - process.burstTime[processID.peek()];
                totalWaitTime += (currTime - process.arrivalTime[processID.peek()] - process.burstTime[processID.peek()]);

                process.turnAroundTime[processID.peek()] = currTime - process.arrivalTime[processID.peek()];
                totalTAT += (currTime - process.arrivalTime[processID.peek()]);

                CPU.remove();
                processID.remove();
            } else {
                CPU.add(CPU.remove() - timeQuantum);
                processID.add(processID.remove());
            }

            contextSwitches++;
        }

        double avgWaitTime = totalWaitTime / numberOfProcess;
        double avgTAT = totalTAT / numberOfProcess;
        double throughput = numberOfProcess / (double) currTime;

        process.avgWaitTime = avgWaitTime;
        process.avgTAT = avgTAT;
        process.throughput = Math.round(throughput * 100.0) / 100.0;
        process.contextSwitches = contextSwitches;
    }

    // This function will add all the processes in CPU and processID, whose arrival time is less than current time
    private static boolean scheduler(int[] arrivalTime, int[] burstTime, int currTime, Queue<Integer> CPU, Queue<Integer> processID) {
        boolean Flag = false;

        // This map will map the arrivalTime of process to its processId
        HashMap<Integer, Integer> map = new HashMap<>();

        // This array will contain all the processes whose arrival time is less than or equal to the current time
        ArrayList<Integer> processes = new ArrayList<>();

        for(int i=0; i<arrivalTime.length; i++) {
            if(arrivalTime[i] <= currTime) {
                Flag = true;
                processes.add(arrivalTime[i]);
                map.put(arrivalTime[i], i);
                arrivalTime[i] = Integer.MAX_VALUE;
            }
        }

        // Sorting the processes array i.e. process will less arrival time will come first
        Collections.sort(processes);

        for(Integer process : processes) {
            CPU.add(burstTime[map.get(process)]);
            processID.add(map.get(process));
        }

        return Flag;
    }

    // This function will find the minimum arrival time according to the current time
    private static int findMinAT(int[] arrivalTime, int currTime) {
        int idx = -1, minTime = Integer.MAX_VALUE;
        for(int i=0; i<arrivalTime.length; i++) {
            if(arrivalTime[i] <= currTime && arrivalTime[i] < minTime) {
                minTime = arrivalTime[i];
                idx = i;
            }
        }
        return idx;
    }
}
