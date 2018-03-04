/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_2017_p2;

/**
 *
 * @author rizos
 */
public class CPU {

    private Process runningProcess;// τη τρεχουσα διεργασία που χρησιμοποιεί τη CPU

    private int timeToNextContextSwitch;// χρονική στιγμή της επόμενης διακοπής
    private int lastProcessStartTime;//περιέχει τη χρονική στιγμή έναρξης της τελευταίας διεργασίας
    private Clock clock; //ο τρέχων χρόνος
    private Xcheduler x;
    public CPU(Clock clock, Xcheduler x) {
        this.clock = clock;
        runningProcess = null;
        timeToNextContextSwitch = 0;
        lastProcessStartTime = 0;
        this.x = x;
    }

    public void addProcess(Process process) {// εισαγωγή της διεργασίας προς εκτέλεση στη CPU
        runningProcess = process;
        runningProcess.setProcessState(2,clock.ShowTime());
        lastProcessStartTime = clock.ShowTime();
        
        int quantum = x.getQuantum();
        int processTime = runningProcess.getCpuRemainingTime();
        if(quantum<0 || quantum>processTime)
            timeToNextContextSwitch = processTime + clock.ShowTime(); //SJF + RR όταν έχουμε μικρότερο χρόνο εκτελεσης απο το quantum
        else
            timeToNextContextSwitch = quantum + clock.ShowTime(); //RR οταν ο χρόνος εκτελεσης είναι μεγαλύτερος από το quantum
            
    }

    public Process removeProcessFromCpu() { // εξαγωγή της τρέχουσας διεργασίας από τη CPU
        if(runningProcess.getCpuRemainingTime()==0)
            runningProcess.setProcessState(3,clock.ShowTime());

        else
            runningProcess.setProcessState(1,clock.ShowTime());
        return runningProcess;
    }

    public void execute() {// εκτέλεση της διεργασίας με αντίστοιχη μέιωση του χρόνου εκτέλεσής της
        while (clock.ShowTime() < timeToNextContextSwitch ) {//sjf waiting mode πρεπει να προστεθει x.SJFkati==boolean
            boolean indi = clock.Time_Run();
            runningProcess.changeCpuRemainingTime(1);
            if(this.x.isTypeAlg() == true && indi==true ) {
                x.stop(clock, this);
            }

        }
        if (clock.ShowTime() < timeToNextContextSwitch) {
            runningProcess.setProcessState(1,clock.ShowTime()); //διακοπή της τρέχουσας και αλλαγή κατάστασης σε waiting
        }
    }
}
