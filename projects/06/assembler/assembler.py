#!/usr/local/bin/python3

# import os
import sys
import code_trans
from parser import Parser
from symbol_table import SymbolTable

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

def binary_word(number):
    """
    docstring
    """
    a_instruction = '0000000000000000'
    bin_repr = bin(int(number))[2:]
    size = len(bin_repr)
    return a_instruction[:-size] + bin_repr

def is_integer(symbol):
    """
    docstring
    """
    try:
        int(symbol)
    except ValueError:
        return False
    else:
        return True

def first_pass():
    code_parser = Parser(sys.argv[1]) 
    program_counter = 0
    while(code_parser.has_more_commands()):
        code_parser.advance()
        if code_parser.command_type() == 'C_COMMAND':
            program_counter = program_counter + 1
        elif code_parser.command_type() == 'A_COMMAND':
            program_counter = program_counter + 1
        elif code_parser.command_type() == 'L_COMMAND':
            if not SymbolTable.contains(code_parser.symbol()):
                SymbolTable.add_entry(code_parser.symbol(), binary_word(program_counter))

def second_pass():
    """
    docstring
    """
    code_parser = Parser(sys.argv[1])
    memory_counter = 16
    with open(output_file, 'w') as file_object:
        while(code_parser.has_more_commands()):
            code_parser.advance()
            word = ''
            if code_parser.command_type() == 'C_COMMAND':
                word = '111' + code_trans.comp(code_parser.comp()) + code_trans.dest(code_parser.dest()) + code_trans.jump(code_parser.jump()) + '\n'
            elif code_parser.command_type() == 'A_COMMAND':
                if is_integer(code_parser.symbol()):
                    word = binary_word(code_parser.symbol()) + '\n'
                elif SymbolTable.contains(code_parser.symbol()):
                    word = SymbolTable.get_address(code_parser.symbol()) + '\n'
                elif not SymbolTable.contains(code_parser.symbol()):
                    SymbolTable.add_entry(code_parser.symbol(), binary_word(memory_counter))
                    word = binary_word(memory_counter) + '\n'
                    memory_counter = memory_counter + 1
            elif code_parser.command_type() == 'L_COMMAND':
                pass
            file_object.write(word)
    

first_pass()

second_pass()

