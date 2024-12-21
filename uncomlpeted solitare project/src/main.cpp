#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include "Game.h"

using namespace std;



int main() {
Game game("deck.txt");

        string line;
    cout<<game.toString();

    ifstream commandFile("commands.txt");
    if (!commandFile.is_open()) {
        cerr << "Hata: Komut dosyası açılamadı." << endl;
        return 1;
    }
        while (getline(commandFile,line)) {
            stringstream ss(line);
            string command;

            ss>>command;

            if(command=="move") {
                string second;

                ss>>second;

                if(second=="pile") {
                    int dest,a,target;
                    ss>>dest>>a>>target;
                    game.movePile(dest,a,target);
                }
                else if(second=="to") {
                    string third,fourth;
                    ss>>third>>fourth;
                    if(fourth=="pile") {
                        int n;
                        ss>>n;

                        game.moveToFound(false,n);
                    }
                    else if(fourth=="waste") {
                        game.moveToFound(true,0);
                    }

                }
                else if(second=="waste") {
                    int c;
                    ss>>c;
                    game.moveWaste(c);
                }
            }
            else if(command=="open") {
                string second;
                ss>>second;
                if(second=="from") {
                    game.open(true,0);
                }
                else {
                    int n=stoi(second);
                    game.open(false,n);
                }
            }
            cout<<game.toString();
        }

    commandFile.close();




    return 0;
}
