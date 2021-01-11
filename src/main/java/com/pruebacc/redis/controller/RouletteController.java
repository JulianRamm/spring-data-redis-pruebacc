package com.pruebacc.redis.controller;

import com.pruebacc.redis.OperationState;
import com.pruebacc.redis.entity.Bet;
import com.pruebacc.redis.entity.Roulette;
import com.pruebacc.redis.entity.Summary;
import com.pruebacc.redis.repository.RouletteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/*
 *Controller that exposes the different services offered by the API
 */
@RestController
@RequestMapping("/roulette")
public class RouletteController {
    /*
     *Object that gives access to all the methods to modify the redis database
     */
    @Autowired
    private RouletteDao rouletteDao;
    /*
     *Post operation that creates a new Roulette. Returns the id of the roulette created and the 200 HTTP status
     */
    @PostMapping("/createRoulette")
    public ResponseEntity<String> createRoulette(){
       Roulette roulette = rouletteDao.createNewRoulette();

       return new ResponseEntity<>("{\"id\" :" +roulette.getId()+"}", HttpStatus.OK);
    }
    /*
     *Put operation that given the id of a roulette, opens the roulette to new bets. Returns a message that says if the
     *operation was successful or failed because the roulette with the given id does not exists
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> activateRoulette(@PathVariable Integer id){
        Roulette roulette = rouletteDao.findRouletteById(id);
        if(roulette != null){
            roulette.setActive(true);
            rouletteDao.updateRoulette(roulette);

            return new ResponseEntity<>("{\"message\" : "+rouletteDao.updateRoulette(roulette)+"}", HttpStatus.OK);
        }

        return new ResponseEntity<>("{\"message\" : \"" + OperationState.FAILED.getViewName() +"\"}", HttpStatus.OK);
    }
    /*
     *Post operation that closes a particular roulette to next bets. Sets the status of the roulette and updates the roulette
     */
    @PostMapping("/closeRoulette/{id}")
    public Summary closeRoulette(@PathVariable Integer id){
        Roulette roulette = rouletteDao.findRouletteById(id);
        if(roulette != null){
            roulette.setActive(false);
            rouletteDao.updateRoulette(roulette);

            return rouletteDao.closeBetsOnRoulette(roulette);
        }

        return null;
    }
    /*
     *Post operation that creates a new bet on a particular roulette. Firsts checks if the bet is valid. Checking if
     *the bet is only to a number or to a color. If the bet is valid, it creates the new bet
     */
    @PostMapping
    public ResponseEntity<String> makeABet(@RequestBody Bet bet, @RequestHeader("userId") Integer userId){
        String response = OperationState.SUCCESSFUL.getViewName();
        Roulette roulette = rouletteDao.findRouletteById(bet.getRouletteId());
        if(roulette==null){
            response = OperationState.ROULETTE_NON_EXISTENT.getViewName();
        }
        else{
            if(!roulette.getActive()){
                response = OperationState.ROULETTE_CLOSED.getViewName();
            }
            else{
                if((bet.getBlackBet() && bet.getRedBet()) || ((bet.getBlackBet() || bet.getRedBet()) && bet.getNumberBet()!=-1)){
                    response = OperationState.BET_NOT_ALLOWED.getViewName();
                }
                else if(!(bet.getBlackBet() || bet.getRedBet()) && bet.getNumberBet() <0 || bet.getNumberBet() >36){
                    response = OperationState.BET_NUMBER_ERROR.getViewName();
                }
                else if(bet.getBet() <1 || bet.getBet()>10000){
                    response = OperationState.BET_QUANTITY_ERROR.getViewName();
                }
                else{
                    rouletteDao.makeABetOnRoulette(roulette, bet, userId);
                }
            }
        }

        return new ResponseEntity<>("{\"message\" : \"" + response +"\"}", HttpStatus.OK);
    }
    /*
     *Get operation that returns all the roulettes
     */
    @GetMapping
    public List<Roulette> getAll(){

        return rouletteDao.getAllRoulettes();
    }
    /*
     *Get operation that returns a particular roulette given the id
     */
    @GetMapping("/{id}")
    public Roulette findRoulette(@PathVariable Integer id){

        return rouletteDao.findRouletteById(id);
    }
    /*
     *Delete operation that deletes a particular roulette given the id
     */
    @DeleteMapping("/{id}")
    public String remove(@PathVariable Integer id){

        return rouletteDao.deleteRoulette(id);
    }
}
