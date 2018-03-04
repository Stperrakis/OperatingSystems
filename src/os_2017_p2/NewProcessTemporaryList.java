/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_2017_p2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rizos
 */
public class NewProcessTemporaryList {

    private List<Process> processList;// η λίστα που περιέχει τις νέες διεργασίες
    private int itt ;

    public NewProcessTemporaryList() {// constructor - αρχικοποίηση της λίστας και άλλων πεδίων
        //ProcessGenerator generator = new ;
        processList = new ArrayList<>();
        itt = 0;
        //processList = parseProcessFile.
    }

    public NewProcessTemporaryList(List<Process> processList) {// constructor - φόρτωση της λίστας από αρχείο
        this.processList = processList;
    }

    public void addNewProcess(Process process) { // εισαγωγή μιας νέας διεργασίας στη λίστα
        try {
            if (process != null) {
                processList.add(process);
            }
        } catch (NullPointerException e) {
        }
    }

   /* public Process getFirst() {// επιστροφή της πρώτης διεργασίας της λίστας βάση του arrivaltime
        getFirst++;
        try {
            if (processList.get(getFirst) != null) {
                return processList.get(getFirst);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return null;
    }*/
    public Process showNextArrival(){//δείχνει ποια διεργασία είναι πρώτη
        int size = processList.size();
        if(size>0)
        {
            return processList.get(0);
        }
        return null;
    }
    public List<Process> getProcessList() {
        return processList;
    }

    public boolean noMoreProcess() {
        if (!processList.isEmpty()) {
            return true;//ενημερώνει ότι έχουν προστεθέι στην ουρά για εκτέλεση όλες οι υπάρχουσες διεργασίες
        }
        return false;
    }

    public void printList() {// εκτύπωση της λίστας με τις νέες διεργασίες στην οθόνη
        try {
            System.out.println("PID/tARRIVAL TIME/tCURRENT STATE/n");
            if (processList != null) {
                for (Process proc : processList) {
                    proc.printProcess();
                }
            } else {
                System.out.println("No processes.");
            }
        } catch (NullPointerException e) {

        }
    }

}
