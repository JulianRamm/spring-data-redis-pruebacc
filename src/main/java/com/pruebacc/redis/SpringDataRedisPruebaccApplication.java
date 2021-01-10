package com.pruebacc.redis;

import com.pruebacc.redis.entity.Roulette;
import com.pruebacc.redis.repository.RouletteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/roulette")
public class SpringDataRedisPruebaccApplication {
	@Autowired
	private RouletteDao rouletteDao;
	@PostMapping
	public Roulette save(@RequestBody Roulette roulette){
		return rouletteDao.save(roulette);
	}
	@GetMapping
	public List<Roulette> getAll(){
		return rouletteDao.findAll();
	}
	@GetMapping("/{id}")
	public Roulette findRoulette(@PathVariable int id){
		return rouletteDao.findRouletteById(id);
	}
	@DeleteMapping("/{id}")
	public String remove(@PathVariable int id){
		return rouletteDao.deleteRoulette(id);
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringDataRedisPruebaccApplication.class, args);
	}

}
