import time, sys

if len(sys.argv) != 3:
    print("\033[31mYou must write two arguments for this program!")
    exit()

dwords, dvalues, keys, values, letter, point, score, selectedline = {}, {}, [], [], [], [], 0, -1

with open(sys.argv[1], encoding="utf8") as file:
  for line in file:
      key,value = line.split(":")
      dwords[key] = value[:-1]

with open(sys.argv[2], encoding="utf8") as txtfile:
  for line in txtfile:
      key, value = line.split(":")
      dvalues[key] = value[:-1]

def Game():
    global selectedline, dwords
    selectedline += 1
    score = 0
    if selectedline == (len(dwords)):
        print("\033[37mYou played all game opportunities :)") #If all options on dictionary are played, game ends with that message :)
        exit()
    for k, v in dwords.items():
        keys.append(k)
        values.append(v)
    keysgame, valuesgame, oldguesses, starttime = keys[selectedline], list(values[selectedline].split(",")), [], int(time.time())
    print("\033[37mShuffled letters are: " + str(keysgame) + " Please guess words for these letters with minimum three letters.")
    def GamePlay():
        guess = input("\033[37mGuessed Word: ")
        guess = (guess.replace("i", "İ").upper())
        finishtime = int(time.time())
        if 30 - finishtime + starttime < 1:
            def Score():
                if len(oldguesses) == 0:
                    print("\033[32mScore for " + str(keysgame) + " is " + "0" + " and no words guessed correctly.")
                else:
                    guessedletters, liststr = [], ""
                    for item in oldguesses:
                        for word in item:
                            global score
                            guessedletters.append(word)
                        for m in guessedletters:
                            score = int(score) + int(dvalues[m])
                    for item in oldguesses:
                        item = (item.replace("I", "ı"))
                        liststr = liststr + (str(item).lower()) + "-"
                    print("\033[32mScore for " + str(keysgame) + " is " + str(score) + " and guessed words are: " + (liststr[:-1]))
            Score()
            Game()
        elif guess in oldguesses:
            print("\033[31mThis word is guessed before.\nYou have " + str(30 - finishtime + starttime) + " time.")
            GamePlay()
        elif guess in valuesgame:
            oldguesses.append(guess)
            print("\033[37mYou have " + str(30 - finishtime + starttime) + " time.")
            GamePlay()
        else:
            print("\033[31mYour guessed word is not a valid word.\nYou have " + str(30 - finishtime + starttime) + " time.")
            GamePlay()
    GamePlay()
Game()