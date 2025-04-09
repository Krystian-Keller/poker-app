package com.sap.ase.poker.rest;

import com.sap.ase.poker.dto.BetRequestDto;
import com.sap.ase.poker.dto.GetTableResponseDto;
import com.sap.ase.poker.dto.PlayerDto;
import com.sap.ase.poker.model.IllegalActionException;
import com.sap.ase.poker.model.IllegalAmountException;
import com.sap.ase.poker.service.TableService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(TableController.PATH)
public class TableController {
    public static final String PATH = "/api/v1";
    private final TableService tableService;
    private final Broadcaster<String, GetTableResponseDto> broadcaster;

    public TableController(TableService tableService, Broadcaster<String, GetTableResponseDto> broadcaster) {
        this.tableService = tableService;
        this.broadcaster = broadcaster;
    }

    @GetMapping(path = "/events/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GetTableResponseDto> getEventSource(@PathVariable("id") String playerId) {
        System.out.println("SSE request from " + playerId);
        return broadcaster.asFlux(playerId);
    }

    @PostMapping("/players")
    public ResponseEntity<Void> joinTable(@RequestBody PlayerDto player) {
        tableService.addPlayer(player.getId(), player.getName());
        broadcaster.join(player.getId());
        broadcastChanges();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/actions")
    public ResponseEntity<Void> placeBet(@RequestBody BetRequestDto betRequest)
            throws IllegalAmountException, IllegalActionException {
        int amount = betRequest.getArgs().length == 0 ? 0 : betRequest.getArgs()[0];
        tableService.performAction(betRequest.getType(), amount);
        broadcastChanges();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/start")
    public ResponseEntity<Void> start() {
        tableService.start();
        broadcastChanges();
        return ResponseEntity.noContent().build();
    }

    public GetTableResponseDto getTable(String playerId) {
        return new GetTableResponseDto.Builder().players(tableService.getPlayers())
                .currentPlayer(tableService.getCurrentPlayer())
                .pot(tableService.getPot())
                .communityCards(tableService.getCommunityCards())
                .bets(tableService.getBets())
                .state(tableService.getState().getValue())
                .winner(tableService.getWinner())
                .winnerHand(tableService.getWinnerHand())
                .playerCards(tableService.getPlayerCards(playerId))
                .build();
    }

    private void broadcastChanges() {
        broadcaster.broadcast(this::getTable);
    }
}
