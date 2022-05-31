package Que;

import PCB.*;
import java.util.LinkedList;
import java.util.Queue;

public class Que {
    //就绪队列
    private Queue<PCB> m_PReadyPCBS;

    //等待就绪队列
    private Queue<PCB> m_WaitingPCBS;
    //输入等待队列
    private Queue<PCB> m_PInputWaitingPCBS;
    //输出等待队列
    private Queue<PCB> m_POutputWaitingPCBS;
    //单例模式
    private static Que que=new Que();

    public Queue<PCB> getM_PReadyPCBS() {
        return m_PReadyPCBS;
    }


    private Que(){
         //初始化队列
         m_PReadyPCBS=new LinkedList<>();
         m_WaitingPCBS =new LinkedList<>();
         m_PInputWaitingPCBS=new LinkedList<>();
         m_POutputWaitingPCBS=new LinkedList<>();
     }
    public static Que getInstance(){
         return que;
    }

    public PCB removePCB(String m){
        PCB pcb=new PCB();
        switch (m){
            case "R":
                pcb=m_PReadyPCBS.poll();
                break;
            case "I":
                pcb=m_PInputWaitingPCBS.poll();
                break;
            case "O":
                pcb=m_POutputWaitingPCBS.poll();
                break;
            case  "W":
                pcb= m_WaitingPCBS.poll();
                break;
        }
        return pcb;
    }
    public void addPCBToQue(String m,PCB pcb){
        switch (m){
            case "R":
                m_PReadyPCBS.offer(pcb);
                break;
            case "I":
               m_PInputWaitingPCBS.offer(pcb);
                break;
            case "O":
                m_POutputWaitingPCBS.offer(pcb);
                break;
            case  "W":
                m_WaitingPCBS.offer(pcb);
                break;
        }
    }

    public void setM_PReadyPCBS(Queue<PCB> m_PReadyPCBS) {
        this.m_PReadyPCBS = m_PReadyPCBS;
    }

    public Queue<PCB> getM_WaitingPCBS() {
        return m_WaitingPCBS;
    }

    public void setM_WaitingPCBS(Queue<PCB> m_WaitingPCBS) {
        this.m_WaitingPCBS = m_WaitingPCBS;
    }

    public Queue<PCB> getM_PInputWaitingPCBS() {
        return m_PInputWaitingPCBS;
    }

    public void setM_PInputWaitingPCBS(Queue<PCB> m_PInputWaitingPCBS) {
        this.m_PInputWaitingPCBS = m_PInputWaitingPCBS;
    }

    public Queue<PCB> getM_POutputWaitingPCBS() {
        return m_POutputWaitingPCBS;
    }

    public void setM_POutputWaitingPCBS(Queue<PCB> m_POutputWaitingPCBS) {
        this.m_POutputWaitingPCBS = m_POutputWaitingPCBS;
    }
}
