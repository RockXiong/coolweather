package com.coolweather.app.util;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.coolweather.app.model.City;
import com.coolweather.app.model.CoolWeatherDB;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

public class MyUtility
	{
		/*
		 * �����ʹ�����������ص�ʡ������//�����������xml����
		 */
		public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response)
			{
				// Log.d("myparser", "handleProvincesResponse");
				if (!TextUtils.isEmpty(response))
					{
						try
							{
								XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
								XmlPullParser xmlPullParser = factory.newPullParser();
								xmlPullParser.setInput(new StringReader(response));
								int eventType = xmlPullParser.getEventType();
								Log.d("myparser", "׼������");
								while (eventType != XmlPullParser.END_DOCUMENT)
									{
										String nodeName = xmlPullParser.getName();

										// ��ʼ����ĳ���ڵ�
										Log.d("myparser", "��ʼ����");
										switch (eventType)
											{
											case XmlPullParser.START_TAG:
												{
													if ("city".equals(nodeName))
														{
															Province province = new Province();
															province.setProvince_name(xmlPullParser.getAttributeValue(null, "quName"));
															province.setProvince_code(xmlPullParser.getAttributeValue(null, "pyName"));
															Log.d("nodeName", nodeName + "|" + xmlPullParser.getAttributeValue(null, "quName") + "|" + xmlPullParser.getAttributeValue(null, "pyName"));
															// ������ĳ���ڵ�󣬽��������������ݴ洢��Province��
															Log.d("myparser", "��ʼ�������ݿ�");
															coolWeatherDB.saveProvince(province);
														}
													break;
												}
											case XmlPullParser.END_TAG:
												break;
											default:
												break;
											}
										eventType = xmlPullParser.next();
									}
								return true;

							}
						catch (Exception e)
							{
								// TODO: handle exception
								Log.d("myparser", "����:" + e.getMessage());
								return false;
							}
					}
				return false;
			}

		/*
		 * �����ʹ�����������ص��м�����
		 */

		public synchronized static boolean handleCityResponse(CoolWeatherDB coolWeatherDB, String response, String provinceCode)
			{
				// Log.d("myparser", "handleProvincesResponse");
				if (!TextUtils.isEmpty(response))
					{
						try
							{
								XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
								XmlPullParser xmlPullParser = factory.newPullParser();
								xmlPullParser.setInput(new StringReader(response));
								int eventType = xmlPullParser.getEventType();
								Log.d("myparser", "׼������");
								while (eventType != XmlPullParser.END_DOCUMENT)
									{
										String nodeName = xmlPullParser.getName();

										// ��ʼ����ĳ���ڵ�
										Log.d("myparser", "��ʼ����");
										switch (eventType)
											{
											case XmlPullParser.START_TAG:
												{

													if ("city".equals(nodeName))
														{
															City city = new City();
															city.setCity_name(xmlPullParser.getAttributeValue(null, "cityname"));
															city.setCity_code(xmlPullParser.getAttributeValue(null, "pyName"));
															city.setCity_url(xmlPullParser.getAttributeValue(null, "url"));
															city.setProvince_id(provinceCode);
															Log.d("nodeName", nodeName + "|" + xmlPullParser.getAttributeValue(null, "cityname") + "|" + xmlPullParser.getAttributeValue(null, "pyName"));
															// ������ĳ���ڵ�󣬽��������������ݴ洢��city��
															Log.d("myparser", "��ʼ�������ݿ�");
															coolWeatherDB.saveCity(city);
														}

													break;
												}
											case XmlPullParser.END_TAG:
												break;
											default:
												break;
											}
										eventType = xmlPullParser.next();
									}
								return true;

							}
						catch (Exception e)
							{
								// TODO: handle exception
								Log.d("myparser", "����:" + e.getMessage());
								return false;
							}
					}
				return false;
			}

		/*
		 * �����ʹ�����������ص��ؼ�����
		 */
		public synchronized static boolean handleCountyResponse(CoolWeatherDB coolWeatherDB, String response, String cityID)
			{
				// Log.d("myparser", "handleProvincesResponse");
				if (!TextUtils.isEmpty(response))
					{
						try
							{
								XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
								XmlPullParser xmlPullParser = factory.newPullParser();
								xmlPullParser.setInput(new StringReader(response));
								int eventType = xmlPullParser.getEventType();
								Log.d("myparser", "׼������");
								while (eventType != XmlPullParser.END_DOCUMENT)
									{
										String nodeName = xmlPullParser.getName();

										// ��ʼ����ĳ���ڵ�
										Log.d("myparser", "��ʼ����");
										switch (eventType)
											{
											case XmlPullParser.START_TAG:
												{
													if ("city".equals(nodeName))
														{
															County county = new County();
															county.setCounty_name(xmlPullParser.getAttributeValue(null, "cityname"));
															county.setCounty_code(xmlPullParser.getAttributeValue(null, "url"));
															county.setCity_id(cityID);
															Log.d("nodeName", nodeName + "|" + xmlPullParser.getAttributeValue(null, "quName") + "|" + xmlPullParser.getAttributeValue(null, "pyName"));
															// ������ĳ���ڵ�󣬽��������������ݴ洢��Province��
															Log.d("myparser", "��ʼ�������ݿ�");
															coolWeatherDB.saveCounty(county);
														}
													break;
												}
											case XmlPullParser.END_TAG:
												break;
											default:
												break;
											}
										eventType = xmlPullParser.next();
									}
								return true;

							}
						catch (Exception e)
							{
								// TODO: handle exception
								Log.d("myparser", "����:" + e.getMessage());
								return false;
							}
					}
				return false;
			}

		/*
		 * �������������ص�JSON������Ϣ���ݣ���������������浽���� ���������ص���������: { "weatherinfo": { "city":"����", "cityid":"101300301", "temp1":"30��", "temp2":"21��", "weather":"����", "img1":"d1.gif", "img2":"n1.gif", "ptime":"08:00" } }
		 */
		public static void handleCityWeatherInfoResponse(Context context, String response)
			{
				try
					{
						JSONObject jsonObject = new JSONObject(response);
						JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
						String cityName = weatherInfo.getString("city");
						String cityid=weatherInfo.getString("cityid");
						String temp1=weatherInfo.getString("temp1");
						String temp2=weatherInfo.getString("temp2");
						String weather=weatherInfo.getString("weather");
						String img1=weatherInfo.getString("img1");
						String img2=weatherInfo.getString("img2");
						String ptime=weatherInfo.getString("ptime");
						saveCityWeatherInfo(context,cityName,cityid,temp1,temp2,weather,img1,img2,ptime);
					}
				catch (JSONException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace(); 
					}
			}

		/*
		 * ���������������ݱ��浽sharepreferences�ļ���
		 */
		private static void saveCityWeatherInfo(Context context, String cityName, String cityid, String temp1, String temp2, String weather, String img1, String img2, String ptime)
			{
				// TODO Auto-generated method stub
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy��M��d��",Locale.CHINA);
				SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(context).edit();
				editor.putBoolean("city_selected", true);
				editor.putString("cityName", cityName);
				editor.putString("cityid", cityid);
				editor.putString("temp1", temp1);
				editor.putString("temp2", temp2);
				editor.putString("weather", weather);
				editor.putString("img1", img1);
				editor.putString("img2",img2);
				editor.putString("ptime",ptime);
				editor.putString("current_date", sdf.format(new Date()));
				editor.commit();
				
			}

	}
