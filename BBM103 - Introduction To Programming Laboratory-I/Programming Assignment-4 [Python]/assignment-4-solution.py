import sys

# In this code i also add some more functions;
# (1) Writing total move count to output file
# (2) Writing left health conditions to output file
# (3) Writing step by step solution to output file
# (4) Writing error message (if health conditions are not enough for solving maze) to output file
# - In this code; only problem is, code can give Recursion Maximum Depth Error;
# - If there are loops or if there are lots of P blocks nearly. But not all of them. Sometimes...

VisitedPoints, MazeList, HealthList, BonusPoints, MiniList, Solution1, Solution2, EndPoint, x, y, CurrentLine, TotalMoves, Health = [], [], [], [], [], [], [], (), 0, 0, 0, 0, int(sys.argv[3])

with open(sys.argv[1]) as file: # Creating Matrix Of Items, Without'\n' Items
    for item in file.readlines(0):
        MazeList.append(list(item.strip('\n')))
    for a in range(len(MazeList)):
        for b in range(len(MazeList)):
            if MazeList[a][b] == "S":
                x = a
                y = b

with open(sys.argv[2]) as file:
    for item in file.readlines(0):
        HealthList.append(list(item.strip('\n')))
    abc = len(HealthList[0])
    for a in range(len(HealthList)):
        for b in range(abc):
            if HealthList[a][b] == "H":
                BonusPoints.append((a,b))

for line in MazeList:
    CurrentLetter = 0
    for letter in line:
        if letter == "F":
            EndPoint = (CurrentLine, CurrentLetter)
        if letter == "S":
            x, y = (CurrentLine, CurrentLetter)
        CurrentLetter += 1
    CurrentLine += 1

def MazeSolver(x, y):
    global TotalMoves
    if MazeList[x][y] == "F":
        Solution1.append("We Solved The Maze! :) \nFinal Position Is: " + "(" + str(x) + "," + str(y) + ")" + '\n')
        for a,b in VisitedPoints:
            MazeList[a][b] = "1"
        ab = len(MazeList[0])
        for x in range(len(MazeList)):
            for y in range(ab):
                if MazeList[x][y] == "W":
                    MazeList[x][y] = "0"
                elif MazeList[x][y] == "P":
                    MazeList[x][y] = "0"
                elif MazeList[x][y] == "X":
                    MazeList[x][y] = "0"
                elif MazeList[x][y] == "H":
                    MazeList[x][y] = "1"

        file = open(sys.argv[4], "w")
        file.write("(1) Solution without health condition:\n")

        for x in MazeList:
            y = ""
            for item in x:
                y = y + (str(item) + ", ")
            file.write(y[:-2])
            file.write("\n")
        file.write("\nTotal Moves Until Solve Maze: " + str(TotalMoves) + '\n')
        file.close()

        # Recalling original x, y positions
        CurrentLine = 0
        for line in HealthList:
            CurrentLetter = 0
            for letter in line:
                if letter == "S":
                    x, y = (CurrentLine, CurrentLetter)
                CurrentLetter += 1
            CurrentLine += 1
        HPMazeSolver(x, y)
    elif MazeList[x][y] == "W":
        Solution1.append("There Is A Wall There, I'm Passing That One: " + "(" + str(x) + "," + str(y) + ")" + '\n')
        return False
    elif MazeList[x][y] == "X":
        Solution1.append("Visiting A Point, Which Has Visited Before: " + "(" + str(x) + "," + str(y) + ")" + '\n')
        MazeList[x][y] = "P"
        return False

    elif MazeList[x][y] == "P":
        TotalMoves += 1
        VisitedPoints.append((x, y))
        Solution1.append("Visiting Here: " + "(" + str(x) + "," + str(y) + ")" + '\n')
        MazeList[x][y] = "X"

    if ((y > 0 and MazeSolver(x, y-1))
        or (x < len(MazeList) - 1 and MazeSolver(x+1, y))
        or (x > 0 and MazeSolver(x -1, y))
        or (y < len(MazeList)-1 and MazeSolver(x, y+1))):
        return True

    return False

def HPMazeSolver(x,y):
    global Health
    if HealthList[x][y] == "H":
        Health = int(sys.argv[3])
    if Health == 0:
        ab = len(HealthList[0])
        Solution2.append("Sorry, health conditions end. \nWe couldn't solve maze with health conditions...")
        for x in range(len(HealthList)):
            for y in range(ab):
                if HealthList[x][y] == "W":
                    HealthList[x][y] = "0"
                elif HealthList[x][y] == "P":
                    HealthList[x][y] = "0"
                elif HealthList[x][y] == "X":
                    HealthList[x][y] = "1"
        file = open(sys.argv[4], "a")
        file.write("\n(2) Solution with health condition:\n")
        file.write("""-Sorry, we can't be able to solve the maze with low health conditions.
-Please try it again with more health conditions.
-But we were able to solve that:\n""")

        for x in HealthList:
            y = ""
            for item in x:
                y = y + (str(item) + ", ")
            file.write(y[:-2])
            file.write('\n')
        file.write("\nHealth Conditions Left: " + str(Health) + "\n\n")
        file.write("*******SOLUTIONS STEP BY STEP*******\n\n")
        file.write("a) Without Health Condition\n")
        for item in Solution1:
            file.write(item)
        file.write('\n')
        file.write("b) With Health Condition\n")
        for itemx in Solution2:
            file.write(itemx)
        file.close()
        exit()
    if HealthList[x][y] == "F":
        Solution2.append("We Solved The Maze With Health Conditions! :) \nFinal Position Is: " + "(" + str(x) + "," + str(y) + ")" + '\n')
        abx = len(HealthList[0])
        for x in range(len(HealthList)):
            for y in range(abx):
                if HealthList[x][y] == "W":
                    HealthList[x][y] = "0"
                elif HealthList[x][y] == "P":
                    HealthList[x][y] = "0"
                elif HealthList[x][y] == "X":
                    HealthList[x][y] = "1"
        file = open(sys.argv[4], "a+")
        file.write("\n(2) Solution with health condition:\n")

        for item in BonusPoints:
            a = item[0]
            b = item[1]
            MazeList[a][b] = "H"

        for hx in HealthList:
            y = ""
            for item in hx:
                y = y + (str(item) + ", ")
            file.write(y[:-2])
            file.write('\n')
        file.write("\nHealth Conditions Left: " + str(Health) + "\n")
        file.write('\n')
        file.write("*******SOLUTIONS STEP BY STEP*******\n\n***a) Without Health Condition***\n")
        for item in Solution1:
            file.write(item)
        file.write('\n')
        file.write("***b) With Health Condition***\n")
        for itemx in Solution2:
            file.write(itemx)
        exit()

    elif HealthList[x][y] == "W":
        Solution2.append("There Is A Wall There, I'm Passing That One: " + "(" + str(x) + "," + str(y) + ")" + '\n')
        return False
    elif HealthList[x][y] == "X":
        Solution2.append("Visiting A Point, Which Has Visited Before: " + "(" + str(x) + "," + str(y) + ")" + '\n')
        return False
    elif HealthList[x][y] == "P":
        Solution2.append("Visiting Here: " + "(" + str(x) + "," + str(y) + ")" + '\n')
        HealthList[x][y] = "X"
        Health -= 1

    if ((y > 0 and HPMazeSolver(x, y-1))
            or (x < len(HealthList) - 1 and HPMazeSolver(x+1, y))
            or (x > 0 and HPMazeSolver(x - 1, y))
            or (y < len(HealthList)-1 and HPMazeSolver(x, y+1))):
        return True
    return False

MazeSolver(x,y)