package com.n2t.translator;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class VMTranslator {
    public static void main(String... args) {
        Logger logger = Logger.getLogger(VMTranslator.class.getName());
        try {
            String sourceFileName = args[0];
            if(!sourceFileName.endsWith(".vm")) {
                throw new IllegalArgumentException(sourceFileName + " is not valid VM file.");
            }
            logger.info("sourceFileName" + sourceFileName);
            String outputFileName = sourceFileName.substring(0, sourceFileName.length()-3) + ".asm";
            logger.info("outputFileName" + outputFileName);
            File inputSource = new File(sourceFileName);
            if(!inputSource.exists()) {
                throw new IllegalArgumentException("File: " + sourceFileName + " does not exist.");
            }

            Parser parser = new Parser(sourceFileName);
            CodeWriter writer = new CodeWriter(outputFileName);
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
                }else if(parser.commandType() == CommandType.IF) {
                    writer.writeIf(parser.arg1());
                }
            }
            writer.close();
        } catch (IndexOutOfBoundsException e) {
            logger.info("Usage: VMTranslator <SOURCE_FILE>");
        } catch (IllegalArgumentException iae) {
            logger.info(iae.getMessage());
        } catch (IOException ioe) {
            logger.info("File could not be created");
        }
    }
}
