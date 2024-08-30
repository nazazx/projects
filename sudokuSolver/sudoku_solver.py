import sys


#This function converts sudoku to rows of sudoku
def sudoku_rows(sudoku):
    rows = sudoku.split("\n")
    liste = []
    for i in rows:
        a = i.split()
        liste.append([int(i) for i in a])
    return liste


#This function converts rows to columns of sudoku
def sudoku_columns(rows):
    sudoku_columns = []
    for i in range(9):
        liste = []
        for row in rows:
            a = row[i]
            liste.append(int(a))
        sudoku_columns.append(liste)
    return sudoku_columns




#this function allows us to make squares list of sudoku
def sudoku_squares(rows):
    squares = []
    for i in range(0, 9, 3):
        for j in range(0, 9, 3):
            grup = []
            for a in rows[i:i+3]:
                grup.extend(a[j:j+3])
            squares.append(grup)
    return squares



#This function helps us find the square we want
def find_square(row, column):
    row_indeks = (row // 3)
    column_indeks = (column // 3)
    return (row_indeks * 3) + (column_indeks)





#This function controls the number to set a value
def control(rows,columns,squares,number, row, column):
    columns = sudoku_columns(rows)
    squares = sudoku_squares(rows)

    if number in rows[row]:
        return False

    if number in columns[column]:
        return False

    if number in squares[find_square(row, column)]:
        return False

    return True




#This function determines possible values 
def possible_value(rows,columns,squares,row, column):
    values = set()
    for x in range(1, 10):
        if control(rows,columns,squares, x, row, column):
            values.add(x)
    return values



# this funciton allows to print converting rows to sudoku
def printing(rows):
    for row in rows:
        sudoku = " ".join(str(cell) if cell != 0 else '0' for cell in row)
        print(sudoku)




# This function allows to solve sudoku that is given
def step_solution(rows,columns,squares,output, n=1):
    for i in range(9):
        for j in range(9):
            if rows[i][j] == 0:
                possible_values = possible_value(rows,columns,squares, i,j)
                if len(possible_values) == 1:
                    rows[i][j] = possible_values.pop()

                    original_stdout = sys.stdout
                    with open(output, 'a') as f:
                        sys.stdout = f
                        print("------------------")
                        a = f"Step {n} - {rows[i][j]} @ R{i+1}C{j+1} "
                        
                        print(a)
                        print("------------------")
                        printing(rows)

                    sys.stdout = original_stdout
                    step_solution(rows,columns,squares,output, n+1)


def main():
    input1 = sys.argv[1]
    with open(input1, "r") as dosya:
        sudoku = dosya.read()

    rows = sudoku_rows(sudoku)
    columns=sudoku_columns(rows)
    squares=sudoku_squares(rows)
    
    
    output = sys.argv[2]
    step_solution(rows,columns,squares,output)
    with open(output, 'a') as f:
        sys.stdout = f
        print("------------------")
        

if __name__ == "__main__":
    main()

    
    







        
    
   




  
    


    
                         
          






   







    
    
    
    
    


    




        
        
            
    

    
    
        
        

