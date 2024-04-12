package Utils;

public class ProcessInfo {
    public int[] arrivalTime;
    public int[] burstTime;
    public int[] waitingTime;
    public int[] turnAroundTime;

    public double avgWaitTime;
    public double avgTAT;
    public double throughput;
    public int contextSwitches;

    public ProcessInfo(int[] arrivalTime, int[] burstTime) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;

        waitingTime = new int[arrivalTime.length];
        turnAroundTime = new int[arrivalTime.length];
    }
}
