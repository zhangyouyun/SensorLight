package cn.jarlen.light;

import cn.jarlen.sensorlight.R;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorLight extends Activity implements SensorEventListener {

	public static final float LIGHT_SUNLIGHT_MAX = 120000.0f;
	public static final float LIGHT_SUNLIGHT = 110000.0f;
	public static final float LIGHT_SHADE = 20000.0f;
	public static final float LIGHT_OVERCAST = 10000.0f;
	public static final float LIGHT_SUNRISE = 400.0f;
	public static final float LIGHT_CLOUDY = 100.0f;
	public static final float LIGHT_FULLMOON = 0.25f;
	public static final float LIGHT_NO_MOON = 0.001f;

	private TextView content;
	private SensorManager sm;
	private Sensor light;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.light);
		content = (TextView) findViewById(R.id.content);
		sm = (SensorManager) this.getSystemService(SENSOR_SERVICE);

	}

	@Override
	protected void onResume() {
		super.onResume();

		light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
		if (light == null) {
			content.setText("显示失败");
		} else {
			sm.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sm.unregisterListener(this);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
			String mode = getLightMode(event.values[0]);
			content.setText(mode + " "+event.values[0]);
		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	private String getLightMode(float value) {

		if (value >= LIGHT_SUNLIGHT_MAX) {
			return "强光";
		} else if (value > LIGHT_SUNLIGHT) {
			return "强光";
		} else if (value > LIGHT_SHADE) {
			return "晴空万里";
		} else if (value > LIGHT_OVERCAST) {
			return "晴空有阴影";
		} else if (value > LIGHT_SUNRISE) {
			return "多云";
		} else if (value > LIGHT_CLOUDY) {
			return "日出";
		} else if (value > LIGHT_FULLMOON) {
			return "阴天";
		} else if (value > LIGHT_NO_MOON) {
			return "圆月";
		} else {
			return "漆黑一片";
		}
	}
}
