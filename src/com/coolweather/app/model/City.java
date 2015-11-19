package com.coolweather.app.model;

public class City
	{
		private int id;
		private int province_id;
		private String city_name;
		private String city_code;

		/**
		 * @return the id
		 */
		public int getId()
			{
				return id;
			}

		/**
		 * @param id
		 *            the id to set
		 */
		public void setId(int id)
			{
				this.id = id;
			}

		/**
		 * @return the province_id
		 */
		public int getProvince_id()
			{
				return province_id;
			}

		/**
		 * @param province_id
		 *            the province_id to set
		 */
		public void setProvince_id(int province_id)
			{
				this.province_id = province_id;
			}

		/**
		 * @return the city_name
		 */
		public String getCity_name()
			{
				return city_name;
			}

		/**
		 * @param city_name
		 *            the city_name to set
		 */
		public void setCity_name(String city_name)
			{
				this.city_name = city_name;
			}

		/**
		 * @return the city_code
		 */
		public String getCity_code()
			{
				return city_code;
			}

		/**
		 * @param city_code
		 *            the city_code to set
		 */
		public void setCity_code(String city_code)
			{
				this.city_code = city_code;
			}

	}
