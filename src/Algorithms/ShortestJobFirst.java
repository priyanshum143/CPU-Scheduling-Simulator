package Algorithms;

import Utils.ProcessInfo;

public class ShortestJobFirst {
    public static ProcessInfo simulateSJF(int[] arrivalTime, int[] burstTime) {
        ProcessInfo process = new ProcessInfo(arrivalTime, burstTime);
        calculateMetrics(process);
        return process;
    }

    private static void calculateMetrics(ProcessInfo process) {
        int numberOfProcess = process.arrivalTime.length;

        // Making a copy of arrivalTime because we will be updating arrival time of terminated processes
        int[] tempArrivalTime = process.arrivalTime.clone();

        // Initially the current time will be equal to the lowest arrival time
        int currTime = minArrivalTime(tempArrivalTime);
        double totalTAT = 0, totalWaitTime = 0;

        for(int i=1; i<=numberOfProcess; i++) {
            int idx = findMinAT(tempArrivalTime, process.burstTime, currTime);

            totalWaitTime += currTime - tempArrivalTime[idx];
            process.waitingTime[idx] = currTime - tempArrivalTime[idx];

            currTime += process.burstTime[idx];

            totalTAT += currTime - tempArrivalTime[idx];
            process.turnAroundTime[idx] = currTime - tempArrivalTime[idx];

            // Updating the arrival time of current process to max int value
            // so that we don't get the same process everytime
            tempArrivalTime[idx] = Integer.MAX_VALUE;
        }

        process.avgWaitTime = totalWaitTime / numberOfProcess;
        process.avgTAT = totalTAT / numberOfProcess;
        process.throughput = (double) Math.round((numberOfProcess / (double) currTime) * 100) / 100;
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

    // This function is used to find the minimum arrival time
    // Initially we want our current time to be equal to the minimum arrival time.
    private static int minArrivalTime(int[] arrivalTime) {
        int minTime = Integer.MAX_VALUE;
        for(int i=0; i<arrivalTime.length; i++) {
            if(arrivalTime[i] < minTime) {
                minTime = arrivalTime[i];
            }
        }
        return minTime;
    }
}
