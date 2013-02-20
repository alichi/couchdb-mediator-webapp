package com.loghmani.nosql.couchdb.ektorp.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/couchDBRecipeConfig.xml" })
public class RecipeRepositoryTest {

	@Autowired
	RecipeRepository recipeRepo;

	private static final Log log = LogFactory
			.getLog(RecipeRepositoryTest.class);

	@Test
	public void testCreateRecipe() {
		String id = "my-first-recipe" + UUID.randomUUID().toString();

		log.debug("We are creating a sample recipe!");
		List<String> ingredients = new ArrayList<String>();
		ingredients.add("Rice");
		ingredients.add("Tomatoes");
		ingredients.add("Potatoes");

		Recipe recipe = new Recipe();
		recipe.setDateCreated(new DateTime());
		recipe.setIngredients(ingredients);
		recipe.setInstruction("Mix all the ingredients and ask your mom how to cook!");
		recipe.setTitle("My food when I am alone!");
		recipe.setId(id);

		recipeRepo.add(recipe);

		log.debug("We just added a recipe!");
		log.debug("Let's test if it is added!");

		Recipe myRecipe = recipeRepo.get(id);
		log.debug("Here is the recipe:\n");
		log.debug("id: " + myRecipe.getId());
		log.debug("title: " + myRecipe.getTitle());
		log.debug("DateTime: " + myRecipe.getDateCreated());
		log.debug("instruction: " + myRecipe.getInstruction());
		log.debug("ingredients: ");
		for (Iterator<String> iterator = ingredients.iterator(); iterator
				.hasNext();) {
			String ingredient = iterator.next();
			log.debug(ingredient + "\n");
		}

	}

}
