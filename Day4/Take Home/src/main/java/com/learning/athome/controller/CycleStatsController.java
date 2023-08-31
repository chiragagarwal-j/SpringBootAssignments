package com.learning.athome.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learning.athome.entity.cycle.Cycle;
import com.learning.athome.repository.cycle.cycleRepository;

import jakarta.annotation.PostConstruct;

@Controller
public class CycleStatsController {

	@Autowired
	private cycleRepository cycleRepo;

	private List<Cycle> cycleList;

	@PostConstruct
	public void init() {
		cycleList = new ArrayList<>();
		cycleRepo.findAll().forEach(c -> cycleList.add(c));

	}

	@GetMapping("/cycle")
	public String rent(Model model) {
		model.addAttribute("cycleList", cycleList);
		return "cycle";
	}

	@PostMapping("/checkin")
	public String checkinCycle(@RequestParam int id) {
		Optional<Cycle> optionalCycle = cycleRepo.findById(id);
		if (optionalCycle.isPresent()) {
			Cycle cycle = optionalCycle.get();
			cycle.setAvailable(false);
			cycleRepo.save(cycle);
			updateCycleList();
		}
		return "redirect:/cycle";
	}

	@PostMapping("/checkout")
	public String checkoutCycle(@RequestParam int id) {
		Optional<Cycle> optionalCycle = cycleRepo.findById(id);
		if (optionalCycle.isPresent()) {
			Cycle cycle = optionalCycle.get();
			cycle.setAvailable(true);
			cycleRepo.save(cycle);
			updateCycleList();
		}
		return "redirect:/cycle";
	}

	private void updateCycleList() {
		cycleList.clear();
		cycleRepo.findAll().forEach(c -> cycleList.add(c));
	}
}
