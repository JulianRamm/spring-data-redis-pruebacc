package com.pruebacc.redis.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Roulette")
public class Roulette implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private Boolean active;
    private List<Cell> cells;
}
