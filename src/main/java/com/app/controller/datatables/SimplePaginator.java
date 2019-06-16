package com.app.controller.datatables;

import com.app.controller.datatables.data.TableDataException;
import com.app.controller.datatables.data.TableDataService;
import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.TablePage;
//https://github.com/davioooh/datatables-pagination/blob/2.x/datatables-pagination-example/src/main/java/com/davioooh/paginationdemo/UserTableRepository.java
public class SimplePaginator implements TablePaginator {

	
	
	private TableDataService dataService;

    public SimplePaginator(TableDataService dataService) {
        this.dataService = dataService;
    }
    
    
    
	@Override
	public TablePage getPage(PaginationCriteria paginationCriteria) {
		   TablePage page = new TablePage();
	        try {
	            page = generatePage(paginationCriteria);
	        } catch (TableDataException tde) {
	             page.setError("Error generating table page.");
	        }
	        return page;	
	}
	protected TablePage generatePage(PaginationCriteria paginationCriteria) throws TableDataException {
        TablePage page = new TablePage();
        page.setDraw(paginationCriteria.getDraw());
        page.setRecordsTotal(dataService.countTotalEntries());
        page.setRecordsFiltered(dataService.countFilteredEntries(paginationCriteria));

        page.setData(dataService.getPageEntries(paginationCriteria));

        return page;
}
}
