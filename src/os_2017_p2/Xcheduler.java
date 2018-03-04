package os_2017_p2;

public class Xcheduler {
    private int quantum;
    private boolean typeAlg;
    private boolean stop;

    public Xcheduler(boolean type, ReadyProcessesList list1) {
        typeAlg = type;
        this.quantum = -1;
    }

    public Xcheduler(int quantum, ReadyProcessesList list1) {
        this.quantum = quantum;
        this.typeAlg = false;
    }


    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public boolean isTypeAlg() {
        return typeAlg;
    }

    public void setTypeAlg(boolean typeAlg) {
        this.typeAlg = typeAlg;
    }


    public Process addProcessToReadyList(ReadyProcessesList list) {
        Process temp;
        if (this.quantum < 0) {
            temp = list.getNextSJF();

        } else {
            temp = list.getNextRR();
        }

        return temp;

    }

    public Boolean stop(Clock clock,CPU o) {
        Process a = clock.getReadyList().getProcessList().get(0) ;
         for( Process temp : clock.getReadyList().getProcessList()){
           if(temp.getProcessTotalTime()<a.getProcessTotalTime()){
               a =  temp ;
           }

        }
       o.removeProcessFromCpu();
         o.addProcess(a);
         return true ;
    }

    public String routingAlgo(){
        if(quantum>0)
            return "RR";
        else
            if(typeAlg==true)
                return "SJF Primitive";
            return "SJF NoN-Primitive";
    }

}