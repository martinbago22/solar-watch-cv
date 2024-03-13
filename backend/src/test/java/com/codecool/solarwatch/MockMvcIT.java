package com.codecool.solarwatch;

import com.codecool.solarwatch.model.dto.integration_model.SunInfoResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcIT {
    @Autowired
    private MockMvc mvc;
    private String jwtToken;

    //TODO saját DTO-t használni OBJECTMAPPER
    // TERMINÁL NYITÁSKOR KÖRNYEZETI VÁLTOZÓK BEÁLLÍTÁSA EXPORT VARIABLE_NAME=VARIABLE echo $VARIABLE_NAME (check the variable)
    // (minden terminál nyitáskor elvesznek) , TEST - IT átnevezés (így nem unit testként futtatja hanem integrációs tesztként -> failsafe plugin -> mvn verify
    @BeforeEach
    void setup() throws Exception {
        jwtToken = getJwtToken();
    }

    private String getJwtToken() throws Exception {
        this.mvc
                .perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "abc123",
                                    "password": "pw1"
                                }""")
                ).andExpect(status().isCreated());
        return
                mvc.perform(post("/api/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "username": "abc123",
                                            "password": "pw1"
                                        }""")
                        ).andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();
    }

    @Test
    void getSunInfoAfterLoginReturns200StatusCode() throws Exception {
        SunInfoResponseDTO expectedResponse = new SunInfoResponseDTO("Budapest", LocalDate.now().toString());
        MvcResult result =
                this.mvc
                        .perform(get("/api/weatherforecast/suninfo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        ).andExpect(status().isOk())
                        .andDo(print())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        SunInfoResponseDTO actualResponse = objectMapper.readValue(responseBody, SunInfoResponseDTO.class);

        assertEquals(expectedResponse.cityName(), actualResponse.cityName());
        assertEquals(expectedResponse.date(), actualResponse.date());
    }
}
