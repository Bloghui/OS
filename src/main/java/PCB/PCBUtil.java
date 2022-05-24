package PCB;

import Instruction.*;
import org.testng.annotations.Test;

import javax.swing.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class PCBUtil {
    public static LinkedList<Instruction> appendInstruction(String s,LinkedList<Instruction> list){
        String regex = "[CIOWH][0-9]+";
        char c = s.charAt(0);
        int time = 0;
        InstructionSet set = null;
        String[] split = s.split("\\D+");
        for (int i = 0; i < split.length; i++) {
            if(!split[i].equals(""))
                time = Integer.parseInt(split[i]);
        }
        switch (c) {
            case 'I':
                set = InstructionSet.INPUT;
                break;
            case 'C':
                set = InstructionSet.CALC;
                break;
            case 'W':
                set = InstructionSet.WAIT;
                break;
            case 'H':
                set = InstructionSet.HALT;
                break;
            case'O':
                set=InstructionSet.OUTPUT;
                break;
        }
        Instruction instruction = new Instruction(time, set);
        list.add(instruction);
        return  list;
    }
    public static LinkedList<PCB> getPCBFromFile(File file) throws IOException {
        LinkedList<PCB> list=new LinkedList<>();
        BufferedReader reader=new BufferedReader(new FileReader(file));
        String regex="[P][0-9]+";
        String regex1 = "[CIOWH][0-9]+";
        String s=new String();
        s=reader.readLine();
        LinkedList<Instruction> instructionLinkedList = null;
        PCB pcb=null;
        while (s!=null&&!s.equals("")) {
            if (s.matches(regex)) {
                pcb = new PCB();
                pcb.setM_PName(s);
                list.add(pcb);
                //指令序列
               instructionLinkedList=new LinkedList<>();
            }
            else if(s.matches(regex1)){
                instructionLinkedList = appendInstruction(s,instructionLinkedList);
                pcb.setM_IcList(instructionLinkedList);
            }
            else{
                JOptionPane.showMessageDialog(null, "非法指令", "错误", JOptionPane.QUESTION_MESSAGE);
            }
            s=reader.readLine();
        }
        return list;
    }


}


