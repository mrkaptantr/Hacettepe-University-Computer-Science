import sys
x = int(sys.argv[1])

list1 = [("*"*(2*a-1)).center(2*x-1) for a in range(1,x+1)]
list2 = [("*"*(2*a-1)).center(2*x-1) for a in range(x-1,0,-1)]

for item in list1 + list2:
    print(item)