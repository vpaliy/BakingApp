package data.local;

import android.content.Context;
import android.os.Build;
import android.support.compat.BuildConfig;
import com.vpaliy.bakingapp.data.local.RecipeDatabaseHelper;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = Build.VERSION_CODES.LOLLIPOP)
public class RecipeDatabaseHelperTest {

    private RecipeDatabaseHelper recipeDatabaseHelper;

    @Before
    public void setUp(){
        Context context= RuntimeEnvironment.application;
        recipeDatabaseHelper=new RecipeDatabaseHelper(context);
    }

    @After
    public void cleanUp(){
        if(recipeDatabaseHelper!=null) recipeDatabaseHelper.close();
    }

    @Test
    public void returnsCorrectDatabaseName(){
        String databaseName=RecipeDatabaseHelper.DATABASE_NAME;
        assertThat(databaseName,is(recipeDatabaseHelper.getDatabaseName()));
    }




}
