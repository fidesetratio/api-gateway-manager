package com.app.controller.datatables.data;

import java.util.List;
import java.util.Map;

import com.app.controller.datatables.models.PaginationCriteria;

public interface TableDataService {
	/**
	 * Used to get the total count of the entries (before filtering).
	 * 
	 * @return the total count of the entries.
	 * @throws TableDataException
	 */
	long countTotalEntries() throws TableDataException;

	/**
	 * Used to get the number of total filtered result according to provided search
	 * criteria declared in {@code PaginationCriteria}}
	 * 
	 * @param paginationCriteria
	 *            pagination criteria.
	 * @return the count of filter entries.
	 * @throws TableDataException
	 */
	long countFilteredEntries(PaginationCriteria paginationCriteria) throws TableDataException;

	/**
	 * Returns entries for a table page. It should filter entries by search keys and
	 * sort them by ordering criteria declared in {@code PaginationCriteria}}
	 * 
	 * @param paginationCriteria
	 *            pagination criteria.
	 * @return filter and ordered entities.
	 * @throws TableDataException
	 */
	List<Map<String, Object>> getPageEntries(PaginationCriteria paginationCriteria) throws TableDataException;

}
