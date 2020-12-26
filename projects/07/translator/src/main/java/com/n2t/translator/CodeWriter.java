package com.n2t.translator;


import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {
    private FileWriter myWriter;
    private int label_counter = 0;
    private String label_id = "";

    public CodeWriter(String output) {
        try {
            myWriter = new FileWriter(output);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void setFileName(String fileName) {
        throw new UnsupportedOperationException();
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
            }
        }else if("pop".equals(command)) {
            throw new UnsupportedOperationException();
        }
        try {
            myWriter.write(commandStr);
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
