#include <stdio.h>
#include <iostream>
#include <fstream>
#include <string>
#include <string.h> 
#include <bits/stdc++.h> 

#define MAX_TREE_HT 256 

using namespace std;

int outputSize;

/******* LİNKED LİST MODULE *******/
// Generic linked list node class
template<class T>
struct node {
	node<T>* next;
    node<T>* lastn;
	T data;
};		

// Generic linked list class
template<class T>
class LinkedList{
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
    // Change item in given index if it exists
    void set(const int index, const T &item){
        int x = 0;
        if(first==NULL) {
            printf("Linked List not initialized");
            return;
        } 
        else if(index == 0){
            first->data = item;
            return;
        }
        else if(index > 0){
            struct node<T>* ptr = first;
            for(int i=0; i<index; i++){
                ptr = ptr->next;
            }
            ptr->data = item;
        }
    };
    // Priting linkedlist (i used this method only for debugging)
    void display(){
        struct node<T>* ptr;
        if(first==NULL){}
        else {
            ptr = first;
            cout << "[";
            while (ptr != NULL) {
                cout<< ptr->data << ", ";
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

/******* HUFFMAN TREE & ENCODING MODULE *******/
map<char, string> codes; 
map<char, int> freq; 

struct MinHeapNode { 
    char data;          
    int freq;           
    MinHeapNode *left, *right; 
    MinHeapNode(char data, int freq){ 
        left = right = NULL; 
        this->data = data; 
        this->freq = freq; 
    } 
}; 

struct compare { 
    bool operator()(MinHeapNode* l, MinHeapNode* r) { 
        return (l->freq > r->freq); 
    } 
}; 

void printCodes(struct MinHeapNode* root, string str) { 
    if (!root) 
        return; 
    if (root->data != '$') 
        cout << root->data << ": " << str << "\n"; 
    printCodes(root->left, str + "0"); 
    printCodes(root->right, str + "1"); 
} 

void storeCodes(struct MinHeapNode* root, string str) { 
    if (root==NULL) 
        return; 
    if (root->data != '$') 
        codes[root->data]=str; 
    storeCodes(root->left, str + "0"); 
    storeCodes(root->right, str + "1"); 
} 
  
priority_queue<MinHeapNode*, vector<MinHeapNode*>, compare> minHeap; 

void HuffmanCodes(int size){ 
    struct MinHeapNode *left, *right, *top; 
    for (map<char, int>::iterator v=freq.begin(); v!=freq.end(); v++) 
        minHeap.push(new MinHeapNode(v->first, v->second)); 
    while (minHeap.size() != 1) { 
        left = minHeap.top(); 
        minHeap.pop(); 
        right = minHeap.top(); 
        minHeap.pop(); 
        top = new MinHeapNode('$', left->freq + right->freq); 
        top->left = left; 
        top->right = right; 
        minHeap.push(top); 
    } 
    storeCodes(minHeap.top(), ""); 
} 

void calcFreq(string str, int n){ 
    for (int i=0; i<str.size(); i++) 
        freq[str[i]]++; 
} 

string decode_file(struct MinHeapNode* root, string s) { 
    string ans = ""; 
    struct MinHeapNode* curr = root; 
    for (int i=0;i<s.size();i++) { 
        if (s[i] == '0') 
           curr = curr->left; 
        else
           curr = curr->right; 
        if (curr->left==NULL and curr->right==NULL) { 
            ans += curr->data; 
            curr = root; 
        } 
    } 
    return ans+'\0'; 
} 

// This method prints Huffman tree
void printHuffmanTree(struct MinHeapNode* root, int height){
    string space = " ";
    if (!root) 
        return; 
    else{
        for (int k = 0; k < height*3; k++){
            cout << space;
        }
        cout << "+- " << root->freq << endl;
        printHuffmanTree(root->left, height+1);
        printHuffmanTree(root->right, height+1);
    }
}

// This method finds Huffman encode of given character
void findCharsHuffmanCode(char *x){
    cout << x << endl;
    bool checker = false;
    for (auto v=codes.begin(); v!=codes.end(); v++){
        if(v->first == *x){
            cout << "Huffman encoding of character " <<  v->first << " is: " << v->second << endl; 
            checker = true;
        }
    }
    if (!checker) {
        cout << "Character of " << x << " does not exist!" << endl; 
    }
    cout << "Frequencies Of All Characters Are:" << endl; 
    for (auto v=codes.begin(); v!=codes.end(); v++) 
        cout << v->first <<' ' << v->second << endl; 
    cout << endl;
}

/******* FİLE OPERATİONS MODULE *******/

LinkedList<string> inputFile;
// This method reads input file line by line and saves it in a linkedlist.
void readLineByLine(string fileName){
	string line;
	ifstream myfile(fileName);
	if (myfile.is_open()) {
		while (getline(myfile, line)) {
            inputFile.add(line);
        }
    }
};

LinkedList<string> backupFile;
string encodedString, decodedString; 
void encode(bool checker){
    if(checker){
        string str = inputFile[0];
        transform(str.begin(), str.end(), str.begin(), ::tolower); 
        calcFreq(str, str.length()); 
        HuffmanCodes(str.length()); 
        for (auto i: str) 
            encodedString+=codes[i]; 
        cout << "Encoded Huffman Data: " << encodedString << endl << endl;

        std::ofstream myfile("backup.txt");
        myfile << inputFile[0];
        myfile.close();

    } else {
        ifstream myfile("backup.txt");
        string line;
	    if (myfile.is_open()) {
		    while (getline(myfile, line)) {
                backupFile.add(line);
            }
        }
        string str = backupFile[0];
        transform(str.begin(), str.end(), str.begin(), ::tolower); 
        calcFreq(str, str.length()); 
        HuffmanCodes(str.length()); 
        for (auto i: str) 
            encodedString+=codes[i]; 
    } 
}

int main(int argc, char* argv[]) {
    // -l
    if(strcmp(argv[1],"-l") == 0){
        encode(false);
        printHuffmanTree(minHeap.top(),0);
        cout << endl;
    }

    // -s character
    else if(strcmp(argv[1],"-s") == 0){
        encode(false);
        findCharsHuffmanCode(argv[2]);
    }

    // -i input_file.txt -encode 
    else if(strcmp(argv[3],"-encode") == 0){
        readLineByLine(argv[2]);
        encode(true);
    }

    // -i input_file.txt -decode
    else if(strcmp(argv[3],"-decode") == 0){
        encode(false);
        readLineByLine(argv[2]);
        decodedString = decode_file(minHeap.top(), encodedString); 
        cout << "Decoded Huffman Data: " << decodedString << endl << endl; 
    }
    return 0;
}