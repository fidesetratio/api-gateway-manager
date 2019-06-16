package com.app.controller.datatables.models;

import java.util.List;

public class PaginationCriteria {
	
	/**
	 * Draw counter. This is used by DataTables to ensure that the Ajax returns from
	 * server-side processing requests are drawn in sequence by DataTables (Ajax
	 * requests are asynchronous and thus can return out of sequence). This is used
	 * as part of the draw return parameter.
	 */
	private int draw;

	/**
	 * Paging first record indicator. This is the start point in the current data
	 * set (0 index based - i.e. 0 is the first record).
	 */
	private int start;

	/**
	 * Number of records that the table can display in the current draw. It is
	 * expected that the number of records returned will be equal to this number,
	 * unless the server has fewer records to return. Note that this can be -1 to
	 * indicate that all records should be returned (although that negates any
	 * benefits of server-side processing!)
	 */
	private int length;

	/**
	 * Global search criteria.
	 */
	private SearchCriteria search;

	/**
	 * Column's ordering criteria.
	 */
	private List<OrderingCriteria> order;

	/**
	 * Table column's list.
	 */
	private List<Column> columns;

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public SearchCriteria getSearch() {
		return search;
	}

	public void setSearch(SearchCriteria search) {
		this.search = search;
	}

	public List<OrderingCriteria> getOrder() {
		return order;
	}

	public void setOrder(List<OrderingCriteria> order) {
		this.order = order;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		buffer.append("draw:"+draw);
		buffer.append("\n");
		buffer.append("start"+start);
		buffer.append("\n");
		buffer.append("order size:"+order.size());
		buffer.append("\n");
		buffer.append("length:"+length);
		buffer.append("\n");
		if(order.size()>0) {
			for(OrderingCriteria criteria : order) {
				buffer.append("column:"+criteria.getColumn()+" direction:"+criteria.getDir());

				buffer.append("\n");
			}
		}
		if(search != null) {
			buffer.append("search name:"+search.getValue()+" reqex:"+search.isRegex());
			buffer.append("\n");
			
		}
		
		return buffer.toString();
	}
	


}
