/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_2017_p2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rizos
 */
public class ReadyProcessesList {

    private List<Process> processList;
    private List<Process> AlreadyprocessList;
    private int itt;
    private int maxLength;

    public ReadyProcessesList() {
        processList = new ArrayList<>();
        AlreadyprocessList = new ArrayList<>();
        this.itt = 0;
        maxLength = 0;
    }

    public void addProcess(Process item) {/* προσθήκη μιας νέας έτοιμης διεργασίας στη λίστα*/
            if (item != null) {
                processList.add(item);

                item.setProcessState(1,item.getProcessArrivalTime());
                if(processList.size()>maxLength)
                    maxLength=processList.size();
            }

    }
    public void addAlreadyProcess(Process item){/*προσθήκη στην alreadyList που έχεις τις terminated*/
        try {
            if (item != null) {
                AlreadyprocessList.add(item);
                processList.remove(item);
            }
        } catch (NullPointerException e) {
        }
    }
    
    public List<Process> getProcessList() {
        return processList;
    }

    public List<Process> getAlreadyprocessList() {
        return AlreadyprocessList;
    }

    public int getItt() {
        return itt;
    }

    public void setItt(int itt) {
        this.itt = itt;
    }

    public void switchList(Process a) {
        int i = processList.indexOf(a);
        Process temp = processList.get(i);
        processList.remove(i);
        this.AlreadyprocessList.add(temp);
    }

    /*public int getCurrent() {
        int u = this.itt - 1;
        return u > 0 ? u : 0;
    }*/

    public Process getFirst() {// picks the first element at the list  arrivaltime
        return this.processList.get(0);
    }
    public void printList() {
        try {
            System.out.println("PID/tARRIVAL TIME/tCURRENT STATE/n");
            if (processList != null)
                for (Process proc : processList) {
                    proc.printProcess();
                }
            else
                System.out.println("No processes.");
        } catch (NullPointerException e) {
        }
    }


    public Process getNextRR() {
        int size = this.processList.size();
        this.itt +=  1;
        if (size< itt+1) {
           this.itt = 0;
        }
        return this.processList.get(itt);
    }

    public Process getNextSJF() {
        Process b = this.processList.get(0);

        for (Process proc : this.processList) {
            if (b.getProcessTotalTime() > proc.getProcessTotalTime()) {
                b = proc;
            }
        }
        return b;
    }
    
    public int getMaxLength(){
        return maxLength;
    }

}