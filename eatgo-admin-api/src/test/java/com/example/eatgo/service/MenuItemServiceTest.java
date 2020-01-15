package com.example.eatgo.service;

import com.example.eatgo.domain.MenuItem;
import com.example.eatgo.domain.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MenuItemService.class)
class MenuItemServiceTest {

    @Autowired
    MenuItemService menuItemService;

    @MockBean
    MenuItemRepository menuItemRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void bulkUpdate() throws Exception {
        //given
        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(MenuItem.builder().name("KimChi").build());
        menuItems.add(MenuItem.builder().id(1L).name("GukBob").build());
        menuItems.add(MenuItem.builder().id(2L).destroy(true).build());

        //when
        menuItemService.bulkUpdate(1L, menuItems);

        //then
        verify(menuItemRepository, times(2)).save(any());
        verify(menuItemRepository, times(1)).deleteById(eq(2L));
    }

    @Test
    void getMenuItems() throws Exception {
        //given
        List<MenuItem> mockMenuItems = new ArrayList<>();
        mockMenuItems.add(MenuItem.builder().name("KimChi").build());

        given(menuItemRepository.findAllByRestaurantId(1L)).willReturn(mockMenuItems);

        //when
        List<MenuItem> menuItems = menuItemService.getMenuItems(1L);
        MenuItem menuItem = menuItems.get(0);

        //then
        assertThat(mockMenuItems.get(0).getName()).isEqualTo(menuItem.getName());
    }
}