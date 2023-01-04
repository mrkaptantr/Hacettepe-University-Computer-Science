# Even Number Evaluator
# 10.11.2019 - Sunday
# Hacettepe University: Computer Engineering - Quiz 2.2

import sys

S = sys.argv[1]
S = tuple(int(x.strip()) for x in S.split(','))
totalsum = []

for num in S:
    if num > 0:
        totalsum.append(num)

E = []
number = ""
evensum = 0

for num in S:
    if num % 2 == 0 and num > 0:
        if not num % 2:
            evensum += num
            E.append(num)

E = str(E)
number = '"' + E[1:-1] + '"'
rate = evensum / sum(totalsum)
rate = round(rate, 3)

print("Even Numbers: {}".format(number))
print("Sum of Even Numbers: {}".format(evensum))
print("Even Number Rate: {}".format(rate))