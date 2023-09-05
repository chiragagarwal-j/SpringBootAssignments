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

import com.learning.athome.entity.cycle.CycleStock;
import com.learning.athome.repository.cycle.CycleStockRepository;

import jakarta.annotation.PostConstruct;

@Controller
public class ImprovedCycleController {

    @Autowired
    private CycleStockRepository cycleStockRepo;

    private List<CycleStock> cycleList;

    @PostConstruct
    public void init() {
        cycleList = new ArrayList<>();
        cycleStockRepo.findAll().forEach(c -> cycleList.add(c));

    }

    @GetMapping("/cycleStock")
    public String rent(Model model) {
        model.addAttribute("cycleList", cycleList);
        return "cycleStock";
    }

    @PostMapping("/borrow")
    public String borrowCycle(@RequestParam int id) {
        Optional<CycleStock> optionalCycle = cycleStockRepo.findById(id);
        if (optionalCycle.isPresent()) {
            CycleStock cycle = optionalCycle.get();
            int currentAvailableCycles = cycle.getAvailableCycles();
            if (currentAvailableCycles > 0) {
                cycle.setAvailableCycles(currentAvailableCycles - 1);
                cycleStockRepo.save(cycle);
                updateCycleList();
            }
        }
        return "redirect:/cycleStock";
    }

    @PostMapping("/return")
    public String returnCycle(@RequestParam int id) {
        Optional<CycleStock> optionalCycle = cycleStockRepo.findById(id);
        if (optionalCycle.isPresent()) {
            CycleStock cycle = optionalCycle.get();
            int currentAvailableCycles = cycle.getAvailableCycles();
            if (currentAvailableCycles > 0) {
                cycle.setAvailableCycles(currentAvailableCycles + 1);
                cycleStockRepo.save(cycle);
                updateCycleList();
            }
        }
        return "redirect:/cycleStock";
    }

    // @PostMapping("/restock")
    // public String restockCycle(@RequestParam("brandId") int id, @RequestParam("restockQuantity") int restockQuantity) {
    //     Optional<CycleStock> optionalCycle = cycleStockRepo.findById(id);

    //     if (optionalCycle.isPresent()) {
    //         CycleStock cycle = optionalCycle.get();
    //         int currentAvailableCycles = cycle.getAvailableCycles();
    //         int newAvailableCycles = currentAvailableCycles + restockQuantity;
    //         cycle.setAvailableCycles(newAvailableCycles);
    //         cycleStockRepo.save(cycle);
    //         updateCycleList();
    //     }

    //     return "redirect:/cycleStock";
    // }

    private void updateCycleList() {
        cycleList.clear();
        cycleStockRepo.findAll().forEach(c -> cycleList.add(c));
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser() {
       
        return "redirect:/register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser() {
       
        return "redirect:/restock";
    }

}
