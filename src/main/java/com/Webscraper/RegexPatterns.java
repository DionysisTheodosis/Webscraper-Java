package com.Webscraper;

public enum RegexPatterns {
		FACEBOOK("^(https?://)?(www\\.)?facebook\\.com/"),
	    INSTAGRAM("^(https?://)?(www\\.)?instagram\\.com/"),
	    LINKEDIN("^(https?://)?(www\\.)?linkedin\\.com/"),
	    TWITTER("^(https?://)?(www\\.)?twitter\\.com/"),
	    CITY("<span\\s*style\\s*=\\s*\"text-transform:uppercase;\">\\s*([^<]+)</span></div>\\s*<div\\s*class\\s*=\\s*\"\">"),
	    POSTAL_CODE("</div>\\s*<div\\s*class\\s*=\\s*\"\">\\s*([^<]+)<span\\s*style\\s*=\\s*\"text-transform:uppercase;\">"),
	    COUNTRY("</span></div>\\s*<div\\s*class\\s*=\\s*\"\">\\s*([^<]+)");
	    private final String pattern;

	    RegexPatterns(String pattern) {
	        this.pattern = pattern;
	    }

	    public String getPattern() {
	        return pattern;
	    }
	}


