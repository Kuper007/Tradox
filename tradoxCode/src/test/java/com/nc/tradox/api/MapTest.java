package com.nc.tradox.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MapTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MapController controller;

    @Test
    public void isAccountControllerNotNull() throws Exception{
        assertThat(controller).isNotNull();
    }

}
