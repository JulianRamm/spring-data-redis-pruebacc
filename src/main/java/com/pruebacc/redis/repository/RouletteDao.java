package com.pruebacc.redis.repository;

import com.pruebacc.redis.OperationState;
import com.pruebacc.redis.entity.*;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RouletteDao {
    private RedisTemplate redisTemplate;
    private HashOperations hashOperations;
    public RouletteDao(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }
    public static final String HASH_KEY = "Roulette";
    public Integer generateNextId(){
        Set<Integer> keys = hashOperations.keys(HASH_KEY);
        return keys.size()+1;
    }
    public Roulette save(){
        Integer id = generateNextId();
        Roulette roulette = new Roulette();
        roulette.setId(id);
        roulette.setActive(false);
        List<Cell> cells = new ArrayList<>(37);
        for(int i = 0; i < 37; i++){
            Cell cell = new Cell(i, i % 2 == 0, new HashSet<>());
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
    public String makeABetOnRoulette(Roulette roulette, Bet bet, Integer userId){
        bet.setUserId(userId);
        if(!(bet.getBlackBet() || bet.getRedBet()) && bet.getNumberBet()!=-1 ){
            Cell cell = roulette.getCells().get(bet.getNumberBet());
            cell.getBets().add(bet);
        }
        else{
            if(bet.getBlackBet()){
                for(Cell c: roulette.getCells()){
                    if(!c.getRed()){
                        c.getBets().add(bet);
                    }
                }
            }
            else{
                for(Cell c: roulette.getCells()){
                    if(c.getRed()){
                        c.getBets().add(bet);
                    }
                }
            }
        }
        return updateRoulette(roulette);
    }
    public Summary closeBets(Roulette roulette){
        Summary summary = new Summary();
        Set<Result> results = new HashSet<>();
        Random rand = new Random();
        //int winningNumber = rand.nextInt(37);
        int winningNumber =1;
        summary.setWinningNumber(winningNumber);
        List<Cell> cells = roulette.getCells();
        Cell winningCell = cells.get(winningNumber);
        for(Cell cell: cells){
            generateResultsOfACell(cell, winningCell, results);
        }
        summary.setResults(results);
        return summary;
    }
    public void generateResultsOfACell(Cell cell, Cell winningCell, Set<Result> setToAddResults){
        for(Bet bet: cell.getBets()){
            Result res = new Result();
            res.setBetMade(bet);
            res.setUserId(bet.getUserId());
            if(winningCell.getNumber().equals(bet.getNumberBet())){
                res.setEarnedMoney(bet.getBet()*5.0);
            }
            else if(winningCell.getRed().equals(bet.getRedBet())){
                res.setEarnedMoney(bet.getBet()*1.8);
            }
            else{
                res.setEarnedMoney(0.0);
            }
            setToAddResults.add(res);
        }
    }
}
