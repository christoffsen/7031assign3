package school.comp7031.assignment3;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabContainer extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TabHost tabHost = getTabHost();
        tabHost.setup();
        TabHost.TabSpec mainTabSpec = tabHost.newTabSpec("photos");
        TabHost.TabSpec videoTabSpec = tabHost.newTabSpec("videos");
        TabHost.TabSpec audioTabSpec = tabHost.newTabSpec("audio");

        mainTabSpec.setIndicator("photos");
        videoTabSpec.setIndicator("videos");
        audioTabSpec.setIndicator("audio");

        mainTabSpec.setContent(new Intent(this, MainActivity.class));
        videoTabSpec.setContent(new Intent(this, VideoGalleryActivity.class));
        audioTabSpec.setContent(new Intent(this, AudioGalleryActivity.class));

        tabHost.addTab(mainTabSpec);
        tabHost.addTab(videoTabSpec);
        tabHost.addTab(audioTabSpec);

        tabHost.setCurrentTab(0);
    }
}