package app.web.scout.util;

import org.apache.commons.lang.StringEscapeUtils;

public class XSSUtils {

	public static String stripXSS(String value) {
		return value == null ? value : StringEscapeUtils.escapeHtml(value);
	}

}
