package com.loghmani.nosql.couchdb.mediator;

import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loghmani.nosql.couchdb.ektorp.client.Recipe;
import com.loghmani.nosql.couchdb.ektorp.client.RecipeRepository;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

	@Autowired
	RecipeRepository recipeRepo;

	private static final Log log = LogFactory.getLog(RecipeController.class);

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody
	Recipe createRecipe(@RequestBody Recipe recipe) {

		log.debug("recipe = " + recipe);
		recipe.setId(UUID.randomUUID().toString());

		recipeRepo.add(recipe);
		Recipe savedRecipe = recipeRepo.get(recipe.getId());
		log.debug("Saved and read the saved recipe = " + savedRecipe);

		return savedRecipe;
	}

	@RequestMapping(value = "/create/params", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String createRecipeParams(@RequestParam String title,
			@RequestParam String instruction,
			@RequestParam(value = "ingredients[]") String[] ingredients) {

		log.debug("recipe title = " + title);
		Recipe recipe = new Recipe();
		recipe.setId(UUID.randomUUID().toString());
		recipe.setTitle(title);
		recipe.setIngredients(Arrays.asList(ingredients));
		recipe.setDateCreated(new DateTime());
		recipe.setInstruction(instruction);

		recipeRepo.add(recipe);
		Recipe savedRecipe = recipeRepo.get(recipe.getId());
		log.debug("Saved and read the saved recipe = " + savedRecipe);

		return savedRecipe.toString();
	}

	@RequestMapping(value = "/read/{recipeId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Recipe readRecipe(@PathVariable String recipeId) {
		log.debug("reading recipe for ID = " + recipeId);

		Recipe recipe = recipeRepo.get(recipeId);

		log.debug("recipe = " + recipe);
		return recipe;
	}

}
