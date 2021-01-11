package com.pruebacc.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/*
 *Class that represents a bet. A bet contains the information that says if the bet is blue, red, or a number. Also, contains
 * the quantity of money, the user that made the bet and the roulette in which this bet is placed.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Bet implements Serializable {
    /*
     *Indicates the number for which the user made the bet. If it's a red or black bet, not to a number, the value is -1
     */
    private Integer numberBet;
    /*
     *Indicates if the bet that was made is red, false on the contrary
     */
    private Boolean redBet;
    /*
     *Indicates if the bet that was made is black, false on the contrary
     */
    private Boolean blackBet;
    /*
     *The amount of money that the user made on the bet
     */
    private Integer bet;
    /*
     *The id of the roulette in which this bet is placed
     */
    private Integer rouletteId;
    /*
     *The id of the user that made the bet
     */
    private Integer userId;
}
