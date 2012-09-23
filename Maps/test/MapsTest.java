import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.faithapps.android.poc.maps.Maps;
import com.faithapps.android.poc.maps.R;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MapsTest {

    @Test
    public void shouldHaveHappySmiles() throws Exception {
    	String appName = new Maps().getResources().getString(R.string.app_name);
        assertThat(appName, equalTo("Maps"));
    }
}