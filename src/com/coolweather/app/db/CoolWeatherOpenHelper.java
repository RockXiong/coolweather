package com.coolweather.app.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CoolWeatherOpenHelper extends SQLiteOpenHelper
	{

		/*
		 * Province���������
		 */
		public static final String CREATE_PROVINCE = "create table tb_Province(id integer primary key autoincrement,province_name text,province_code text)";
		/*
		 * City�������
		 */
		public static final String CREATE_CITY = "create table tb_City(id integer primary key autoincrement,city_name text,city_code text,city_url text,province_id text)";
		/* County������� */
		public static final String CREATE_County = "create table tb_County(id integer primary key autoincrement,county_name text,county_code text,city_id text)";

		public CoolWeatherOpenHelper(Context context, String name, CursorFactory factory, int version)
			{
				super(context, name, factory, version);
				// TODO Auto-generated constructor stub
			}

		@Override
		public void onCreate(SQLiteDatabase db)
			{
				// TODO Auto-generated method stub
				db.execSQL(CREATE_PROVINCE);// ����province��
				db.execSQL(CREATE_CITY);// ����city��
				db.execSQL(CREATE_County);// ����county��
			}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
			{
				// TODO Auto-generated method stub

			}

	}
