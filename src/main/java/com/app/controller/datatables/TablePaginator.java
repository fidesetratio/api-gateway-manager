package com.app.controller.datatables;

import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.TablePage;

public interface TablePaginator {
	TablePage getPage(PaginationCriteria paginationCriteria);
}
