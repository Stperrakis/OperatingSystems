/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_2017_p2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author rizos
 */
public class Statistics {

    private double averageWaitingTime;/* ο τρέχων μέσος χρόνος αναμονής των διεργασιών προς εκτέλεση */
    private double totalWaitingTime;/* ο τρέχων συνολικός χρόνος αναμονής διεργασιών */
    private double responseTime;/* ο μέσος χρόνος απόκρισης */
    private int maximumLengthOfReadyProcessesList;/* το τρέχον μέγιστο πλήθος διεργασιών προς εκτέλεση */
    public int totalNumberOfProcesses;/* ο τρέχων συνολικός αριθμός διεργασιών */
    private Path outputFile;/*αρχείο που αποθηκεύονται τα στατιστικά δεδομένα */
    private ReadyProcessesList readyList;

    public Statistics(Path outputFile, ReadyProcessesList readyList) {
        this.outputFile = outputFile;
        this.readyList = readyList;

        averageWaitingTime = 0;
        totalWaitingTime = 0;
        responseTime = 0;
        maximumLengthOfReadyProcessesList = 0;
        totalNumberOfProcesses = 0;
    }

    /* ελέγχει το μήκος της λίστας έτοιμων διεργασιών και ενημερώνει, αν είναι απαραίτητο, 
    την τιμή της μεταβλητής maximumLengthOfReadyProcessesList */
    public void UpdateMaximumListLength() {
        readyList.getMaxLength();
    }

    public void statisticsCalculator() {//υπολογίζει του χρόνους απο τη λίστα με τις terminated διεργασίες
        for (Process p : readyList.getAlreadyprocessList()) {
            totalWaitingTime += p.getWaitingTime();
            responseTime += p.getResponseTime();
        }
        if (!readyList.getAlreadyprocessList().isEmpty()) {
            averageWaitingTime = totalWaitingTime / readyList.getAlreadyprocessList().size();
            responseTime = responseTime / readyList.getAlreadyprocessList().size();
        } else { //action has been failed
            averageWaitingTime = 0;
            responseTime = 0;
        }
        maximumLengthOfReadyProcessesList = readyList.getMaxLength();
        totalNumberOfProcesses = readyList.getAlreadyprocessList().size();
    }


    public void WriteStatistics2File(String type) {/* προσθέτει μια νέα γραμμή με τα τρέχοντα στατιστικά στο αρχείο outputFile */
        statisticsCalculator();
        try (BufferedWriter out = new BufferedWriter(new FileWriter(outputFile.toString(),true))) {
            out.write("---STATISTICS---\n\n*** "+type+" Algorithm ***\n\n"+"Average waiting time: " + ((float) averageWaitingTime) + "\nTotal waiting time: " +
                    ((int) totalWaitingTime) + "\nResponse time: " + ((int) responseTime) + "\n Maximum length of ready processes: "
                    + maximumLengthOfReadyProcessesList + "\nTotal number of processes: " + totalNumberOfProcesses + "\n\n\n");

        } catch (IOException e) {
        }
    }

    public void printStatistics(String type) {
        statisticsCalculator();
        System.out.println("---STATISTICS---\n\n*** "+type+" Algorithm ***\n\n"+"Average waiting time: " + ((float) averageWaitingTime) + "\nTotal waiting time: " +
                ((int) totalWaitingTime) + "\nResponse time: " + ((int) responseTime) + "\n Maximum length of ready processes: "
                + maximumLengthOfReadyProcessesList + "\nTotal number of processes: " + totalNumberOfProcesses + "\n\n\n");
    }
}
