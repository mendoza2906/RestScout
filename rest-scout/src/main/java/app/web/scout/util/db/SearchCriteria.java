package app.web.scout.util.db;

import lombok.Getter;
import lombok.Setter;

public class SearchCriteria {
	
	public SearchCriteria(String key, String operation, String value) {
		this.setKey(key);
		this.setOperation(operation);
		this.setValue(value);
	}
	
	
	@Getter @Setter	private String key;
	@Getter @Setter private String operation;
	@Getter @Setter private Object value;
	
}
