package com.Webscraper;

import java.util.List;

public class Enterprise {
	private String id;
	private String company_name;
	private String country;
	private List<String> street_number;
	private String postal_code;
	private String city;
	private String website;
	private String linkedin;
	private String facebook;
	private String instagram;
	private String twitter;
	private String email;

	public Enterprise(String id, String company_name) {
		this.id = id;
		this.company_name = company_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<String> getStreet_number() {
		return street_number;
	}

	public void setStreet_number(List<String> street_number) {
		this.street_number = street_number;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Enterprise [id=" + id + ", company_name=" + company_name + ", country=" + country + ", street_number="
				+ street_number + ", postal_code=" + postal_code + ", city=" + city + ", website=" + website
				+ ", linkedin=" + linkedin + ", facebook=" + facebook + ", instagram=" + instagram + ", twitter="
				+ twitter + ", email=" + email + "]";
	}

}
