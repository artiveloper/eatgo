package com.example.eatgo.interfaces;

import com.example.eatgo.domain.Category;
import com.example.eatgo.service.CategoryService;
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
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CategoryService categoryService;

    @Test
    void list() throws Exception {
        //given
        List<Category> categories = Arrays.asList(Category.builder().name("Korean Food").build());
        doReturn(categories).when(categoryService).getCategories();

        //when
        mvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("Korean Food")
                ));

        //then
        verify(categoryService).getCategories();
    }

    @Test
    void create() throws Exception {
        //given
        Category expectedCategory = Category.builder()
                .name("Seoul")
                .build();

        doReturn(expectedCategory).when(categoryService).addCategory("Korean Food");

        //when
        String requestBody = "{\"name\": \"Korean Food\"}";
        mvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string("{}"));

        //then
        verify(categoryService).addCategory(any());
    }

}