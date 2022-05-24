package test;

import Log.Log;
import PCB.PCB;
import View.Frame;
import org.testng.annotations.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class test {
    @Test
    public void test() throws IOException {
        JFileChooser fileChooser=new JFileChooser();
        int option= fileChooser.showOpenDialog(null);
        if(option==JFileChooser.APPROVE_OPTION){
            System.out.println(fileChooser.getSelectedFile().getName());
            File file =fileChooser.getSelectedFile();
            //创建一个新的PCB
            String pName="";
            String temp[]= file.getName().split("[.]");
            for(int i=0;i<temp.length;i++){
                if(!temp[i].equals("txt"))
                    pName=temp[i];
            }
            PCB pcb=new PCB(pName);
           // pcb.LoadPCBS(file);
        }
    }
    @Test
    public void test1(){
        Frame frame=new Frame();
        frame.setVisible(true);
    }
    @Test
    public void test2() throws IOException {
        PCB pcb=new PCB();
        pcb.setM_PName("测试");
        File file=new File("D:\\test.txt");
        Log log=new Log(file);
        log.writeLog(pcb,"C",new Date());
    }
}
