package PCB;

import Dispatch.Dispatch;
import Instruction.Instruction;
import lombok.Data;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

import java.util.UUID;
@Data
public class PCB {
    //时间片大小
    static int m_NTimeSlice;
    //进程名称
    private String m_PName;
    //进程标识符
    private String m_PID;
    //当前运行指令运行还需要的时间
    private int m_RTime;
    //指向本进程的指令序列
    private LinkedList<Instruction> m_IcList;
    //指向正在运行或将要运行的指令
    private Instruction m_PRunIC;

    //设置时间片大小
    public static void setRunTimeSplice(int nTime){
        m_NTimeSlice=nTime;
    }

    //创建一个进程
    public PCB(String m_PName) {
        this.m_PName = m_PName;
        //生成进程号
        m_PID=UUID.randomUUID().toString();
        //默认还需时间为0
        m_RTime=0;
        //调度器
    }
    public PCB(){
        //生成进程号
        m_PID=UUID.randomUUID().toString();
        //默认还需时间为0
        m_RTime=0;
    }
    //从文件中加载要运行的进程信息
    public void loadPCBInfo() throws IOException {
        //将要运行的指令
        this.m_PRunIC=getInstruction();
        //当前指令运行还需要时间
        if(m_PRunIC!=null)
        this.m_RTime=m_PRunIC.getM_NRunTime();
        else {
            this.m_RTime=0;
        }
    }
    //获得一条指令
    public Instruction getInstruction(){
        if(m_IcList.size()>0)
        return m_IcList.poll();
        else
            return null;
    }
    public static void setM_NTimeSlice(int time){
        m_NTimeSlice=time;
    }

}
