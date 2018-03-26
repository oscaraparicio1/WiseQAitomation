package es.sogeti.wiseqarest.utilities;

import java.util.List;

public class StringUtilities {

	public static String parseListToString(List<String> list) {
		StringBuilder output = new StringBuilder();
		for(String s : list) {
			output.append(s + "\n");
		}
		return output.toString();
	}
}
