package school.comp7031.assignment3;

import android.os.Bundle;

public class SettingsActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.doSetup();
        getLayoutInflater().inflate(R.layout.activity_settings, this.contentPanel);
    }
}