package com.stdioh321.crud;

import com.stdioh321.crud.model.AuthRequest;
import com.stdioh321.crud.model.AuthResponse;
import com.stdioh321.crud.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

public class UtilsTest {
    public static UtilsTest instance;


    public static UtilsTest getInstance(MockMvc mockMvc) {
        if (instance == null) instance = new UtilsTest();
        instance.mockMvc = mockMvc;
        return instance;
    }

    private MockMvc mockMvc;

    public String getTokenFromAuth() throws Exception {

        var authRequest = new AuthRequest();
        authRequest.setUsername("test");
        authRequest.setPassword("Test@4231");
        String json = Utils.mapToJson(authRequest);

        String result = mockMvc.perform(post("/api/v1/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        var authResponse = Utils.mapFromJson(result, AuthResponse.class);
        return authResponse.getJwt();

    }


    public ResultActions doAuthRequest(String url) throws Exception {
        return doAuthRequest(url, null, "");
    }

    public ResultActions doAuthRequest(String url, HttpMethod method, String content) throws Exception {
        if (Objects.isNull(method)) method = HttpMethod.GET;
        return mockMvc.perform(
                request(method, url)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getTokenFromAuth())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
        );
    }
}
