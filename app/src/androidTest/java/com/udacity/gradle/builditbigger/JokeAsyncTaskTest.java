package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;


public class JokeAsyncTaskTest extends AndroidTestCase {

    public void testJokeDownload() {

        try {
            JokeAsyncTask task = new JokeAsyncTask();
            task.execute();
            String joke = task.get(10, TimeUnit.SECONDS);

            assertThat(joke.length(), is(greaterThan(0)));
            //assertTrue(joke.length() < 0);

        } catch (Exception e) {

        }
    }

}
