package com.loghmani.nosql.couchdb.mediator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import com.loghmani.nosql.couchdb.ektorp.client.Recipe;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/mediator-dispatcher-servlet.xml",
		"classpath:/META-INF/spring/couchDBRecipeConfig.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
// overrides the default stack of listeners
public class RecipeControllerTest {

	private static final Log log = LogFactory
			.getLog(RecipeControllerTest.class);

	@Autowired
	private ApplicationContext applicationContext;

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private HandlerAdapter handlerAdapter;
	private RecipeController controller;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		handlerAdapter = new AnnotationMethodHandlerAdapter();
		// I could get the controller from the context here
		controller = new RecipeController();
	}

	@Test
	public void testPOST() throws Exception {
		request.setRequestURI("/recipe/create");
		request.setMethod("POST");
		request.setContentType(MediaType.APPLICATION_JSON.toString());

		List<String> ingredients = new ArrayList<String>();
		ingredients.add("Rice");
		ingredients.add("Tomatoes");
		ingredients.add("Potatoes");

		Recipe recipe = new Recipe();
		recipe.setDateCreated(new DateTime());
		recipe.setIngredients(ingredients);
		recipe.setInstruction("Mix all the ingredients and ask your mom how to cook!");
		recipe.setTitle("My food when I am alone!");

		request.setContent(serializeRecipe());

		handlerAdapter.handle(request, response, controller);

		log.debug("recipe saved!");
		// assert something
	}

	@Test
	public void testGET() throws Exception {
		request.setRequestURI("/recipe/read/my-first-recipef8dad871-eb8b-4385-9cec-e2e259cb937e");
		request.setMethod("GET");
		request.setContentType(MediaType.APPLICATION_JSON.toString());

		handlerAdapter.handle(request, response, controller);

		log.debug("recipe saved!");
		// assert something
	}

	private byte[] serializeRecipe() throws IOException {
		List<String> ingredients = new ArrayList<String>();
		ingredients.add("Rice");
		ingredients.add("Tomatoes");
		ingredients.add("Potatoes");

		Recipe recipe = new Recipe();
		recipe.setDateCreated(new DateTime());
		recipe.setIngredients(ingredients);
		recipe.setInstruction("Mix all the ingredients and ask your mom how to cook!");
		recipe.setTitle("My food when I am alone!");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(recipe);
		return out.toByteArray();
	}
}
