


Important Note: naiveBayesPredictor method collapses while uploading file to Github, correct form of naiveBayesPredictor function is:

def naiveBayesPredictor(bagOfWords, oppositeBagOfWords, trainSet, testSet, limitOfKeywords, probabilityMode, influencePresence, willPrint, NUMBER_OF_KEYWORDS_TO_PRINT = 10):
  predictions = list()

  # Step-1: Calculating main probabilities
  main_probabilities = {}
  main_probabilities['business'] = abs(math.log2((business_df.shape[0] / df.shape[0])))
  main_probabilities['tech'] = abs(math.log2((tech_df.shape[0] / df.shape[0])))
  main_probabilities['politics'] = abs(math.log2((politics_df.shape[0] / df.shape[0])))
  main_probabilities['sport'] = abs(math.log2((sport_df.shape[0] / df.shape[0])))
  main_probabilities['entertainment'] = abs(math.log2((entertainment_df.shape[0] / df.shape[0])))
  # In Standart Format: {'business': '0.23', 'tech': '0.18', 'politics': '0.18', 'sport': '0.23', 'entertainment': '0.18'}
  # But here they are stored in logarithmic format.

  # Step-2: Calculating logarithmic probabilities
  logProbabilities, oppositeLogProbabilities = calculateLogProbabilities(bagOfWords, oppositeBagOfWords, limitOfKeywords, probabilityMode, influencePresence)
  
  if(willPrint):
    print("")
    print("--- Most Effective Presence Keywords For Each Category ---")
    for key in list(logProbabilities.keys()):
      print(str(key) + "-> [", end = "")
      i=0
      for subkey in list(logProbabilities[key].keys()):
        if(i<NUMBER_OF_KEYWORDS_TO_PRINT):
          print(str(subkey), end = ", ")
          i+=1
      print("]", end = "")
      print("")
    print("")
    print("--- Most Effective Absence Keywords For Each Category ---")
    for key in list(oppositeLogProbabilities.keys()):
      print(str(key) + "-> [", end = "")
      i=0
      for subkey in list(oppositeLogProbabilities[key].keys()):
        if(i<10):
          print(str(subkey), end = ", ")
          i+=1
      print("]", end = "")
      print("")

  # Step-3: Making predictions for each of sample
  for i in range(testSet.shape[0]):
    predictions.append(makePrediction(bagOfWords, testSet[i], main_probabilities, logProbabilities, oppositeLogProbabilities))

  # Step-4: Returning all predictions
  return predictions
