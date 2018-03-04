/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_2017_p2;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author rizos
 */
public class UI {

    public UI() {
    }

    public Xcheduler pickCheduler(ReadyProcessesList readyList) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int answer = 0;
        while (answer <= 0 || answer > 3) {
            System.out.println("Choose routing algorithm:\n1)RR\n2)SJF\n3)SJF Primitive");
            try {
                answer = scanner.nextInt();
            } catch (NullPointerException e) {
            }
        }
        Xcheduler x;
        switch (answer) {
            case 1:
                int quantum = 0;
                while (quantum <= 0) {
                    System.out.println("Give Quantum Number: ");
                    try {
                        quantum = scanner.nextInt();
                    } catch (NullPointerException e) {
                    }
                }   x = new Xcheduler(quantum,readyList);//RR
                break;
            case 2:
                x = new Xcheduler(false, readyList);//SJF
                break;
            default:
                x = new Xcheduler(true,readyList);//SJF primitive
                break;
        }
        return x;
    }

    public int flowExecute() throws IOException{
        Scanner scanner = new Scanner(System.in);
        int answer = 0;
        while (answer <= 0 || answer > 2) {
            System.out.println("Choose flow execute mode:\n1)PseudoGenerator mode\n2)Load processes from file mode");
            try {
                answer = scanner.nextInt();
            } catch (NullPointerException e) {
            }
        }
        if (answer == 1) {
            answer = 0;
            while (answer <= 0 || answer > 10000) {
            System.out.println("How many many processes would you like to create?(1-10000)?");
                try {
                    answer = scanner.nextInt();
                } catch (NullPointerException e) {
                }
            }
            return answer;
        }
        return 0;
    }
}
