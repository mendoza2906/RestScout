package app.web.scout.rest;

import org.springframework.data.domain.PageRequest;

/**
 * Controller abstract class for REST controllers.
 * @author Carlos
 *
 */
public abstract class Controller {

    /**
     * Gets a PageRequest object for paging searches. 
     * @param page
     * @param rows
     * @param sortField
     * @param sortOrder
     * @return
     */
    protected PageRequest getPageRequest(Integer page, Integer rows, String sortField, Integer sortOrder) {
    	PageRequest pageRequest;
		
    	if (sortOrder != null && 
    		sortField != null && 
    		!"undefined".equals(sortField)) {
    		if (sortOrder == 1) {
    			pageRequest = PageRequest.of(page, rows, org.springframework.data.domain.Sort.by(sortField).ascending());
    		} else {
    			pageRequest = PageRequest.of(page, rows, org.springframework.data.domain.Sort.by(sortField).descending());
    		}
    	} else {
    		pageRequest = PageRequest.of(page, rows);
    	}
    	return pageRequest;
    }

}
