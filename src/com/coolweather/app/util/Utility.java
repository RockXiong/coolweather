package com.coolweather.app.util;

import android.text.TextUtils;

import com.coolweather.app.model.City;
import com.coolweather.app.model.CoolWeatherDB;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

public class Utility
	{
		/*
		 * �����ʹ�����������ص�ʡ������
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
										// ���������������ݴ洢��Province��
										coolWeatherDB.saveProvince(province);
									}
								return true;
							}
					}
				return false;
			}

		/*
		 * �����ʹ�����������ص��м�����
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
										// ���������������ݴ������ݿ�
										coolWeatherDB.saveCity(city);
									}
								return true;
							}
					}

				return false;
			}

		/*
		 * �����ʹ�����������ص��ؼ�����
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
