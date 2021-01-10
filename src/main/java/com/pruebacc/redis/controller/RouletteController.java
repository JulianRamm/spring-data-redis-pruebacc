package com.pruebacc.redis.controller;

import com.pruebacc.redis.OperationState;
import com.pruebacc.redis.entity.Roulette;
import com.pruebacc.redis.repository.RouletteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/roulette")
public class RouletteController {
    @Autowired
    private RouletteDao rouletteDao;
    @PostMapping("/{id}")
    public ResponseEntity<String> save(@PathVariable Integer id){
       Roulette roulette = rouletteDao.save(id);
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
