import sys

x = sys.argv[1]
x = x.split(",")
x = list(set([int(item) for item in x]))

def removefunc(k):
    del x[k-1::k]
    if k == 7:
        print(*x)

removefunc(2)
removefunc(3)
removefunc(7)