package school.comp7031.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.SpeechRecognizer;
import android.os.Bundle;
import android.gesture.*;

import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.GestureDetector;
import android.widget.ImageView;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import java.util.PropertyResourceBundle;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureOverlayView.OnGesturePerformedListener, SensorEventListener {
    private int index = 0;
    private int gestureThreshold = 5;
    private int samplingPeriod = 100000;
    private boolean canTilt = false;
    private double lastTiltTime = 0;
    private float zRef = Float.MIN_VALUE;

    private CountDownTimer timer;
    private GestureDetector gestures;
    private GestureLibrary customGestures;
    private SensorManager sensorManager;
    private Sensor sensor;
    private LinkedList<Integer> imgList = new LinkedList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestures = new GestureDetector(getBaseContext(), this);
        Collections.addAll(imgList, R.drawable.dog, R.drawable.cat, R.drawable.ferret, R.drawable.parakeet);

        customGestures = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if(!customGestures.load()){
            new AlertDialog.Builder(this).setMessage("Failed to load gestures").show();
            finish();
        }
        GestureOverlayView gestureView = findViewById(R.id.gestureView);
        gestureView.addOnGesturePerformedListener(this);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(this, sensor, samplingPeriod);

        timer = new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                canTilt = true;
            }
        }.start();

    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = customGestures.recognize(gesture);
        for (Prediction curPred : predictions) {
            if(curPred.score > gestureThreshold) {
                if(curPred.name.contains("right")) {
                    goRight(overlay);
                }
                else if(curPred.name.contains("left")) {
                    goLeft(overlay);
                }
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float z = event.values[2];
            if(zRef == Float.MIN_VALUE) {
                zRef = z;
                return;
            }
            else {
                if(canTilt) {
                    float zChange = zRef - z;
                    if (zChange > 0.1f) {
                        goRight(null);
                    }
                    else if (zChange < -0.1f) {
                        goLeft(null);
                    }
                    canTilt = false;
                    timer.start();
                }
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {return gestures.onTouchEvent(event);}

    @Override
    public boolean onDown(MotionEvent event) {return true;}


    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        this.goRight(findViewById(R.id.rightButton));
        return true;
    }


    @Override
    public void onLongPress(MotionEvent e) {
        this.goLeft(findViewById(R.id.leftButton));
    }


    void updateImageToIndex() {
        View f = findViewById(R.id.imageView);
        ((ImageView)findViewById(R.id.imageView)).setImageResource(imgList.get(index));
    }

    public void goRight(View view) {
        index += 1;
        if(index >= imgList.size()) {
            index = 0;
        }
        updateImageToIndex();
    }

    public void goLeft(View view) {
        index -= 1;
        if(index < 0){
            index = imgList.size() - 1;
        }
        updateImageToIndex();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) { return false; }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) { return false; }


    public void startSpeechActivity (View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int request, int result, Intent intent) {
        super.onActivityResult(request, result, intent);
        try {
            ArrayList<String> speech = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String word = speech.get(0);
            if(word.contains("right")) {
                goRight(null);
            }
            else if(word.contains("left")) {
                goLeft(null);
            }
        }
        catch (Exception e){
            Log.println(Log.WARN, "speech", "Unable to parse speech");
        }
    }
}