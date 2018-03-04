/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_2017_p2;

/**
 * @author rizos
 */
public class Clock {

    protected static int ticks;//τρέχων αριθμό χτύπων ρολογιού που έχουν παρέλθει
    private NewProcessTemporaryList tempList;
    private ReadyProcessesList readyList;

    public Clock(NewProcessTemporaryList tempList, ReadyProcessesList readyList) {
        ticks = 0;
        this.tempList = tempList;
        this.readyList = readyList;
    }

    public boolean Time_Run() {// αύξηση των χτύπων του ρολογιού (+1)
        ticks++;
        Process a = tempList.showNextArrival();
        if (a != null) {
            if (a.getProcessArrivalTime() == ticks) {//εαν το arrivaltime της διεργασίας είναι ίσο με το τρεχων χρόνο
                readyList.addProcess(a);//προσθέτουμε την διεργασία στην  ReadyList
                tempList.getProcessList().remove(a);
                return true;
            }

        }
        return false;
    }

    public int ShowTime() {// επιστροφή της ώρας βάσει της μεταβλητής ticks

        return ticks;
    }

    public ReadyProcessesList getReadyList() {
        return readyList;
    }

    public NewProcessTemporaryList getTempList() {
        return tempList;
    }
}
