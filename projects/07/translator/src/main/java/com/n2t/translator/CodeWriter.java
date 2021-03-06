package com.n2t.translator;


import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {
    private FileWriter myWriter;
    private int label_counter = 0;
    private String label_id = "";
    private String fileName;
    // This will be used to generate the labels inside a function.
    private String functionName = "";

    public CodeWriter(String output) {
        try {
            myWriter = new FileWriter(output);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName.substring(0, fileName.lastIndexOf(".vm"));;
    }

    public void writeInit() {
        String commandStr = "@256" + "\n"
                + "D=A" + "\n"
                + "@SP" + "\n"
                + "M=D" + "\n";

        try {
            myWriter.write(commandStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeArithmetic(String command) {
        String commandStr = "";

        if("add".equals(command)) {
            commandStr = "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "D=M\n"
                    + "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "D=D+M\n"
                    + "M=D\n"
                    + "@SP\n"
                    + "M=M+1\n";
        }else if("sub".equals(command)) {
            commandStr = "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "D=M\n"
                    + "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "D=M-D\n"
                    + "M=D\n"
                    + "@SP\n"
                    + "M=M+1\n";
        }else if("neg".equals(command)) {
            commandStr = "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "M=-M\n"
                    + "@SP\n"
                    + "M=M+1\n";
        }else if("eq".equals(command)) {
            label_id = Integer.toString(label_counter);
            commandStr = "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "D=M\n"
                    + "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "D=M-D\n"
                    + "@EQ" + label_id + "\n"
                    + "D;JEQ\n"
                    + "@SP\n"
                    + "A=M\n"
                    + "M=0\n"
                    + "@EQRP" + label_id + "\n"
                    + "0;JMP\n"
                    + "(EQ" + label_id + ")\n"
                    + "@SP\n"
                    + "A=M\n"
                    + "M=-1\n"
                    + "(EQRP" + label_id + ")\n"
                    + "@SP\n"
                    + "M=M+1\n";
            label_counter += 1;
        }else if("gt".equals(command)){
            label_id = Integer.toString(label_counter);
            commandStr = "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "D=M\n"
                    + "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "D=M-D\n"
                    + "@GT" + label_id + "\n"
                    + "D;JGT\n"
                    + "@SP\n"
                    + "A=M\n"
                    + "M=0\n"
                    + "@GTRP" + label_id + "\n"
                    + "0;JMP\n"
                    + "(GT" + label_id + ")\n"
                    + "@SP\n"
                    + "A=M\n"
                    + "M=-1\n"
                    + "(GTRP" + label_id + ")\n"
                    + "@SP\n"
                    + "M=M+1\n";
            label_counter += 1;
        }else if("lt".equals(command)){
            label_id = Integer.toString(label_counter);
            commandStr = "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "D=M\n"
                    + "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "D=M-D\n"
                    + "@LT" + label_id + "\n"
                    + "D;JLT\n"
                    + "@SP\n"
                    + "A=M\n"
                    + "M=0\n"
                    + "@LTRP" + label_id + "\n"
                    + "0;JMP\n"
                    + "(LT" + label_id + ")\n"
                    + "@SP\n"
                    + "A=M\n"
                    + "M=-1\n"
                    + "(LTRP" + label_id + ")\n"
                    + "@SP\n"
                    + "M=M+1\n";
            label_counter += 1;
        }else if("and".equals(command)) {
            commandStr = "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "D=M\n"
                    + "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "D=D&M\n"
                    + "M=D\n"
                    + "@SP\n"
                    + "M=M+1\n";
        }else if("or".equals(command)) {
            commandStr = "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "D=M\n"
                    + "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "D=D|M\n"
                    + "M=D\n"
                    + "@SP\n"
                    + "M=M+1\n";
        }else if("not".equals(command)) {
            commandStr = "@SP\n"
                    + "M=M-1\n"
                    + "A=M\n"
                    + "M=!M\n"
                    + "@SP\n"
                    + "M=M+1\n";
        }

        try {
            myWriter.write(commandStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writePushPop(String command, String segment, int index) {
        String commandStr = "";

        if("push".equals(command)) {
            if("constant".equals(segment)) {
                commandStr = "@" + index + "\n"
                        + "D=A" + "\n"
                + "@SP" + "\n"
                + "A=M" + "\n"
                + "M=D" + "\n"
                + "@SP" + "\n"
                + "M=M+1" + "\n";
            }else if("local".equals(segment)){
                commandStr = "@" + index + "\n"
                + "D=A" + "\n"
                + "@LCL" + "\n"
                + "A=M" + "\n"
                + "A=D+A" + "\n"
                + "D=M" + "\n"
                + "@SP" + "\n"
                + "A=M" + "\n"
                + "M=D" + "\n"
                + "@SP" + "\n"
                + "M=M+1" + "\n";
            }else if("argument".equals(segment)){
                commandStr = "@" + index + "\n"
                        + "D=A" + "\n"
                        + "@ARG" + "\n"
                        + "A=M" + "\n"
                        + "A=D+A" + "\n"
                        + "D=M" + "\n"
                        + "@SP" + "\n"
                        + "A=M" + "\n"
                        + "M=D" + "\n"
                        + "@SP" + "\n"
                        + "M=M+1" + "\n";
            }else if("this".equals(segment)){
                commandStr = "@" + index + "\n"
                        + "D=A" + "\n"
                        + "@THIS" + "\n"
                        + "A=M" + "\n"
                        + "A=D+A" + "\n"
                        + "D=M" + "\n"
                        + "@SP" + "\n"
                        + "A=M" + "\n"
                        + "M=D" + "\n"
                        + "@SP" + "\n"
                        + "M=M+1" + "\n";
            }else if("that".equals(segment)){
                commandStr = "@" + index + "\n"
                        + "D=A" + "\n"
                        + "@THAT" + "\n"
                        + "A=M" + "\n"
                        + "A=D+A" + "\n"
                        + "D=M" + "\n"
                        + "@SP" + "\n"
                        + "A=M" + "\n"
                        + "M=D" + "\n"
                        + "@SP" + "\n"
                        + "M=M+1" + "\n";
            }else if("pointer".equals(segment)) {
                String location = index == 0 ? "@THIS" : "@THAT";
                commandStr = location + "\n"
                        + "D=M" + "\n"
                        + "@SP" + "\n"
                        + "A=M" + "\n"
                        + "M=D" + "\n"
                        + "@SP" + "\n"
                        + "M=M+1" + "\n";
            }else if("temp".equals(segment)) {
                commandStr = "@" + index + "\n"
                        + "D=A" + "\n"
                        + "@5" + "\n"
                        + "A=D+A" + "\n"
                        + "D=M" + "\n"
                        + "@SP" + "\n"
                        + "A=M" + "\n"
                        + "M=D" + "\n"
                        + "@SP" + "\n"
                        + "M=M+1" + "\n";
            }else if("static".equals(segment)) {
                commandStr = "@" + fileName + "." + index + "\n"
                        + "D=A" + "\n"
                        + "@16" + "\n"
                        + "A=A+D" + "\n"
                        + "D=M" + "\n"
                        + "@SP" + "\n"
                        + "A=M" + "\n"
                        + "M=D" + "\n"
                        + "@SP" + "\n"
                        + "M=M+1" + "\n";
            }
        }else if("pop".equals(command)) {
            if("local".equals(segment)) {
                commandStr = "@" + index + "\n"
                        + "D=A" + "\n"
                        + "@LCL" + "\n"
                        + "D=M+D" + "\n"
                        + "@R13" + "\n"
                        + "M=D" + "\n"
                        + "@SP" + "\n"
                        + "M=M-1" + "\n"
                        + "A=M" + "\n"
                        + "D=M" + "\n"
                        + "@R13" + "\n"
                        + "A=M" + "\n"
                        + "M=D" + "\n";
            }else if("argument".equals(segment)) {
                commandStr = "@" + index + "\n"
                        + "D=A" + "\n"
                        + "@ARG" + "\n"
                        + "D=M+D" + "\n"
                        + "@R13" + "\n"
                        + "M=D" + "\n"
                        + "@SP" + "\n"
                        + "M=M-1" + "\n"
                        + "A=M" + "\n"
                        + "D=M" + "\n"
                        + "@R13" + "\n"
                        + "A=M" + "\n"
                        + "M=D" + "\n";
            }else if("this".equals(segment)) {
                commandStr = "@" + index + "\n"
                        + "D=A" + "\n"
                        + "@THIS" + "\n"
                        + "D=M+D" + "\n"
                        + "@R13" + "\n"
                        + "M=D" + "\n"
                        + "@SP" + "\n"
                        + "M=M-1" + "\n"
                        + "A=M" + "\n"
                        + "D=M" + "\n"
                        + "@R13" + "\n"
                        + "A=M" + "\n"
                        + "M=D" + "\n";
            }else if("that".equals(segment)) {
                commandStr = "@" + index + "\n"
                        + "D=A" + "\n"
                        + "@THAT" + "\n"
                        + "D=M+D" + "\n"
                        + "@R13" + "\n"
                        + "M=D" + "\n"
                        + "@SP" + "\n"
                        + "M=M-1" + "\n"
                        + "A=M" + "\n"
                        + "D=M" + "\n"
                        + "@R13" + "\n"
                        + "A=M" + "\n"
                        + "M=D" + "\n";
            }else if("pointer".equals(segment)) {
                String location = index == 0 ? "@THIS" : "@THAT";
                commandStr = "@SP" + "\n"
                        + "M=M-1" + "\n"
                        + "A=M" + "\n"
                        + "D=M" + "\n"
                        + location + "\n"
                        + "M=D" + "\n";
            }else if("temp".equals(segment)) {
                commandStr = "@" + index + "\n"
                        + "D=A" + "\n"
                        + "@5" + "\n"
                        + "D=A+D" + "\n"
                        + "@R13" + "\n"
                        + "M=D" + "\n"
                        + "@SP" + "\n"
                        + "M=M-1" + "\n"
                        + "A=M" + "\n"
                        + "D=M" + "\n"
                        + "@R13" + "\n"
                        + "A=M" + "\n"
                        + "M=D" + "\n";
            }else if("static".equals(segment)) {
                commandStr = "@" +  fileName + "." + index + "\n"
                        + "D=A" + "\n"
                        + "@16" + "\n"
                        + "D=A+D" + "\n"
                        + "@R13" + "\n"
                        + "M=D" + "\n"
                        + "@SP" + "\n"
                        + "M=M-1" + "\n"
                        + "A=M" + "\n"
                        + "D=M" + "\n"
                        + "@R13" + "\n"
                        + "A=M" + "\n"
                        + "M=D" + "\n";
            }
        }
        try {
            myWriter.write(commandStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLabel(String label) {
        String commandStr = "(" + this.functionName + "$" + label + ")" + "\n";
        try {
            myWriter.write(commandStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeGoto(String label) {
        String commandStr = "@" + this.functionName + "$" + label + "\n"
                + "0;JMP" + "\n";
        try {
            myWriter.write(commandStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeIf(String label) {
        String commandStr = "@SP" + "\n"
            + "M=M-1" + "\n"
            + "A=M" + "\n"
            + "D=M" + "\n"
            + "@" + this.functionName + "$" + label + "\n"
            + "D;JNE" + "\n";
        try {
            myWriter.write(commandStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void writeFunction(String functionName, int numLocals) {
        this.functionName = functionName;
        String commandStr = "(" + functionName + ")" + "\n";
        String pushStr = "@SP" + "\n"
                + "A=M" + "\n"
                + "M=0" + "\n"
                + "@SP" + "\n"
                + "M=M+1" + "\n";

        for(int i=0; i < numLocals; i++) {
            commandStr = commandStr + pushStr;
        }

        try {
            myWriter.write(commandStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeReturn() {
        //R13=FRAME
        //R14=RET
        String commandStr =
            "@LCL" + "\n" //FRAME=LCL
            + "D=M" + "\n"
            + "@R13" + "\n"
            + "M=D" + "\n"
            //RET=*(FRAME-5)
            + "D=M" + "\n"
            + "@5" + "\n"
            + "A=D-A" + "\n"
            + "D=M" + "\n"
            + "@R14" + "\n"
            + "M=D" + "\n"
            //*ARG=pop()
            + "@SP" + "\n"
            + "M=M-1" + "\n"
            + "A=M" + "\n"
            + "D=M" + "\n"
            + "@ARG" + "\n"
            + "A=M" + "\n"
            + "M=D" + "\n"
            //SP=ARG+1
            + "@ARG" + "\n"
            + "D=M+1" + "\n"
            + "@SP" + "\n"
            + "M=D" + "\n"
            //THAT=*(FRAME-1)
            + "@R13" + "\n"
            + "D=M" + "\n"
            + "A=D-1" + "\n"
            + "D=M" + "\n"
            + "@THAT" + "\n"
            + "M=D" + "\n"
            //THIS=*(FRAME-2)
            + "@R13" + "\n"
            + "D=M" + "\n"
            + "@2" + "\n"
            + "A=D-A" + "\n"
            + "D=M" + "\n"
            + "@THIS" + "\n"
            + "M=D" + "\n"
            //ARG=*(FRAME-3)
            + "@R13" + "\n"
            + "D=M" + "\n"
            + "@3" + "\n"
            + "A=D-A" + "\n"
            + "D=M" + "\n"
            + "@ARG" + "\n"
            + "M=D" + "\n"
            //LCL=*(FRAME-4)
            + "@R13" + "\n"
            + "D=M" + "\n"
            + "@4" + "\n"
            + "A=D-A" + "\n"
            + "D=M" + "\n"
            + "@LCL" + "\n"
            + "M=D" + "\n"
            //goto RET
            + "@R14" + "\n"
            + "A=M" + "\n"
            + "0;JMP" + "\n";

        label_counter += 1;
        try {
            myWriter.write(commandStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCall(String functionName, int numArgs) {
        String retAddr = functionName + "_$RET_ADDR$" + label_counter;
        String argShift = String.valueOf(numArgs + 5);
        String commandStr = //push return-address
            "@" + retAddr + "\n"
            + "D=A" + "\n"
            + "@SP" + "\n"
            + "A=M" + "\n"
            + "M=D" + "\n"
            + "@SP" + "\n"
            + "M=M+1" + "\n"
            //push  LCL
            + "@LCL" + "\n"
            + "D=M" + "\n"
            + "@SP" + "\n"
            + "A=M" + "\n"
            + "M=D" + "\n"
            + "@SP" + "\n"
            + "M=M+1" + "\n"
            //push ARG
            + "@ARG" + "\n"
            + "D=M" + "\n"
            + "@SP" + "\n"
            + "A=M" + "\n"
            + "M=D" + "\n"
            + "@SP" + "\n"
            + "M=M+1" + "\n"
            //push THIS
            + "@THIS" + "\n"
            + "D=M" + "\n"
            + "@SP" + "\n"
            + "A=M" + "\n"
            + "M=D" + "\n"
            + "@SP" + "\n"
            + "M=M+1" + "\n"
            //push THAT
            + "@THAT" + "\n"
            + "D=M" + "\n"
            + "@SP" + "\n"
            + "A=M" + "\n"
            + "M=D" + "\n"
            + "@SP" + "\n"
            + "M=M+1" + "\n"
            //ARG=SP-n-5
            + "@SP" + "\n"
            + "D=M" + "\n"
            + "@" + argShift + "\n"
            + "D=D-A" + "\n"
            + "@ARG" + "\n"
            + "M=D" + "\n"
            //LCL=SP
            + "@SP" + "\n"
            + "D=M" + "\n"
            + "@LCL" + "\n"
            + "M=D" + "\n"
            //goto f
            + "@" + functionName + "\n"
            + "0;JMP" + "\n"
            + "(" + retAddr + ")" + "\n";

        label_counter += 1;

        try {
            myWriter.write(commandStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeVMCommand(String vmCommandStr) {
        try {
            myWriter.write("// " + vmCommandStr + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
