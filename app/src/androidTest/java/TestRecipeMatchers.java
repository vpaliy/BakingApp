
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.utils.StringUtils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.vpaliy.bakingapp.utils.StringUtils.mergeColoredText;

public class TestRecipeMatchers {

    static Matcher<View> withColoredText(int leftTextId, String right){
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            protected boolean matchesSafely(TextView item) {
                if(item.getText()!=null) {
                    String result= mergeColoredText(item.getContext()
                            .getString(leftTextId),right,0,0).toString();
                    return TextUtils.equals(item.getText(), result);
                }
                return false;
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("Has text from a resource:"+leftTextId+" and:"+right);
            }
        };
    }

    static Matcher<View> withTextAndDisplayed(String text){
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            protected boolean matchesSafely(TextView item) {
                return  withText(text).matches(item) && isDisplayed().matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Is visible and has text:"+text);
            }
        };
    }

    static Matcher<View> withDrawable(final int resourceId){
        return new BoundedMatcher<View, CircleImageView>(CircleImageView.class) {
            @Override
            protected boolean matchesSafely(CircleImageView item) {
                return drawablesMatch(item.getDrawable(), ContextCompat.getDrawable(item.getContext(),resourceId));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format(Locale.US,"Has image drawable %d",resourceId));
            }
        };
    }

    static Matcher<View> withCompoundDrawable(final int resourceId){
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            protected boolean matchesSafely(TextView item) {
                Drawable match=ContextCompat.getDrawable(item.getContext(),resourceId);
                for(Drawable drawable:item.getCompoundDrawables()){
                    if(drawablesMatch(drawable,match)){
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format(Locale.US,"Has drawable %d",resourceId));
            }
        };
    }

    private static boolean drawablesMatch(Drawable drawableA, Drawable drawableB) {
        if(drawableA==null||drawableB==null) return false;
        Drawable.ConstantState stateA = drawableA.getConstantState();
        Drawable.ConstantState stateB = drawableB.getConstantState();
        return (stateA != null && stateB != null && stateA.equals(stateB))
                || getBitmap(drawableA).sameAs(getBitmap(drawableB));
    }

    private static Bitmap getBitmap(Drawable drawable) {
        Bitmap result;
        if (drawable instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return result;
    }
}
