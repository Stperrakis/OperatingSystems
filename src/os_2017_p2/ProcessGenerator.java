/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_2017_p2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author rizos
 */
public class ProcessGenerator {

    private Path inputFile;// αρχείο που αποθηκεύονται τα δεδομένα των νέων διεργασιών
    private Process lastProcess; //βοηθητικη μεταβλητή για την δημιουργία ψευδοτυχαίας διεργασίας
    private List<Integer> previousPID; // λίστα με τα pid των ψευδοτυχαιων διεργασιών

    /*αν readFile == false δημιουργεί το αρχείο inputFile με όνομα
    filename για αποθήκευση, αλλιώς ανοίγει το αρχείο inputFile για ανάγνωση */
    public ProcessGenerator(Path inputFile, boolean readFile) {
        /*if (readFile == false) {
            inputFile = new File(filename);
        }*/
        this.inputFile = inputFile;
        lastProcess = null;
        previousPID = new ArrayList<>();
    }

    public Process createProcess() { //δημιουργία μιας νέας διεργασίας με (ψευδο-)τυχαία χαρακτηριστικα
        boolean exist = true;
        Integer pid = null;
        while(exist){    
            pid = randomInt(1,10000);//autoboxing σε integer
            exist=false;
            for(Integer i : previousPID)
                if(pid.equals(i))
                    exist=true;
        }
        previousPID.add(pid); //προσθετει το νεο pid στα ηδη υπάρχοντα
        Process randomProcess;
        if(lastProcess==null)
            randomProcess = new Process(pid,0, randomInt(1,100));
        else{
            int atime = 0;
            while(atime==0){ //για να υπάρχεα πάντα μία διεργασία προς εκτέλεση
                atime=randomInt(lastProcess.getProcessArrivalTime()+1,lastProcess.getProcessArrivalTime()+6);
                if(atime > lastProcess.getProcessArrivalTime() + lastProcess.getProcessTotalTime())
                    atime=0;
                /*όσο ο ψευδοτυχαίος χρόνος αφηξης είναι μεγαλύτερος απο το αθροισμα του χρόνου αφηξης και επεξερασίας της προηγουμενης
                ζητάει καινούριο χρόνο αφηξης για την νέα διεργασία*/
            }
            randomProcess = new Process(pid, atime, randomInt(1,100));
        }
                    
        //pid:autounboxing σε int, παίρνει αριθμούς από το 1 μέχρι το 10000 και είναι μοναδικό
        //η άφηξη κάθε επόμενης διεργασίας έχει μέγιστη διαφορα 20 χτύπους απο την προηγούμενη (+5)
        //ενώ ο χρόνος χρήσης της CPU μπορεί να είναι απο 1-100 χτύπους
        lastProcess = randomProcess;
        return randomProcess;
    }

    private int randomInt(int start, int end) {
        Random random = new Random();
        if (start < end) {
            long range = (long) end - (long) start + 1;
            long fraction = (long) (range * random.nextDouble());
            int randomNumber = (int) (fraction + start);
            return randomNumber;
        }
        return -1;
    }

    public void StoreProcessToFile(List<Process> updateList) throws IOException {//αποθήκευση των στοιχείων της νέας διεργασίας στο αρχείο inputFile
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(inputFile.toString()))) {
            out.writeObject(updateList);
        }
    }

    public List<Process> parseProcessFile() throws IOException, ClassNotFoundException {//ανάγνωση των στοιχείων νέων διεργασιών από το αρχείο inputFile
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(inputFile.toString()))) {
            List<Process> newList = (List<Process>) in.readObject();
            return newList;
        }
    }
}
