//
// Created by Ömer Taha on 29.09.2024.
//

#ifndef GAME_H
#define GAME_H
#include <string>
#include "Card.h"

using namespace std;


class Game {
private:
    Card tableu[7][31];
    Card stock[24];
    Card waste[3];
    Card found[4][13];
public:

    Game(const string& filePath);

    //to show the game in terminal
    string toString();
    Card& showStockLast();
    Card& showTableuNLast(int n);
    Card& showWasteLast();
    string wastes();
    string foundes();

    //comment function

    void open(bool stock,int n);
    void moveToFound(bool waste,int n);

    void movePile(int dest,int n,int target);

    void moveWaste(int n);





};



#endif //GAME_H
