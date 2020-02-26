// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

// SCREEN = RAM[16384]
// KBD = RAM[24576]

@i
M = 0 // i = 0

(LOOP)
@i
D = M // D = i
@8192
D = A - D // 8192 - i
@END
D;JLE

@i
D = M // D = i
@SCREEN
A = A + D // A + i
M = 1
@i
M = M + 1 // i = i + 1 
@LOOP
0;JMP

(END)
@END
0;JMP
