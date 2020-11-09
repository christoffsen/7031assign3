package school.comp7031.assignment3a2;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TabHost host = getTabHost();
        host.addTab(host.newTabSpec("one").setIndicator("TAB1").setContent(new Intent(this, CameraActivity.class)));
        host.addTab(host.newTabSpec("two").setIndicator("TAB2").setContent(new Intent(this, GalleryActivity.class)));
    }
}
