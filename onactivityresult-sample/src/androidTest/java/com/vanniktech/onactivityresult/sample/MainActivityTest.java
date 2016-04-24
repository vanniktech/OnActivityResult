package com.vanniktech.onactivityresult.sample;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void testActivityStarts() {
        getActivity(); // Trigger activity creation.
        getInstrumentation().waitForIdleSync(); // Wait for it to complete startup.

        getActivity().onActivityResult(2, 2, new Intent()); // Send dummy intent
    }
}
