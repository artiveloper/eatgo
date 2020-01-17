package com.example.eatgo.interfaces;

import com.example.eatgo.domain.Region;
import com.example.eatgo.service.RegionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RegionController.class)
public class RegionControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    RegionService regionService;

    @Test
    void list() throws Exception {
        //given
        List<Region> givenRegions = Arrays.asList(Region.builder().name("Seoul").build());
        given(regionService.getRegions()).willReturn(givenRegions);

        //when
        mvc.perform(get("/regions"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("Seoul")
                ));

        //then
        verify(regionService).getRegions();
    }

    @Test
    void create() throws Exception {
        //given
        Region expectedRegion = Region.builder()
                .name("Seoul")
                .build();

        doReturn(expectedRegion).when(regionService).addRegion("Seoul");

        //when
        String requestBody = "{\"name\": \"Seoul\"}";
        mvc.perform(post("/regions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string("{}"));

        //then
        verify(regionService).addRegion(any());
    }
}