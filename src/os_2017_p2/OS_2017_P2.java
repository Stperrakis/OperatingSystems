/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_2017_p2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author rizos
 */
public class OS_2017_P2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        NewProcessTemporaryList tempList;
        ProcessGenerator generator;
        UI ui = new UI(); //γραφικο περιβαλλον στο terminal

        /*αποφασίζει τη ροή εκτέλεσης, ενω σε περίπτωση ψευδοτυχαιων διεργασιών
        τον επιθυμιτό αριθμό διεργασιών που θέλουμε απο τη γεννήτρια*/
        int mode = ui.flowExecute();
        Path processPath = Paths.get("data/process.dat");
        if (mode == 0) {
            generator = new ProcessGenerator(processPath, false);
            tempList = new NewProcessTemporaryList(generator.parseProcessFile());//φόρτωση λίστας διεργασιών από ήδη υπάρχον αρχείο
        } else {
            generator = new ProcessGenerator(processPath, false);
            tempList = new NewProcessTemporaryList();
            for (int i = 0; i < mode; i++) {
                tempList.addNewProcess(generator.createProcess());//δημιουργεί ψευδοτυχαίες διεργασίες και τις βάζει στο αρχείο
            }
            generator.StoreProcessToFile(tempList.getProcessList());//αφού δημιουργηθεί η λίστα την αποθηκεύει
        }

        Boolean runningStatus = true;
        ReadyProcessesList readyList = new ReadyProcessesList();
        Clock clock = new Clock(tempList, readyList);
        Xcheduler x;
        x = ui.pickCheduler(readyList);
        CPU cpu = new CPU(clock, x); //ορισμος της  cpu βασει αλγορίθμου και ρολογιού
        Process sayIt = tempList.showNextArrival();
        readyList.addProcess(sayIt); //παίρνει την πρώτη διεργασία βαση της αφηξης της απο την new
        tempList.getProcessList().remove(sayIt);
        while (runningStatus) { //το true θα ανατικατασταθεί με μία συνάρτηση που θα μας γυρνάει πότε έιναι ολα terminated στην ReadyList
            if (clock.getTempList().getProcessList().isEmpty() && clock.getReadyList().getProcessList().isEmpty()) {
                break;
            }
            Process p;
            cpu.addProcess(x.addProcessToReadyList(readyList));//την στέλνει στην cpu
            cpu.execute();//εκτελείται η πρώτη διεργασία η οποία έχει παντα arrivaltime=0
            p = cpu.removeProcessFromCpu();//remove απο τη cpu
            if (p.getProcessState() == 3) {
                readyList.addAlreadyProcess(p); //διεργασία terminated τοτε add στη λίστα με τις terminated processes
            }
        }

        Path statPath = Paths.get("data/statistics.txt"); //OS: MACOS
        Statistics stat = new Statistics(statPath, readyList);
        stat.WriteStatistics2File(x.routingAlgo());
        stat.printStatistics(x.routingAlgo());
        }
}


