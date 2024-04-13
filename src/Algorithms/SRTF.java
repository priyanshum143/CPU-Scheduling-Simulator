package Algorithms;

import Utils.ProcessInfo;

import java.util.HashMap;
import java.util.PriorityQueue;

public class SRTF {
    public static ProcessInfo simulateSRTF(int[] arrivalTime, int[] burstTime) {
        ProcessInfo process = new ProcessInfo(arrivalTime, burstTime);
        calculateMetrics(process);
        return process;
    }

    private static void calculateMetrics(ProcessInfo process) {
        int numberOfProcess = process.arrivalTime.length;

        // Making a copy of burstTime because we will be continuously updating burst time of processes
        int[] remainingBurstTime = process.burstTime.clone();

        // Initially the current time will be equal to 0
        int currTime = 0;
        double totalWaitTime = 0, totalTAT = 0;

        // Starting the context switches from -1 because we have to ignore the case where last process gets terminated
        int contextSwitches = -1;

        // This heap will run the process with the shortest remaining burst time first
        PriorityQueue<Integer> CPU = new PriorityQueue<>();

        // HashMap will check if the current process is already been added to the CPU or not
        HashMap<Integer, Boolean> isAdded = new HashMap<>();
        for(int i=0; i<numberOfProcess; i++) isAdded.put(i, false);

        int currProcess = -1, prevProcess = -1;
        int processCompleted = 0;

        while(processCompleted < numberOfProcess) {
            currProcess = findMinAT(process.arrivalTime, remainingBurstTime, currTime);

            // This will make sure that CPU is not idle at current time
            while(currProcess == -1) {
                currTime++;
                currProcess = findMinAT(process.arrivalTime, remainingBurstTime, currTime);
            }

            if(currProcess != prevProcess) contextSwitches++;

            if(!isAdded.get(currProcess)) {
                isAdded.put(currProcess, true);
                CPU.add(remainingBurstTime[currProcess]);
            }

            currTime += 1;
            remainingBurstTime[currProcess] -= 1;
            CPU.add(CPU.poll() - 1);

            // Checking if the current process is completed or not
            if(CPU.peek() == 0) {
                processCompleted += 1;
                remainingBurstTime[currProcess] = Integer.MAX_VALUE;
                CPU.poll();

                process.waitingTime[currProcess] = currTime - process.arrivalTime[currProcess] - process.burstTime[currProcess];
                totalWaitTime += (currTime - process.arrivalTime[currProcess] - process.burstTime[currProcess]);

                process.turnAroundTime[currProcess] = currTime - process.arrivalTime[currProcess];
                totalTAT += (currTime - process.arrivalTime[currProcess]);
            }

            prevProcess = currProcess;
        }

        double avgWaitTime = totalWaitTime / numberOfProcess;
        double avgTAT = totalTAT / numberOfProcess;
        double throughput = numberOfProcess / (double) currTime;

        process.avgWaitTime = avgWaitTime;
        process.avgTAT = avgTAT;
        process.throughput = Math.round(throughput * 100.0) / 100.0;
        process.contextSwitches = contextSwitches;
    }

    // This function will find the minimum burst time according to the current time
    private static int findMinAT(int[] arrivalTime, int[] burstTime, int currTime) {
        int idx = -1, minTime = Integer.MAX_VALUE;
        for(int i=0; i<arrivalTime.length; i++) {
            if(arrivalTime[i] <= currTime && burstTime[i] < minTime) {
                minTime = burstTime[i];
                idx = i;
            }
        }
        return idx;
    }
}
