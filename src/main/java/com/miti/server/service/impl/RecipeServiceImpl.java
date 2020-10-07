package com.miti.server.service.impl;

import com.miti.server.model.entity.Recipe;
import com.miti.server.repository.RecipeRepository;
import com.miti.server.service.CategoryService;
import com.miti.server.service.RecipeService;
import com.miti.server.service.UserService;
import com.miti.server.util.Check;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecipeServiceImpl implements RecipeService {
  private final RecipeRepository recipeRepository;
  private final UserService userService;
  private final CategoryService categoryService;

  @Override
  public Recipe addRecipe(Recipe recipe) {
    if (checkFields(recipe.getName()))
      return recipeRepository.save(new Recipe(
          recipe.getName(),
          recipe.getDescription(),
          recipe.getAuthor(),
          recipe.getCategory()
      ));
    throw new RuntimeException("Recipe with name: " + recipe.getName() + " already exists!");
  }

  @Override
  public void addAllRecipes(List<Recipe> recipes) {
    List<Recipe> _recipes = new ArrayList<>();
    for (Recipe recipe : recipes) {
      if (checkFields(recipe.getName()))
        _recipes.add(recipe);
    }
    recipeRepository.saveAll(_recipes);
  }

  @Override
  public Recipe getRecipeById(Long recipeId) {
    return recipeRepository.findById(recipeId).orElseThrow(()
        -> new RuntimeException("Recipe with id: " + recipeId + " doesn't exist!"));
  }

  @Override
  public Recipe getRecipeByName(String name) {
    if (Check.param(name)) {
      Recipe recipe = recipeRepository.getRecipeByName(name);
      if (recipe != null)
        return recipe;
      throw new RuntimeException("Recipe with name: " + name + " doesn't exist!");
    }
    throw new RuntimeException("Name: " + name + " is incorrect!");
  }

  @Override
  public List<Recipe> getAllRecipes() {
    List<Recipe> recipes = recipeRepository.findAll();
    return recipes;
  }

  @Override
  public List<Recipe> getRecipesByAuthorId(Long authorId) {
    if (Check.param(authorId)) {
      List<Recipe> recipes = recipeRepository.getRecipesByAuthor(userService.getUserById(authorId));
      if (recipes != null)
        return recipes;
      throw new RuntimeException("Recipe with authorId: " + authorId + " doesn't exist!");
    }
    throw new RuntimeException("AuthorId: " + authorId + " is incorrect!");
  }

  @Override
  public List<Recipe> getRecipesByCategoryId(String categoryId) {
    if (Check.param(categoryId)) {
      List<Recipe> recipes = recipeRepository.getRecipesByCategory(categoryService.getCategoryById(categoryId));
      if (recipes != null)
        return recipes;
      throw new RuntimeException("Recipe with categoryId: " + categoryId + " doesn't exist!");
    }
    throw new RuntimeException("CategoryId: " + categoryId + " is incorrect!");
  }

  @Override
  public void deleteRecipeById(Long recipeId) {
    recipeRepository.deleteById(recipeId);
  }

  private boolean checkFields(String name) {
    return !recipeRepository.existsByName(name);
  }
}
