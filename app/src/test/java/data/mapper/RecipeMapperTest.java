package data.mapper;

import com.vpaliy.bakingapp.data.mapper.Mapper;
import com.vpaliy.bakingapp.data.mapper.RecipeMapper;
import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.data.model.StepEntity;
import com.vpaliy.bakingapp.domain.model.Ingredient;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.domain.model.Step;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;


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
        RecipeEntity entity=provideRecipeEntity();
        Recipe recipe=mapper.map(entity);


    }

}
