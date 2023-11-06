package com.Webscraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.google.common.base.Function;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class App {

	public static void main(String[] args) {

		List<Enterprise> enterpriseList = new ArrayList<>();
		long timestamp = System.currentTimeMillis();
		String url = "https://liste-exposants.hubj2c.com/natexpo23/main/loadTableForDatatable?lang=fr&_="
				+ String.valueOf(timestamp);
		String cookie = "PHPSESSID=modcdagutbfjhlvfsb777d1i78; Expires=null; Domain=hubj2c.com; Path=/; Secure; SameSite=None";

		// Construct the curl command
		List<String> commands = new ArrayList<>();
		commands.add("curl");
		commands.add("-k"); // Ignore SSL certificate verification
		commands.add("-H");
		commands.add("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		commands.add("-H");
		commands.add("Cookie: " + cookie);
		commands.add(url);
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(commands);
			Process process = processBuilder.start();

			// Capture the output of the command
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			StringBuilder response = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				response.append(line).append("\n");
			}

			// Wait for the command to complete
			int exitCode = process.waitFor();

			if (exitCode == 0) {

				String jsonString = response.toString();
				JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
				JsonArray dataArray = jsonObject.getAsJsonArray("data");

				for (int i = 0; i < dataArray.size(); i++) {
					JsonObject item = dataArray.get(i).getAsJsonObject();
					String exposantId = item.get("DT_RowId").getAsString();
					String exposantName = item.get("exposant").getAsString();
					enterpriseList.add(new Enterprise(exposantId, exposantName));
				}
			} else {
				System.err.println("Curl command failed with exit code " + exitCode);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		int numberOfCompanies = enterpriseList.size();
		int currentPass = 0;
		for (Enterprise company : enterpriseList) {
			String status = "Progress Status: " + currentPass + " / " + numberOfCompanies;
			System.out.print("\r" + status);

			// Specify the path to the ChromeDriver executable
			ExcelWriter file = new ExcelWriter("C:\\Users\\dionysis\\Desktop\\ListOfCompanies.xlsx", "first");
			WebDriveImpl.setWebDriver(company.getId());

			// Find and print the street information using XPath
			try {
				List<WebElement> streetP = WebDriveImpl.getFluentWait()
						.until(new Function<WebDriver, List<WebElement>>() {
							public List<WebElement> apply(WebDriver driver) {
								return driver.findElements(By.className("fe-adresse"));
							}
						});
				List<String> streetInfo = new ArrayList<>();
				for (WebElement el : streetP) {
					streetInfo.add(el.getText().trim());
				}
				company.setStreet_number(streetInfo);

			} catch (Exception e) {
				e.printStackTrace();
			}

			// Define social media platform patterns
			try {
				WebElement websiteElement = null;
				WebElement anchorElement = null;
				websiteElement = WebDriveImpl.getFluentWait().until(new Function<WebDriver, WebElement>() {
					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.className("fe-lien"));
					}
				});

				// Now you have the website element after the wait

				anchorElement = websiteElement.findElement(By.tagName("a"));
				company.setWebsite(anchorElement.getAttribute("href"));

			} catch (Exception e) {

			}
			try {
				List<WebElement> elements = WebDriveImpl.getFluentWait()
						.until(new Function<WebDriver, List<WebElement>>() {
							public List<WebElement> apply(WebDriver driver) {
								return driver.findElements(By.className("fe-social"));
							}
						});
				
				for (WebElement element : elements) {
					String href = element.getAttribute("href");
					if(href.contains("facebook")) {
						System.out.println("yeahhh");
					}
					company.setFacebook(FindElementUtil.find(RegexPatterns.FACEBOOK.getPattern(),href,true));
					System.out.println(company.getFacebook());
					company.setInstagram(FindElementUtil.find(RegexPatterns.INSTAGRAM.getPattern(),href,true));
					company.setLinkedin(FindElementUtil.find(RegexPatterns.LINKEDIN.getPattern(),href,true));
					company.setTwitter(FindElementUtil.find(RegexPatterns.TWITTER.getPattern(),href,true));
				}

		

			} catch (Exception e) {

			}

			String pageSource = WebDriveImpl.getDriver().getPageSource();

			company.setCity(FindElementUtil.find(RegexPatterns.CITY.getPattern(), pageSource,false));
			company.setPostal_code(FindElementUtil.find(RegexPatterns.POSTAL_CODE.getPattern(), pageSource,false));
			// Find the countryy
			String country = FindElementUtil.find(RegexPatterns.COUNTRY.getPattern(), pageSource,false);
			country = country.replaceAll("\\s+", " ").trim();
			company.setCountry(country);

			
			String excludedEmail = "natexpo@j2c-communication.fr";
			Pattern emailPattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,7}\\b");
			// Find all email addresses using regular expressions
			Matcher emailMatcher = emailPattern.matcher(pageSource);
			// Create a set to store unique email addresses
			Set<String> uniqueEmails = new HashSet<>();
			while (emailMatcher.find()) {
				String email = emailMatcher.group();
				if (!email.equals(excludedEmail)) {
					uniqueEmails.add(email);
				}
			}

			for (String email : uniqueEmails) {
				company.setEmail(email);

			}

			// Close the Selenium WebDriver
			WebDriveImpl.getDriver().quit();

			file.addRow(company);
			file.save("C:\\Users\\dionysis\\Desktop\\ListOfCompanies.xlsx");
			currentPass++;

		}

	}

}
