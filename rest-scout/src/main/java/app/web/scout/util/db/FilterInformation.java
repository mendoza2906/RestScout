package app.web.scout.util.db;

import lombok.Getter;
import lombok.Setter;

public class FilterInformation {
	@Getter @Setter	private String field;
	@Getter @Setter private String condition;
	@Getter @Setter private String operation;
	@Getter @Setter private String value;
	
}
