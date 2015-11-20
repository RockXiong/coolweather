package com.coolweather.app.util;

import android.text.TextUtils;

import com.coolweather.app.model.City;
import com.coolweather.app.model.CoolWeatherDB;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

public class Utility
	{
		/*
		 * 解析和处理服务器返回的省级数据
		 */
		public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response)
			{
				if (!TextUtils.isEmpty(response))
					{
						String[] allProvinces = response.split(",");
						if (allProvinces != null && allProvinces.length > 0)
							{
								for (String p : allProvinces)
									{
										String[] array = p.split("\\|");
										Province province = new Province();
										province.setProvince_code(array[0]);
										province.setProvince_name(array[1]);
										// 将解析出来的数据存储到Province表
										coolWeatherDB.saveProvince(province);
									}
								return true;
							}
					}
				return false;
			}

		/*
		 * 解析和处理服务器返回的市级数据
		 */

		public synchronized static boolean handleCityResponse(CoolWeatherDB coolWeatherDB, String response, String provinceID)
			{
				if (!TextUtils.isEmpty(response))
					{
						String[] allCities = response.split(",");
						if (allCities != null && allCities.length > 0)
							{
								for (String c : allCities)
									{
										String[] array = c.split("\\|");
										City city = new City();
										city.setCity_code(array[0]);
										city.setCity_name(array[1]);
										city.setProvince_id(provinceID);
										// 将解析出来的数据存入数据库
										coolWeatherDB.saveCity(city);
									}
								return true;
							}
					}

				return false;
			}

		/*
		 * 解析和处理服务器返回的县级数据
		 */
		public synchronized static boolean handleCountyResponse(CoolWeatherDB coolWeatherDB, String response, String cityID)
			{
				if (!TextUtils.isEmpty(response))
					{
						String[] allCounties = response.split(",");
						if (allCounties != null && allCounties.length > 0)
							{
								for (String c : allCounties)
									{
										String[] array = c.split("\\|");
										County county = new County();
										county.setCity_id(cityID);
										county.setCounty_code(array[0]);
										county.setCounty_name(array[1]);
										coolWeatherDB.saveCounty(county);
									}
								return true;
							}
					}
				return false;
			}
	}
