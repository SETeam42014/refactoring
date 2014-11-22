package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.data.Sale;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.SalesSystemException;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Purchase history details model.
 */
public class PurchaseInfoTableModel extends SalesSystemTableModel<SoldItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger
			.getLogger(PurchaseInfoTableModel.class);

	private Sale sale;

	private SalesSystemModel model;

	public PurchaseInfoTableModel() {
		super(new String[] { "Id", "Name", "Price", "Quantity", "Sum" });
	}

	public PurchaseInfoTableModel(SalesSystemModel model) {
		this();
		this.sale = new Sale();
		this.model = model;
	}

	protected Object getColumnValue(SoldItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		case 4:
			return item.getSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return getColumnValue(sale.getSoldItems().get(rowIndex), columnIndex);
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final SoldItem item : sale.getSoldItems()) {
			buffer.append(item.getId() + "\t");
			buffer.append(item.getName() + "\t");
			buffer.append(item.getPrice() + "\t");
			buffer.append(item.getQuantity() + "\t");
			buffer.append(item.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	public SoldItem getForStockItem(long stockItemId) {
		for (SoldItem item : sale.getSoldItems()) {
			if (item.getStockItem().getId().equals(stockItemId)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Add new StockItem to table.
	 */
	public void addItem(final SoldItem soldItem) throws SalesSystemException {
		StockItem stockItem = soldItem.getStockItem();
		long stockItemId = stockItem.getId();
		SoldItem existingItem = getForStockItem(stockItemId);

		if (existingItem != null) {
			int totalQuantity = existingItem.getQuantity()
					+ soldItem.getQuantity();
			validateQuantityInStock(stockItem, totalQuantity);
			existingItem.setQuantity(totalQuantity);

			log.debug("Found existing item " + soldItem.getName()
					+ " increased quantity by " + soldItem.getQuantity());

		} else {
			validateQuantityInStock(soldItem.getStockItem(),
					soldItem.getQuantity());
			this.sale.addSoldItem(soldItem);
			// rows.add(soldItem);
			log.debug("Added " + soldItem.getName() + " quantity of "
					+ soldItem.getQuantity() + " soldItem_ID: "
					+ soldItem.getSale().getId() + " (" + this.sale.getId()
					+ ")");
		}

		fireTableDataChanged();
	}

	/**
	 * Returns the total sum that needs to be paid for all the items in the
	 * basket.
	 */
	public double getTotalPrice() {
		return this.sale.getSum();
	}

	private void validateQuantityInStock(StockItem item, int quantity)
			throws SalesSystemException {

		if (!model.getWarehouseTableModel().hasEnoughInStock(item, quantity)) {
			log.info(" -- not enough in stock!");
			throw new SalesSystemException();
		}

	}

	public static PurchaseInfoTableModel getEmptyTable() {
		return new PurchaseInfoTableModel();
	}

	/**
	 * Replace the current contents of the table with the SoldItems of the given
	 * Sale. (Used by the history details table in the HistoryTab).
	 */
	public void showSale(Sale sale) {
		this.sale = sale;
		fireTableDataChanged();
	}

	public List<SoldItem> getTableRows() {
		return sale.getSoldItems();
	}

	@Override
	public int getRowCount() {
		try {
			return this.sale.getSoldItems().size();
		} catch (Exception e) {
			return 0;
		}
	}

	public void clear() {
		List<SoldItem> soldItems = this.sale.getSoldItems();
		soldItems.clear();
	}

}
