package com.example.eatgo.service;

import com.example.eatgo.domain.Category;
import com.example.eatgo.domain.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        List<Category> regions = categoryRepository.findAll();
        return regions;
    }

}
