package Algorithms;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class SRTF {
    public static void simulateSRTF(int[] arrivalTime, int[] burstTime) {
        calculateMetrics(arrivalTime, burstTime);
    }

    private static void calculateMetrics(int[] arrivalTime, int[] burstTime) {
        int numberOfProcess = arrivalTime.length;

        int[] initialBurstTime = new int[numberOfProcess];
        for(int i=0; i<numberOfProcess; i++) initialBurstTime[i] = burstTime[i];

        int totalWaitTime = 0, totalTAT = 0;

        int currTime = 0;
        PriorityQueue<Integer> cpu = new PriorityQueue<>();

        HashMap<Integer, Boolean> isAdded = new HashMap<>();
        for(int i=0; i<numberOfProcess; i++) { isAdded.put(i, false); }
        int currProcess = -1, prevProcess = -1;

        int contextSwitches = -1;

        int i = 0;
        while(i < numberOfProcess) {
            currProcess = findMinAt(arrivalTime, burstTime, currTime);
            if(currProcess != prevProcess) contextSwitches++;

            if(!isAdded.get(currProcess)) {
                cpu.add(burstTime[currProcess]);
                isAdded.put(currProcess, true);
                i++;
            }

            currTime += 1;
            burstTime[currProcess] -= 1;
            if(!cpu.isEmpty()) {
                cpu.add(cpu.poll() - 1);
                if(cpu.peek() == 0) {
                    cpu.poll();
                    burstTime[currProcess] = Integer.MAX_VALUE;

                    totalWaitTime += (currTime - arrivalTime[currProcess] - initialBurstTime[currProcess]);
                    totalTAT += (currTime - arrivalTime[currProcess]);
                }
            }

            prevProcess = currProcess;
        }

        while(!cpu.isEmpty()) {
            currTime += cpu.poll();
            totalWaitTime += (currTime - arrivalTime[currProcess] - initialBurstTime[currProcess]);
            totalTAT += (currTime - arrivalTime[currProcess]);
        }

        double avgWaitTime = (double) totalWaitTime / (double) numberOfProcess;
        double avgTAT = (double) totalTAT / (double) numberOfProcess;
        double throughput = (double) numberOfProcess / (double) currTime;

        System.out.println("Average Wait Time => " + avgWaitTime);
        System.out.println("Average Turn Around Time => " + avgTAT);
        System.out.println("Throughput => " + throughput);
        System.out.println("Number of context switches => " + contextSwitches);
    }

    private static int findMinAt(int[] arrivalTime, int[] burstTime, int currTime) {
        int idx = -1, minTime = Integer.MAX_VALUE;
        for(int i=0; i<arrivalTime.length; i++) {
            if (arrivalTime[i] <= currTime && burstTime[i] < minTime) {
                minTime = burstTime[i];
                idx = i;
            }
        }
        return idx;
    }
}
