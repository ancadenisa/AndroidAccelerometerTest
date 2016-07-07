package org.flowerplatform.acceslerometertest;

import android.support.v7.app.ActionBarActivity;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class AccelerometerActivity extends ActionBarActivity implements SensorEventListener {
	
	Sensor accelerometer;
	Sensor gyroscope;
	Sensor orientaton;
	
	SensorManager sensorManager;
	TextView acceleration;
	TextView rotation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accelerometer);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		if(gyroscope == null) {
			Toast.makeText(this, "Gyroscope sensor not supported, using orientaton sesor instead", Toast.LENGTH_LONG).show();
			orientaton = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
			sensorManager.registerListener(this, orientaton, SensorManager.SENSOR_DELAY_NORMAL);
			
		} else {
			sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		acceleration = (TextView) findViewById(R.id.acceleration);
		rotation = (TextView) findViewById(R.id.rotation);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accelerometer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public class JavascriptBridge {
		Context mContext;

		JavascriptBridge(Context c) {
			mContext = c;
		}
		@JavascriptInterface 
		public void showToast(String toast) {
			Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
		}

	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		 if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			acceleration.setText("Acceleration: \nX: " + event.values[0] + "\nY: " + event.values[1] + "\nZ: " + event.values[2]);
		 } else if (gyroscope != null && event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			rotation.setText("Gyroscope: \nX: " + event.values[0] + "\nY: " + event.values[1] + "\nZ: " + event.values[2]);
		 } else if (orientaton!= null && event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			 rotation.setText("Orientation: \nX: " + event.values[0] + "\nY: " + event.values[1] + "\nZ: " + event.values[2]); 
		 }
		
	}
}
