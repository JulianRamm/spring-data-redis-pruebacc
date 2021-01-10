package com.pruebacc.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cell implements Serializable{
    private int number;
    private boolean red;
    private int bet;
    private boolean colorBet;
    private boolean numberBet;
    private Set<Integer> gamblers;
}
