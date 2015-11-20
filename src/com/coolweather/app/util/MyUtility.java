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
		 * 解析和处理服务器返回的省级数据//这里解析的是xml数据
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
								Log.d("myparser", "准备解析");
								while (eventType != XmlPullParser.END_DOCUMENT)
									{
										String nodeName = xmlPullParser.getName();

										// 开始解析某个节点
										Log.d("myparser", "开始解析");
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
															// 解析完某个节点后，将解析出来的数据存储到Province表
															Log.d("myparser", "开始存入数据库");
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
								Log.d("myparser", "出错:" + e.getMessage());
								return false;
							}
					}
				return false;
			}

		/*
		 * 解析和处理服务器返回的市级数据
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
								Log.d("myparser", "准备解析");
								while (eventType != XmlPullParser.END_DOCUMENT)
									{
										String nodeName = xmlPullParser.getName();

										// 开始解析某个节点
										Log.d("myparser", "开始解析");
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
															// 解析完某个节点后，将解析出来的数据存储到city表
															Log.d("myparser", "开始存入数据库");
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
								Log.d("myparser", "出错:" + e.getMessage());
								return false;
							}
					}
				return false;
			}

		/*
		 * 解析和处理服务器返回的县级数据
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
								Log.d("myparser", "准备解析");
								while (eventType != XmlPullParser.END_DOCUMENT)
									{
										String nodeName = xmlPullParser.getName();

										// 开始解析某个节点
										Log.d("myparser", "开始解析");
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
															// 解析完某个节点后，将解析出来的数据存储到Province表
															Log.d("myparser", "开始存入数据库");
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
								Log.d("myparser", "出错:" + e.getMessage());
								return false;
							}
					}
				return false;
			}

		/*
		 * 解析服务器返回的JSON城市信息数据，并将解析结果保存到本地 服务器返回的数据样例: { "weatherinfo": { "city":"柳州", "cityid":"101300301", "temp1":"30℃", "temp2":"21℃", "weather":"多云", "img1":"d1.gif", "img2":"n1.gif", "ptime":"08:00" } }
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
		 * 将解析出来的数据保存到sharepreferences文件中
		 */
		private static void saveCityWeatherInfo(Context context, String cityName, String cityid, String temp1, String temp2, String weather, String img1, String img2, String ptime)
			{
				// TODO Auto-generated method stub
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);
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
