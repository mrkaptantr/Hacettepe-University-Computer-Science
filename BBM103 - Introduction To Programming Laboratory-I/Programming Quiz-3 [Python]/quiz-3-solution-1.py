import sys
totalx, printx, total = 0, "", str(int(sys.argv[1])**int(sys.argv[2]))

def Check():
    global totalx, printx, total
    if 0 <= int(totalx) < 10:
        print(printx)
    else:
        total, totalx = totalx, 0
        printx += " = "
        for x in str(total):
            totalx += int(x); printx += x + " + "
        printx, totalx = printx[:-2] + "= " + str(totalx), int(totalx)
        Check()

def calculator():
    global totalx, printx
    for x in total:
        totalx += int(x); printx += x + " + "
    printx = (sys.argv[1] + "^" + sys.argv[2] + " = " + str(total) + " = " + printx[:-2] + "= " + str(totalx))
    Check()
calculator()