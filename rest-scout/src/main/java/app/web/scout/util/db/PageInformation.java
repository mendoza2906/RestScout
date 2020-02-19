package app.web.scout.util.db;

import java.util.List;

import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.Setter;

public class PageInformation {
	@Getter @Setter	private int page;
	@Getter @Setter private int size;
	@Getter @Setter private String sortfield;
	@Getter @Setter private Sort.Direction direction;
	@Getter @Setter private List<FilterInformation> filterInformation;
}
