#!/usr/local/bin/python3

# import os
import sys
import code_trans
from parser import Parser
from symbol_table import SymbolTable

# if __name__ == "__main__":
#     main()
#print(sys.argv[1])
def file_name():
    """
    docstring
    """
    file_name = ''
    
    try:
        file_name = sys.argv[1]
    except IndexError:
        print("Usage: assembler.py <source file>")
        exit()
    else:
        if not file_name.endswith('.asm'):
            print('Input file: ' + file_name + ' is not an assembler file')
            exit()
        else:
            if '/' in file_name:
                file_name = file_name.split('/')[-1]
            file_name = file_name.split('.')[0] + '.hack'
            return file_name
        
output_file = file_name()

code_parser = Parser(sys.argv[1])

def binary_word(number):
    """
    docstring
    """
    a_instruction = '0000000000000000'
    bin_repr = bin(int(number))[2:]
    size = len(bin_repr)
    return a_instruction[:-size] + bin_repr

with open(output_file, 'w') as file_object:
    while(code_parser.has_more_commands()):
        code_parser.advance()
        word = ''
        if code_parser.command_type() == 'C_COMMAND':
            word = '111' + code_trans.comp(code_parser.comp()) + code_trans.dest(code_parser.dest()) + code_trans.jump(code_parser.jump()) + '\n'
            file_object.write(word)
        elif code_parser.command_type() == 'A_COMMAND':
            word = binary_word(code_parser.symbol()) + '\n'
            file_object.write(word)
        elif code_parser.command_type() == 'L_COMMAND':
            pass
