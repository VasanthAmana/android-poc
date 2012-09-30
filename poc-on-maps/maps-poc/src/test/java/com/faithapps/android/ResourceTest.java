package com.faithapps.android;


import com.faithapps.android.poc.maps.Maps;
import com.faithapps.android.poc.maps.R;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ResourceTest {

    @Test
    public void shouldHaveTheRightTitle() {
        String appName = new Maps().getResources().getString(R.string.app_name);
        assertThat(appName, equalTo("Faith Maps"));
        throw new RuntimeException();
    }
    
    
}