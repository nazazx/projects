//
// Created by Ömer Taha on 29.09.2024.
//

#include "Game.h"
#include <fstream>
#include "Card.h"
#include <iostream>


Game::Game(const string &filePath) {
    ifstream file(filePath);

    if (!file.is_open()) {
        cerr << "Dosya açılamadı: " << filePath << endl;
        return;
    }
    string line;


    for (int i = 0; i < 7; ++i) {
        for (int j = 0; j <= i; ++j) {
            if (getline(file, line)) {
                Card card(line);
                if (i == j) {
                    card.isopen = true;
                }
                tableu[i][j] = card;
            }
        }
    }


    for (int i = 0; i < 24; i++) {
        if (getline(file, line)) {
            Card card(line);
            stock[i] = card;
        }
    }
}


string Game::toString() {
    string result;

    result+=showStockLast().to_string();
    result+=wastes();
    result+="        ";
    result+=foundes();
    result+="\n";
    result+="\n";

    for (int i=0;i<8;i++) {
        for(int j=0;j<7;j++) {
            if(tableu[j][i].number==0) {
                result+="      ";

            }else {

                result+=tableu[j][i].to_string()+"  ";


            }
        }
        result+="\n";
    }
    return  result;

}

Card& Game::showStockLast() {
    int last=-1;
    for (int i=0;i<24;i++) {
        if(stock[i].number==0) {
            break;
        }
        last=i;
    }
    return stock[last];
}
Card &Game::showTableuNLast(int n) {
    int last = -1;
    for (int i = 0; i < 31; i++) { // Make sure the index range is correct for your tableau size.
        if (tableu[n][i].number == 0) {
            break;
        }
        last = i;
    }
    if (last == -1) {
        cout << "No card found in tableau " << n << endl;
    }
    return tableu[n][last];
}

Card & Game::showWasteLast() {
int last=-1;
for(int i=0;i<3;i++){
if (waste[i].number==0) {
    break;
}
    last=i;
}
    return waste[last];
}

string Game::wastes() {
    string result;
    for (int i=0;i<3;i++) {
        if(waste[i].number==0) {
            result+="--- ";
        }
        else {
            result+=waste[i].to_string();
        }
    }
    return result;
}
string Game::foundes() {
    string result;
    int index1,index2;
    for (int i=0;i<4;i++) {
        if(found[i][0].number==0) {
            result+="--- ";
            continue;
        }
        for (int j=0;j<13;j++) {
            if(found[i][j].number==0) {
                break;
            }
            index1=i;
            index2=j;
        }
        result+=found[index1][index2].to_string();
    }
    return result;
}


void Game::open(bool isStock,int n) {
    if(isStock) {
        int a=0;
        while (a<3) {
            Card &card =showStockLast();
            card.isopen=true;
            waste[a]=card;
            card.destroycard();
            a++;
        }


    }
    else {
        Card &card=showTableuNLast(n);
        if(card.isopen==false) {
            card.isopen=true;
        }
    }


}

void Game::moveToFound(bool fromWaste, int n) {
    Card *card;  // Declare a pointer to a card object

    // Set the card pointer based on whether it's from the waste or tableau
    if (fromWaste) {
        card = &showWasteLast();  // Point to the last card in the waste
    } else {
        card = &showTableuNLast(n);  // Point to the last card in the tableau at column n
    }

    // Determine which foundation pile corresponds to the card's suit
    int index = -1;
    if (card->name == 'H') index = 0;
    else if (card->name == 'D') index = 1;
    else if (card->name == 'S') index = 2;
    else if (card->name == 'C') index = 3;

    if (index == -1) {
        cout << "Invalid card suit." << endl;
        return;
    }

    // Check if the foundation pile is empty (only Ace can start)
    if (card->number == 1) {
        found[index][0] = *card;  // Move the card to the foundation
        card->destroycard();  // Destroy the card from the original location
    } else {
        // Check if the move is valid (number follows the sequence in the foundation)
        int lastIndex = -1;
        for (int i = 0; i < 13; i++) {
            if (found[index][i].number == 0) {
                lastIndex = i;
                break;
            }
        }

        if (lastIndex > 0 && found[index][lastIndex - 1].number + 1 == card->number) {
            found[index][lastIndex] = *card;  // Move the card to the foundation
            card->destroycard();  // Destroy the card from the original location
        } else {
            cout << "Not a valid move to foundation." << endl;
        }
    }
}


void Game::movePile(int dest, int n, int target) {
    int last = -1;

    // Find the top card in the destination column
    for (int i = 0; i < 31; i++) {
        if (tableu[dest][i].number == 0) {
            break;
        }
        last = i;
    }

    // Adjust the last index to the starting card for the move (n cards)
    int startIndex = last - n ;  // Start from this card to move
    if (startIndex < 0) {
        cout << "Invalid move, not enough cards in the column." << endl;
        return;
    }

    // Find the top card in the target column
    int last2 = -1;
    for (int j = 0; j < 31; j++) {
        if (tableu[target][j].number == 0) {
            break;
        }
        last2 = j;
    }

    if(!((tableu[dest][last].number==13)&&(last2==-1))) {
        if (tableu[target][last2].number - 1 != tableu[dest][startIndex].number || !tableu[target][last2].isopen) {
            cout << "Invalid move: cannot stack cards." << endl;
            return;
        }
    }


    // Move the pile of n cards
    for (int i = 0; i <= n; i++) {
        Card &card = tableu[dest][startIndex + i];
        tableu[target][last2 + i + 1] = card;  // Place the card in the target column
        card.destroycard();  // Destroy the card in the source column
    }
}

void Game::moveWaste(int n) {
    Card &card=showWasteLast();
    int last = -1;

    // Find the top card in the destination column
    for (int i = 0; i < 31; i++) {
        if (tableu[n][i].number == 0) {
            break;
        }
        last = i;
    }

    // If the column is empty, only a King can be placed
    if (last == -1) {
        if (card.number == 13) {  // Check if it's a King
            tableu[n][0] = card;
            card.destroycard();
        } else {
            cout << "Only a King can be placed in an empty column." << endl;
        }
        return;
    }


    if(tableu[n][last].number-1==card.number) {
        tableu[n][last+1]=card;
        card.destroycard();
    }

}
