/*
 * Created on Oct 26, 2016 11:25:20 AM
 *
 * Copyright (c) 2016 The Principal Financial Group
 */
package com.adaptionsoft.games.uglytrivia;

public class Player {
    
    private int place;
    private int  purse;
    private boolean isInPenaltyBox;
    private String name;
    
    public Player() {
        super();
    }
    
    public Player(String name) {
        super();
        this.name = name;
        this.place = 0;
        this.purse = 0;
        this.isInPenaltyBox = false;
    }
    
    public void advancePlayerPlace(int roll, int gameBoardSize) {
        place += roll;
        if (place > gameBoardSize - 1){
            place -= gameBoardSize;
        }
        
        System.out.println(name + "'s new location is " + place);
    }


    public int getPlace() {
        return place;
    }
    
    public void setPlace(int place) {
        this.place = place;
    }
    
    public int getPurse() {
        return purse;
    }
    
    public void setPurse(int purse) {
        this.purse = purse;
    }
    
    public boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }
    
    public void setInPenaltyBox(boolean isInPenaltyBox) {
        this.isInPenaltyBox = isInPenaltyBox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
