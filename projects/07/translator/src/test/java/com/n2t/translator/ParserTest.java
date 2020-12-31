package com.n2t.translator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    static Parser moreCommands;
    static Parser noMoreCommands;
    static Parser onlyComments;

    @BeforeAll
    public static void init() {
        try {
            moreCommands = new Parser("Program.vm");
            noMoreCommands = new Parser("ProgramEmpty.vm");
            onlyComments = new Parser("ProgramNoCommands.vm");
        }catch(FileNotFoundException fne) {
            System.out.println("File Not Found");
        }
    }
    @Test
    void testHasMoreCommands() {
        assertTrue(moreCommands.hasMoreCommands());
    }

    @Test
    void testHasNoMoreCommands() {
        assertFalse(noMoreCommands.hasMoreCommands());
    }

    @Test
    void testIgnoreComments() {
        assertFalse(onlyComments.hasMoreCommands());
    }

    @Test
    void testAdvance() {
        moreCommands.advance();
        moreCommands.advance();
        moreCommands.advance();
        assertFalse(moreCommands.hasMoreCommands());
    }

    @Test
    void testArithmeticCommand() {
        moreCommands.advance();
        moreCommands.advance();
        moreCommands.advance();
        assertTrue(moreCommands.commandType() == CommandType.ARITHMETIC);
    }

    @Test
    void testPushCommand() {
        moreCommands.advance();
        assertTrue(moreCommands.commandType() == CommandType.PUSH);
    }
}