
#include <stdio.h>
#include <iostream>
#include <fstream>
#include <string>
#include <string.h> 

using namespace std;

string inputFile[250];

string* readLineByLine(string fileName, string* array){
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

void writeTxtFile(int size, string* file, string fileName){
std::ofstream myfile (fileName);
for(int i=0; i<size; i++){
		myfile << file[i];
	}
	myfile.close();
}

template<class T>
struct node {
	node<T>* next;
    	node<T>* lastn;
	T data;
};

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
    void remove(int index){
        size -= 1;
        struct node<T>* temp1 = first;
        if(index == 1){
            first = temp1->next;
            free(temp1);
            return;
        }
        for(int i=0; i<index-2; i++)
            temp1 = temp1 -> next;
        struct node<T>* temp2 = temp1 -> next;
        temp1 -> next = temp2 -> next;
        free(temp2);
    }
	T get(int index) {
		if(index == 0) {
			return this->first->data;
		} else {
			node<T>* curr = this->first;
			for(int i = 0; i < index; ++i) {
				curr = curr->next;
			}
			return curr->data;
		}
	}
	T operator[](int index) {
		return get(index);
	}
};

class User{
    public:
        int userId;
        string userName;
};

class Movie{
    public:
        int movieId;
        string movieTitle;
        int year;
};

LinkedList<User> userList;
LinkedList<Movie> movieList;
LinkedList<Movie> checkoutedMovieList;
LinkedList<int> movieIDList;
LinkedList<int> checkedMovieList;

//int id = 0;
class LibrarySystem{
    public:
    //LibrarySystem();
    //~LibrarySystem();
    void addMovie (const int movieId, const string movieTitle, const int year){
        bool checker = false;
        for(int i=0; i<movieList.size;i++){
            if(movieList[i].movieId == movieId){
                cout << "Movie " << movieId << " already exists" << endl;
                checker=true;
                return;
            } 
        }  
        if(!checker){
            Movie newMovie;
            newMovie.movieId = movieId; newMovie.movieTitle = movieTitle; newMovie.year = year;
            movieList.add(newMovie);
            cout << "Movie " << movieId << " has been added" << endl;
            return;
        }
    };
    void deleteMovie (const int movieId){
        bool exist = false;
        for(int q=0; q<movieList.size; q++){
            if(movieList[q].movieId == movieId){
                exist=true;
            }
        }
        if(exist){
            bool checked = false;
            for(int j=0; j<1;j++){
                for(int i=0; i<movieIDList.size;i++){
                    if(movieIDList[i] == movieId){
                        cout << "Movie " << movieId << " has been checked out" << endl;
                        checked = true;
                    }
                }
                if(!checked){
                    cout << "Movie " << movieId << " has not been checked out" << endl;
                }
            }
            for(int i=0; i<movieList.size;i++){
                if(movieList[i].movieId == movieId){
                    movieList.remove(i+1);
                    cout << "Movie " << movieId << " has been deleted" << endl;
                    return;
                }
            }
        } else if(!exist) {
            cout << "Movie " << movieId << " does not exist" << endl;
        }
    };
    void addUser (const int userId, const string userName){
        bool checker = false;
        for(int i=0; i<userList.size;i++)
        
        {
            if(userList[i].userId == userId){
                cout << "User " << userId << " already exists" << endl;
                checker = true;
                return;
            } 
        }  

        if(!checker){
            User newUser;
            newUser.userId = userId; newUser.userName = userName;
            userList.add(newUser);
            cout << "User " << userId << " has been added" << endl;
            return;
        }
    };
    void deleteUser (const int userId){
        bool exist = false;
        for(int q=0; q<userList.size; q++){
            if(userList[q].userId == userId){
                exist=true;
            }
        }
        if(exist){
            for(int i=0; i<userList.size;i++){
                if(userList[i].userId == userId){
                    userList.remove(i+1);
                    cout << "User " << userId << " has been deleted" << endl;
                    return;
                }
            }
        } else if(!exist) {
            cout << "User " << userId << " does not exist" << endl;
        }
    };
    void checkOutMovie (const int movieId, const int userId){
        bool checker = false;
        for(int j=0; j<1;j++){
            for(int i=0; i<movieList.size;i++){
                if(movieList[i].movieId == movieId){
                    checker = true;
                }
            }
            if(!checker){
                cout << "Movie " << movieId << " does not exist for checkout" << endl;
                return;
            }
        }
        bool checker2 = false;
        if(checker){
            for(int i=0; i<userList.size; i++){
                if(userList[i].userId == userId){
                    checker2 = true;
                }
            }
            if(!checker2){
                cout << "User " << userId << " does not exist for checkout" << endl;
                return;
            }
        }
        bool checkem = false;
        for(int i=0; i<movieIDList.size; i++){
            if(movieIDList[i] == movieId){
                checkem = true;
            }
        }
        if(checkem){
            cout << "Movie " << movieId << " does not exist for checkout" << endl;
            return;
        }
        if(!checkem){
            for(int i=0; i<movieList.size;i++){
                if(movieList[i].movieId == movieId){    
                    movieIDList.add(movieId);
                    checkedMovieList.add(userId);
                    cout << "Movie " << movieId << " has been checked out by User " << userId << endl;
                    return;
                }
            }
        }
    };
    void returnMovie (const int movieId){
        if(movieList.size > 1){
            for(int i=0; i<movieList.size;i++){
                if(movieList[i].movieId == movieId){
                    cout << "Movie " << movieId << " has been returned" << endl;
                    return;
                } 
            }  
        }
        cout << "Movie " << movieId << " not exist in the library" << endl;
        return;
    };
    void showAllMovies(){
	bool checker = true;
        cout << "Movie id - Movie name - Year - Status" << endl;
        string info = "";
        for(int i=movieList.size-1; i>=0;i--){
	    checker = true;
            for(int j=0; j<movieIDList.size; j++){
                if(movieList[i].movieId == movieIDList[j]){
                    cout << movieList[i].movieId << " " << movieList[i].movieTitle << " " << movieList[i].year << " Checked out by User " << checkedMovieList[j] << endl;
                    checker = false;
                }
            }
	if (checker) {
            cout << movieList[i].movieId << " " << movieList[i].movieTitle << " " << movieList[i].year << " Not checked out" << endl;
	}
        }
    };
    void showMovie(const int movieId){
        bool checker = false;
        for(int i=movieList.size-1; i>=0;i--){
            if(movieList[i].movieId == movieId){
                checker = true;
            }
            for(int j=0; j<movieIDList.size; j++){
                if((movieId == movieIDList[j]) & (movieIDList[j] == movieList[i].movieId)){
                    cout << movieId << " " << movieList[i].movieTitle << " " << movieList[i].year << " Checked out by User " << checkedMovieList[j] << endl;
                    return;
                }
            }
            if(checker){
                cout << movieId << " " << movieList[i].movieTitle << " " << movieList[i].year << " Not checked out" << endl;
                break;
            }
        }
        if(!checker){
            cout << "Movie with the id " << movieId << " does not exist" << endl;
        }
    };
    void showUser(const int userId){
        if(userList.size > 1){
            for(int i=0; i<userList.size;i++){
                if(userList[i].userId == userId){
                    cout << "User id: " << userId << " User name: " << userList[i].userName << endl;
                }
            }
        }
        cout << "User " << userId << " checked out the following Movies:" << endl;
        cout << "Movie id - Movie name - Year" << endl;
        for(int i=0; i<checkedMovieList.size;i++){
            if(checkedMovieList[i] == userId){
                for(int j=0; j<movieList.size;j++){
                    if(movieList[j].movieId == movieIDList[i]){
                        cout << movieIDList[i] << " " << movieList[j].movieTitle << " " << movieList[j].year << endl;
                    }
                }
            }
        }
    };
};

LibrarySystem librarySystem;
void analyseCommandFile(string item, bool checker){
   // This method analyses command file line by line and find correct methods to execute.
   if((item.substr(0,8)).compare("addMovie") == 0){
      if(checker){cout << "===addMovie() method test===" << endl;}
      std::string s = item; std::string delimiter = "\t"; std::string token;
      int indx = 0; int movieID = 0; string movieTitle = ""; int movieYear = 0; size_t pos = 0;
      while ((pos = s.find(delimiter)) != std::string::npos) {
         token = s.substr(0, pos);
         if(indx==1){movieID = std::stoi(token);}
         if(indx==2){movieTitle = token;}
         s.erase(0, pos + delimiter.length());
         indx += 1;
      }
      librarySystem.addMovie(movieID, movieTitle, std::stoi(s));
   }
   if((item.substr(0,11)).compare("deleteMovie") == 0){
      if(checker){cout << "===deleteMovie() method test===" << endl;}
      std::string s = item; std::string delimiter = "\t"; std::string token; size_t pos = 0; int indx = 0;
      while ((pos = s.find(delimiter)) != std::string::npos) {
         token = s.substr(0, pos);
         s.erase(0, pos + delimiter.length());
         indx += 1;
      }
      librarySystem.deleteMovie(std::stoi(s));
   }
   if((item.substr(0,7)).compare("addUser") == 0){
      if(checker){cout << "===addUser() method test===" << endl;}
      std::string s = item; std::string delimiter = "\t"; std::string token;
      int indx = 0; int userID = 0; string movieTitle = ""; size_t pos = 0;
      while ((pos = s.find(delimiter)) != std::string::npos) {
         token = s.substr(0, pos);
         if(indx==1){userID = std::stoi(token);}
         s.erase(0, pos + delimiter.length());
         indx += 1;
      }
      librarySystem.addUser(userID, s);
   }
   if((item.substr(0,10)).compare("deleteUser") == 0){
      if(checker){cout << "===deleteUser() method test===" << endl;}
      std::string s = item; std::string delimiter = "\t"; std::string token; size_t pos = 0; int indx = 0;
      while ((pos = s.find(delimiter)) != std::string::npos) {
         token = s.substr(0, pos);
         s.erase(0, pos + delimiter.length());
         indx += 1;
      }
      librarySystem.deleteUser(std::stoi(s));
   }
   if((item.substr(0,13)).compare("checkoutMovie") == 0){
      if(checker){cout << "===checkOutMovie() method test===" << endl;}
      std::string s = item; std::string delimiter = "\t"; std::string token;
      int indx = 0; int movieID = 0; string movieTitle = ""; size_t pos = 0;
      while ((pos = s.find(delimiter)) != std::string::npos) {
         token = s.substr(0, pos);
         if(indx==1){movieID = std::stoi(token);}
         s.erase(0, pos + delimiter.length());
         indx += 1;
      }
      cout << "********* " << movieID << " " << s << endl;
      librarySystem.checkOutMovie(movieID, std::stoi(s));
   }
   if((item.substr(0,8)).compare("showUser") == 0){
      if(checker){cout << "===showUser() method test===" << endl;}
      std::string s = item; std::string delimiter = "\t"; std::string token; size_t pos = 0; int indx = 0;
      while ((pos = s.find(delimiter)) != std::string::npos) {
         token = s.substr(0, pos);
         s.erase(0, pos + delimiter.length());
         indx += 1;
      }
      librarySystem.showUser(std::stoi(s));
   }
   if((item.substr(0,9)).compare("showMovie") == 0){
      if(checker){cout << "===showMovie() method test===" << endl;}
      std::string s = item; std::string delimiter = "\t"; std::string token; size_t pos = 0; int indx = 0;
      while ((pos = s.find(delimiter)) != std::string::npos) {
         token = s.substr(0, pos);
         s.erase(0, pos + delimiter.length());
         indx += 1;
      }
      librarySystem.showMovie(std::stoi(s));
   }
   if((item.substr(0,12)).compare("showAllMovie") == 0){
      if(checker){cout << "===showAllMovie() method test===" << endl;}
      librarySystem.showAllMovies();
   }
   if((item.substr(0,11)).compare("returnMovie") == 0){
      if(checker){cout << "===returnMovie() method test===" << endl;}
      std::string s = item; std::string delimiter = "\t"; std::string token; size_t pos = 0; int indx = 0;
      while ((pos = s.find(delimiter)) != std::string::npos) {
         token = s.substr(0, pos);
         s.erase(0, pos + delimiter.length());
         indx += 1;
      }
      librarySystem.returnMovie(std::stoi(s));
   }
}   

int main(int argc, char* argv[]) {
   std::ofstream outfile (argv[2]);
   string* commandFile = readLineByLine(argv[1], inputFile);
   cout << "===Movie Library System===" << endl << endl;
   for(int i=0; i<100; i++){   
      bool checker = true;
      bool checker2 = false;
      if((98>i) & (i>=1)){
          string a = commandFile[i-1].substr(0,7);
          string b = commandFile[i].substr(0,7);
          string c = commandFile[i+1].substr(0,7);
          checker = a.compare(b) != 0;
          checker2 = b.compare(c) != 0;
      }
      analyseCommandFile(commandFile[i], checker);
      if(checker2){cout << endl;}
   }
   return 0;
}




