/**
 * 
 */
package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;

/**
 * @author Johani
 *
 */
public abstract class DatabaseTableModel<T extends DisplayableItem> extends
		SalesSystemTableModel<T> {

	private static final long serialVersionUID = 1L;

	protected List<T> rows;

	public DatabaseTableModel(final String[] headers) {
		super(headers);
		rows = new ArrayList<T>();
	}

	/**
	 * @param item
	 *            item describing selected row
	 * @param columnIndex
	 *            selected column index
	 * @return value displayed in column with specified index
	 */
	protected abstract Object getColumnValue(T item, int columnIndex);

	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public String getColumnName(final int columnIndex) {
		return headers[columnIndex];
	}

	public int getRowCount() {
		return rows.size();
	}

	public Object getValueAt(final int rowIndex, final int columnIndex) {
		return getColumnValue(rows.get(rowIndex), columnIndex);
	}

	// search for item with the specified id
	public T getItemById(final long id) {
		for (final T item : rows) {
			if (item.getId() == id)
				return item;
		}
		throw new NoSuchElementException();
	}

	public List<T> getTableRows() {
		return rows;
	}

	public void clear() {
		rows = new ArrayList<T>();
		fireTableDataChanged();
	}

	public void populateWithData(final List<T> data) {
		rows.clear();
		rows.addAll(data);
		fireTableDataChanged();
	}

	public void addRow(T row) {
		rows.add(row);
		fireTableDataChanged();
	}

	public T getRow(int index) {
		return rows.get(index);
	}

	public List<T> getRows() {
		return rows;
	}
}
