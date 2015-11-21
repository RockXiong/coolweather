package com.coolweather.app.activitty;

import java.util.Date;

import com.coolweather.app.R;
import com.coolweather.app.service.AutoUpdateService;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.MyUtility;
import com.coolweather.app.util.Utility;
import com.coolweather.app.util.weatherServiceURL;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;

public class WeatherActivity extends Activity implements OnClickListener
	{
		private LinearLayout weatherInfoLayout;
		/*
		 * ������ʾ������*
		 */
		private TextView textView_CityName;
		// ������ʾ����ʱ��
		private TextView textView_Publish;
		// ������ʾ����������Ϣ
		private TextView textView_WeatherDesp;
		// ��ʾ����1
		private TextView textView_Temp1;
		// ��ʾ����2
		private TextView textView_Temp2;
		// ��ʾ��ǰ����
		private TextView textView_currentData;
		// �л�����
		private Button button_SwitchCity;
		// ˢ������
		private Button button_Refresh;

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.app.Activity#onCreate(android.os.Bundle)
		 */
		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				// TODO Auto-generated method stub
				super.onCreate(savedInstanceState);
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				setContentView(R.layout.weather_layout);
				// ��ʼ�����ؼ�
				initControls();
				String countyCode = this.getIntent().getStringExtra("county_code");
				if (!TextUtils.isEmpty(countyCode))
					{
						// ���ؼ�����ʱ��ȥ��ѯ�ؼ�����
						textView_Publish.setText("����ͬ��...");
						weatherInfoLayout.setVisibility(View.INVISIBLE);
						textView_CityName.setVisibility(View.INVISIBLE);
						queryWeatherCode(countyCode);
					}
				else
					{
						// û���ؼ�����ʱ��ֱ����ʾ��������
						showWeather();
					}
				button_SwitchCity.setOnClickListener(this);
				button_Refresh.setOnClickListener(this);
			}

		private void initControls()
			{
				// TODO Auto-generated method stub
				weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
				textView_CityName = (TextView) findViewById(R.id.textView_CityName);
				textView_currentData = (TextView) findViewById(R.id.textView_CurrentDate);
				textView_Publish = (TextView) findViewById(R.id.textView_Publish);
				textView_Temp1 = (TextView) findViewById(R.id.textView_Temp1);
				textView_Temp2 = (TextView) findViewById(R.id.textView_Temp2);
				textView_WeatherDesp = (TextView) findViewById(R.id.textView_WeatherDesp);
				button_SwitchCity = (Button) findViewById(R.id.button_SwitchCity);
				button_Refresh = (Button) findViewById(R.id.button_RefreshWeather);
			}

		/*
		 * ��ѯ�ؼ���������Ӧ����������
		 */
		private void queryWeatherCode(String countyCode)
			{
				// TODO Auto-generated method stub
				String address = weatherServiceURL.weatherServiceAddress + countyCode + ".html";
				queryFromServer(address, "countyCode");
			}

		/*
		 * ��ѯ������������Ӧ������
		 */
		private void queryWeatherInfo(String weatherCode)
			{
				String address = weatherServiceURL.weatherServiceAddress + weatherCode + ".html";
				queryFromServer(address, "weatherCode");
			}

		/*
		 * ���ݴ���ĵ�ַ���������������ѯ�������Ż���������Ϣ
		 */
		private void queryFromServer(final String address, final String type)
			{
				// TODO Auto-generated method stub
				HttpUtil.sendHttpRequest(address, new HttpCallbackListener()
					{

						@Override
						public void onFinish(String response)
							{
								// TODO Auto-generated method stub
								if ("countyCode".equals(type))
									{
										if (!TextUtils.isEmpty(response))
											{
												// �ӷ��������ص������н�������������
												// String[] array=response
												MyUtility.handleCityWeatherInfoResponse(WeatherActivity.this, response);
												runOnUiThread(new Runnable()
													{
														public void run()
															{
																showWeather();
															}
													});
											}
									}
								else
									if ("weatherCode".equals(type))
										{
											// ������������ص�������Ϣ
											MyUtility.handleCityWeatherInfoResponse(WeatherActivity.this, response);
											runOnUiThread(new Runnable()
												{
													public void run()
														{
															showWeather();
														}
												});
										}
							}

						@Override
						public void onError(Exception e)
							{
								// TODO Auto-generated method stub
								runOnUiThread(new Runnable()
									{
										public void run()
											{
												textView_Publish.setText("ͬ��ʧ��!");
											}
									});
							}
					});
			}

		/*
		 * ��sharedpreferences�ļ��ж�ȡ�洢��������Ϣ������ʾ�������� SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(context).edit(); editor.putBoolean("city_selected", true); editor.putString("cityName", cityName); editor.putString("cityid", cityid); editor.putString("temp1", temp1); editor.putString("temp2", temp2); editor.putString("weather", weather); editor.putString("img1", img1); editor.putString("img2",img2); editor.putString("ptime",ptime); editor.putString("current_date", sdf.format(new Date()));
		 */
		private void showWeather()
			{
				// TODO Auto-generated method stub
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
				textView_CityName.setText(preferences.getString("cityName", " "));
				textView_Temp1.setText(preferences.getString("temp1", " "));
				textView_Temp2.setText(preferences.getString("temp2", " "));
				textView_WeatherDesp.setText(preferences.getString("weather", " "));
				textView_Publish.setText("����" + preferences.getString("ptime", " ") + "����");
				textView_currentData.setText(preferences.getString("current_date", " "));
				weatherInfoLayout.setVisibility(View.VISIBLE);
				textView_CityName.setVisibility(View.VISIBLE);
				Log.d("myService", "Weather Updated");
				// ��������
				Intent intent = new Intent(this, AutoUpdateService.class);
				startService(intent);
			}

		@Override
		public void onClick(View v)
			{
				// TODO Auto-generated method stub
				switch (v.getId())
					{
					case R.id.button_SwitchCity:
						Intent intent = new Intent(this, ChooseAreaActivity.class);
						intent.putExtra("from_weather_activity", true);
						startActivity(intent);
						finish();
						break;
					case R.id.button_RefreshWeather:
						textView_Publish.setText("����ͬ��...");
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
						String weatherCode = prefs.getString("cityid", " ");
						if (!TextUtils.isEmpty(weatherCode))
							{
								queryWeatherInfo(weatherCode);
							}
						break;
					default:
						break;
					}
			}
	}
