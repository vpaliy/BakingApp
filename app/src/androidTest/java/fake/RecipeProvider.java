package fake;

import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.domain.model.Ingredient;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.domain.model.Step;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SuppressWarnings("WeakerAccess")
public class RecipeProvider {

    private final static Random RANDOMIZER=new Random();

    private final static String VIDEO_URL_ONE="https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4";
    private final static String VIDEO_URL_TWO="https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdcf4_8-put-brownies-in-oven-to-bake-brownies/8-put-brownies-in-oven-to-bake-brownies.mp4";
    private final static String VIDEO_URL_THREE="https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdcf9_9-final-product-brownies/9-final-product-brownies.mp4";

    public final static String RECIPE_NAME_ONE="Nutella Pie";
    public final static String RECIPE_NAME_TWO="Brownies";
    public final static String RECIPE_NAME_THREE="Yellow Cake";
    public final static String RECIPE_NAME_FOUR="Cheesecake";

    private static Ingredient provideIngredient(String ingredientString){
        Ingredient ingredient=new Ingredient();
        ingredient.setQuantity(RANDOMIZER.nextInt(20));
        ingredient.setId(RANDOMIZER.nextInt(30));
        ingredient.setMeasure("Spoon");
        ingredient.setIngredient(ingredientString);
        return ingredient;
    }

    public static List<Ingredient> provideIngredients(){
        return Arrays.asList(provideIngredient("Butter"),
                provideIngredient("Milk"),
                provideIngredient("Almonds"));
    }

    private static Step provideStep(String videoUrl, String thumbnailUrl){
        Step step=new Step();
        step.setStepId(RANDOMIZER.nextInt(30));
        step.setDescription("Description:"+Integer.toString(RANDOMIZER.nextInt(100)));
        step.setShortDescription("Short description"+Integer.toString(RANDOMIZER.nextInt(100)));
        step.setVideoUrl(videoUrl);
        step.setImageUrl(thumbnailUrl);
        return step;
    }

    public static List<Step> provideSteps(){
        return Arrays.asList(provideStep(VIDEO_URL_ONE,null),
                    provideStep(VIDEO_URL_TWO,null),
                    provideStep(VIDEO_URL_THREE,null),
                    provideStep(null,VIDEO_URL_ONE),
                    provideStep(null,VIDEO_URL_TWO),
                    provideStep(null,VIDEO_URL_THREE));
    }

    public static Recipe provideRecipe(String recipeName){
        Recipe recipe=new Recipe();
        recipe.setIngredients(provideIngredients());
        recipe.setSteps(provideSteps());
        recipe.setServings(RANDOMIZER.nextInt(30));
        recipe.setName(recipeName);
        recipe.setId(RANDOMIZER.nextInt(30));
        return recipe;
    }

    public static List<Recipe> provideRecipes(){
        return Arrays.asList(provideRecipe(RECIPE_NAME_ONE),
                    provideRecipe(RECIPE_NAME_TWO),
                    provideRecipe(RECIPE_NAME_THREE),
                    provideRecipe(RECIPE_NAME_FOUR));
    }
}
