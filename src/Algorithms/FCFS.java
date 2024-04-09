package Algorithms;

public class FCFS {
    public static ProcessInfo simulateFCFS(int[] arrivalTime, int[] burstTime) {
        ProcessInfo process = new ProcessInfo(arrivalTime, burstTime);
        calculateMetrics(process);
        return process;
    }

    private static void calculateMetrics(ProcessInfo process) {
        int numberOfProcess = process.arrivalTime.length;

        // Making a copy of arrivalTime because we will be updating arrival time of terminated processes
        int[] tempArrivalTime = process.arrivalTime.clone();

        // Initially the current time will be equal to the lowest arrival time
        int currTime = tempArrivalTime[findMinAT(tempArrivalTime)];
        double totalWaitTime = 0, totalTAT = 0;

        for(int i=1; i<=numberOfProcess; i++) {
            int idx = findMinAT(tempArrivalTime);

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
        process.throughput = numberOfProcess / (double) currTime;
    }

    // This function will find the minimum arrival time according to the current time
    private static int findMinAT(int[] arrivalTime) {
        int idx = -1, minTime = Integer.MAX_VALUE;
        for(int i=0; i<arrivalTime.length; i++) {
            if(arrivalTime[i] < minTime) {
                minTime = arrivalTime[i];
                idx = i;
            }
        }
        return idx;
    }
}
