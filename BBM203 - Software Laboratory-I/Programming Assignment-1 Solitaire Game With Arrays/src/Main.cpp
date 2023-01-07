#include <stdio.h>
#include <iostream>
#include <fstream>
#include <string>

using namespace std;

class card{
    public:
    	string value;
        string visibleValue;
        string type;
        string number;
};

// All Arrays and Variables
int index; int lastIndex; int openLimit; int stock; int stockMoves; int rem; int sec2; int sec2index; bool checker; int outsize;
string deck_file_name;string command_file_name;string output_file_name; string deckFile[251];string commandFile[251];string outputFile[251];card cards[250]; card indexes[80]; int indexesIndex;
int pile1index; int pile2index; int pile3index; int pile4index; int pile5index; int pile6index; int pile7index; int checkIndexesIndex;
int stockMoveNumber;
card pile[7][30];card hFoundation[15];card dFoundation[15];card sFoundation[15];card cFoundation[15];card stockPlace[3];card secondPer[90];int secondIndex;
int hindex;int dindex;int sindex;int cindex;int secIndex;int limiter;bool passed; int newindex;int outputSize; int m; int q;
string file1[251]; string file2[251];

// Read File Function
string* readLineByLine1(string fileName, string* array){
	int index = 0;
	string line;
	ifstream myfile(fileName);
	if (myfile.is_open()) {
		while (getline(myfile, line)) {
			array[index] = line;
			index += 1;
		}
		myfile.close();
	}
	return array;
}
string* readLineByLine2(string fileName, string* array){
	int index = 0;
	string line;
	ifstream myfile(fileName);
	if (myfile.is_open()) {
		while (getline(myfile, line)) {
			array[index] = line;
			index += 1;
            		outsize += 1;
		}
		myfile.close();
	}
	return array;
}

void writeTxtFile(int size, string* file, string fileName){
//ofstream myfile;
//myfile.open (fileName);

std::ofstream myfile (fileName);

for(int i=0; i<size; i++){
		myfile << file[i];
	}
	myfile.close();
}

void analyseDeckFile(string* File){

		// Initializing foundation table
		hindex = 0; dindex = 0; sindex = 0; cindex = 0; openLimit=0;
		card newcard1;newcard1.number = "00";newcard1.type = "";newcard1.value = "";newcard1.visibleValue = "---";hFoundation[0] = newcard1;
		card newcard2;newcard2.number = "00";newcard2.type = "";newcard2.value = "";newcard2.visibleValue = "---";dFoundation[0] = newcard2;
		card newcard3;newcard3.number = "00";newcard3.type = "";newcard3.value = "";newcard3.visibleValue = "---";sFoundation[0] = newcard3;
		card newcard4;newcard4.number = "00";newcard4.type = "";newcard4.value = "";newcard4.visibleValue = "---";cFoundation[0] = newcard4;

		// Initializing game table
		for (int i=0; i<18; i++){card newcard;newcard.number = "00";newcard.type = "";newcard.value = "";newcard.visibleValue = "   ";pile[0][i] = newcard;}
        for (int i=0; i<18; i++){card newcard;newcard.number = "00";newcard.type = "";newcard.value = "";newcard.visibleValue = "   ";pile[1][i] = newcard;}
        for (int i=0; i<18; i++){card newcard;newcard.number = "00";newcard.type = "";newcard.value = "";newcard.visibleValue = "   ";pile[2][i] = newcard;}
        for (int i=0; i<18; i++){card newcard;newcard.number = "00";newcard.type = "";newcard.value = "";newcard.visibleValue = "   ";pile[3][i] = newcard;}
        for (int i=0; i<18; i++){card newcard;newcard.number = "00";newcard.type = "";newcard.value = "";newcard.visibleValue = "   ";pile[4][i] = newcard;}
		for (int i=0; i<18; i++){card newcard;newcard.number = "00";newcard.type = "";newcard.value = "";newcard.visibleValue = "   ";pile[5][i] = newcard;}
        for (int i=0; i<18; i++){card newcard;newcard.number = "00";newcard.type = "";newcard.value = "";newcard.visibleValue = "   ";pile[6][i] = newcard;}
		
        // Preparation of main game table and first cards
		for (int i=51; i>=0; i--){card newcard;newcard.number = File[i].substr(1,2);newcard.type = File[i].substr(0,1);newcard.value = File[i].substr(0,3);newcard.visibleValue = "***";cards[51-i] = newcard;}
		cards[0].visibleValue = cards[0].value; cards[7].visibleValue = cards[7].value; cards[13].visibleValue = cards[13].value; cards[18].visibleValue = cards[18].value; cards[22].visibleValue = cards[22].value; cards[25].visibleValue = cards[25].value; cards[27].visibleValue = cards[27].value;

		pile[0][0] = cards[0]; pile[1][0] = cards[1]; pile[2][0] = cards[2]; pile[3][0] = cards[3]; pile[4][0] = cards[4]; pile[5][0] = cards[5]; pile[6][0] = cards[6];
		pile[1][1] = cards[7]; pile[2][1] = cards[8]; pile[3][1] = cards[9]; pile[4][1] = cards[10]; pile[5][1] = cards[11]; pile[6][1] = cards[12];
		pile[2][2] = cards[13]; pile[3][2] = cards[14]; pile[4][2] = cards[15]; pile[5][2] = cards[16]; pile[6][2] = cards[17];
		pile[3][3] = cards[18]; pile[4][3] = cards[19]; pile[5][3] = cards[20]; pile[6][3] = cards[21];
		pile[4][4] = cards[22]; pile[5][4] = cards[23]; pile[6][4] = cards[24];
		pile[5][5] = cards[25]; pile[6][5] = cards[26];
		pile[6][6] = cards[27];

		for(int i=0; i<3; i++){
			card newcard;newcard.number = "00";newcard.type = "";newcard.value = "";newcard.visibleValue = "   ";stockPlace[i] = newcard;
		}

        cards[28].visibleValue = "---"; cards[29].visibleValue = "---"; cards[30].visibleValue = "---"; 
		stockPlace[0] = cards[28]; stockPlace[1] = cards[29]; stockPlace[2] = cards[30];

        index = 27; lastIndex = 0; secondIndex = 0; secIndex = 0; limiter = 0; passed = false; indexesIndex = 0; checkIndexesIndex = 0; newindex = 51; lastIndex = 51;
		pile1index = 0; pile2index = 1; pile3index = 2; pile4index = 3; pile5index = 4; pile6index = 5; pile7index = 6; rem=0; m=0; sec2=0; sec2index =100; checker = true; q=0;

	}

    // This method writes game table to output file
    string printGameTable(){
        string table;
        table ="\n@@@ " + stockPlace[0].visibleValue + " " + stockPlace[1].visibleValue + " " + stockPlace[2].visibleValue + "         " + hFoundation[hindex].visibleValue + " " + dFoundation[dindex].visibleValue + " " + sFoundation[sindex].visibleValue + " " + cFoundation[cindex].visibleValue + "\n";
        for(int i=0; i<18; i++){
            if (((pile[0][i].visibleValue).compare("   ") == 0) && ((pile[1][i].visibleValue).compare("   ") == 0) && ((pile[2][i].visibleValue).compare("   ") == 0) && ((pile[3][i].visibleValue).compare("   ") == 0) && ((pile[4][i].visibleValue).compare("   ") == 0) && ((pile[5][i].visibleValue).compare("   ") == 0) && ((pile[6][i].visibleValue).compare("   ") == 0)){
                
            } else {
                table += (pile[0][i].visibleValue + "   " + pile[1][i].visibleValue + "   " + pile[2][i].visibleValue + "   " + pile[3][i].visibleValue + "   " + pile[4][i].visibleValue + "   " + pile[5][i].visibleValue + "   " + pile[6][i].visibleValue + "\n");
            }
        }
        return table;
    }

    // This method finds given piles last items index number
    int findLastIndex(int pile_num){
        if (pile_num == 0){return pile1index;}
        if (pile_num == 1){return pile2index;}
        if (pile_num == 2){return pile3index;}
        if (pile_num == 3){return pile4index;}
        if (pile_num == 4){return pile5index;}
        if (pile_num == 5){return pile6index;}
        if (pile_num == 6){return pile7index;}
        return 0;
    }

    // This method edis last index of given pile
    void editLastIndex(int pile_num, int value){
        if (pile_num == 0){pile1index = pile1index + value;}
        if (pile_num == 1){pile2index = pile2index + value;}
        if (pile_num == 2){pile3index = pile3index + value;}
        if (pile_num == 3){pile4index = pile4index + value;}
        if (pile_num == 4){pile5index = pile5index + value;}
        if (pile_num == 5){pile6index = pile6index + value;}
        if (pile_num == 6){pile7index = pile7index + value;}
    }

    // Game method, opens 3 cars from stock
    void openFromStock(){
        if(secondIndex != 8){
            if((stockMoveNumber % 3 == 0) && (rem == 3)){
                if(!stockPlace[2].visibleValue.compare("") == 0){   
                    cards[newindex] = stockPlace[0];
                    cards[newindex+1] = stockPlace[1];
                    cards[newindex+2] = stockPlace[2];
                    newindex += 3;
                    sec2 +=3;
                }
            }
            if(stockMoveNumber % 3 == 1){
                if(!stockPlace[1].visibleValue.compare("") == 0){   
                    cards[newindex] = stockPlace[0];
                    newindex += 1;
                    sec2 +=1;
                }
            }
            if(stockMoveNumber % 3 == 2){
                if(!stockPlace[0].visibleValue.compare("") == 0){   
                    cards[newindex] = stockPlace[0];
                    cards[newindex+1] = stockPlace[1];
                    newindex += 2;
                    sec2 +=2;
                }
            }
            stockPlace[0] = cards[index+1];
            stockPlace[0].visibleValue = stockPlace[0].value;
            stockPlace[1] = cards[index+2];
            stockPlace[1].visibleValue = stockPlace[1].value;
            stockPlace[2] = cards[index+3];
            stockPlace[2].visibleValue = stockPlace[2].value;
            index += 3;stockMoveNumber = 3;secondIndex += 1;rem = 3;
        } else if ((secondIndex = 8) || (secondIndex = sec2index)){
            if (checker){
                if(sec2 % 3 == 0){
                    sec2index = (sec2 / 3);
                } else {
                    sec2index = (sec2 / 3)+1;
                }
                checker = false;sec2index += 8;
            }
            if(!passed){
                if((stockMoveNumber % 3 == 0) && (rem == 3)){
                    if(!stockPlace[2].visibleValue.compare("") == 0){   
                        cards[newindex] = stockPlace[0];
                        cards[newindex+1] = stockPlace[1];
                        cards[newindex+2] = stockPlace[2];
                        newindex += 3;
                    }
                }
                if(stockMoveNumber % 3 == 1){
                    if(!stockPlace[1].visibleValue.compare("") == 0){   
                        cards[newindex] = stockPlace[0];
                        newindex += 1;
                    }
                }
                if(stockMoveNumber % 3 == 2){
                    if(!stockPlace[0].visibleValue.compare("") == 0){   
                        cards[newindex] = stockPlace[0];
                        cards[newindex+1] = stockPlace[1];
                        newindex += 2;
                    }
                }
                stockPlace[0] = cards[index+1];
                stockPlace[0].visibleValue = "---";
                stockPlace[1] = cards[index+2];
                stockPlace[1].visibleValue = "---";
                stockPlace[2] = cards[index+3];
                stockPlace[2].visibleValue = "---";
                passed = true;stockMoveNumber = 3;secondIndex += 1;
            }
        }    
    }

    // This method moves last item of pile to foundation
    void moveToFoundationPile(int pile_num){
        int x = findLastIndex(pile_num);
        card copy = pile[pile_num][x];
        string val = copy.visibleValue.substr(0,1);
        if (val.compare("H") == 0){
            hFoundation[hindex+1]=copy;
            hindex +=1;
            pile[pile_num][x].visibleValue = "   ";
            editLastIndex(pile_num, -1);
        }
        if (val.compare("S") == 0){
            sFoundation[sindex+1]=copy;
            sindex +=1;
            pile[pile_num][x].visibleValue = "   ";
            editLastIndex(pile_num, -1);
        }
        if (val.compare("D") == 0){
            dFoundation[dindex+1]=copy;
            dindex +=1;
            pile[pile_num][x].visibleValue = "   ";
            editLastIndex(pile_num, -1);
        }
        if (val.compare("C") == 0){
            cFoundation[cindex+1]=copy;
            cindex +=1;
            pile[pile_num][x].visibleValue = "   ";
            editLastIndex(pile_num, -1);
        }
    }

    // This method finds ideal card in foundation and moves it to waste.
    void moveToFoundationWaste(){
        if (stockMoveNumber % 3 == 0){
            card copys = stockPlace[2];
            string val = copys.visibleValue.substr(0,1);
            if (val.compare("H") == 0){hFoundation[hindex+1]=copys; hindex += 1;}
            if (val.compare("D") == 0){dFoundation[dindex+1]=copys; dindex += 1;}
            if (val.compare("S") == 0){sFoundation[sindex+1]=copys; sindex += 1;}
            if (val.compare("C") == 0){cFoundation[cindex+1]=copys; cindex += 1;}
            card newcard;
            newcard.number = "00"; newcard.type = ""; newcard.value = ""; newcard.visibleValue = "---";
            stockPlace[2] = newcard;
            stockMoveNumber+=1;
        }
        else if (stockMoveNumber % 3 == 1){
            card copys = stockPlace[1];
            string val = copys.visibleValue.substr(0,1);
            if (val.compare("H") == 0){hFoundation[hindex+1]=copys; hindex += 1;}
            if (val.compare("D") == 0){dFoundation[dindex+1]=copys; dindex += 1;}
            if (val.compare("S") == 0){sFoundation[sindex+1]=copys; sindex += 1;}
            if (val.compare("C") == 0){cFoundation[cindex+1]=copys; cindex += 1;}
            card newcard;
            newcard.number = "00"; newcard.type = ""; newcard.value = ""; newcard.visibleValue = "---";
            stockPlace[1] = newcard;
            stockMoveNumber+=1;
        }
        else if (stockMoveNumber % 3 == 2){
            card copys = stockPlace[0];
            string val = copys.visibleValue.substr(0,1);
            if (val.compare("H") == 0){hFoundation[hindex+1]=copys; hindex += 1;}
            if (val.compare("D") == 0){dFoundation[dindex+1]=copys; dindex += 1;}
            if (val.compare("S") == 0){sFoundation[sindex+1]=copys; sindex += 1;}
            if (val.compare("C") == 0){cFoundation[cindex+1]=copys; cindex += 1;}
            card newcard;
            newcard.number = "00"; newcard.type = ""; newcard.value = ""; newcard.visibleValue = "---";
            stockPlace[0] = newcard;
            stockMoveNumber+=1;
        }
    }

    // This method finds last item of given pile and moves it to given pile.
    void movePile(int source_pile_num, int source_pile_card_index, int destination_pile_num){
        try{
            card array[source_pile_card_index+1];
            int arrayOfA[source_pile_card_index+1];
            int arrayOfB[source_pile_card_index+1];
            int arraym[source_pile_card_index+1];
            for(int i=0; i<=source_pile_card_index; i++){
                int y = findLastIndex(source_pile_num);
                card copy = pile[source_pile_num][y];
                array[i] = copy;
                array[i].visibleValue = copy.visibleValue;
                arraym[i] = y;
                int x = findLastIndex(destination_pile_num);
                int a; int b;
                a = std::stoi(pile[destination_pile_num][x].visibleValue.substr(1,2));
                b = std::stoi(copy.visibleValue.substr(1,2));
                arrayOfA[i] = a;
                arrayOfB[i] = b;
                editLastIndex(source_pile_num, -1);
            }
            editLastIndex(source_pile_num, +(source_pile_card_index+1));
            for(int i=0; i<=source_pile_card_index; i++){ 
                if (arrayOfA[i] > arrayOfB[i]) {
                    pile[source_pile_num][arraym[i]].visibleValue = "   ";
                    editLastIndex(source_pile_num, -1);
                    int x = findLastIndex(destination_pile_num);
                    pile[destination_pile_num][x+1].value = array[source_pile_card_index-i].visibleValue;
                    pile[destination_pile_num][x+1].visibleValue = array[source_pile_card_index-i].visibleValue;
                    editLastIndex(destination_pile_num, +1);
                    
                } 
                else {}
            }
        } catch (const std::exception& e){
            if (findLastIndex(destination_pile_num) == -1){ 
                int y = findLastIndex(source_pile_num);
                card copy = pile[source_pile_num][y-source_pile_card_index];
                int x = findLastIndex(destination_pile_num);
                pile[destination_pile_num][0].value = copy.value;
                pile[destination_pile_num][0].visibleValue = copy.value;
                editLastIndex(destination_pile_num, +1);
                pile[source_pile_num][y].visibleValue = "   ";
                editLastIndex(source_pile_num, -1);
            }
        }  
    }            
                
    // This method finds ideal card and moves it to given destination nums' waste.
    void moveWaste(int destination_pile_num){
        if (stockMoveNumber % 3 == 0){
            card copy = stockPlace[2];
            int x = findLastIndex(destination_pile_num);
            pile[destination_pile_num][x+1] = copy;
            editLastIndex(destination_pile_num, +1);
            card newcard;
            newcard.number = ""; newcard.type = ""; newcard.value = ""; newcard.visibleValue = "---";
            stockPlace[2] = newcard;
            stockMoveNumber+=1;
            rem -= 1;
        }
        else if (stockMoveNumber % 3 == 1){
            card copy = stockPlace[1];
            int x = findLastIndex(destination_pile_num);
            pile[destination_pile_num][x+1] = copy;
            editLastIndex(destination_pile_num, +1);
            card newcard;
            newcard.number = ""; newcard.type = ""; newcard.value = ""; newcard.visibleValue = "---";
            stockPlace[1] = newcard;
            stockMoveNumber+=1;
            rem -= 1;
        }
        else if (stockMoveNumber % 3 == 2){
            card copy = stockPlace[0];
            int x = findLastIndex(destination_pile_num);
            pile[destination_pile_num][x+1] = copy;
            editLastIndex(destination_pile_num, +1);
            card newcard;
            newcard.number = ""; newcard.type = ""; newcard.value = ""; newcard.visibleValue = "---";
            stockPlace[0] = newcard;
            stockMoveNumber+=1;
            rem -= 1;
        }
    }

    void moveFoundation(int source_foundation_num, int destination_pile_num){
        // Sorry this method doesnt exist because of lots of exams and quizzes :(
    }

    // Open method makes cars value visible.
    void open(int pileNumber){
        int x = findLastIndex(pileNumber);
        pile[pileNumber][x].visibleValue = pile[pileNumber][x].value;
    }

    // Exit game and finish execution.
    void exit() {
        outputFile[outputSize] = ("\nGame Over!\n");
        outputSize += 1;
        exit(0);
        
    }

// Analyse command file line by line and find correct methods to execute.
void analyseCommandFile(string item){
		if((item.substr(0,9)).compare("move pile") == 0){
			movePile((std::stoi(item.substr(10,1))), (std::stoi(item.substr(12,1))), (std::stoi(item.substr(14,1))));
		}
		if((item.substr(0,10)).compare("move waste") == 0){
			moveWaste((std::stoi(item.substr(11,1))));
		}
		if((item.substr(0,15)).compare("move foundation") == 0){
			moveFoundation((std::stoi(item.substr(16,1))), (std::stoi(item.substr(18,1))));
		}
		if((item.substr(0,18)).compare("move to foundation") == 0){
			if ((item.substr(19,4)).compare("pile") == 0){
				moveToFoundationPile(std::stoi(item.substr(24,1)));
			}
			else if ((item.substr(19,5)).compare("waste") == 0){
				moveToFoundationWaste();
			}
		}
	if((item.substr(0,4)).compare("open") == 0){
		if((item.substr(0,15)).compare("open from stock") == 0){
			openFromStock();
		}
		else{
			open(std::stoi(item.substr(5,1)));
		}
	}
}
		
int main(int argc, char* argv[]) {
	deck_file_name = argv[1];
	command_file_name = argv[2];
	output_file_name = argv[3];

    	string* commandFile = readLineByLine2(command_file_name, file1);
    	string* deckFile = readLineByLine1(deck_file_name, file2);
	
	analyseDeckFile(deckFile);
	outputFile[outputSize] = printGameTable();
    outputSize+=1; 

    for (int j=0; j<outsize; j++){
	try{
        analyseCommandFile(commandFile[j]);
        outputFile[outputSize] = ("\n" + commandFile[j] + "\n\n" + "****************************************" + "\n");
        outputSize+=1;
        outputFile[outputSize] = printGameTable();
        outputSize+=1; 
	}catch(int i){}
    }

    if ((hFoundation[hindex].visibleValue.compare("H13")) && (dFoundation[dindex].visibleValue.compare("D13")) && (sFoundation[sindex].visibleValue.compare("S13")) && (cFoundation[cindex].visibleValue.compare("C13"))){
		outputFile[outputSize] = "You Win!";
		outputSize+=1;
	}

	writeTxtFile(outputSize, outputFile, output_file_name);
    return 0;
}

