package com.loghmani.nosql.couchdb.ektorp.client;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RecipeRepository extends CouchDbRepositorySupport<Recipe> {

	@Autowired
	public RecipeRepository(@Qualifier("recipeDatabase") CouchDbConnector db) {
		super(Recipe.class, db);
		initStandardDesignDocument();
	}

}
