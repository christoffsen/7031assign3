package school.comp7031.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CameraActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.doSetup();
        getLayoutInflater().inflate(R.layout.activity_camera, this.contentPanel);
    }
}