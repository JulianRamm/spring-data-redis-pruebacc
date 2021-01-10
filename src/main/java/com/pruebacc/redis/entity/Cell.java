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
    private Integer number;
    private Boolean red;
    private Set<Bet> bets;
}
