package com.pruebacc.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/*
 *This class represents a particular result after the roulette wheel ran
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Result implements Serializable {
    /*
     *The id of the user that made a particular bet
     */
    private Integer userId;
    /*
     *The amount of money that the user earned after the roulette wheel ran. It is equal to 5 times his initial bet if
     *the bet was under a number, 1.8 times his initial bet if the bet was under a color and 0 if the results of the
     *roulette are different from his bet
     */
    private Double earnedMoney;
    /*
     *The bet that this particular user made
     */
    private Bet betMade;
}
