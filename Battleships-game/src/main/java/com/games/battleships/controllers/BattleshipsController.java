package com.games.battleships.controllers;

import com.games.battleships.models.BattleshipsGame;
import com.games.battleships.models.Square;
import com.games.battleships.services.contracts.BattleshipsService;
import com.games.battleships.services.contracts.IOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

import static com.games.battleships.Constants.SHIP_SIZES;


@Controller
@RequestMapping("/battleships")
public class BattleshipsController {

    private final BattleshipsService battleshipsService;
    private final IOService ioService;

    @Autowired
    public BattleshipsController(BattleshipsService battleshipsService, IOService ioService) {
        this.battleshipsService = battleshipsService;
        this.ioService = ioService;
    }

    @GetMapping("/new")
    public String startGame(){

        BattleshipsGame game = battleshipsService.createBattleshipsGame();

        return "redirect:/battleships/" + game.getId();
    }

    @GetMapping("/{gameId}")
    public String currentGame(@PathVariable String gameId, Model model){
        BattleshipsGame game = battleshipsService.getGameById(gameId);

        Square[][] playerField = ioService.deserializeGameField(game.getPlayerField());
        Square[][] computerField = ioService.deserializeGameField(game.getComputerField());
        Square[][] visualField = ioService.deserializeGameField(game.getVisualField());

        model.addAttribute("playerField", playerField);
        model.addAttribute("computerField", computerField);
        model.addAttribute("visualField", visualField);
        model.addAttribute("game", game);
        return "battleships";
    }

    @GetMapping("/{gameId}/turn/{row}/{col}")
    public String newTurn(@PathVariable String gameId, @PathVariable int row,@PathVariable int col){

        battleshipsService.playerTurn(gameId,new int[]{row,col});
        battleshipsService.computerTurn(gameId);

        return "redirect:/battleships/" + gameId;
    }
}
