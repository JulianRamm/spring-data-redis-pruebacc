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

@RestController
@RequestMapping("/roulette")
public class RouletteController {
    @Autowired
    private RouletteDao rouletteDao;

    @PostMapping("/createRoulette")
    public ResponseEntity<String> save(){
       Roulette roulette = rouletteDao.save();

       return new ResponseEntity<>("{\"id\" :" +roulette.getId()+"}", HttpStatus.OK);
    }
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
    @PostMapping("/closeRoulette/{id}")
    public Summary closeRoulette(@PathVariable Integer id){
        Roulette roulette = rouletteDao.findRouletteById(id);
        if(roulette != null){
            roulette.setActive(false);
            rouletteDao.updateRoulette(roulette);

            return rouletteDao.closeBets(roulette);
        }

        return null;
    }
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
    @GetMapping
    public List<Roulette> getAll(){

        return rouletteDao.findAll();
    }
    @GetMapping("/{id}")
    public Roulette findRoulette(@PathVariable Integer id){

        return rouletteDao.findRouletteById(id);
    }
    @DeleteMapping("/{id}")
    public String remove(@PathVariable Integer id){

        return rouletteDao.deleteRoulette(id);
    }
}
