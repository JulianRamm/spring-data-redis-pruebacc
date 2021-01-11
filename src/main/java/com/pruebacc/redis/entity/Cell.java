package com.pruebacc.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Set;
/*
 *This class represents a Cell inside the roulette, a roulette contains 37 cell representing the numbers between 0 and 36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cell implements Serializable{
    /*
     *The number of the cell, this value is within the range of 0 and 36
     */
    private Integer number;
    /*
     *Indicates if the cell is of the color red, false if the cell is black
     */
    private Boolean red;
    /*
     *The set of bets that have been made for this cell. Whether is a number, red, or black bet.
     */
    private Set<Bet> bets;
}
