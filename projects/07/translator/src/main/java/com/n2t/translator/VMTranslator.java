package com.n2t.translator;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.Logger;

public class VMTranslator {
    public static void main(String... args) {
        Logger logger = Logger.getLogger(VMTranslator.class.getName());
        try {
            String sourceFileName = args[0];
            logger.info("sourceFileName" + sourceFileName);
            String outputFileName = "";
            if(sourceFileName.endsWith(".vm")) {
                outputFileName = sourceFileName.substring(0, sourceFileName.length()-3) + ".asm";
            }else{
                outputFileName = sourceFileName + ".asm";
            }
            logger.info("outputFileName" + outputFileName);
            File inputSource = new File(sourceFileName);
            Parser parser;
            CodeWriter writer;
            if(inputSource.exists()) {
                //Check if the argument is a directory
                if(inputSource.isDirectory()) {
                    String[] sourceFiles = inputSource.list((d, s) -> {
                        return s.toLowerCase().endsWith(".vm");
                    });
                    writer = new CodeWriter(outputFileName);
                    for(int i = 0; i < sourceFiles.length; i++) {
                        logger.info("sourceFiles[" + i + "]:" + sourceFiles[i]);
                        parser = new Parser(inputSource + "/" + sourceFiles[i]);
                        writer.setFileName(sourceFiles[i]);
                        //
                        while(parser.hasMoreCommands()) {
                            parser.advance();
                            if(parser.commandType() == CommandType.ARITHMETIC) {
                                writer.writeArithmetic(parser.arg1());
                            }else if(parser.commandType() == CommandType.PUSH) {
                                writer.writePushPop("push", parser.arg1(), parser.arg2());
                            }else if(parser.commandType() == CommandType.POP) {
                                writer.writePushPop("pop", parser.arg1(), parser.arg2());
                            }else if(parser.commandType() == CommandType.LABEL) {
                                writer.writeLabel(parser.arg1());
                            }else if(parser.commandType() == CommandType.GOTO) {
                                writer.writeGoto(parser.arg1());
                            }else if(parser.commandType() == CommandType.IF) {
                                writer.writeIf(parser.arg1());
                            }else if(parser.commandType() == CommandType.FUNCTION) {
                                writer.writeFunction(parser.arg1(), parser.arg2());
                            }else if(parser.commandType() == CommandType.RETURN) {
                                writer.writeReturn();
                            }else if(parser.commandType() == CommandType.CALL) {
                                writer.writeCall(parser.arg1(), parser.arg2());
                            }
                        }
                        //
                    }
                    writer.close();
                }else {
                    parser = new Parser(sourceFileName);
                    writer = new CodeWriter(outputFileName);
                    writer.setFileName(sourceFileName);
                    //
                    while(parser.hasMoreCommands()) {
                        parser.advance();
                        if(parser.commandType() == CommandType.ARITHMETIC) {
                            writer.writeArithmetic(parser.arg1());
                        }else if(parser.commandType() == CommandType.PUSH) {
                            writer.writePushPop("push", parser.arg1(), parser.arg2());
                        }else if(parser.commandType() == CommandType.POP) {
                            writer.writePushPop("pop", parser.arg1(), parser.arg2());
                        }else if(parser.commandType() == CommandType.LABEL) {
                            writer.writeLabel(parser.arg1());
                        }else if(parser.commandType() == CommandType.GOTO) {
                            writer.writeGoto(parser.arg1());
                        }else if(parser.commandType() == CommandType.IF) {
                            writer.writeIf(parser.arg1());
                        }else if(parser.commandType() == CommandType.FUNCTION) {
                            writer.writeFunction(parser.arg1(), parser.arg2());
                        }else if(parser.commandType() == CommandType.RETURN) {
                            writer.writeReturn();
                        }else if(parser.commandType() == CommandType.CALL) {
                            writer.writeCall(parser.arg1(), parser.arg2());
                        }
                    }
                    writer.close();
                    //
                }
            }else {
                throw new IllegalArgumentException("File: " + sourceFileName + " does not exist.");
            }

        } catch (IndexOutOfBoundsException e) {
            logger.info("Usage: VMTranslator <SOURCE_FILE>");
        } catch (IllegalArgumentException iae) {
            logger.info(iae.getMessage());
        } catch (IOException ioe) {
            logger.info("File could not be created");
        }
    }
}
