package com.pruebacc.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Bet implements Serializable {
    private Integer numberBet;
    private Boolean redBet;
    private Boolean blackBet;
    private Integer bet;
    private Integer rouletteId;
    private Integer userId;
}
