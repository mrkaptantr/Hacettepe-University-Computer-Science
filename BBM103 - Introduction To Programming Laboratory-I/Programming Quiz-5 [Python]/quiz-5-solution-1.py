import sys
x, val = int(sys.argv[1]), 1

def Printerv2():
    global val
    if val == 1:
        print(("*" * (2 * val - 1)).center((x*2)-1))
        exit()
    else:
        print(("*" * (2 * val - 1)).center((x*2)-1))
    val -= 1
    Printerv2()

def Printer():
    global val
    if val == x:
        Printerv2()
    else:
        print(("*" * (2 * val - 1)).center((x*2)-1))
    val += 1
    Printer()
Printer()