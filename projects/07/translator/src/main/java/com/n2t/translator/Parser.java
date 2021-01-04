package com.n2t.translator;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

import java.io.FileNotFoundException;


public class Parser {
    private Iterator<String> linesIterator;
    private String currentCommand;

    public Parser(String sourceFile) throws FileNotFoundException {
            Scanner source = new Scanner(new File(sourceFile));
            List<String> linesOfCode = new ArrayList<>();
            while(source.hasNextLine()) {
                String line = source.nextLine().trim();
                if(!line.isEmpty() && !line.startsWith("//")) {
                    linesOfCode.add(line);
                }
            }
            this.linesIterator = linesOfCode.iterator();
    }

    public boolean hasMoreCommands() {
         return linesIterator.hasNext();
    }

    public void advance() {
        currentCommand = linesIterator.next().trim().replaceAll("\\s+"," ");
    }

    public CommandType commandType() {
        CommandType commandType = null;

        String[] arithmeticCommands = {"add", "sub", "neg", "eq", "gt", "lt", "and", "or", "not"};
        Arrays.sort(arithmeticCommands);
        if(Arrays.binarySearch(arithmeticCommands, currentCommand) >= 0) {
            commandType = CommandType.ARITHMETIC;
        }else if(currentCommand.startsWith("push")) {
            commandType = CommandType.PUSH;
        }else if(currentCommand.startsWith("pop")) {
            commandType = CommandType.POP;
        }else if(currentCommand.startsWith("label")) {
            commandType = CommandType.LABEL;
        }else if(currentCommand.startsWith("goto")) {
            commandType = CommandType.GOTO;
        }else if(currentCommand.startsWith("if-goto")) {
            commandType = CommandType.IF;
        }
        return commandType;
    }

    public String arg1() {
        String arg1 = "";
        if(commandType() == CommandType.ARITHMETIC) {
            arg1 = currentCommand;
        }else if(commandType() == CommandType.PUSH || commandType() == CommandType.POP || commandType() == CommandType.LABEL || commandType() == CommandType.IF || commandType() == CommandType.GOTO) {
            arg1 = currentCommand.split(" ")[1];
        }
        return arg1;
    }

    public int arg2() {
        return Integer.parseInt(currentCommand.split(" ")[2]);
    }

}
