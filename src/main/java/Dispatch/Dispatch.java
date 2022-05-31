package Dispatch;

import Instruction.*;
import Log.Log;
import PCB.PCB;
import Que.Que;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Data
public class Dispatch  {
    private Que que;
    private int NTime;
    private Log log;
    //单例模式
    private static Dispatch dispatch=new Dispatch();
    private Dispatch(){
        que=Que.getInstance();
        File file=new File("D:\\log.docx");
        if(file.exists())
            file.delete();
        log=new Log(file);
    }
    public static Dispatch getInstance(){
        return dispatch;
    }
    //写入日志
    public void writeLog(PCB pcb , String model, Date date) throws IOException {
        log.writeLog(pcb,model,date);
    }

    public PCB getPCBFromQue(String m){
        return que.removePCB(m);
    }
   public  void toQue(PCB pcb) throws  IOException {
                if(pcb!=null) {
                    //当前运行的指令
                    Instruction instruction = pcb.getM_PRunIC();
                    if (instruction != null) {
                        InstructionSet set = instruction.getM_Set();
                        switch (set) {
                            case INPUT:
                                //调入输入等待队列
                                appendQue(pcb,"I");
                                System.out.println("调入输入等待队列：" + que.getM_PInputWaitingPCBS().size());
                                break;
                            case OUTPUT:
                                //调入输出等待队列
                                appendQue(pcb,"O");
                                System.out.println("调入输出等待队列：" + que.getM_POutputWaitingPCBS().size()+pcb.getM_PName()+pcb.getM_PRunIC());
                                break;
                            case WAIT:
                                //调入等待队列
                                appendQue(pcb,"W");
                                System.out.println("调入等待队列：" + que.getM_WaitingPCBS().size());
                                break;
                            case HALT:
                                //结束进程
                                System.out.println("进程结束");
                                writeLog(pcb,"D",new Date());
                                break;
                        }
                    }
                }
    }
    //处理就绪队列
   public void treadReadyQue(PCB pcb) throws IOException {
        if(pcb!=null&&pcb.getM_PRunIC()!=null&&pcb.getM_PRunIC().getM_Set()==InstructionSet.CALC) {
            //获得一个PCB进行
            //运行时间片加1
            Instruction instruction = pcb.getM_PRunIC();
            instruction.updateInstruction();
            //指令还需要时间
            int i = instruction.getM_NRunTime() - instruction.getM_Runtime();
            if (i == 0) {
                pcb.loadPCBInfo();
                pcb.setM_RTime(0);
            } else {
                pcb.setM_RTime(i);
                pcb.setM_PRunIC(instruction);
            }
            appendQue(pcb, "R");
        }
    }

    public void  treadQue(String m, PCB pcb) throws IOException {
        int size=0;
        switch (m){
            case "I":
                size=que.getM_PInputWaitingPCBS().size();
                break;
            case "O":
                size=que.getM_POutputWaitingPCBS().size();
                break;
            case "W":
                size=que.getM_WaitingPCBS().size();
                break;
            case"R":
                treadReadyQue(pcb);
                break;
        }
        while(size>0){
            size--;
             pcb= getPCBFromQue(m);
            Instruction instruction=pcb.getM_PRunIC();
            if(instruction==null){
                que.removePCB(m);
                break;
            }
            instruction.updateInstruction();
            //指令还需要时间
            int i =instruction.getM_NRunTime()-instruction.getM_Runtime();
            if(i==0){
                 pcb.loadPCBInfo();
                 pcb.setM_RTime(0);
                //添加到就绪队列
                appendQue(pcb,"R");
            }
            else{
                    pcb.setM_RTime(i);
                    pcb.setM_PRunIC(instruction);
                    que.addPCBToQue(m,pcb);
            }

        }

    }
    public void appendQue(PCB pcb,String m) throws IOException {
       que.addPCBToQue(m,pcb);
        dispatch.writeLog(pcb,m,new Date());
    }

}
