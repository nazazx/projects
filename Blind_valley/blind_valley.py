import sys


def table_numbers(table):
    rows = table.split("\n")
    numbers = []
    for i in rows[:4]:
        a = i.split()
        numbers.append([int(i) for i in a])
    return numbers


def table_letters(table):
    rows = table.split("\n")
    letters= []
    for j in rows[4:]:
        b=j.split()
        letters.append(b)
    return letters


#Checking the number of b and h according to the numbers in the given input
def control1(letters, numbers):
    for row in range(len(letters)):
        for col in range(len(letters[0])):
            if numbers[0][row] != -1 and letters[row].count("H") != numbers[0][row]:
                return False
            if numbers[1][row] != -1 and letters[row].count("B") != numbers[1][row]:
                return False
            if numbers[2][col] != -1 and [letters[r][col] for r in range(len(letters))].count("H") != numbers[2][col]:
                return False
            if numbers[3][col] != -1 and [letters[r][col] for r in range(len(letters))].count("B") != numbers[3][col]:
                return False
    return True


#Same letters (B, H) cannot come together
def control2(letters,row,column):
    if letters[row][column] !="N":
        if row > 0 and letters[row][column] == letters[row-1][column]:
            return False
        if column > 0 and letters[row][column] == letters[row][column-1]:
            return False
        if row < len(letters)-1 and letters[row][column] == letters[row+1][column]:
            return False
        if column < len(letters[0])-1 and letters[row][column] == letters[row][column+1]:
            return False

    return True

def plan_control(table, letters, row, column):
    ori_letters = table_letters(table)
    cell = ori_letters[row][column]
   
    # Side by side control of L and R
    if cell == 'L' and column + 1 < len(letters[0]):
        if letters[row][column]=="B":
            if letters[row][column + 1]  in ["B","H","N"]:
                if letters[row][column + 1]!="H":
                    return False
        elif letters[row][column]=="H":
             if letters[row][column + 1]  in ["B","H","N"]:
                if letters[row][column + 1]!="B":
                    return False
        elif letters[row][column]=="N":
             if letters[row][column + 1]  in ["B","H","N"]:
                if letters[row][column + 1]!="N":
                    return False
    # check left of R
    if cell == 'R' and column > 0:
        if letters[row][column]=="B":
            if letters[row][column -1]  in ["B","H","N"]:
                if letters[row][column - 1]!="H":
                    return False
        elif letters[row][column]=="H":
             if letters[row][column -1]  in ["B","H","N"]:
                if letters[row][column - 1]!="B":
                    return False
        elif letters[row][column]=="N":
             if letters[row][column - 1]  in ["B","H","N"]:
                if letters[row][column -1]!="N":
                    return False
    # Control of U and D one by one
    if cell == 'U' and row + 1 < len(letters):
        if letters[row][column]=="B":
            if letters[row+1][column]  in ["B","H","N"]:
                if letters[row+1][column ]!="H":
                    return False
        elif letters[row][column]=="H":
             if letters[row+1][column]  in ["B","H","N"]:
                if letters[row+1][column]!="B":
                    return False
        elif letters[row][column]=="N":
             if letters[row+1][column ]  in ["B","H","N"]:
                if letters[row+1][column]!="N":
                    return False

    # check up of d
    if cell == 'D' and row > 0:
        if letters[row][column]=="B":
            if letters[row-1][column]!="H":
                return False
        elif letters[row][column]=="H":
            if letters[row-1][column]!="B":
                return False
        elif letters[row][column]=="N":
            if letters[row-1][column]!="N":
                return False
                
    return True   

#This function uses the backtracking method to solve the puzzle.
def solving(table,letters, numbers, row, column):
    if row == len(letters): 
        return control1(letters, numbers)
  
    if column == len(letters[0]):  # pass to other row
        return solving(table,letters, numbers, row + 1, 0)

    for value in ["H", "B", "N"]:  
            letters[row][column] = value
            if  control2(letters, row, column) and plan_control(table, letters, row, column):
                if solving(table,letters, numbers, row, column + 1):
                    return True
            letters[row][column] = ' '

    return False


def main():
    input_file_name = sys.argv[1]
    output_file_name=sys.argv[2]
    with open(input_file_name , "r") as dosya:
        table=dosya.read()
        numbers=table_numbers(table)
        letters=table_letters(table)
        if solving(table,letters, numbers, 0, 0):
            for row in letters:
                with open(output_file_name,"a") as dosya:
                    dosya.write(' '.join(row)+"\n")
        else:
             with open(output_file_name,"w") as dosya:
                        dosya.write("No solution!")
            
if __name__ == "__main__":
    main()     