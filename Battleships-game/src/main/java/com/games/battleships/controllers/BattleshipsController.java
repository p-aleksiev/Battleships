package com.games.battleships.controllers;

import com.games.battleships.models.Square;
import com.games.battleships.services.contracts.BattleshipsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/battleships")
public class BattleshipsController {

    private final BattleshipsService battleshipsService;

    @Autowired
    public BattleshipsController(BattleshipsService battleshipsService) {
        this.battleshipsService = battleshipsService;
    }

    @GetMapping
    public String startGame(Model model){
        model.addAttribute("playerField",battleshipsService.arrangeShips(new int[]{1,2,3,4,6}));
        model.addAttribute("computerField",battleshipsService.arrangeShips(new int[]{1,2,3,4,6}));
        model.addAttribute("emptyField",battleshipsService.createEmptyField());

        return "battleships";
    }

    @PostMapping
    public String greenZone(@ModelAttribute int[] coordinates, Model model){
        return "";
    }
}
