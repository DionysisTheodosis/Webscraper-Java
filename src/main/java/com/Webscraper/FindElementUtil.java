package com.Webscraper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class FindElementUtil {
	private FindElementUtil(){
		
	}
	public static String find(String regex_str,String source,boolean url) {
		Pattern pattern = Pattern.compile(regex_str);
		Matcher match = pattern.matcher(source);
		if(url) {
			System.out.println(source);
			return match.find() ? source: null;
		}
		else {
			return match.find() ? match.group(1): null;
		}
		

	}
}
