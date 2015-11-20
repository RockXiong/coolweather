package com.coolweather.app.model;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.db.CoolWeatherOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CoolWeatherDB
	{
		/*
		 * 数据库名
		 */
		public static final String DB_NAME = "cool_weather.db";

		/*
		 * 表名
		 */
		private static final String TB_PROVINCE = "tb_Province";
		private static final String TB_CITY = "tb_City";
		private static final String TB_COUNTY = "tb_County";

		/*
		 * 字段名
		 */
		private static final String PROVINCE_NAME = "province_name";
		private static final String PROVINCE_CODE = "province_code";
		private static final String CITY_NAME = "city_name";
		private static final String CITY_CODE = "city_code";
		private static final String CITY_URL = "city_url";
		private static final String PROVINCE_ID = "province_id";
		private static final String COUNTY_NAME = "county_name";
		private static final String COUNTY_CODE = "county_code";
		private static final String CITY_ID = "city_id";

		/*
		 * 数据库版本
		 */
		public static final int VERSION = 1;

		private static CoolWeatherDB coolWeatherDB;
		private SQLiteDatabase database;

		/*
		 * 将构造方法私有化
		 */
		private CoolWeatherDB(Context context)
			{
				CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
				database = dbHelper.getWritableDatabase();
			}

		/*
		 * 获取CoolWeatherDB的实例
		 */
		public synchronized static CoolWeatherDB getInstance(Context context)
			{
				if (coolWeatherDB == null)
					{
						coolWeatherDB = new CoolWeatherDB(context);
					}
				return coolWeatherDB;
			}

		/*
		 * 将province实例存储到数据库
		 */
		public void saveProvince(Province province)
			{
				// database.insert(DB_NAME, nullColumnHack, values)
				if (province != null)
					{
						ContentValues values = new ContentValues();
						values.put(PROVINCE_NAME, province.getProvince_name());
						values.put(PROVINCE_CODE, province.getProvince_code());
						if (database.insert(TB_PROVINCE, null, values) > 0)
							{
								Log.d("insert", "insertd success");
							}

					}
			}

		/*
		 * 将city实例存储到数据库
		 */
		public void saveCity(City city)
			{
				if (city != null)
					{
						ContentValues values = new ContentValues();
						values.put(CITY_NAME, city.getCity_name());
						values.put(CITY_CODE, city.getCity_code());
						values.put(CITY_URL, city.getCity_url());
						values.put(PROVINCE_ID, city.getProvince_id());
						database.insert(TB_CITY, null, values);
					}
			}

		/*
		 * 将county实例存储到数据库
		 */
		public void saveCounty(County county)
			{
				if (county != null)
					{
						ContentValues values = new ContentValues();
						values.put(COUNTY_NAME, county.getCounty_name());
						values.put(COUNTY_CODE, county.getCounty_code());
						values.put(CITY_ID, county.getCity_id());
						database.insert(TB_COUNTY, null, values);
					}
			}

		/*
		 * 从数据库读取所有省份信息
		 */
		public List<Province> loadProvince()
			{
				List<Province> list = new ArrayList<Province>();
				Cursor cursor = database.query(TB_PROVINCE, null, null, null, null, null, null);// 查询整张Province表并将结果赋给cursor
				if (cursor.moveToFirst())
					{
						do
							{
								Province province = new Province();
								province.setId(cursor.getInt(cursor.getColumnIndex("id")));
								province.setProvince_name(cursor.getString(cursor.getColumnIndex(PROVINCE_NAME)));
								province.setProvince_code(cursor.getString(cursor.getColumnIndex(PROVINCE_CODE)));
								list.add(province);
							}
						while (cursor.moveToNext());
					}
				if (cursor != null)
					{
						cursor.close();
					}
				return list;
			}

		/*
		 * 从数据库读取一个省的所有城市信息
		 */
		public List<City> loadCity(String provinceID)
			{
				List<City> list = new ArrayList<City>();
				Cursor cursor = database.query(TB_CITY, null, PROVINCE_ID + "==?", new String[] { provinceID }, null, null, null);
				if (cursor.moveToFirst())
					{
						do
							{
								City city = new City();
								city.setId(cursor.getInt(cursor.getColumnIndex("id")));
								city.setCity_name(cursor.getString(cursor.getColumnIndex(CITY_NAME)));
								city.setCity_code(cursor.getString(cursor.getColumnIndex(CITY_CODE)));
								city.setProvince_id(cursor.getString(cursor.getColumnIndex(PROVINCE_ID)));
								list.add(city);
							}
						while (cursor.moveToNext());
					}
				if (cursor != null)
					{
						cursor.close();
					}
				return list;
			}

		/*
		 * 从数据库读取一个城市的所有县城信息
		 */
		public List<County> loadCounty(String cityID)
			{
				List<County> list = new ArrayList<County>();
				Cursor cursor = database.query(TB_COUNTY, null, CITY_ID + "==?", new String[] { cityID }, null, null, null);
				if (cursor.moveToFirst())
					{
						do
							{
								County county = new County();
								county.setId(cursor.getInt(cursor.getColumnIndex("id")));
								county.setCounty_name(cursor.getString(cursor.getColumnIndex(COUNTY_NAME)));
								county.setCounty_code(cursor.getString(cursor.getColumnIndex(COUNTY_CODE)));
								county.setCity_id(cursor.getString(cursor.getColumnIndex(CITY_ID)));
								list.add(county);
							}
						while (cursor.moveToNext());
					}
				if (cursor != null)
					{
						cursor.close();
					}
				return list;
			}
	}
