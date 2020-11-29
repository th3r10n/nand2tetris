class Parser:
    def __init__(self, source_file):
        """
        docstring
        """
        self.lines = self.set_lines(source_file)
        self.current_command = ''
        

    def set_lines(self, source_file):
        """
        docstring
        """
        lines = []
        with open(source_file, 'r') as file_object:
            for line in file_object:
                line = line.strip()
                if line and not line.startswith('//'):
                    #print(line)
                    lines.append(line)
        return lines

    def has_more_commands(self):
        """
        docstring
        """
        #print(len(self.lines))
        if(len(self.lines) > 0):
            return True
        else:
            return False

    def advance(self):
        """
        docstring
        """
        self.current_command = self.lines.pop(0)    

    def command_type(self):
        """
        docstring
        """
        if self.current_command.startswith('@'):
            return 'A_COMMAND'
        elif self.current_command.startswith('(') and self.current_command.endswith(')'):
            return 'L_COMMAND'
        else:
            return 'C_COMMAND'

    def symbol(self):
        """
        docstring
        """
        if self.current_command.startswith('@'):
            return self.current_command[1:]
        else:
            return self.current_command[1:-1]

    def dest(self):
        """
        docstring
        """
        if '=' in self.current_command:
            return self.current_command.split('=')[0]
        else:
            return 'null'    
    
    def comp(self):
        """
        docstring
        """
        computation = ''
        if '=' in self.current_command:
            computation = self.current_command.split('=')[1]
            if ';' in computation:
                return computation.split(';')[0]
            else:
                return computation
        elif ';' in self.current_command:
            computation = self.current_command.split(';')[0]
            return computation
        else:
            return self.current_command


    def jump(self):
        """
        docstring
        """
        if ';' in self.current_command:
            return self.current_command.split(';')[1]
        else:
            return 'null'