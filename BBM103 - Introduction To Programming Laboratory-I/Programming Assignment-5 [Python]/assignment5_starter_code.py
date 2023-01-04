import numpy as np # 1.16.4
import pandas as pd # 0.24.2
import matplotlib # 2.2.4
#Python 2.7


def sigmoid(x):
    return 1 / (1 + np.exp(-0.005*x))

def sigmoid_derivative(x):
    return 0.005 * x * (1 - x )

def read_and_divide_into_train_and_test(csv_file):
    #TODO
    return training_inputs, training_labels, test_inputs, test_labels



def run_on_test_set(test_inputs, test_labels, weights):
    tp = 0
    #calculate test_predictions
    #TODO map each prediction into either 0 or 1

    for predicted_val, label in zip(test_predictions, test_outputs):
        if predicted_val == label:
            tp += 1
    # accuracy = tp_count / total number of samples
    return accuracy


def plot_loss_accuracy(accuracy_array, loss_array):
    #todo plot loss and accuracy change for each iteration

def main():
    csv_file = './breast-cancer-wisconsin.csv'
    iteration_count = 2500
    np.random.seed(1)
    weights = 2 * np.random.random((9, 1)) - 1
    accuracy_array = []
    loss_array = []
    training_inputs, training_labels, test_inputs, test_labels = read_and_divide_into_train_and_test(csv_file)

    for iteration in range(iteration_count):
        input = training_inputs
        #calculate outputs
        #calculate loss
        #calculate tuning
        #update weights
        #run_on_test_set

    # you are expected to add each accuracy value into accuracy_array
    # you are expected to find mean value of the loss for plotting purposes and add each value into loss_array
    plot_loss_accuracy(accuracy_array, loss_array)

if __name__ == '__main__':
    main()
