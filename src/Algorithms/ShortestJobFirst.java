package Algorithms;

public class ShortestJobFirst {
    public static void simulateSJF(int[] arrivalTime, int[] burstTime) {
        calculateMetrics(arrivalTime, burstTime);
    }

    private static void calculateMetrics(int[] arrivalTime, int[] burstTime) {
        int numberOfProcess = arrivalTime.length;

        int currTime = 0;
        int totalTAT = 0, totalWaitTime = 0;
        for(int i=0; i<numberOfProcess; i++) {
            int idx = findMinAT(arrivalTime, burstTime, currTime);

            totalWaitTime += currTime - arrivalTime[idx];
            currTime += burstTime[idx];
            totalTAT += currTime - arrivalTime[idx];

            arrivalTime[idx] = Integer.MAX_VALUE;
        }

        double avgWaitTime =  (double) totalWaitTime / (double) numberOfProcess;
        double avgTAT = (double) totalTAT / (double) numberOfProcess;
        double throughput = (double) numberOfProcess / (double) currTime;

        System.out.println("Average Waiting Time => " + avgWaitTime);
        System.out.println("Average Turn Around Time => " + avgTAT);
        System.out.println("Throughput => " + throughput);
        System.out.println("Number of Context Switches => " + (numberOfProcess - 1));
    }

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
