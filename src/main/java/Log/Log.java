package Log;

import PCB.PCB;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    //调度日志
    private File file;

    public Log(File file) {
        this.file = file;
    }

    public  void writeLog(PCB pcb , String model, Date date) throws IOException {
        String log="";
        String time;
        //时间记录
        String df="yyyy/MM/dd HH:mm:ss:SSS";
        SimpleDateFormat format=new SimpleDateFormat(df);
        time=format.format(date);
        switch (model){
            case "C":
                log="创建一个进程";
                break;
            case "D":
                log="进程结束";
                break;
            case "I":
                log="调度进程到输入等待队列";
                break;
            case "O":
                log="调度进程到输出等待队列";
                break;
            case "W":
                log="调度进程到等待队列";
                break;
            case "R":
                log="调度进程到就绪队列";
                break;
        }
        BufferedWriter writer=new BufferedWriter(new FileWriter(file,true));
        writer.write(time+" "+log+"\t进程名:"+pcb.getM_PName());
        writer.newLine();
        writer.close();
    }


}
