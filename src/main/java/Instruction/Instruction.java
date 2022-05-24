package Instruction;



public class Instruction {
    //指令运行时间
    private int m_Runtime;
    //指令需要运行的时间长度
    private int m_NRunTime;
    //指令类型标记
    InstructionSet m_nInstructionID;

    public void setM_Runtime(int m_Runtime) {
        this.m_Runtime = m_Runtime;
    }

    public void setM_NRunTime(int m_NRunTime) {
        this.m_NRunTime = m_NRunTime;
    }

    public Instruction(int m_NRunTime, InstructionSet m_nInstructionID) {
        this.m_NRunTime = m_NRunTime;
        this.m_nInstructionID = m_nInstructionID;
    }
    @Override
    public String toString() {
        return "Instruction{" +
                "m_Runtime=" + m_Runtime +
                ", m_NRunTime=" + m_NRunTime +
                ", m_nInstructionID=" + m_nInstructionID +
                '}';
    }

    public int getM_Runtime() {
        return m_Runtime;
    }

    public int getM_NRunTime() {
        return m_NRunTime;
    }
    public InstructionSet getM_Set(){
        return  m_nInstructionID;
    }
 //指令时间片设置
    public void  updateInstruction(){
        if(m_Runtime<m_NRunTime)
       m_Runtime+=1;
    }
}
