import sys
import math

lstfile1, lstfile2, printlist, lstindex = [], [], [], 0


if len(sys.argv) < 3:
    print("IndexError: number of input files less than expected.\n")
    print("Ëœ Game Over Ëœ")

try:
    file = open(sys.argv[1], "r")
except FileNotFoundError:
    print("IOError: cannot open " + sys.argv[1] + "\n")
    print("Ëœ Game Over Ëœ")
    exit()

try:
    file = open(sys.argv[2], "r")
except FileNotFoundError:
    print("IOError: cannot open " + sys.argv[2] + "\n")
    print("Ëœ Game Over Ëœ")
    exit()

file1 = open(sys.argv[1], "r")
for item in file1:
    lstfile1.append(item)

file2 = open(sys.argv[2], "r")
for item in file2:
    lstfile2.append(item)

class PassError(Exception):
    pass

for item in lstfile1:
    try:
        resultlist = []
        try:

            div, nondiv, start, to = item.split(" ")
        except ValueError:
            printlist.append("IndexError: number of operands less than expected.")
            printlist.append("Results to compare: " + str(lstfile1[lstindex][:-1]))
            printlist.append("------------")
            raise PassError()

        div = math.floor(float(div))
        nondiv = math.floor(float(nondiv))
        start = math.floor(float(start))
        to = math.floor(float(to))

        for result in range(int(start) + 1, int(to) - 1):
            if result % int(div) == 0 and result % int(nondiv) != 0:
                resultlist.append(result)

        results = ""
        for item in resultlist:
            results = results + " " + str(item)
        printlist.append("My results:        " + str(results))

        if results[1:] == lstfile2[lstindex][:-1]:
            printlist.append("Results to compare: " + str(lstfile2[lstindex][:-1]))
            printlist.append("Goool!!!")
            printlist.append("------------")
        else:
            printlist.append("Results to compare: " + str(lstfile2[lstindex][:-1]))
            printlist.append("Assertion Error: results donâ€™t match.")
            printlist.append("------------")

        lstindex += 1
    except ValueError:
        printlist.append("ValueError: only numeric input is accepted.")
        printlist.append("Given input: " + str(item)[:-1])
        printlist.append("------------")
        lstindex += 1
    except IndexError:
        printlist.append("IndexError: number of operands less than expected.")
        printlist.append("Given input: " + str(item)[:-1])
        printlist.append("------------")
        lstindex += 1
    except ZeroDivisionError:
        printlist.append("ZeroDivisionError: You canâ€™t divide by 0.")
        printlist.append("Given input: " + str(item)[:-1])
        printlist.append("------------")
        lstindex += 1
    except PassError:
        lstindex += 1
        pass
    if lstindex == len(lstfile1):
        printlist.append("Ëœ Game Over Ëœ\n")

for item in printlist:
    print(item)