package school.comp7031.assignment3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navView;
    FrameLayout contentPanel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public void doSetup(){
        setContentView(R.layout.drawer_activity);
        DrawerLayout layout = (DrawerLayout)findViewById(R.id.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, layout, toolbar, R.string.open_drawer, R.string.close_drawer);
        layout.addDrawerListener(toggle);
        toggle.syncState();

        navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        contentPanel = (FrameLayout) findViewById(R.id.contentPanel);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Class activityClass;
        int itemID = item.getItemId();

        switch(itemID){
            case R.id.activity_camera:
                activityClass = CameraActivity.class;
                break;
            case R.id.activity_search:
                activityClass = SearchActivity.class;
                break;
            case R.id.activity_settings:
                activityClass = SettingsActivity.class;
                break;
            default:
                activityClass = MainActivity.class;
        }
        startActivity(new Intent(getApplicationContext(), activityClass));
        return true;
    }
}
