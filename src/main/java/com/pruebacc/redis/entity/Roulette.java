package com.pruebacc.redis.entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;
import java.util.List;

/*
 *This class represents a roulette. It has a set of cells, 37 cells in total.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Roulette")
public class Roulette implements Serializable {
    /*
     *Serial version UID used to verify that the sender of the serialization and receiver are compatible
     */
    private static final long serialVersionUID = 1L;
    /*
     *Id of the roulette. It's also used as the key when the data is being stored
     */
    @Id
    private Integer id;
    /*
     *Indicates true if the roulette is open to new bets or false on the contrary.
     */
    private Boolean active;
    /*
     *List of cells that are contained inside the roulette. The roulette has 37 cells in total
     */
    private List<Cell> cells;
}
