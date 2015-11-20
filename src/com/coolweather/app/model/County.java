package com.coolweather.app.model;

public class County
	{
		private int id;
		private String city_id;
		private String county_name;
		private String county_code;

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
		 * @return the city_id
		 */
		public String getCity_id()
			{
				return city_id;
			}

		/**
		 * @param city_id
		 *            the city_id to set
		 */
		public void setCity_id(String city_id)
			{
				this.city_id = city_id;
			}

		/**
		 * @return the county_name
		 */
		public String getCounty_name()
			{
				return county_name;
			}

		/**
		 * @param county_name
		 *            the county_name to set
		 */
		public void setCounty_name(String county_name)
			{
				this.county_name = county_name;
			}

		/**
		 * @return the county_code
		 */
		public String getCounty_code()
			{
				return county_code;
			}

		/**
		 * @param county_code
		 *            the county_code to set
		 */
		public void setCounty_code(String county_code)
			{
				this.county_code = county_code;
			}

	}
