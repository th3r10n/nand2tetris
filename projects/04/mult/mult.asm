// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.

// M[0] = R0
// M[1] = R1
// M2[2] is initialized to 0

@i
M = 1 // i = 1
@2 
M = 0 // R2 = 0
(LOOP)
@i
D = M // D = i
@1
D = M - D // R1 - i
@END
D;JLT
@0
D = M // D = R0
@2
M = M + D // R2 = R2 + R0
@i
M = M + 1 // i = i + 1
@LOOP
0;JMP // Goto LOOP
(END)
@END
0;JMP