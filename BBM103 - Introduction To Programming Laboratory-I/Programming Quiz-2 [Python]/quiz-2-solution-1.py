# Quadratic Equation Solver
# 09.11.2019 - Saturday
# Hacettepe University: Computer Engineering - Quiz 2.1

import sys

discr = (int(sys.argv[2]) ** 2) - (4 * int(sys.argv[1]) * int(sys.argv[3]))
if discr < 0:
    print("There is not real roots, because discriminant is below 0. Please try it again.")
    sys.exit()

discr = discr ** 0.5
sol1 = (-int(sys.argv[2]) + discr) // 2 * int(sys.argv[1])
sol2 = (-int(sys.argv[2]) - discr) // 2 * int(sys.argv[1])

if discr == 0:
    print("""There is only one (repeated) solution.
Solution: {} """.format(sol1))
if discr > 0:
    print("""There are two real solutions:
Solution(s): {} and {}""".format(sol1, sol2))