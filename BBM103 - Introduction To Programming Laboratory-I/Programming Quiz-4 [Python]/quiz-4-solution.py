import sys
lstgroup, values, texts, valueandtexts, lstIndex, MessageNumber = [], [], [], [], 0, 0

with open(sys.argv[1], encoding="utf8") as file:
    for line in file:
        valueandtexts.append(line)
    valueandtexts[-1] = valueandtexts[-1] + "\n" # you said last line hasn't got /n character on piazza, if we wont add this command, output wont be same
    valueandtexts.sort()
    for item in valueandtexts:
        groupnumber, value, text = item.split("\t")
        lstgroup.append(groupnumber)
        values.append(value)
        texts.append(text)
    lstgroup.sort()

new_file = open(sys.argv[2], "w", encoding="utf8")

def Checking():
    global lstIndex, MessageNumber, valueandtexts
    if int(lstIndex) == len(valueandtexts):
        exit()
    elif int(values[lstIndex]) == 0:
        new_file.write("Message " + str(MessageNumber) + "\n")
        MessageNumber += 1
        Writing()
    else:
        Writing()

def Writing():
    global lstIndex
    new_file.write(lstgroup[lstIndex] + "\t" + values[lstIndex] + "\t" + texts[lstIndex])
    lstIndex += 1
    Checking()

Checking()