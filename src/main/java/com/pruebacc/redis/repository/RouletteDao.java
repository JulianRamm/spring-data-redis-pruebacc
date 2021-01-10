package com.pruebacc.redis.repository;

import com.pruebacc.redis.entity.Roulette;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RouletteDao {
    @Autowired
    private RedisTemplate template;
    public static final String HASH_KEY = "Roulette";
    public Roulette save(Roulette roulette){
        template.opsForHash().put(HASH_KEY, roulette.getId(), roulette);

        return roulette;
    }
    public List<Roulette> findAll(){

        return template.opsForHash().values(HASH_KEY);
    }
    public Roulette findRouletteById(int id){

        return (Roulette) template.opsForHash().get(HASH_KEY, id);
    }
    public String deleteRoulette(int id){
        template.opsForHash().delete(id);

        return "Roulette removed";
    }
}
