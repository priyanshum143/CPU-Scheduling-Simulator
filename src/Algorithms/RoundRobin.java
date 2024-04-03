package Algorithms;

import java.awt.*;
import java.util.*;

public class RoundRobin {
    public static void simulateRR(int[] arrivalTime, int[] burstTime, int timeQuantum) {
        calculateMetrics(arrivalTime, burstTime, timeQuantum);
    }

    private static void calculateMetrics(int[] arrivalTime, int[] burstTime, int timeQuantum) {
        int[] initialArrivalTime = new int[arrivalTime.length];
        for(int i=0; i<arrivalTime.length; i++) initialArrivalTime[i] = arrivalTime[i];

        int contextSwitches = 0;
        int totalWaitTime = 0, totalTAT = 0;

        int currTime = 0;
        int idx = findMinAT(arrivalTime, currTime);
        arrivalTime[idx] = Integer.MAX_VALUE;

        boolean flag = true;

        Queue<Integer> cpu = new ArrayDeque<>();
        cpu.add(burstTime[idx]);
        Queue<Integer> index = new ArrayDeque<>();
        index.add(idx);

        while(!cpu.isEmpty()) {
            int remainingTime = cpu.peek();
            currTime += Math.min(remainingTime, timeQuantum);

            if(flag) flag = scheduler(arrivalTime, burstTime, currTime, cpu, index);

            if(remainingTime <= timeQuantum) {
                totalWaitTime += (currTime - initialArrivalTime[index.peek()] - burstTime[index.peek()]);
                totalTAT += (currTime - initialArrivalTime[index.peek()]);

                cpu.remove();
                index.remove();
            } else {
                cpu.add(cpu.remove() - timeQuantum);
                index.add(index.remove());
            }

            if(!cpu.isEmpty()) contextSwitches++;
        }

        double avgWaitTime = (double) totalWaitTime / (double) arrivalTime.length;
        double avgTAT = (double) totalTAT / (double) arrivalTime.length;
        double throughput = (double) arrivalTime.length / (double) currTime;

        System.out.println("Average Waiting Time => " + avgWaitTime);
        System.out.println("Average Turn Around Time => " + avgTAT);
        System.out.println("Throughput => " + throughput);
        System.out.println("Number of Context Switches => " + contextSwitches);
    }

    private static boolean scheduler(int[] arrivalTime, int[] burstTime, int currTime, Queue<Integer> cpu, Queue<Integer> index) {
        boolean flag = false;
        HashMap<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> processes = new ArrayList<>();

        for(int i=0; i<arrivalTime.length; i++) {
            if(arrivalTime[i] <= currTime) {
               flag = true;
               processes.add(arrivalTime[i]);
               map.put(arrivalTime[i], i);
               arrivalTime[i] = Integer.MAX_VALUE;
            }
        }

        Collections.sort(processes);

        for(Integer process : processes) {
            cpu.add(burstTime[map.get(process)]);
            index.add(map.get(process));
        }

        return flag;
    }

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
