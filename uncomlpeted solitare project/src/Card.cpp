//
// Created by Ömer Taha on 29.09.2024.
//

#include "Card.h"
#include <sstream>

Card::Card(string s) {
    name = s[0];
    string numberString = s.substr(1);

    number = stoi(numberString);
    isopen = false;
}

Card::Card() {
    name = ' ';
    number = 0;
    isopen = false;
}

string Card::to_string() {
    string numb;
    if(number/10==0) {
        numb="0"+std::to_string(number);
    }
    else {
        numb=std::to_string(number);
    }
    if (isopen) {
        return name+numb+" ";
    }
    else {
        return "@@@ ";
    }

}


void Card::destroycard() {
    isopen = false;
    name = ' ';
    number = 0;
}
