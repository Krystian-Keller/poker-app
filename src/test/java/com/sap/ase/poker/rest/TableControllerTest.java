package com.sap.ase.poker.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ase.poker.dto.BetRequestDto;
import com.sap.ase.poker.dto.GetTableResponseDto;
import com.sap.ase.poker.model.GameState;
import com.sap.ase.poker.model.IllegalAmountException;
import com.sap.ase.poker.model.Player;
import com.sap.ase.poker.service.TableService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TableControllerTest {
    static final String PATH = "/api/v1";

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    Broadcaster<String, GetTableResponseDto> broadcaster;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TableService tableService;

    @Test
    void joinTable_addsValidPlayerToTable() throws Exception {
        Mockito.when(tableService.getState()).thenReturn(GameState.OPEN);
        sendJoinRequest("alice").andExpect(status().isNoContent());
        Mockito.verify(tableService, Mockito.times(1)).addPlayer("alice", "alice");
        Mockito.verify(broadcaster).join(eq("alice"));
    }

    @Test
    void getEventSource_returnsEventStreamWithTableStatus() throws Exception {
        givenOpenTableWith2Players("alice", "bob");
        sendJoinRequest("alice").andExpect(status().isNoContent());

        MockHttpServletResponse response = getEventsFor("alice").andExpect(status().isOk()).andReturn().getResponse();

        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_EVENT_STREAM_VALUE);

        GetTableResponseDto result = parseFluxResponse(response.getContentAsString());
        assertThat(result.getPlayers()).hasSize(2);
        assertThat(result.getState()).isEqualTo(GameState.OPEN.getValue());
    }

    @Test
    void start_startsGameWithTableService() throws Exception {
        mockMvc.perform(post(PATH + "/start")).andExpect(status().isNoContent()).andReturn().getResponse();
        Mockito.verify(tableService, Mockito.times(1)).start();
        verifyThatChangesWereBroadcast();
    }

    @Test
    void placeBet_withRaise_callsTableServiceWithCorrectAmount() throws Exception {
        sendBetRequest("raise", 10)/*.andExpect(status().isNoContent())*/.andReturn().getResponse();
        Mockito.verify(tableService, Mockito.times(1)).performAction("raise", 10);
        verifyThatChangesWereBroadcast();
    }

    @Test
    void performAction_shouldBroadcastError_whenTableServiceThrowsException() throws Exception {
        Mockito.doThrow(new IllegalAmountException("")).when(tableService).performAction(eq("check"), anyInt());
        sendBetRequest("check", null).andExpect(status().isBadRequest()).andReturn().getResponse();
    }

    private GetTableResponseDto parseFluxResponse(String string) throws JsonProcessingException {
        final String jsonResponse = string.substring(0, string.length() - 1).substring(5);
        System.out.println("response:" + jsonResponse);
        GetTableResponseDto result = objectMapper.readValue(jsonResponse, GetTableResponseDto.class);
        return result;
    }

    private ResultActions getEventsFor(String id) throws Exception {
        return mockMvc.perform(get(PATH + "/events/" + id));
    }

    private ResultActions sendJoinRequest(String id) throws Exception {
        String json = "{\"id\":\"" + id + "\",\"name\":\"" + id + "\"}";
        return mockMvc.perform(post(PATH + "/players").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    private ResultActions sendBetRequest(String type, Integer amount) throws Exception {
        BetRequestDto betRequest = new BetRequestDto();
        betRequest.setType(type);
        betRequest.setArgs(amount == null ? new int[] {} : new int[] { amount });
        return mockMvc.perform(post(PATH + "/actions")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(betRequest)));
    }

    private void givenOpenTableWith2Players(String player1, String player2) {
        Mockito.when(tableService.getState()).thenReturn(GameState.OPEN);
        Mockito.when(tableService.getPlayers()).thenReturn(Arrays.asList(
                new Player(player1, player1, 100),
                new Player(player2, player2, 100)));
    }

    private void verifyThatChangesWereBroadcast() {
        Mockito.verify(broadcaster, Mockito.times(1)).broadcast(any());
    }
}
