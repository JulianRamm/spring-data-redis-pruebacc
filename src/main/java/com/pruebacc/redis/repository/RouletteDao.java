package com.pruebacc.redis.repository;

import com.pruebacc.redis.OperationState;
import com.pruebacc.redis.entity.*;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
 *This class implements the different CRUD operations to access, delete, etc. Elements inside the redis database.
 */
@Repository
public class RouletteDao {
    /*
     *Redis template used to access the operations CRUD of the database
     */
    private RedisTemplate redisTemplate;
    /*
     *Object that expose a set of methods that give access to the redis database
     */
    private HashOperations hashOperations;
    /*
     *Constructor that initializes an instance of the RouletteDao class. Also, initializes the redisTemplate object and
     *the hashOperations object
     */
    public RouletteDao(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }
    /*
     *Constant that defines the hash object of which the operations are going to be performed
     */
    public static final String HASH_KEY = "Roulette";
    /*
     *Function that returns the next id that can be used to create a new Roulette. The ids can be generated this way to
     *avoid additional parameters when a new Roulette is created. Also, to avoid the use of ORMs like Hibernate to
     *auto generate the id
     */
    public Integer generateNextId(){
        Set<Integer> keys = hashOperations.keys(HASH_KEY);

        return keys.size()+1;
    }
    /*
     *Method that creates a new Roulette. First, generates a new id that can be used to create the Roulette. Then
     *Initializes the Roulette as inactive and creates 37 cells that are contained inside the roulette. At the moment
     *of creation of the cells, if the number of the cell is even, then the color of this cell is going to be red. Black
     *if the number is odd. To check if a number is even we need to check if it's a multiple of the number 2 with the
     *modulo operation
     */
    public Roulette createNewRoulette(){
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
    /*
     *Method that returns all the roulette's that have been created and stored in the database
     */
    public List<Roulette> getAllRoulettes(){

        return hashOperations.values(HASH_KEY);
    }
    /*
     *Method that returns a particular roulette given the id of the roulette
     */
    public Roulette findRouletteById(Integer id){

        return (Roulette) hashOperations.get(HASH_KEY, id);
    }
    /*
     *Method that deletes a particular roulette given the id of the roulette
     */
    public String deleteRoulette(Integer id){
        hashOperations.delete(HASH_KEY, id);

        return OperationState.SUCCESSFUL.getViewName();
    }
    /*
     *Method that updates or modify an existent roulette
     */
    public String updateRoulette(Roulette roulette){
        hashOperations.put(HASH_KEY, roulette.getId(), roulette);

        return OperationState.SUCCESSFUL.getViewName();
    }
    /*
     *This method allows the creation of a bet on a particular roulette. It receives the user id that made the bet,
     *the bet and the roulette in which the bet is going to be placed. First, checks if the bet is a number bet and if so,
     *it adds this bet on the particular cell that has this number. If the bet is a color bet, adds the bet to each one
     *of the cells that have this color.
     */
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
    /*
     *This method closes the bets on a particular roulette. First, generates a random number between 0 and 36 and chooses
     *that number as the number of the winning cell. After this, generates the results of all of the bets for each one of the cells
     *that are contained inside roulette.
     */
    public Summary closeBetsOnRoulette(Roulette roulette){
        Summary summary = new Summary();
        Set<Result> results = new HashSet<>();
        Random rand = new Random();
        int winningNumber = rand.nextInt(37);
        summary.setWinningNumber(winningNumber);
        List<Cell> cells = roulette.getCells();
        Cell winningCell = cells.get(winningNumber);
        for(Cell cell: cells){
            generateResultsOfACell(cell, winningCell, results);
        }
        summary.setResults(results);

        return summary;
    }
    /*
     *Method that generates the results of a particular cell. For each one of the bets inside the cell, checks if the number bet
     *is equal to the number og the cell that won the game or if the color is equal to the color bet. If none of this conditions are
     *satisfied, the money earned by the user that made the bet is equal to 0.
     */
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
