package com.example.eatgo.interfaces;

import com.example.eatgo.domain.Category;
import com.example.eatgo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> list() {
        List<Category> categories = categoryService.getCategories();
        return categories;
    }

}
