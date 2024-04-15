# CPU Scheduling Simulator

Welcome to the CPU Scheduling Simulator! This simulator allows users to analyze the performance of four different CPU scheduling algorithms: First-Come First-Served, Shortest Job First(Non-Preemptive), Shortest Remaining Time First, and Round Robin.

## Usage

1. **Selection of Algorithm:**
   - Press 1 for FCFS (First-Come, First-Served)
   - Press 2 for SJF (Shortest Job First)
   - Press 3 for SRTF (Shortest Remaining Time First)
   - Press 4 for Round Robin

2. **Entering Process Information:**
   - After selecting an algorithm, the user will be prompted to enter the arrival time and burst time of each process.
   - The simulator will then calculate various metrics such as wait time, turnaround time, context switches, and throughput.

3. **Viewing Metrics:**
   - After inputting the process information, the simulator will display the calculated metrics for the chosen algorithm.

## Metrics Provided
- Wait Time: Total time spent by a process waiting in the ready queue.
- Turnaround Time: Total time taken by a process from the time of arrival to completion.
- Context Switches: Number of times the CPU switches from one process to another.
- Throughput: Number of processes completed per unit time.

## How to Install
To install this project from Git, follow these steps:

1. Open your terminal or command prompt.
2. Navigate to the directory where you want to clone the repository.
3. Run the following command: git clone https://github.com/priyanshum143/CPU-Scheduling-Simulator.git
