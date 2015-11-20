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
	}
