import numpy as np # 1.16.4
import pandas as pd # 0.24.2
import matplotlib as plt # 2.2.4
# Python 2.7

MalignantCount = 0
BenignCount = 0

def sigmoid(x):
    return 1 / (1 + np.exp(-0.005*x))

def sigmoid_derivative(x):
    return 0.005 * x * (1 - x )

def read_and_divide_into_train_and_test(csv_file):

    global MalignantCount, BenignCount
    df = pd.read_csv(csv_file)
    df.isnull().sum()
    df.drop(['Code_number'], 1, inplace=True)
    df['Bare_Nuclei'] = pd.to_numeric(df['Bare_Nuclei'], errors="coerce")
    df = df[["Clump_Thickness","Uniformity_of_Cell_Size","Uniformity_of_Cell_Shape","Marginal_Adhesion","Single_Epithelial_Cell_Size","Bare_Nuclei","Bland_Chromatin","Normal_Nucleoli","Mitoses","Class"]]

    len_training = round(len(df) / 10 * 8)
    training_case = df.iloc[:len_training, :]

    dictionary = {1: 'M', 0: 'B'}
    training_case.Class = [dictionary[item] for item in training_case.Class]
    for item in training_case.Class:
        if item == "M":
            MalignantCount += 1
        elif item == "B":
            BenignCount += 1

    # Visualization of Data (Counts of Patient Types and Heatmap of Values)
    list1 = ['Clump_Thickness','Uniformity_of_Cell_Size','Uniformity_of_Cell_Shape','Marginal_Adhesion','Single_Epithelial_Cell_Size',"Bare_Nuclei",'Bland_Chromatin','Normal_Nucleoli','Mitoses' ]
    list2 = ['Mitoses', 'Normal_Nucleoli', 'Bland_Chromatin',"Bare_Nuclei", 'Single_Epithelial_Cell_Size', 'Marginal_Adhesion', 'Uniformity_of_Cell_Shape', 'Uniformity_of_Cell_Size','Clump_Thickness']

    matrix = []
    for item in list1:
        emptylist = []
        for item2 in list1:
            appendeditem = round(df.corr()[item][item2], 2)
            emptylist.append(appendeditem)
        matrix.append(emptylist)

    import matplotlib.pyplot as plt
    type = ("Malignant", "Benign")
    count = (MalignantCount, BenignCount)
    plt.bar(type, count)
    plt.title("Count Of Malignant and Benign Patients")
    plt.xlabel("Type of Cancer")
    plt.ylabel("Count of Patients")
    plt.show()

    nmp = np.array(matrix)

    fig, ax = plt.subplots()
    im = ax.imshow(nmp)
    ax.set_xticks(np.arange(len(list1)))
    ax.set_yticks(np.arange(len(list2)))
    ax.set_xticklabels(list1)
    ax.set_yticklabels(reversed(list2))
    plt.setp(ax.get_xticklabels(), rotation=45, ha="right", rotation_mode="anchor")

    for i in range(len(list1)):
        for j in range(len(list2)):
            text = ax.text(j, i, nmp[i, j], ha="center", va="center", color="w")
    ax.set_title("Correlations of The Attributes of Training Set")
    fig.tight_layout()
    plt.show()

    df.drop(['Bare_Nuclei'], 1, inplace=True)
    training_inputs = df.iloc[:len_training, :-1]
    training_labels = df.iloc[:len_training, -1:]
    test_inputs = df.iloc[len_training:, :-1]
    test_labels = df.iloc[len_training:, -1:]

    return training_inputs, training_labels, test_inputs, test_labels

def run_on_test_set(test_inputs, test_labels, weights):
    tp = 0
    test_output = sigmoid(np.dot(test_inputs, weights))

    test_outputs = []
    for item in test_labels['Class']:
        test_outputs.append(item)

    test_predictions = []
    for item in test_output:
        if item > 0.5:
            test_predictions.append(1)
        else:
            test_predictions.append(0)

    total = 0
    for predicted_val, label in zip(test_predictions, test_outputs):
        total += 1
        if predicted_val == label:
            tp += 1
        accuracy = tp / len(test_outputs)
    return accuracy

def plot_loss_accuracy(accuracy_array, loss_array):
    import matplotlib.pyplot as plt

    iteration_count = [item for item in range(len(accuracy_array))]
    plt.plot(accuracy_array,  color='blue')
    plt.plot(loss_array, color='red')
    plt.title("Accuracy (Blue) & Loss (Red) Plot For Test Case")
    plt.xlabel('Iteration Count')
    plt.ylabel('Accuracy / Loss Percentage')
    plt.show()

def main():
    csv_file = './breast-cancer-wisconsin.csv'
    iteration_count = 2500
    np.random.seed(1)
    weights = 2 * np.random.random((8, 1)) - 1
    accuracy_array = []
    loss_array = []

    training_inputs, training_labels, test_inputs, test_labels = read_and_divide_into_train_and_test(csv_file)
    accuracy_array = []

    for iteration in range(iteration_count):
        input = training_inputs

        #Forward Propagation;
        y = np.dot(input,weights)#np.where(np.isnan(input),0,input).dot(np.where(np.isnan(weights),0,weights))
        y = sigmoid(y)
        accuracy_array.append(run_on_test_set(test_inputs, test_labels, weights))

        #Backward Propagation
        loss = training_labels - y
        tuning = loss * sigmoid_derivative(y)
        weights += np.dot(np.transpose(training_inputs), tuning)
        loss_array.append(np.mean(loss))

    plot_loss_accuracy(accuracy_array, loss_array)

if __name__ == '__main__':
    main()