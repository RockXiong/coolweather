package com.coolweather.app.model;

public class City
	{
		private int id;
		private String province_id;
		private String city_name;
		private String city_code;
		private String city_url;
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
		public String getProvince_id()
			{
				return province_id;
			}

		/**
		 * @param provinceCode
		 *            the province_id to set
		 */
		public void setProvince_id(String provinceCode)
			{
				this.province_id = provinceCode;
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

		public String getCity_url()
			{
					return city_url;
			}

		public void setCity_url(String city_url)
			{
					this.city_url = city_url;
			}

	}
