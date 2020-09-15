package com.miti.server.repository;

import com.miti.server.model.entity.Category;
import com.miti.server.model.entity.Recipe;
import com.miti.server.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> getRecipesByAuthor(User user);

    List<Recipe> getRecipesByCategoryId(String categoryId);

    Recipe getRecipeById(Long id);

    Boolean existsByName(String name);
}
