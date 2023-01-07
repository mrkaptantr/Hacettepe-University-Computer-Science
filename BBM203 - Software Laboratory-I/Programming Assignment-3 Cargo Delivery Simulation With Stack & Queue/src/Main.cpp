#include <stdio.h>
#include <iostream>
#include <fstream>
#include <string>
#include <string.h> 

using namespace std;

string inputFile[250];
string outputFile[250];
int outputSize = 0;

// Generic linked list node class
template<class T>
struct node {
	node<T>* next;
    node<T>* lastn;
	T data;
};		

// Generic linked list class
template<class T>
class LinkedList
{
public:
	node<T>* first;
	node<T>* last;
    int size = 0;
	LinkedList<T>() {
		first = NULL;
		last = NULL;
	}
    // Add to end method
	void add(T data) {
		if(!first) {
			first = new node<T>;
			first->data = data;
			first->next = NULL;
			last = first;
		} else {
			if(last == first) {
				last = new node<T>;
				last->data = data;
				last->next = NULL;
				first->next = last;
			} else {
				node<T>* insdata = new node<T>;
				insdata->data = data;
				insdata->next = NULL;
				last->next = insdata;
				last = insdata;
			}
		}
        size += 1;
	}
    // Remove item in given index if it exists
    void remove(int index){
        size -= 1;
        struct node<T>* temp1 = first;
        if(index == 0){
            first = temp1->next;
            delete temp1;
            return;
        }
        for(int i=0; i<index-2; i++)
            temp1 = temp1 -> next;
        struct node<T>* temp2 = temp1 -> next;
        temp1 -> next = temp2 -> next;
        free(temp2);
    };
    // Priting linkedlist (i used this method only for debugging)
    void display(){
        struct node<T>* ptr;
        if(first==NULL){}
        else {
            ptr = first;
            cout << "[";
            while (ptr != NULL) {
                cout<< ptr->data.name << ", ";
                ptr = ptr->next;
            }
            cout << "]" << endl;
        }
    };
    // Returns value of item at given index
	T get(int index) {
    	if(index == 0) {
			return this->first->data;
        } else {
			node<T>* curr = this->first;
			for(int i = 0; i < index; i++) {
				curr = curr->next;
			}
			return curr->data;
		}
	}
    // For accessing element at given index
	T operator[](int index) {
		return get(index);
	}
    // Size of linkedlist
    int getSize(){
        return size;
    }
};

// Package class and its components
class package{
    public:
        string name;
        string position;    
};

// Truck class and its components
class truck{
    public:
        string name;
        string destination;
        float power;
        LinkedList<package> deliveries;
};

// Stack data structure class and its functions & components
template <typename T>
class Stack {
	public:
	Stack(){
        _head = NULL;
    };
	//~Stack();

	bool isEmpty() const{
        return _size == 0;
    };
	int size() const{
        return _size;
    };
	
	//Push: Place item on top of the stack
	void push(const T& newItem){
        ListNode *temp;
        temp = new ListNode();
        temp->item = newItem;

        if(_head == NULL){
            temp->next = NULL;
        } else {
            temp->next = _head;
        }
        _head = temp;
        _size += 1;
    };
	//Top: Take a look at the topmost item without removing it
	T getTop() const{
        return _head->item;
    };
	//Pop: Remove item from the top of the stack
	void pop(){
        if(_head==NULL)
            cout<<"Stack Underflow"<<endl;
        else {
            //cout<<"The popped element is "<< _head->item <<endl;
            _head = _head->next;
            _size -=1;
        }
    };
    // Priting stack
    void display() {
        struct ListNode* ptr;
        if(_head==NULL){}
        else {
            ptr = _head;
            while (ptr != NULL) {
                outputFile[outputSize] = ptr->item.name + "\n";
                outputSize += 1;
                ptr = ptr->next;
            }
        }
    };
    private:
	struct ListNode {
		T item;
		ListNode* next;
	};
	ListNode* _head;
	int _size = 0;
};

// Queue data structure class and its functions & components
template<typename T>
class Queue {
	public:
	Queue(){
        _firstNode = _lastNode = NULL;
    };
	//~Queue();
	bool isEmpty() const{
        return _size == 0;
    };
	int size() const{
        return _size;
    };

	//Enqueue: Items are added to the back of the queue
	void enqueue(const T& newItem){
        QueueNode* temp = new QueueNode(newItem); 
        _size += 1;
        if (_lastNode == NULL) {
            _firstNode = _lastNode = temp;
            return;
        } 
        _lastNode->next = temp;
        _lastNode = temp;
    };

	//Dequeue: Items are removed from the front of the queue
	void dequeue(){
        temp = _firstNode;
        if (_firstNode == NULL) {
            cout<<"Underflow"<<endl;
            return;
        }
        else if (temp->next != NULL) {
            temp = temp->next;
            free(_firstNode);
            _firstNode = temp;
        } else {
            free(_firstNode);
            _firstNode = NULL;
            _lastNode = NULL;
        }
        _size -= 1;
    };
    // Priting queue (i used this method only for debugging)
    void display(){
    struct QueueNode* ptr;
        if(_firstNode==NULL){
        } else {
            ptr = _firstNode;
            while (ptr != NULL) {
                outputFile[outputSize] = ptr->item.name + "\n";
                outputSize += 1;
                ptr = ptr->next;
            }
        }
    };
	//Get Front: Take a look at the first item
	T getFront() const{
        return _firstNode->item;
    };
	private:
	struct QueueNode {
		T item;
		QueueNode *next;
        QueueNode(T d) { 
            item = d; 
            next = NULL; 
        } 
	};
	int _size = 0;
	/* to avoid the traversal to the last node, 
	 an additional pointer to the last node is defined*/
    QueueNode *_firstNode;
	QueueNode *_lastNode;
    QueueNode *temp;
};

// Station class and its components
class station{
    public:
        string name;
        Queue<truck> *truckQueue = new Queue<truck>();
        Stack<package> *packageStack = new Stack<package>();
};

// Required linkedlists for required operations
LinkedList<station> stations;
LinkedList<truck> trucks;
LinkedList<package> packages;
LinkedList<std::string> missions;
LinkedList<std::string> dests;

// This method reads input file line by line and saves it in a linkedlist.
void readLineByLine(string fileName, char* argv[]){
	string line;
	ifstream myfile(fileName);
	if (myfile.is_open()) {
		while (getline(myfile, line)) {
			if(fileName.compare(argv[1]) == 0){
                dests.add(line);
            }
            if(fileName.compare(argv[2]) == 0){
                package newpackage;
                std::string delimiter = " "; std::string token;
                int indx = 0; string name = ""; size_t pos = 0;
                while ((pos = line.find(delimiter)) != std::string::npos) {
                    token = line.substr(0, pos);
                    if(indx==0){name = token;}
                    line.erase(0, pos + delimiter.length());
                    indx += 1;
                }
                newpackage.name = name; newpackage.position = line;
                packages.add(newpackage);
            }
            if(fileName.compare(argv[3]) == 0){
                truck newtruck;
                std::string delimiter = " "; std::string token;
                int indx = 0; string name = ""; string destination = ""; size_t pos = 0;
                while ((pos = line.find(delimiter)) != std::string::npos) {
                    token = line.substr(0, pos);
                    if(indx==0){name = token;}
                    if(indx==1){destination = token;}
                    line.erase(0, pos + delimiter.length());
                    indx += 1;
                }
                newtruck.destination = destination; newtruck.name = name; newtruck.power = std::stof(line);
                trucks.add(newtruck);
            }
            if(fileName.compare(argv[4]) == 0){
                missions.add(line);
            }
		}
		myfile.close();
	}
}

// This method creates and writes output file
void writeTxtFile(int size, string* file, string fileName){
std::ofstream myfile (fileName);
for(int i=0; i<size; i++){
	string outitem = file[i];
	myfile << outitem;
}
myfile.close();
}

void cargoDeliverySimulation(){
    // Initializing stations
    for(int i=0; i<dests.size; i++){
        station newStation;
        newStation.name = dests[i];
        stations.add(newStation);
    }
    // Adding packages to stations
    for(int k=0; k<packages.size; k++){
        for(int j=0; j<stations.size; j++){
            if((packages[k].position).compare(stations[j].name) == 0){
                package *newPackage = new package();
                newPackage->name = packages[k].name; newPackage->position = packages[k].position; 
                stations[j].packageStack->push(*newPackage);
            }
        }
    }
    // Adding trucks to stations
    for(int k=0; k<trucks.size; k++){
        for(int j=0; j<stations.size; j++){
            if((trucks[k].destination).compare(stations[j].name) == 0){
                truck *newTruck = new truck();
                newTruck->name = trucks[k].name; newTruck->destination = trucks[k].destination; newTruck->power = trucks[k].power;
                stations[j].truckQueue->enqueue(*newTruck);
            }
        }
    }
    // Mission initialization (Input file splitting...)
    for(int l=0; l<missions.size; l++){
        string startPos; string midwayPos; string targetPos; int startTakeItem; int midTakeItem; string midLeftItems;
        std::string delimiter = "-"; std::string token; string line = missions[l];
        int indx = 0; string name = ""; string destination = ""; size_t pos = 0;
        while ((pos = line.find(delimiter)) != std::string::npos) {
            token = line.substr(0, pos);
            if(indx==0){startPos = token;}
            if(indx==1){midwayPos = token;}
            if(indx==2){targetPos = token;}
            if(indx==3){startTakeItem = std::stoi(token);}
            if(indx==4){midTakeItem = std::stoi(token);}
            line.erase(0, pos + delimiter.length());
            indx += 1;
        }
        midLeftItems = line;

        // Splitting and finding indexes of items of midway station and storing them in a linkedlist
        LinkedList<int> midLeftIndexes;
        std::string tokens; std::string del = ","; size_t position = 0;
        while ((position = midLeftItems.find(del)) != std::string::npos) {
            tokens = midLeftItems.substr(0, position);
            midLeftIndexes.add(std::stoi(tokens));
            midLeftItems.erase(0, position + del.length());
        }
        midLeftIndexes.add(std::stoi(midLeftItems));

        // Taking cargo from starting position (Start of our cargo.)
        // Note: This method includes some error handling cases.
        truck startTruck;
        for(int i=0; i<stations.size; i++){
            if(stations[i].name.compare(startPos) == 0){
                if(stations[i].truckQueue->isEmpty()){
                    cout << "No trucks in starting position!" << endl;
                    return;
                } else {
                    startTruck = stations[i].truckQueue->getFront();
                    stations[i].truckQueue->dequeue();
                    if(stations[i].packageStack->size() < startTakeItem){
                        cout << "Not enough packages in starting position! " << endl;
                        startTakeItem = stations[i].packageStack->size();
                    }
                    for(int j=0; j<startTakeItem; j++){
                        package startPackage;
                        startPackage = stations[i].packageStack->getTop();
                        startTruck.deliveries.add(startPackage);
                        stations[i].packageStack->pop();
                    }
                }
            }
        }
        int leftMid = midLeftIndexes.getSize();
        int m = startTruck.deliveries.getSize();
        // Taking and giving cargos at midway cargo station
        for(int q=0; q<stations.size; q++){
            if(stations[q].name.compare(midwayPos) == 0){
                if(stations[q].packageStack->size() < midTakeItem){
                    cout << "Not enough packages in midway position!" << endl;
                    midTakeItem = stations[q].packageStack->size();
                } else {
                    int x=startTruck.deliveries.getSize();
                    // Taking deliveries
                    for(int i=0; i<midTakeItem; i++){
                        package startPackage;
                        startPackage = stations[q].packageStack->getTop();
                        startTruck.deliveries.add(startPackage);
                        stations[q].packageStack->pop();
                    }
                    // Giving deliveries to station
                    LinkedList<package> temp;
                    LinkedList<package> temp2;
                    for(int i=midLeftIndexes.getSize()-1; i>=0; i--){
                        try {temp.add(startTruck.deliveries[midLeftIndexes[i]]); temp2.add(startTruck.deliveries[midLeftIndexes[i]]);} catch(int e) {}
                    }
                    for(int z=temp.size-1; z>=0; z--){
                        stations[q].packageStack->push(temp[z]);
                    }
                    m -= leftMid;
                }
            }
        }
        // Final station operations
        for(int q=0; q<stations.size; q++){
            if(stations[q].name.compare(targetPos) == 0){
                // Giving deliveries
                for(int j=0; j<m; j++){
                    stations[q].packageStack->push(startTruck.deliveries[0]);
                }
                for(int i=0; i<startTruck.deliveries.size-leftMid-m; i++){
                    stations[q].packageStack->push(startTruck.deliveries[0+leftMid+i+m]);
                }
                // Parking truck end ending simulation step
                stations[q].truckQueue->enqueue(startTruck);
            }
        }
    }
};

int main(int argc, char* argv[]) {

   outputSize = 0;
   std::ofstream outfile (argv[5]);

   readLineByLine(argv[1], argv);
   readLineByLine(argv[2], argv);
   readLineByLine(argv[3], argv);
   readLineByLine(argv[4], argv);

    cargoDeliverySimulation();

    for(int i=0; i<dests.size; i++){
        outputFile[outputSize] = dests[i] + "\n";
        outputSize += 1;
        outputFile[outputSize] = "Packages:\n";
        outputSize += 1;
        stations[i].packageStack->display();
        outputFile[outputSize] = "Trucks:\n";
        outputSize += 1;
        stations[i].truckQueue->display();
        outputFile[outputSize] = "-------------\n";
        outputSize += 1;
    }
  
   writeTxtFile(outputSize, outputFile, argv[5]);

   return 0;
}