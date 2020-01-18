package com.example.eatgo.service;

import com.example.eatgo.domain.Category;
import com.example.eatgo.domain.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
class CategoryServiceTest {
    
    @InjectMocks
    CategoryService categoryService;

    @Mock
    CategoryRepository CategoryRepository;

    @Test
    void getCategories() throws Exception {
        //given
        List<Category> expectedCategories = Arrays.asList(
                Category.builder()
                        .name("Korean Food")
                        .build());

        doReturn(expectedCategories).when(CategoryRepository).findAll();

        //when
        List<Category> actualCategories = categoryService.getCategories();
        Category actualCategory = actualCategories.get(0);

        //then
        assertThat(actualCategory.getName()).isEqualTo(expectedCategories.get(0).getName());
    }

}