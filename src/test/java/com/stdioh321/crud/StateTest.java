package com.stdioh321.crud;

import com.stdioh321.crud.exception.EntityNotFoundException;
import com.stdioh321.crud.model.AuthRequest;
import com.stdioh321.crud.model.AuthResponse;
import com.stdioh321.crud.model.State;
import com.stdioh321.crud.service.StateService;
import com.stdioh321.crud.utils.Utils;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@Rollback(value = true)
@AutoConfigureMockMvc
class StateTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void beforeTest() {
        System.out.println("beforeTest-------------------------------------------------");
    }

    @AfterEach
    public void afterTest() {
        System.out.println("afterTest-------------------------------------------------");
    }

    @Autowired
    private StateService stateService;


    @Test
    public void controllerShouldHaveStateById() throws Exception {
        var tempState = stateService.getAll().stream().findFirst().orElseThrow(() -> new EntityNotFoundException(null, "State"));
        var result = UtilsTest.getInstance(mockMvc).doAuthRequest("/api/v1/state/" + tempState.getId())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        State state = Utils.mapFromGson(result, State.class);
        Assert.assertEquals(tempState.getId(), state.getId());

    }

    @Test
    @Transactional
    public void controllerShouldPostState() throws Exception {
        System.out.println("controllerShouldPostState-------------------------------------------------------");
        State s = new State();
        s.setName(Utils.getRandomString(20));
        s.setInitial(Utils.getRandomString(2));
        var result = UtilsTest.getInstance(mockMvc).doAuthRequest("/api/v1/state", HttpMethod.POST, Utils.mapToJson(s))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        State state = Utils.mapFromGson(result, State.class);


        Assert.assertNotNull(stateService.getById(state.getId()));
        System.out.println("controllerShouldPostState-------------------------------------------------------");
    }

    @Test
    @Transactional
    public void controllerShouldPutState() throws Exception {
        State s = new State();
        String tempName1 = Utils.getRandomString(20);
        String tempName2 = Utils.getRandomString(20);

        s.setName(tempName1);
        s.setInitial(Utils.getRandomString(2));
        s = stateService.post(s);
        s.setName(tempName2);

        var result = UtilsTest.getInstance(mockMvc).doAuthRequest("/api/v1/state/" + s.getId(), HttpMethod.PUT, Utils.mapToJson(s))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        State state = Utils.mapFromGson(result, State.class);


        System.out.println(s.getUpdatedAt());
        System.out.println(state.getUpdatedAt());

        Assert.assertEquals(tempName2, state.getName());
    }

    @Test
    @Transactional
    public void controllerShouldDeleteState() throws Exception {
        State s = new State();
        String tempName = Utils.getRandomString(20);

        s.setName(tempName);
        s.setInitial(Utils.getRandomString(2));
        s = stateService.post(s);

        var result = UtilsTest.getInstance(mockMvc).doAuthRequest("/api/v1/state/" + s.getId(), HttpMethod.DELETE, Utils.mapToJson(s))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        State state = Utils.mapFromGson(result, State.class);

        Assert.assertEquals(s.getId(), state.getId());
        Assert.assertEquals(s.getId(), stateService.getTrashedById(state.getId()).getId());


    }

    @Test
    public void controllerShouldHaveOneState() throws Exception {
        MvcResult mvcResult = UtilsTest.getInstance(mockMvc).doAuthRequest("/api/v1/state")
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        List states = (List<State>) Utils.mapFromJson(result, List.class);
        Assert.assertTrue(states.size() > 0);
    }

    @Test
    @Transactional
    public void shouldHaveOneState() throws Exception {
        State tempState = new State();
        String tempStr = Utils.getRandomString(10);
        tempState.setName(tempStr);
        tempState.setInitial(tempStr.substring(0, 2));
        tempState = stateService.post(tempState);
        int size = stateService.getAll().size();
        Assert.assertTrue(size > 0);

    }

    @Test
    @Transactional
    public void shouldFindState() throws Exception {
        State tempState = new State();
        String tempStr = Utils.getRandomString(20);
        tempState.setName(tempStr);
        tempState.setInitial(tempStr.substring(0, 2));
        tempState = stateService.post(tempState);

        Assert.assertNotNull(stateService.getById(tempState.getId()));

    }

    @Test
    @Transactional
    public void shouldAddState() throws Exception {
        State tempState = new State();
        String tempStr = Utils.getRandomString(10);
        tempState.setName(tempStr);
        tempState.setInitial(tempStr.substring(0, 2));
        tempState = stateService.post(tempState);
        Assert.assertNotNull(tempState);

    }

    @Test
    @Transactional
    public void shouldPutState() throws Exception {
        State tempState = new State();
        String tempStr = Utils.getRandomString(10);
        tempState.setName(tempStr);
        tempState.setInitial(tempStr.substring(0, 2));
        tempState = stateService.post(tempState);

        String tempStr2 = Utils.getRandomString(10);
        tempState.setName(tempStr2);
        var tempState2 = stateService.put(tempState.getId(), tempState);
        Assert.assertEquals(tempStr2, tempState2.getName());
    }

    /*public ResultActions doAuthRequest(String url) throws Exception {
        return doAuthRequest(url, null, "");
    }

    public ResultActions doAuthRequest(String url, HttpMethod method, String content) throws Exception {
        if (Objects.isNull(method)) method = HttpMethod.GET;
        return mockMvc.perform(
                request(method, url)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + UtilsTest.getInstance(mockMvc).getTokenFromAuth())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
        );
    }

    public String getToken() throws Exception {

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

    }*/
}
