package data.mapper;

import com.vpaliy.bakingapp.data.mapper.IngredientMapper;
import com.vpaliy.bakingapp.data.mapper.Mapper;
import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.domain.model.Ingredient;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class IngredientMapperTest extends MapperTest{

    private Mapper<Ingredient,IngredientEntity> mapper;

    @Before
    public void setUp(){
        mapper=new IngredientMapper();
    }

    @Test
    public void testMapTo(){
        IngredientEntity entity= provideIngredientEntity();
        Ingredient result=mapper.map(entity);
        assertThat(result.getId(),is(FAKE_ID));
        assertThat(result.getIngredient(),is(FAKE_NAME));
        assertThat(result.getMeasure(),is(FAKE_TEXT));
        assertThat(result.getQuantity(),is(FAKE_QUANTITY));
    }

    @Test
    public void testMapToNull(){
        IngredientEntity entity=null;
        Ingredient ingredient=mapper.map(entity);
        assertThat(ingredient, nullValue());
    }

    @Test
    public void testMapFromNull(){
        Ingredient ingredient=null;
        IngredientEntity entity=mapper.reverseMap(ingredient);
        assertThat(entity, nullValue());
    }

    @Test
    public void testMapFrom(){
        Ingredient ingredient=provideIngredient();
        IngredientEntity entity=mapper.reverseMap(ingredient);
        assertThat(entity.getId(),is(FAKE_ID));
        assertThat(entity.getIngredient(),is(FAKE_NAME));
        assertThat(entity.getMeasure(),is(FAKE_TEXT));
        assertThat(entity.getQuantity(),is(FAKE_QUANTITY));
    }

    @Test
    public void testMapListNull(){
        List<IngredientEntity> list=null;
        List<Ingredient> result=mapper.map(list);
        assertThat(result,nullValue());
    }

    @Test
    public void testMapList(){
        List<IngredientEntity> entityList= Arrays.asList(provideIngredientEntity(), provideIngredientEntity(),
                provideIngredientEntity(), provideIngredientEntity(), provideIngredientEntity());
        List<Ingredient> resultList=mapper.map(entityList);
        for(Ingredient result:resultList){
            assertThat(result.getId(),is(FAKE_ID));
            assertThat(result.getIngredient(),is(FAKE_NAME));
            assertThat(result.getMeasure(),is(FAKE_TEXT));
            assertThat(result.getQuantity(),is(FAKE_QUANTITY));
        }
    }
}
