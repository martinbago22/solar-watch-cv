package com.codecool.solarwatch;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    // Továbvinni a service és a db rétegre a tesztet, hogy valóban létrehozza e a szükséges adatokat
    // SUREFIRE plugin kell az intergrációs tesztekre
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
}
