/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_2017_p2;

import java.io.Serializable;

/**
 *
 * @author rizos
 */
public class Process implements Serializable{ 
    private final int pid; // μοναδικός κωδικός κάθε διεργασίας
    private final int arrivalTime; // χρονική στιγμή άφιξης της διεργασίας στο σύστημα
    private int processTotalTime; // συνολικός χρόνος δικεπαιρέωσης της διεργασίας
    private int cpuRemainingTime;// εναπομείναντας χρόνος απασχόλησης της CPU από τη διεργασία
    private int currentState; //τρέχουσα κατάσταση της διεργασίας: 0 – Created/New, 1 - Ready/Waiting, 2 - Running, 3 - Terminated
    private int waitingTime;//χρόνος αναμονής
    private int responseTime;//χρόνος απόκρισης
    public Process(int pid, int arrivalTime, int processTotalTime) { //constructor - αρχικοποίηση των πεδίων 
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.processTotalTime = processTotalTime;
        cpuRemainingTime = processTotalTime;
        currentState = 0;
    }
    public void setProcessState (int currentState,int time) {  // θέτει την κατάσταση της διεργασίας ίση με την παράμετρο newState (αλλαγή της κατάστασής της) */ 
        this.currentState = currentState;
        switch(currentState){
            case 2:
                waitingTime = time - arrivalTime;//η διάρκεια αναμονής για να εκτελεστεί
                break;
            case 3:
                responseTime = time - arrivalTime;//η διάρκεια απο τη στιγμή που ήρθε μέχρι που ολοκληρώθηκε η εκτέλεση της διεργασιας
                break ;
            default:
                break;
        }
    } 
    public int getProcessState(){
        return currentState;
    }
    public void changeCpuRemainingTime(int processingTime) { // αλλάζει τον εναπομείναντα χρόνο απασχόλησης της CPU από τη διεργασία
        cpuRemainingTime = cpuRemainingTime - processingTime;
    }
    public void printProcess(){
        System.out.println(this.pid + "/t" + this.arrivalTime + "/t" + this.currentState);
    }
    public int getProcessTotalTime(){
        return processTotalTime;
    }
    public int getCpuRemainingTime(){
        return cpuRemainingTime;
    }
    public int getProcessArrivalTime(){
        return arrivalTime;
    }
    public int getWaitingTime(){
        return waitingTime;
    }
    public int getResponseTime(){
        return responseTime;
    }
}
