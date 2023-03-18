package com.example.demo.security;

import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minidev.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SecurityLayerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testForbiddenAccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/user/id/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testPermitAccess() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("username", "password", "password");
        mvc.perform(MockMvcRequestBuilders.post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(createUserRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginAndAccessToAuthorizeEndpoint() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("username", "password", "password");

        mvc.perform(MockMvcRequestBuilders.post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(createUserRequest)))
                .andExpect(status().isOk());

        TestLoginUser user = new TestLoginUser("username", "password");

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(user)))
                .andExpect(status().isOk())
                .andReturn();

        String bearerToken = result.getResponse().getHeader(HttpHeaders.AUTHORIZATION);

        mvc.perform(MockMvcRequestBuilders.get("/api/user/id/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginWithWrongUser() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("username", "password", "password");

        mvc.perform(MockMvcRequestBuilders.post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(createUserRequest)))
                .andExpect(status().isOk());

        TestLoginUser user = new TestLoginUser("wrongUsername", "password");

        mvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(user)))
                .andExpect(status().isUnauthorized());
    }

    @Data
    @AllArgsConstructor
    class TestLoginUser {

        private String username;
        private String password;
    }
}
