package com.coolweather.app.util;

import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

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
	}
