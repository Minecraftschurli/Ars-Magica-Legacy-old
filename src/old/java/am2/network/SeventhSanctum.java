package am2.network;

import java.util.HashMap;
import java.util.LinkedList;

import am2.ArsMagica2;

public class SeventhSanctum{
	private static final String webURL = "http://www.seventhsanctum.com/generate.php?Genname=spell";
	private boolean failed = false;

	private static final HashMap<String, String> postOptions = new HashMap<String, String>();

	public static final SeventhSanctum instance = new SeventhSanctum();

	private LinkedList<String> suggestions;

	private SeventhSanctum(){
		suggestions = new LinkedList<String>();
	}

	public void init(){
		postOptions.clear();
		postOptions.put("selGenCount", "25");
		postOptions.put("selGenType", "SEEDALL");

		if (ArsMagica2.config.suggestSpellNames())
			getSuggestions();
		else
			failed = true;
	}

	public String getNextSuggestion(){
		if (failed) return "";

		if (suggestions.size() <= 0)
			getSuggestions();

		if (suggestions.size() <= 0)
			return "";

		return suggestions.pop();
	}

	private void getSuggestions(){
		try{
			String s = am2.utils.WebRequestUtils.sendPost(webURL, postOptions);
			//LogHelper.info(s);
			int startIndex = s.lastIndexOf("SubSubContentTitle");
			if (startIndex == -1) return;
			startIndex = s.indexOf("<!--Title -->", startIndex) + 13;
			int endIndex = s.indexOf("&nbsp;", startIndex);
			if (endIndex == -1) return;

			s = s.substring(startIndex, endIndex);
			s = s.replace("\t", "");
			String[] suggestions = s.split("<div class=\"GeneratorResult");
			for (String suggestion : suggestions)
				parseAndAddSuggestion(suggestion);
		}catch (Throwable t){
			t.printStackTrace();
			failed = true;
		}
	}

	private void parseAndAddSuggestion(String s){
		if (!s.endsWith("</div>")) return;

		int startIndex = s.indexOf(">");
		if (startIndex == -1) return;
		int endIndex = s.indexOf("<", startIndex);
		if (endIndex == -1) return;

		s = s.substring(startIndex + 1, endIndex);
		if (s.length() <= 20)
			suggestions.add(s);
	}
}
