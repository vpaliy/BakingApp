package data.mapper;

import com.vpaliy.bakingapp.data.mapper.Mapper;
import com.vpaliy.bakingapp.data.mapper.RecipeMapper;
import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.data.model.StepEntity;
import com.vpaliy.bakingapp.domain.model.Ingredient;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.domain.model.Step;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class RecipeMapperTest extends MapperTest {

    @Mock
    private Mapper<Step,StepEntity> stepMapper;

    @Mock
    private Mapper<Ingredient,IngredientEntity> ingredientMapper;

    private Mapper<Recipe,RecipeEntity> mapper;

    @Before
    public void setUp(){
        this.mapper=new RecipeMapper(ingredientMapper,stepMapper);
    }

    @Test
    public void testMapTo(){
        when(stepMapper.map(anyListOf(StepEntity.class))).thenReturn(provideStepList());
        when(ingredientMapper.map(anyListOf(IngredientEntity.class))).thenReturn(provideIngredientList());
        RecipeEntity entity=provideRecipeEntity();
        Recipe recipe=mapper.map(entity);
        assertThat(recipe.getId(),is(FAKE_ID));
        assertThat(recipe.getImageUrl(),is(FAKE_IMAGE_URL));
        assertThat(recipe.getName(),is(FAKE_NAME));
        assertThat(recipe.getServings(),is(FAKE_SERVINGS));
        verify(stepMapper).map(anyListOf(StepEntity.class));
        verify(ingredientMapper).map(anyListOf(IngredientEntity.class));
    }

    @Test
    public void testMapFrom(){
        when(stepMapper.reverseMap(any(Step.class))).thenReturn(provideStepEntity());
        when(ingredientMapper.reverseMap(any(Ingredient.class))).thenReturn(provideIngredientEntity());
        Recipe recipe=provideRecipe();
        RecipeEntity recipeEntity=mapper.reverseMap(recipe);
        assertThat(recipeEntity.getId(),is(FAKE_ID));
        assertThat(recipeEntity.getImageUrl(),is(FAKE_IMAGE_URL));
        assertThat(recipeEntity.getName(),is(FAKE_NAME));
        assertThat(recipeEntity.getServings(),is(FAKE_SERVINGS));
        verify(stepMapper,times(recipe.getSteps().size())).reverseMap(any(Step.class));
        verify(ingredientMapper,times(recipe.getIngredients().size())).reverseMap(any(Ingredient.class));
    }



}
