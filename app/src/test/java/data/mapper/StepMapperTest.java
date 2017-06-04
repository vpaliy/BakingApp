package data.mapper;


import com.vpaliy.bakingapp.data.mapper.Mapper;
import com.vpaliy.bakingapp.data.mapper.StepMapper;
import com.vpaliy.bakingapp.data.model.StepEntity;
import com.vpaliy.bakingapp.domain.model.Step;
import org.mockito.runners.MockitoJUnitRunner;

import static common.RecipeTestUtils.FAKE_DESCRIPTION;
import static common.RecipeTestUtils.FAKE_ID;
import static common.RecipeTestUtils.FAKE_IMAGE_URL;
import static common.RecipeTestUtils.FAKE_TEXT;
import static common.RecipeTestUtils.FAKE_VIDEO_URL;
import static common.RecipeTestUtils.provideStep;
import static common.RecipeTestUtils.provideStepEntity;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class StepMapperTest {

    private Mapper<Step,StepEntity> mapper;

    @Before
    public void setUp(){
        mapper=new StepMapper();
    }

    @Test
    public void testMapToIsNull(){
        StepEntity entity=null;
        Step step=mapper.map(entity);
        assertThat(step,nullValue());
    }

    @Test
    public void testMapFromIsNull(){
        Step step=null;
        StepEntity entity=mapper.reverseMap(step);
        assertThat(entity,nullValue());
    }

    @Test
    public void testMapListIsNull(){
        List<StepEntity> list=null;
        List<Step> result=mapper.map(list);
        assertThat(result,nullValue());
    }

    @Test
    public void testMapTo(){
        StepEntity entity=provideStepEntity();
        Step step=mapper.map(entity);
        assertThat(step.getStepId(),is(FAKE_ID));
        assertThat(step.getDescription(),is(FAKE_TEXT));
        assertThat(step.getImageUrl(),is(FAKE_IMAGE_URL));
        assertThat(step.getVideoUrl(),is(FAKE_VIDEO_URL));
        assertThat(step.getShortDescription(),is(FAKE_DESCRIPTION));
    }

    @Test
    public void testMapFrom(){
        Step step=provideStep();
        StepEntity entity=mapper.reverseMap(step);
        assertThat(entity.getId(),is(FAKE_ID));
        assertThat(entity.getDescription(),is(FAKE_TEXT));
        assertThat(entity.getImageUrl(),is(FAKE_IMAGE_URL));
        assertThat(entity.getVideoUrl(),is(FAKE_VIDEO_URL));
        assertThat(entity.getShortDescription(),is(FAKE_DESCRIPTION));
    }

    @Test
    public void testListMapping(){
        List<StepEntity> inputList= Arrays.asList(provideStepEntity(),provideStepEntity(),
                provideStepEntity(),provideStepEntity(),provideStepEntity());
        List<Step> result=mapper.map(inputList);
        for(Step step:result){
            assertThat(step.getStepId(),is(FAKE_ID));
            assertThat(step.getDescription(),is(FAKE_TEXT));
            assertThat(step.getImageUrl(),is(FAKE_IMAGE_URL));
            assertThat(step.getVideoUrl(),is(FAKE_VIDEO_URL));
            assertThat(step.getShortDescription(),is(FAKE_DESCRIPTION));
        }
    }

}
