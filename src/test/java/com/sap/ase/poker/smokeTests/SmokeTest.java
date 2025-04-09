package com.sap.ase.poker.smokeTests;

import com.sap.ase.poker.dto.BetRequestDto;
import com.sap.ase.poker.dto.GetTableResponseDto;
import com.sap.ase.poker.dto.PlayerDto;
import com.sap.ase.poker.model.IllegalActionException;
import com.sap.ase.poker.model.IllegalAmountException;
import com.sap.ase.poker.model.Player;
import com.sap.ase.poker.rest.TableController;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SmokeTest {

//██╗  ██╗ █████╗ ███╗   ██╗██████╗ ███████╗     ██████╗ ███████╗███████╗██╗██╗██╗
//██║  ██║██╔══██╗████╗  ██║██╔══██╗██╔════╝    ██╔═══██╗██╔════╝██╔════╝██║██║██║
//███████║███████║██╔██╗ ██║██║  ██║███████╗    ██║   ██║█████╗  █████╗  ██║██║██║
//██╔══██║██╔══██║██║╚██╗██║██║  ██║╚════██║    ██║   ██║██╔══╝  ██╔══╝  ╚═╝╚═╝╚═╝
//██║  ██║██║  ██║██║ ╚████║██████╔╝███████║    ╚██████╔╝██║     ██║     ██╗██╗██╗
//╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝     ╚═════╝ ╚═╝     ╚═╝     ╚═╝╚═╝╚═╝

    protected static final String PATH = "/api/v1/";
    protected static final int STARTING_CASH = 100;
    protected static final String ALICE_ID = "poker-alice";
    protected static final String BILL_ID = "wild-bill";
    protected static final String AL_CAPONE_ID = "al-capone";
    protected static final int BET = 10;
    protected static final String ALICE_NAME = "Poker Alice";
    protected static final String BILL_NAME = "Wild Bill";
    protected static final String AL_CAPONE_NAME = "Al Capone";

    @Autowired
    protected TableController underTest;

    protected void addTwoPlayers() {
        addPlayer(ALICE_ID, ALICE_NAME);
        addPlayer(BILL_ID, BILL_NAME);
    }

    protected void startGame() {
        underTest.start();
    }

    protected PlayerDto getCurrentPlayerDto() {
        return getTableResponseDtoForPlayer(ALICE_ID).getCurrentPlayer();
    }

    protected PlayerDto getCurrentPlayerState(PlayerDto currentPlayerDto, GetTableResponseDto result) {
        return result.getPlayers().stream().filter(p -> p.getId().equals(currentPlayerDto.getId())).findFirst().get();
    }

    protected BetRequestDto checkActionAsString() {
        return getActionAsString("check", new int[]{});
    }

    protected BetRequestDto foldActionAsString() {
        return getActionAsString("fold", new int[]{});
    }

    protected BetRequestDto raiseActionAsString(int bet) {
        return getActionAsString("raise", new int[]{bet});
    }

    protected BetRequestDto callActionAsString() {
        return getActionAsString("call", new int[]{});
    }

    private BetRequestDto getActionAsString(String type, int[] args) {
        BetRequestDto checkRequest = new BetRequestDto();
        checkRequest.setType(type);
        checkRequest.setArgs(args);
        return (checkRequest);
    }

    protected void performAction(BetRequestDto checkString) {
        underTest.placeBet(checkString);
    }

    protected GetTableResponseDto getTableResponseDtoForPlayer(String id) {
        return underTest.getTable(id);
    }

    protected void addPlayer(String id, String name) {
        underTest.joinTable(new PlayerDto(new Player(id, name, 100)));
    }

    protected void assertIllegalAmountException(ThrowableAssert.ThrowingCallable throwingCallable) {
        assertThatThrownBy(throwingCallable).isInstanceOf(IllegalAmountException.class);
    }

    protected void assertIllegalActionException(ThrowableAssert.ThrowingCallable throwingCallable) {
        assertThatThrownBy(throwingCallable).isInstanceOf(IllegalActionException.class);
    }
}
