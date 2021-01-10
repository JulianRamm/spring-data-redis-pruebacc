package com.pruebacc.redis.repository;

import com.pruebacc.redis.OperationState;
import com.pruebacc.redis.entity.Cell;
import com.pruebacc.redis.entity.Roulette;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Repository
public class RouletteDao {
    private RedisTemplate redisTemplate;
    private HashOperations hashOperations;
    public RouletteDao(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }
    public static final String HASH_KEY = "Roulette";
    public Roulette save(int id){
        Roulette roulette = new Roulette();
        roulette.setId(id);
        roulette.setActive(false);
        List<Cell> cells = new ArrayList<>(37);
        for(int i = 0; i < 37; i++){
            Cell cell = new Cell(i, i % 2 == 0, 0, false, false, new HashSet<>());
            cells.add(cell);
        }
        roulette.setCells(cells);
        hashOperations.put(HASH_KEY, roulette.getId(), roulette);
        return roulette;
    }
    public List<Roulette> findAll(){

        return hashOperations.values(HASH_KEY);
    }
    public Roulette findRouletteById(Integer id){
        System.out.println(hashOperations.get(HASH_KEY, id));
        return (Roulette) hashOperations.get(HASH_KEY, id);
    }
    public String deleteRoulette(Integer id){
        hashOperations.delete(HASH_KEY, id);
        return OperationState.SUCCESSFUL.getViewName();
    }
    public String updateRoulette(Roulette roulette){
        hashOperations.put(HASH_KEY, roulette.getId(), roulette);
        return OperationState.SUCCESSFUL.getViewName();
    }
}
