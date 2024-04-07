package Algorithms;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class SRTF {
    public static ProcessInfo simulateSRTF(int[] arrivalTime, int[] burstTime) {
        ProcessInfo process = new ProcessInfo(arrivalTime, burstTime);
        calculateMetrics(arrivalTime, burstTime, process);
        return process;
    }

    private static void calculateMetrics(int[] arrivalTime, int[] burstTime, ProcessInfo process) {
        int numberOfProcess = arrivalTime.length;

        int[] tempBurstTime = new int[numberOfProcess];
        for(int i=0; i<numberOfProcess; i++) tempBurstTime[i] = burstTime[i];

        // Initially the current time will be equal to the lowest arrival time
        int currTime = 0;
        int totalWaitTime = 0, totalTAT = 0;

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

    // This function will find the minimum burst time according to the current time
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
