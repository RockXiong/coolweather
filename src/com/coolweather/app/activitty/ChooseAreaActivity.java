package com.coolweather.app.activitty;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.app.R;
import com.coolweather.app.model.City;
import com.coolweather.app.model.CoolWeatherDB;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.MyUtility;

public class ChooseAreaActivity extends Activity
	{
		public final static int LEVEL_PROVINCE = 0;
		public final static int LEVEL_CITY = 1;
		public final static int LEVEL_COUNTY = 2;

		private ProgressDialog progressDialog;
		private TextView textViewTitleText;
		private ListView listView;
		private ArrayAdapter<String> adapter;
		private CoolWeatherDB coolWeatherDB;
		private List<String> dataList = new ArrayList<String>();
		// ʡ�б�
		private List<Province> provinceList;
		// ���б�
		private List<City> cityList;
		// ���б�
		private List<County> countyList;
		// ѡ�е�ʡ��
		private Province selectedProvince;
		// ѡ�еĳ���
		private City selectedCity;
		// ��ǰѡ�еļ���
		private int currentLevel;
		// ��־λ,�Ƿ��WeatherActivity��ת����
		private boolean isFromWeatherActivity;

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
				setContentView(R.layout.choose_area);
				isFromWeatherActivity = getIntent().getBooleanExtra("from_weather_activity", false);
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
				// �Ѿ�ѡ���˳����Ҳ��Ǵ�WeatherActivity��ת�������Ż�ֱ����ת��WeatherActivity
				if (preferences.getBoolean("city_selected", false) && !isFromWeatherActivity)
					{
						Intent intent = new Intent(this, WeatherActivity.class);
						startActivity(intent);
						finish();
						return;
					}

				textViewTitleText = (TextView) findViewById(R.id.textView_TitleText);
				listView = (ListView) findViewById(R.id.listView);
				adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
				listView.setAdapter(adapter);
				coolWeatherDB = CoolWeatherDB.getInstance(this);

				listView.setOnItemClickListener(new OnItemClickListener()
					{

						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id)
							{
								// TODO Auto-generated method stub
								if (currentLevel == LEVEL_PROVINCE)
									{
										selectedProvince = provinceList.get(position);
										queryCities();
									}
								else
									if (currentLevel == LEVEL_CITY)
										{
											selectedCity = cityList.get(position);
											queryCounties();
										}
									else
										if (currentLevel == LEVEL_COUNTY)
											{
												String countyCode = countyList.get(position).getCounty_code();
												Intent intent = new Intent(ChooseAreaActivity.this, WeatherActivity.class);
												intent.putExtra("county_code", countyCode);
												startActivity(intent);
												finish();
											}
							}
					});
				queryProvinces();// ����ʡ������
			}

		/*
		 * ��ѯȫ�����е�ʡ�����ȴ����ݿ��ѯ�����û�в�ѯ��ȥ�������ϲ�ѯ
		 */
		private void queryProvinces()
			{
				// TODO Auto-generated method stub
				provinceList = coolWeatherDB.loadProvince();
				if (provinceList.size() > 0)
					{
						dataList.clear();
						for (Province province : provinceList)
							{
								dataList.add(province.getProvince_name());
							}
						adapter.notifyDataSetChanged();// �����Ѿ����ģ�����adapter
						listView.setSelection(0);
						textViewTitleText.setText("�й�");
						currentLevel = LEVEL_PROVINCE;
					}
				else
					{
						queryFromServer(null, "province");
					}
			}

		private void queryFromServer(final String code, final String type)
			{
				// TODO Auto-generated method stub
				// ���ݴ���Ĵ��ź����ʹӷ������ϲ�ѯʡ��������
				String address;
				if (!TextUtils.isEmpty(code))
					{
						address = "http://flash.weather.com.cn/wmaps/xml/" + code + ".xml";
					}
				else
					{
						// address = "http://www.weather.com.cn/data/list3/city.xml";
						address = "http://flash.weather.com.cn/wmaps/xml/china.xml";
					}
				showProgressDialog();
				HttpUtil.sendHttpRequest(address, new HttpCallbackListener()
					{

						@Override
						public void onFinish(String response)
							{
								Log.d("response", response);
								// TODO Auto-generated method stub
								boolean result = false;
								// switch (type)
								// {
								// case "province":
								// result = MyUtility.handleProvincesResponse(coolWeatherDB, response);
								// Log.d("result", result + "");
								// break;
								// case "city":
								// result = Utility.handleCityResponse(coolWeatherDB, response, selectedProvince.getId());
								// break;
								// case "county":
								// result = Utility.handleCountyResponse(coolWeatherDB, response, selectedCity.getId());
								// break;
								// default:
								// break;
								// }
								if ("province".equals(type))
									{
										result = MyUtility.handleProvincesResponse(coolWeatherDB, response);
										Log.d("result", result + "");
									}
								else
									if ("city".equals(type))
										{
											result = MyUtility.handleCityResponse(coolWeatherDB, response, selectedProvince.getProvince_code());
										}
									else
										if ("county".equals(type))
											{
												result = MyUtility.handleCountyResponse(coolWeatherDB, response, selectedCity.getCity_code());
											}
								if (result)
									{
										// ͨ��runOnUiThread()�����ص����̴߳����߼�
										runOnUiThread(new Runnable()
											{
												@Override
												public void run()
													{
														closeProgressDialog();
														// switch (type)
														// {
														// case "province":
														// queryProvinces();
														// break;
														// case "city":
														// queryCities();
														// break;
														// case "county":
														// queryCounties();
														// break;
														// default:
														// break;
														// }
														if ("province".equals(type))
															{
																queryProvinces();
															}
														else
															if ("city".equals(type))
																{
																	queryCities();
																}
															else
																if ("county".equals(type))
																	{
																		queryCounties();
																	}
													}
											});
									}
							}

						@Override
						public void onError(Exception e)
							{
								// TODO Auto-generated method stub
								// ͨ��runOnUiThread()�����ص����̴߳����߼�
								runOnUiThread(new Runnable()
									{
										@Override
										public void run()
											{
												closeProgressDialog();
												Toast.makeText(ChooseAreaActivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
											}
									});
							}
					});
			}

		private void closeProgressDialog()
			{
				// TODO Auto-generated method stub
				if (progressDialog != null)
					{
						progressDialog.dismiss();
					}
			}

		private void showProgressDialog()
			{
				// TODO Auto-generated method stub

				// �رս��ȶԻ���
				if (progressDialog == null)
					{
						progressDialog = new ProgressDialog(this);
						progressDialog.setMessage("���ڼ���...");
						progressDialog.setCanceledOnTouchOutside(false);
					}
				progressDialog.show();
			}

		protected void queryCounties()
			{
				// TODO Auto-generated method stub
				// ��ѯѡ���������е��أ��������ݿ��ѯ���û����ӷ�������ѯ
				countyList = coolWeatherDB.loadCounty(selectedCity.getCity_code());
				if (countyList.size() > 0)
					{
						dataList.clear();
						for (County county : countyList)
							{
								dataList.add(county.getCounty_name());
							}
						adapter.notifyDataSetChanged();
						listView.setSelection(0);
						textViewTitleText.setText(selectedCity.getCity_name());
						currentLevel = LEVEL_COUNTY;
					}
				else
					{
						queryFromServer(selectedCity.getCity_code(), "county");
					}
			}

		protected void queryCities()
			{
				// TODO Auto-generated method stub
				// ��ѯѡ��ʡ�����е��У����ȴ����ݿ��ѯ�����û����ӷ�������ѯ
				cityList = coolWeatherDB.loadCity(selectedProvince.getProvince_code());
				if (cityList.size() > 0)
					{
						dataList.clear();
						for (City city : cityList)
							{
								dataList.add(city.getCity_name());
							}
						adapter.notifyDataSetChanged();
						listView.setSelection(0);
						textViewTitleText.setText(selectedProvince.getProvince_name());
						currentLevel = LEVEL_CITY;
					}
				else
					{
						queryFromServer(selectedProvince.getProvince_code(), "city");
					}
			}

		/*
		 * ����Back���������ݵ�ǰ�ļ������жϣ���ʱӦ�÷������б�ʡ�б���ֱ���˳�
		 */
		@Override
		public void onBackPressed()
			{
				if (currentLevel == LEVEL_COUNTY)
					{
						queryCities();
					}
				else
					if (currentLevel == LEVEL_CITY)
						{
							queryProvinces();
						}
					else
						{
							if (isFromWeatherActivity)
								{
									Intent intent = new Intent(this, WeatherActivity.class);
									startActivity(intent);
								}
							finish();
						}
			}
	}
