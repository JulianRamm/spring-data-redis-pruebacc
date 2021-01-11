package com.pruebacc.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;
import java.util.Set;
/*
 *This class represents the summary after the roulette was closed. It contains the winning number and the set of results
 *for all the users and their bets
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Roulette")
public class Summary implements Serializable {
    /*
     *The number of the cell that was chosen as the winner cell
     */
    private Integer winningNumber;
    /*
     *The set of results for each one of the users that made a bet
     */
    private Set<Result> results;
}
