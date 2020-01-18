package com.example.eatgo.interfaces;

import com.example.eatgo.domain.Category;
import com.example.eatgo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
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

    @PostMapping("/categories")
    public ResponseEntity<?> create(@RequestBody Category resource) throws URISyntaxException {
        Category category = categoryService.addCategory(resource.getName());
        String url = "/categories/" + category.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }

}
