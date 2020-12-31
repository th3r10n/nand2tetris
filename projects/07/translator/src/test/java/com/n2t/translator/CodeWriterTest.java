package com.n2t.translator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeWriterTest {

    @Test
    void testIsConstantPushedToStack() {
        CodeWriter writer = new CodeWriter("SimpleAdd.asm");
        writer.writePushPop("push", "constant", 7);
        writer.close();
    }

    @Test
    void testIsAddCommandWritten() {
        CodeWriter writer = new CodeWriter("Add.asm");
        writer.writeArithmetic("add");
        writer.close();
    }

    @Test
    void testIsSubCommandWritten() {
        CodeWriter writer = new CodeWriter("Sub.asm");
        writer.writeArithmetic("sub");
        writer.close();
    }

    @Test
    void testIsAndCommandWritten() {
        CodeWriter writer = new CodeWriter("And.asm");
        writer.writeArithmetic("and");
        writer.close();
    }

    @Test
    void testIsOrCommandWritten() {
        CodeWriter writer = new CodeWriter("Or.asm");
        writer.writeArithmetic("or");
        writer.close();
    }

    @Test
    void testIsNotCommandWritten() {
        CodeWriter writer = new CodeWriter("Not.asm");
        writer.writeArithmetic("not");
        writer.close();
    }

    @Test
    void testIsEqCommandWritten() {
        CodeWriter writer = new CodeWriter("Eq.asm");
        writer.writeArithmetic("eq");
        writer.writeArithmetic("eq");
        writer.close();
    }

    @Test
    void testIsGtCommandWritten() {
        CodeWriter writer = new CodeWriter("Gt.asm");
        writer.writeArithmetic("gt");
        writer.close();
    }

    @Test
    void testWritePushLocal() {
        CodeWriter writer = new CodeWriter("PushLocal.asm");
        writer.writePushPop("push", "local", 4);
        writer.close();
    }

    @Test
    void testWritePushArgument() {
        CodeWriter writer = new CodeWriter("PushArgument.asm");
        writer.writePushPop("push", "argument", 5);
        writer.close();
    }

    @Test
    void testWritePushPointer0() {
        CodeWriter writer = new CodeWriter("PushPointer.asm");
        writer.writePushPop("push", "pointer", 0);
        writer.close();
    }

    @Test
    void testWritePushPointer1() {
        CodeWriter writer = new CodeWriter("PushPointer1.asm");
        writer.writePushPop("push", "pointer", 1);
        writer.close();
    }

    @Test
    void testWritePopPointer0() {
        CodeWriter writer = new CodeWriter("PopPointer.asm");
        writer.writePushPop("pop", "pointer", 0);
        writer.close();
    }

    @Test
    void testWritePushTemp() {
        CodeWriter writer = new CodeWriter("PushTemp.asm");
        writer.writePushPop("push", "temp", 3);
        writer.close();
    }

    @Test
    void testWritePopTemp() {
        CodeWriter writer = new CodeWriter("PopTemp.asm");
        writer.writePushPop("pop", "temp", 2);
        writer.close();
    }
}