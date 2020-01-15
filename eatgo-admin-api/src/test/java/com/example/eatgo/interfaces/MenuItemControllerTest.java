package com.example.eatgo.interfaces;

import com.example.eatgo.domain.MenuItem;
import com.example.eatgo.service.MenuItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MenuItemController.class)
class MenuItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MenuItemService menuItemService;

    @Test
    void getMenuItems() throws Exception {
        //given
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(MenuItem.builder().name("KimChi").build());

        MenuItem menuItem = menuItems.get(0);

        //then
        assertThat(menuItem.getName()).isEqualTo("KimChi");
    }

    @Test
    void bulkUpdate() throws Exception {
        mockMvc.perform(patch("/restaurants/1/menuitems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[]"))
                .andExpect(status().isOk());

        verify(menuItemService).bulkUpdate(eq(1L), any());
    }
}