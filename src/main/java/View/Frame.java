package View;

import Dispatch.Dispatch;
import Instruction.InstructionSet;
import PCB.*;
import Que.Que;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.io.IOException;
import java.util.LinkedList;

public class Frame extends JFrame{
    private  JButton btn_Run;
    private JPanel contentPane;
    private JTextField NTime;
    private JTextField runProcess;
    private JTextArea textReady;
    private JTextArea inputWaitingQue;
    private JTextArea outPutWaitingQue;
    private JTextArea waitingQue;
    private JButton btn_File;
    private JButton btn_Stop;
    private Listener listener;
    private JLabel PCBInfo;

    public Frame() {
        listener = new Listener();
        this.setTitle("时间片轮转调度");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1050, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        btn_File = new JButton("打开文件");
        btn_File.setBounds(53, 20, 93, 23);
        panel.add(btn_File);
        btn_File.setFocusPainted(false);
        btn_File.addActionListener(listener);

        btn_Run = new JButton("开始调度");
        btn_Run.setBounds(204, 20, 93, 23);
        btn_Run.setFocusPainted(false);
        panel.add(btn_Run);
        btn_Run.addActionListener(listener);


        btn_Stop = new JButton("暂停调度");
        btn_Stop.setBounds(352, 20, 93, 23);
        btn_Stop.setFocusPainted(false);
        panel.add(btn_Stop);
        btn_Stop.addActionListener(listener);


        JLabel lblNewLabel = new JLabel("时间片大小");
        lblNewLabel.setBounds(513, 20, 72, 23);
        panel.add(lblNewLabel);

        NTime = new JTextField();
        NTime.setBounds(595, 22, 151, 21);
        panel.add(NTime);
        NTime.setText("300");
        NTime.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("正在运行的进程");
        lblNewLabel_1.setBounds(53, 94, 138, 23);
        panel.add(lblNewLabel_1);

        runProcess = new JTextField();
        runProcess.setBounds(53, 120, 120, 23);
        panel.add(runProcess);
        runProcess.setColumns(10);

        PCBInfo = new JLabel();
        PCBInfo.setFont(new Font("宋体", Font.PLAIN, 15));
        PCBInfo.setBounds(231, 94, 138, 23);
        panel.add(PCBInfo);

        JLabel lblNewLabel_2 = new JLabel("就绪队列");
        lblNewLabel_2.setBounds(53, 180, 72, 15);
        panel.add(lblNewLabel_2);

        textReady = new JTextArea();
        textReady.setBounds(53, 210, 190, 300);
        panel.add(textReady);
        textReady.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("输入等待队列");
        lblNewLabel_3.setBounds(296, 180, 111, 15);
        panel.add(lblNewLabel_3);

        inputWaitingQue = new JTextArea();
        inputWaitingQue.setBounds(296, 210, 190, 300);
        panel.add(inputWaitingQue);
        inputWaitingQue.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("输出等待队列");
        lblNewLabel_4.setBounds(539, 180, 93, 15);
        panel.add(lblNewLabel_4);

        outPutWaitingQue = new JTextArea();
        outPutWaitingQue.setBounds(539, 210, 190, 300);
        panel.add(outPutWaitingQue);
        outPutWaitingQue.setColumns(10);

        JLabel lblNewLabel_5 = new JLabel("等待队列");
        lblNewLabel_5.setBounds(782, 180, 100, 15);
        panel.add(lblNewLabel_5);

        waitingQue = new JTextArea();
        waitingQue.setBounds(782, 210, 190, 300);
        panel.add(waitingQue);
        waitingQue.setColumns(10);
    }
    class Listener  implements ActionListener {
        Dispatch dispatch=Dispatch.getInstance();
        Que que=Que.getInstance();
        Thread test;

        @SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btn_File) {
                btn_FileAction();
            } else if (e.getSource() == btn_Run) {
                btn_Run.setEnabled(false);
                btn_Stop.setEnabled(true);
                Runnable runnable = new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        int num=1;
                        while (num>0) {
                            num=que.getM_POutputWaitingPCBS().size()+que.getM_PInputWaitingPCBS().size()+que.getM_WaitingPCBS().size()+que.getM_PReadyPCBS().size();
                            btn_RunAction();
                            Thread.sleep(dispatch.getNTime());
                        }
                    }
                };
                test=new Thread(runnable);
                test.start();
            }
            else if(e.getSource()==btn_Stop){
                btn_Stop.setEnabled(false);
                btn_Run.setEnabled(true);
                test.interrupt();
            }
        }
        public void btn_FileAction() throws IOException {
            JFileChooser fileChooser=new JFileChooser();
            //默认目录
            String defaultDirectory = "d:\\";
            //默认文件名
            String defaultFilename = "prc.txt";
            //设置默认目录
            fileChooser.setCurrentDirectory(new File(defaultDirectory));
            //设置默认文件名
            fileChooser.setSelectedFile(new File(defaultFilename));
            int option= fileChooser.showOpenDialog(null);
            if(option==JFileChooser.APPROVE_OPTION){
                System.out.println(fileChooser.getSelectedFile().getName());
                File file =fileChooser.getSelectedFile();
                //获得时间片的大小
                String text = NTime.getText();
                int time;
                if(text.equals("")){
                    JOptionPane.showMessageDialog(null,"警告","未输入时间片大小",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else  {
                    try {
                        time=Integer.parseInt(text);
                    }
                    catch (Exception e1){
                        JOptionPane.showMessageDialog(null,"警告","非法输入",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                dispatch.setNTime(time);
                //设置PCB的时间片大小
                PCB.setM_NTimeSlice(time);
                LinkedList<PCB> PCBList = PCBUtil.getPCBFromFile(file);
                //添加到就绪队列
                for (PCB pcb : PCBList) {
                    pcb.loadPCBInfo();
                    dispatch.appendQue(pcb,"R");
                }
                for (PCB m_pReadyPCB : que.getM_PReadyPCBS()) {
                    System.out.println(m_pReadyPCB);
                }
                write(textReady,PCBList);
            }
        }

        public void btn_RunAction() throws IOException, InterruptedException {
                  PCB pcb =null;
                while(que.getM_PReadyPCBS().size()>0){
                    pcb = dispatch.getPCBFromQue("R");
                    dispatch.toQue(pcb);
                    showEndingPCB(pcb);
                    if(pcb.getM_PRunIC()!=null&&pcb.getM_PRunIC().getM_Set()==InstructionSet.CALC)
                        break;
                   }

                System.out.println("队列信息"+que.getM_PReadyPCBS().size()+"\t"+que.getM_PInputWaitingPCBS().size()+"\t"+que.getM_POutputWaitingPCBS().size()+"\t"+que.getM_WaitingPCBS().size());

                  showAllInfo(pcb);
               if(pcb!=null||que.getM_PReadyPCBS().size()>0)
                  dispatch.treadQue("R",pcb);
                  dispatch.treadQue("I",null);
                  dispatch.treadQue("O",null);
                  dispatch.treadQue("W",null);
                showRunningProcess(pcb);

        }
        public void write(JTextArea text,LinkedList<PCB> list){
            //清空
            text.setText("");
            if(list.size()>0){
                String name=new String();
                for (PCB pcb : list) {
                    name=name+pcb.getM_PName()+"\n";
                }
                text.setText(name);
            }
        }

        public void showRunningProcess(PCB pcb){
            if(pcb!=null&&pcb.getM_PRunIC()!=null&&pcb.getM_PRunIC().getM_Set()==InstructionSet.CALC)
            runProcess.setText(pcb.getM_PName());
            else
            {
                runProcess.setText("");
            }
        }
        public void showEndingPCB(PCB pcb){
            if(pcb!=null&&pcb.getM_PRunIC()!=null&&pcb.getM_PRunIC().getM_Set()==InstructionSet.HALT){
                PCBInfo.setForeground(Color.red);
                PCBInfo.setText("进程："+pcb.getM_PName()+"运行结束");
            }
        }
        public void printInfo(PCB pcb){
            for (PCB m_pInputWaitingPCB : que.getM_PInputWaitingPCBS()) {
                System.out.println("输入等待队列"+m_pInputWaitingPCB.getM_PName()+m_pInputWaitingPCB.getM_PRunIC());
            }
            for (PCB m_pOutputWaitingPCB : que.getM_POutputWaitingPCBS()) {
                System.out.println("输出等待队列"+m_pOutputWaitingPCB.getM_PName()+m_pOutputWaitingPCB.getM_PRunIC());
            }
            for (PCB m_pOutputWaitingPCB : que.getM_WaitingPCBS()) {
                System.out.println("等待队列"+m_pOutputWaitingPCB.getM_PName()+m_pOutputWaitingPCB.getM_PRunIC());
            }
        }
public void  showAllInfo(PCB pcb){
    printInfo(pcb);
    write(textReady, (LinkedList<PCB>) que.getM_PReadyPCBS());
    write(inputWaitingQue, (LinkedList<PCB>) que.getM_PInputWaitingPCBS());
    write(outPutWaitingQue,(LinkedList<PCB>)que.getM_POutputWaitingPCBS());
    write(waitingQue,(LinkedList<PCB>)que.getM_WaitingPCBS());
}
    }

    public static void main(String[] args) {
        Frame frame=new Frame();
        frame.setVisible(true);
    }

}

