"""
This Python file contains the implementation of a chess game using the Tkinter library for the graphical user interface.
It includes various classes representing chess pieces (e.g., King, Queen, Rook, Bishop, Knight, Pawn), each with their
own movement logic. The `Board` class manages the state of the game, including piece positions, valid moves, and special
rules such as castling, stalemate, and checkmate detection. The code also features a Perft test function for debugging
and testing the move generation logic.
"""

import tkinter as tk



class BasePiece:
    def __init__(self, color, x_location, y_location) -> None:
        if color == "black":
            self.color = "black"
        elif color == "white":
            self.color = "white"



        self.x_location = x_location
        self.y_location = y_location
        self.counter = 0

    def move(self, x, y, increment_counter=True):
        if increment_counter:
            self.counter += 1

        self.x_location = x
        self.y_location = y


class Pawn(BasePiece):

    def valid_moves(self, x, y, matris):
        if self.x_location == x and self.y_location == y:
            return False

        if self.color == "white":
            if self.x_location == x:
                if self.y_location - y == 1 and matris[y][x] is None:
                    if y == 0:
                        self.__class__ = Queen
                        return True
                    return True

                if self.y_location == 6 and self.y_location - y == 2 and matris[y][x] is None and matris[y + 1][
                    x] is None:
                    return True

            elif abs(x - self.x_location) == 1 and self.y_location - y == 1:
                if matris[y][x] is not None and matris[y][x].color == "black":
                    if y == 0:
                        self.__class__ = Queen
                        return True
                    return True
                elif self.y_location == 3 and matris[self.y_location][x] is not None and isinstance(
                        matris[self.y_location][x], Pawn) and matris[self.y_location][x].color == "black" and \
                        matris[self.y_location][x].counter == 1:
                    return True

            return False

        elif self.color == "black":
            if self.x_location == x:
                if y - self.y_location == 1 and matris[y][x] is None:
                    if y == 7:
                        self.__class__ = Queen
                        return True
                    return True
                if self.y_location == 1 and y - self.y_location == 2 and matris[y][x] is None and matris[y - 1][
                    x] is None:
                    return True
            elif abs(x - self.x_location) == 1 and y - self.y_location == 1:
                if matris[y][x] is not None and matris[y][x].color == "white":
                    if y == 7:
                        self.__class__ = Queen
                        return True
                    return True
                elif self.y_location == 4 and matris[self.y_location][x] is not None and isinstance(
                        matris[self.y_location][x], Pawn) and matris[self.y_location][x].color == "white" and \
                        matris[self.y_location][x].counter == 1:
                    return True

            return False
        return False


class Rook(BasePiece):
    def move(self, x, y, increment_counter=True):

        if increment_counter:
            self.counter += 1

        self.x_location = x
        self.y_location = y

        print("kalenin countırı", self.counter)

    def valid_moves(self, x, y, matris):

        if self.x_location == x and self.y_location == y:
            return False
        if (self.x_location == x):
            if (y - self.y_location) > 0:
                for i in range(y - self.y_location):
                    i = i + 1

                    if matris[i + self.y_location][x] != None:
                        if matris[i + self.y_location][x].color != self.color and i < ((y - self.y_location)):
                            return False
                        if matris[i + self.y_location][x].color == self.color:
                            return False
                return True
            else:
                for i in range(-(y - self.y_location)):
                    i = i + 1

                    if matris[-i + self.y_location][x] != None:
                        if matris[-i + self.y_location][x].color != self.color and i < (-(y - self.y_location)):
                            return False
                        if matris[-i + self.y_location][x].color == self.color:
                            return False

                return True
        elif (self.y_location == y):
            if (x - self.x_location) > 0:
                for i in range(x - self.x_location):
                    i = i + 1

                    if matris[y][i + self.x_location] != None:
                        if matris[y][i + self.x_location].color != self.color and i < (x - self.x_location):
                            return False
                        if matris[y][i + self.x_location].color == self.color:
                            return False
                return True
            else:
                for i in range(-(x - self.x_location)):
                    i = i + 1

                    if matris[y][-i + self.x_location] != None:
                        if matris[y][-i + self.x_location].color != self.color and i < (-(x - self.x_location)):
                            return False
                        if matris[y][-i + self.x_location].color == self.color:
                            return False

                return True


class Queen(BasePiece):
    def valid_moves(self, x, y, matris):
        if self.x_location == x and self.y_location == y:
            return False

        if (self.x_location == x):
            if (y - self.y_location) > 0:
                for i in range(y - self.y_location):
                    i = i + 1

                    if matris[i + self.y_location][x] != None:
                        if matris[i + self.y_location][x].color != self.color and i < ((y - self.y_location)):
                            return False
                        if matris[i + self.y_location][x].color == self.color:
                            return False
                return True
            else:
                for i in range(-(y - self.y_location)):
                    i = i + 1

                    if matris[-i + self.y_location][x] != None:
                        if matris[-i + self.y_location][x].color != self.color and i < (-(y - self.y_location)):
                            return False
                        if matris[-i + self.y_location][x].color == self.color:
                            return False

                return True
        elif (self.y_location == y):
            if (x - self.x_location) > 0:
                for i in range(x - self.x_location):
                    i = i + 1

                    if matris[y][i + self.x_location] != None:
                        if matris[y][i + self.x_location].color != self.color and i < (x - self.x_location):
                            return False
                        if matris[y][i + self.x_location].color == self.color:
                            return False
                return True
            else:
                for i in range(-(x - self.x_location)):
                    i = i + 1

                    if matris[y][-i + self.x_location] != None:
                        if matris[y][-i + self.x_location].color != self.color and i < (-(x - self.x_location)):
                            return False
                        if matris[y][-i + self.x_location].color == self.color:
                            return False

                return True
        else:
            if abs(x - self.x_location) == abs(y - self.y_location):
                x_direction = 1 if x > self.x_location else -1
                y_direction = 1 if y > self.y_location else -1

                for i in range(0, abs(x - self.x_location)):
                    i = i + 1
                    current_x = self.x_location + i * x_direction
                    current_y = self.y_location + i * y_direction

                    if matris[current_y][current_x] != None:
                        if matris[current_y][current_x].color != self.color and i < abs(x - self.x_location):
                            return False
                        if matris[current_y][current_x].color == self.color:
                            return False

                return True
            return False


class King(BasePiece):

    def valid_moves(self, x, y, matris):
        if self.x_location == x and self.y_location == y:
            return False

        if abs(x - self.x_location) <= 1 and abs(y - self.y_location) <= 1:
            if matris[y][x] is not None and matris[y][x].color == self.color:
                return False

            original_x, original_y = self.x_location, self.y_location
            original_piece = matris[y][x]

            self.x_location, self.y_location = x, y
            matris[original_y][original_x] = None
            matris[y][x] = self

            is_in_check = self.is_under_attack(x, y, matris)

            self.x_location, self.y_location = original_x, original_y
            matris[y][x] = original_piece
            matris[original_y][original_x] = self

            return not is_in_check

        if (self.counter == 0 and self.color == "black" and
                y == 7 and x == 2 and
                matris[7][1] == None and matris[7][2] == None and matris[7][3] == None and
                isinstance(matris[7][0], Rook) and matris[7][0].counter == 0):

            if (not self.is_under_attack(1, 7, matris) and
                    not self.is_under_attack(2, 7, matris) and
                    not self.is_under_attack(3, 7, matris)):
                self.move(2, 7)
                matris[7][4] = None
                matris[7][2] = self

                rook = matris[7][0]
                rook.move(3, 7)
                matris[7][0] = None
                matris[7][3] = rook
                return "rok"

        if (self.counter == 0 and self.color == "white" and
                y == 7 and x == 6 and
                matris[7][5] is None and matris[7][6] is None and
                isinstance(matris[7][7], Rook) and matris[7][7].counter == 0):

            if (not self.is_under_attack(4, 7, matris) and
                    not self.is_under_attack(5, 7, matris) and
                    not self.is_under_attack(6, 7, matris)):
                self.move(6, 7)
                matris[7][4] = None
                matris[7][6] = self

                rook = matris[7][7]
                rook.move(5, 7, increment_counter=False)
                matris[7][7] = None
                matris[7][5] = rook
                return "rok"

        if (self.counter == 0 and self.color == "black" and
                y == 0 and x == 2 and
                matris[0][1] == None and matris[0][2] == None and matris[0][3] == None and
                isinstance(matris[0][0], Rook) and matris[0][0].counter == 0):

            if (not self.is_under_attack(1, 0, matris) and
                    not self.is_under_attack(2, 0, matris) and
                    not self.is_under_attack(3, 0, matris)):
                self.move(2, 0)
                matris[0][4] = None
                matris[0][2] = self

                rook = matris[0][0]
                rook.move(3, 0, increment_counter=False)
                matris[0][0] = None
                matris[0][3] = rook
                return "rok"

        if (self.counter == 0 and self.color == "black" and
                y == 0 and x == 6 and
                matris[0][5] is None and matris[0][6] is None and
                isinstance(matris[0][7], Rook) and matris[0][7].counter == 0):

            if (not self.is_under_attack(4, 0, matris) and
                    not self.is_under_attack(5, 0, matris) and
                    not self.is_under_attack(6, 0, matris)):
                self.move(6, 0)
                matris[0][4] = None
                matris[0][6] = self

                rook = matris[0][7]
                rook.move(5, 0, increment_counter=False)
                matris[0][7] = None
                matris[0][5] = rook
                return "rok"
        return False

    def is_under_attack(self, x, y, matris):
        for row in range(8):
            for col in range(8):
                piece = matris[row][col]
                if piece is not None and piece.color != self.color:
                    if piece.valid_moves(x, y, matris):

                        return True
        return False


class Bishop(BasePiece):
    def valid_moves(self, x, y, matris):
        if self.x_location == x and self.y_location == y:
            return False

        if abs(x - self.x_location) == abs(y - self.y_location):
            x_direction = 1 if x > self.x_location else -1
            y_direction = 1 if y > self.y_location else -1

            for i in range(0, abs(x - self.x_location)):
                i = i + 1
                current_x = self.x_location + i * x_direction
                current_y = self.y_location + i * y_direction

                if matris[current_y][current_x] != None:
                    if matris[current_y][current_x].color != self.color and i < abs(x - self.x_location):
                        return False
                    if matris[current_y][current_x].color == self.color:
                        return False

            return True


class Knight(BasePiece):



    def valid_moves(self, x, y, matris):
        if self.x_location == x and self.y_location == y:
            return False
        if abs(x - self.x_location) == 1 and (abs(y - self.y_location) == 2):
            if matris[y][x] != None:
                if matris[y][x].color != self.color:
                    return True
                else:
                    return False
            else:
                return True
        elif (abs(x - self.x_location) == 2 and (abs(y - self.y_location) == 1)):
            if matris[y][x] != None:
                if matris[y][x].color != self.color:
                    return True
                else:
                    return False
            else:
                return True
        else:
            return False

matris_positions = []
class Board(tk.Tk):
    def __init__(self):
        self.bot = None
        super().__init__()
        self.title("Chess Game")
        self.geometry("400x400")
        self.move_counter = 0
        self.matris = [[None for _ in range(8)] for _ in range(8)]
        self.white_turn = True
        self.selected_piece = None
        self.start_board()
        self.draw_board()

    def start_board(self):
        for i in range(8):
            self.matris[1][i] = Pawn("black", i, 1)
            self.matris[6][i] = Pawn("white", i, 6)
        self.matris[0][0] = Rook("black", 0, 0)
        self.matris[0][7] = Rook("black", 7, 0)
        self.matris[7][0] = Rook("white", 0, 7)
        self.matris[7][7] = Rook("white", 7, 7)
        self.matris[0][1] = Knight("black", 1, 0)
        self.matris[0][6] = Knight("black", 6, 0)
        self.matris[7][1] = Knight("white", 1, 7)
        self.matris[7][6] = Knight("white", 6, 7)
        self.matris[0][2] = Bishop("black", 2, 0)
        self.matris[0][5] = Bishop("black", 5, 0)
        self.matris[7][2] = Bishop("white", 2, 7)
        self.matris[7][5] = Bishop("white", 5, 7)
        self.matris[0][4] = King("black", 4, 0)
        self.matris[0][3] = Queen("black", 3, 0)
        self.matris[7][4] = King("white", 4, 7)
        self.matris[7][3] = Queen("white", 3, 7)



    def on_click(self, row, col):
        if self.selected_piece is None:
            piece = self.matris[row][col]
            if piece and (
                    (self.white_turn and piece.color == "white") or (
                    not self.white_turn and piece.color == "black")):
                self.selected_piece = (piece, row, col)
        else:
            piece, old_row, old_col = self.selected_piece
            move_result = piece.valid_moves(col, row, self.matris)

            if move_result == True:
                if not self.is_king_in_check_after_move(piece, col, row):
                    self.matris[old_row][old_col] = None
                    self.matris[row][col] = piece


                    if isinstance(piece, Pawn) or self.matris[row][col] is not None:
                        self.fifty_move_counter = 0
                    else:
                        self.fifty_move_counter += 1

                    piece.move(col, row)
                    self.draw_board()
                    self.white_turn = not self.white_turn
                    self.move_counter += 1


                    if self.is_fifty_move_rule():

                        self.selected_piece = None
                        return


                    if self.is_checkmate("white" if self.white_turn else "black"):
                        print(f"{'Black' if self.white_turn else 'White'} Checkmated! Game over.")
                        self.selected_piece = None
                        return

                    if self.is_draw():
                        print("The game is tied! Stalemate or other tie situation.")
                        self.selected_piece = None
                        return
            elif move_result == "rok":

                self.draw_board()
                self.white_turn = not self.white_turn

            if not self.white_turn:
                self.after(500, lambda: self.bot.move())
                self.white_turn = not self.white_turn



            self.selected_piece = None

    def is_fifty_move_rule(self):
        return self.fifty_move_counter >= 50

    def get_board_position(self):
        position = ""
        for row in self.matris:
            for piece in row:
                if piece is None:
                    position += "."
                else:
                    position += piece.__class__.__name__[0] + piece.color[0]
        return position

    def is_king_in_check(self, color):
        for row in self.matris:
            for piece in row:
                if isinstance(piece, King) and piece.color == color:
                    return piece.is_under_attack(piece.x_location, piece.y_location, self.matris)
        return False

    def is_king_in_check_after_move(self, piece, x, y):
        original_x, original_y = piece.x_location, piece.y_location
        original_piece = self.matris[y][x]
        self.matris[original_y][original_x] = None
        self.matris[y][x] = piece
        piece.x_location, piece.y_location = x, y
        king_in_check = self.is_king_in_check(piece.color)
        piece.x_location, piece.y_location = original_x, original_y
        self.matris[y][x] = original_piece
        self.matris[original_y][original_x] = piece
        return king_in_check

    def is_checkmate(self, color):
        for row in range(8):
            for col in range(8):
                piece = self.matris[row][col]
                if piece is not None and piece.color == color:
                    for target_row in range(8):
                        for target_col in range(8):
                            if piece.valid_moves(target_col, target_row, self.matris):
                                if not self.is_king_in_check_after_move(piece, target_col, target_row):
                                    return False
        return True

    def is_stalemate(self, color):
        for row in range(8):
            for col in range(8):
                piece = self.matris[row][col]
                if piece is not None and piece.color == color:
                    for target_row in range(8):
                        for target_col in range(8):
                            if piece.valid_moves(target_col, target_row, self.matris):
                                if not self.is_king_in_check_after_move(piece, target_col, target_row):
                                    return False
        if not self.is_king_in_check(color):
            return True
        return False



    def is_threefold_repetition(self):
        current_position = self.get_board_position()
        count = matris_positions.count(current_position)
        return count >= 3

    def is_insufficient_material(self):
        white_pieces = []
        black_pieces = []
        for row in self.matris:
            for piece in row:
                if piece is not None:
                    if piece.color == "white":
                        white_pieces.append(piece)
                    else:
                        black_pieces.append(piece)
        if len(white_pieces) == 1 and len(black_pieces) == 1:
            return True
        if (len(white_pieces) == 2 and all(isinstance(p, (King, Knight, Bishop)) for p in white_pieces) and
            len(black_pieces) == 1) or \
                (len(black_pieces) == 2 and all(isinstance(p, (King, Knight, Bishop)) for p in black_pieces) and
                 len(white_pieces) == 1):
            return True

    def is_draw(self):
        return (self.is_stalemate("white" if self.white_turn else "black") or
                self.is_insufficient_material() or
                self.is_threefold_repetition()
                )





    def draw_board(self):
        print("Matrices:",matris_positions)
        for row in range(8):
            for col in range(8):
                piece = self.matris[row][col]
                if piece:
                    if isinstance(piece, Rook):
                        btn = tk.Button(self, text="Rook", fg=piece.color,
                                        command=lambda r=row, c=col: self.on_click(r, c),
                                        bg="green" if (row + col) % 2 == 0 else "gray")
                    elif isinstance(piece, Knight):
                        btn = tk.Button(self, text="Knight", fg=piece.color,
                                        command=lambda r=row, c=col: self.on_click(r, c),
                                        bg="green" if (row + col) % 2 == 0 else "gray")
                    elif isinstance(piece, Bishop):
                        btn = tk.Button(self, text="Bishop", fg=piece.color,
                                        command=lambda r=row, c=col: self.on_click(r, c),
                                        bg="green" if (row + col) % 2 == 0 else "gray")
                    elif isinstance(piece, Queen):
                        btn = tk.Button(self, text="Queen", fg=piece.color,
                                        command=lambda r=row, c=col: self.on_click(r, c),
                                        bg="green" if (row + col) % 2 == 0 else "gray")
                    elif isinstance(piece, Pawn):
                        btn = tk.Button(self, text="Pawn", fg=piece.color,
                                        command=lambda r=row, c=col: self.on_click(r, c),
                                        bg="green" if (row + col) % 2 == 0 else "gray")
                    elif isinstance(piece, King):
                        btn = tk.Button(self, text="King", fg=piece.color,
                                        command=lambda r=row, c=col: self.on_click(r, c),
                                        bg="green" if (row + col) % 2 == 0 else "gray")
                else:
                    btn = tk.Button(self, command=lambda r=row, c=col: self.on_click(r, c),
                                    bg="green" if (row + col) % 2 == 0 else "gray")

                btn.grid(row=row, column=col, sticky="nsew")

        for i in range(8):
            self.grid_rowconfigure(i, weight=1)
            self.grid_columnconfigure(i, weight=1)

    def perft(self, depth, white_turn):
        if depth == 0:
            return 1
        nodes = 0
        for row in range(8):
            for col in range(8):
                piece = self.matris[row][col]
                if piece is not None and (
                        (white_turn and piece.color == "white") or (not white_turn and piece.color == "black")):
                    for target_row in range(8):
                        for target_col in range(8):
                            if piece.valid_moves(target_col, target_row, self.matris):
                                original_piece = self.matris[target_row][target_col]

                                # Hamleyi yap
                                self.matris[row][col] = None
                                self.matris[target_row][target_col] = piece
                                piece.move(target_col, target_row)

                                # Eğer şah çekiyorsa, geçersiz hamle sayılmalı
                                if not self.is_king_in_check(piece.color):
                                    print(
                                        f"Depth {depth}: {piece.__class__.__name__} from ({row},{col}) to ({target_row},{target_col})")
                                    nodes += self.perft(depth - 1, not white_turn)

                                # Hamleyi geri al
                                self.matris[target_row][target_col] = original_piece
                                self.matris[row][col] = piece
                                piece.move(col, row)
        return nodes

    def run_perft_test(self, depth):
        print(f"Running Perft test to depth {depth}...")
        node_count = self.perft(depth, self.white_turn)
        print(f"Perft test result at depth {depth}: {node_count} nodes")


