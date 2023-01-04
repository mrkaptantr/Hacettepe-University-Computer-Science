# Go-Py Game
# 29.11.2019 - Friday

# Very Important Note: I also add these functions, which are not in PDF or piazza:
# (1) Round Draw: If no winners, code crash. But now when all inputs are filled by X and O and if there is no winners
# it will print a message like "Round draw no winners" and end game without error(s).
# (2) Size Of Board: If size of board is negative, 0, 1 or 2 code crash. But now it'll only print a error message and
# ask size of board again.
# (3) Colorful print functions, for example error messages are red.

# Also it works with Python 3.

print("""----------------------------------------------------------- 
                ---GoPY Board Game--- 
                       (v1.0)
\u001b[32m-What is GoPy Board Game?
\u001b[34mThis game is a board game for two players who take 
turns in putting black and white stones on the board. 
Of course Player 1 is [X] and Player 2 is [O].
\u001b[32m-What's Our Goal? How To Win? 
\u001b[34mEach players’ goal is to create an unbroken row of their 
letter horizontally, vertically, or diagonally. 
Good luck :) \u001b[32m
----------------------------------------------------------""")

SizeOfBoard, MoveNumber = 0, 0

def SizeOfBoardSelect():
    global SizeOfBoard
    SizeOfBoard = int(input("""\u001b[31mFirst of all, please enter a value of game size. \n For Example: If your input will be 3, game will be 3x3"""))
    if type(SizeOfBoard) != int:
        print("Size of board can only be integer, not float or string!")
        SizeOfBoardSelect()
    if SizeOfBoard < 3:
        print("\u001b[31mSize of board can not be less than 3!")
        SizeOfBoardSelect()

SizeOfBoardSelect()
matrix = []
count = 0
for i in range(SizeOfBoard):
    row = []
    for j in range(SizeOfBoard):
        if count < 10:
            row.append('  ' + str(count))
        elif count < 100:
            row.append(' ' + str(count))
        else:
            row.append('' + str(count))
        count += 1
    matrix.append(row)
for item in matrix:
    print(*item)

def Winner():
    global SizeOfBoard
    # (1) Winning Horizontally
    for item in matrix:
        if item == SizeOfBoard * ["  X"]:
            print("\u001b[34mWINNER: X - Congrulations!")
            exit()
        if item == SizeOfBoard * ["  O"]:
            print("\u001b[34mWINNER: O - Congrulations!")
            exit()

    # (2) Winning Vertically
    listhorizontal = []
    rez = [[matrix[j][i] for j in range(len(matrix))] for i in range(len(matrix[0]))]
    for m in rez:
        listhorizontal.append(m)
    for x in listhorizontal:
        if x == SizeOfBoard * ["  X"]:
            print("\u001b[34mWINNER: X - Congrulations!")
            exit()
    listhorizontaly = []
    rez = [[matrix[j][i] for j in range(len(matrix))] for i in range(len(matrix[0]))]
    for m in rez:
        listhorizontaly.append(m)
    for x in listhorizontaly:
        if x == SizeOfBoard * ["  O"]:
            print("\u001b[34mWINNER: O - Congrulations!")
            exit()

    # (3) Winning Diagonally (Left To Right)
    listdiaglr=[]
    for listselectx in range(SizeOfBoard):
        listdiaglr.append(matrix[listselectx][listselectx])
    if listdiaglr == SizeOfBoard * ["  X"]:
        print("\u001b[34mWINNER: X - Congrulations!")
        exit()
    listdiaglry=[]
    for listselectx in range(SizeOfBoard):
        listdiaglr.append(matrix[listselectx][listselectx])
    if listdiaglry == SizeOfBoard * ["  O"]:
        print("\u001b[34mWINNER: O - Congrulations!")
        exit()

    # (4) Winning Diagonally (Right To Left)
    listdiaglr = []
    reversedlist = list(reversed(matrix))
    for listselectx in range(SizeOfBoard):
        listdiaglr.append(reversedlist[listselectx][listselectx])
    if listdiaglr == SizeOfBoard * ["  X"]:
        print("\u001b[34mWINNER: X - Congrulations!")
        exit()
    listdiaglry = []
    reversedlist = list(reversed(matrix))
    for listselectx in range(SizeOfBoard):
        listdiaglry.append(reversedlist[listselectx][listselectx])
    if listdiaglr == SizeOfBoard * ["  O"]:
        print("\u001b[34mWINNER: O - Congrulations!")
        exit()

    # (5) Round Draw
    global MoveNumber
    if MoveNumber == (SizeOfBoard**2):
        print("Round Draw! No winner(s)!")
        exit()

def Player1Play():
    StartX = int(input("\u001b[32mPlayer 1's (X) Turn:"))
    listselect1 = StartX // SizeOfBoard
    listselect2 = StartX % SizeOfBoard

    if not StartX < (SizeOfBoard**2) and 0 < StartX:
        print("""\u001b[31mPlease enter a valid number... \n Valid Number: Numbers in game table, which aren't signed with X or O.""")
        Player2Play()
    if matrix[listselect1][listselect2] == "  X":
        print("\u001b[31mYou have made this choice before. Please try again.")
        Player2Play()
    if matrix[listselect1][listselect2] == "  O":
        print("\u001b[31mThe other player select this cell before. Please try again.")
        Player2Play()

    matrix[listselect1][listselect2] = "  X"
    global MoveNumber
    MoveNumber = MoveNumber + 1
    Winner()
    for item in matrix:
        print(*item)
    Player2Play()

def Player2Play():
    Start = int(input("\u001b[32mPlayer 2's (O) Turn:"))
    listselect1 = Start // SizeOfBoard
    listselect2 = Start % SizeOfBoard

    if not Start < (SizeOfBoard ** 2) and 0 < Start:
        print("""\u001b[31mPlease enter a valid number... \n Valid Number: Numbers in game table, which aren't signed with X or O.""")
        Player1Play()
    if matrix[listselect1][listselect2] == "  X":
        print("\u001b[31mThe other player select this cell before. Please try again.")
        Player1Play()
    if matrix[listselect1][listselect2] == "  O":
        print("\u001b[31mYou have made this choice before. Please try again.")
        Player1Play()

    matrix[listselect1][listselect2] = "  O"
    global MoveNumber
    MoveNumber = MoveNumber + 1
    Winner()

    for item in matrix:
        print(*item)
    Player1Play()
Player1Play()