//
// Created by Ömer Taha on 29.09.2024.
//

#ifndef CARD_H
#define CARD_H
#include <string>

using namespace std;

class Card {
public:
    char name;
    int number=0;
    bool isopen=false;

    Card();
    Card(string s);
    string to_string();
    void destroycard();
};



#endif //CARD_H
