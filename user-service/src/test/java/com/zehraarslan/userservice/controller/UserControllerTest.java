package com.zehraarslan.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zehraarslan.userservice.exception.SystemException;
import com.zehraarslan.userservice.general.RestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {SystemException.class})
class UserControllerTest {


    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }
    protected void isSuccess(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();

        RestResponse restResponse = objectMapper.readValue(content, RestResponse.class);
        boolean success = restResponse.isSuccess();
        assertTrue(success);
    }

    @Test
    void saveUser()  throws Exception {

        String requestAsString = "{\n" +
                "  \"name\": \"zehra\",\n" +
                "  \"surname\": \"arslan\",\n" +
                "  \"username\": \"zars\",\n" +
                "  \"password\": \"122\",\n" +
                "  \"email\": \"za@gmmail\",\n" +
                "  \"phoneNumber\": \"554745\",\n" +
                "  \"latitude\": \"41.0082\",\n" +
                "  \"longitude\": \"28.9784\",\n" +
                "}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .content(requestAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        isSuccess(mvcResult);
    }
}