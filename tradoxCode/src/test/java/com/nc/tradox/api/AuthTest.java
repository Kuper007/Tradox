package com.nc.tradox.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthController controller;

    @Test
    public void isAuthControllerNotNull() throws Exception{
        assertThat(controller).isNotNull();
    }

//    @Test
//    public void postMappingTest() throws Exception{
//        this.mockMvc.perform(post("/check"))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection());
//                //.andExpect(content().);
//    }
//
//    @Test
//    public void getMappingTest() throws Exception{
//        this.mockMvc.perform(get("/result"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }



}
