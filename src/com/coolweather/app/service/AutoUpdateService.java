package com.coolweather.app.service;

import com.coolweather.app.receiver.AutoUpdateReceiver;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.MyUtility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class AutoUpdateService extends Service
	{

		@Override
		public IBinder onBind(Intent intent)
			{
				// TODO Auto-generated method stub
				return null;
			}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
		 */
		@Override
		public int onStartCommand(Intent intent, int flags, int startId)
			{
				// TODO Auto-generated method stub
				new Thread(new Runnable()
					{
						public void run()
							{
								Log.d("myService", "updateWeather");
								updateWeather();
							}
					}).start();
				AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
				int hour = 1 * 15 * 1000;// 一分钟的毫秒数
				long triggerAtTime = SystemClock.elapsedRealtime() + hour;
				Intent i = new Intent(this, AutoUpdateReceiver.class);
				PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
				manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
				return super.onStartCommand(intent, flags, startId);
			}

		protected void updateWeather()
			{
				// TODO Auto-generated method stub
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
				String weatherCode = prefs.getString("cityid", " ");
				String address = "http://www.weather.com.cn/adat/cityinfo/" + weatherCode + ".html";
				Log.d("myService", weatherCode);
				HttpUtil.sendHttpRequest(address, new HttpCallbackListener()
					{

						@Override
						public void onFinish(String response)
							{
								// TODO Auto-generated method stub
								MyUtility.handleCityWeatherInfoResponse(AutoUpdateService.this, response);
								// Toast.makeText(AutoUpdateService.this, "Updated From AutoUpdateService", Toast.LENGTH_SHORT).show();
								Log.d("myService", "Updated From AutoUpdateService");
							}

						@Override
						public void onError(Exception e)
							{
								// TODO Auto-generated method stub

							}
					});
			}

	}
