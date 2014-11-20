package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STOCKITEM")
public class StockItem implements DisplayableItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = false)
	private String name;

	@Column(nullable = false)
	private double price;
	@Column(length = 128)
	private String description;

	@Column(nullable = false)
	private int quantity;

	public StockItem(String name, String desc, double price, int quantity) {
		this.name = name;
		this.description = desc;
		this.price = price;
		this.quantity = quantity;
	}

	/**
	 * Constructs new <code>StockItem</code>.
	 */
	public StockItem() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description)
			throws IllegalArgumentException {
		if (description.isEmpty() || description == null) {
			throw new IllegalArgumentException();
		} else {
			this.description = description;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalArgumentException {
		if (name.isEmpty() || name == null) {
			throw new IllegalArgumentException();
		} else {
			this.name = name;
		}
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		if (price < 0.0) {
			throw new IllegalArgumentException();
		} else {
			this.price = price;
		}
	}

	public Long getId() {
		return id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) throws IllegalArgumentException {
		if (quantity < 0) {
			throw new IllegalArgumentException();
		} else {
			this.quantity = quantity;
		}
	}

	@Override
	public String toString() {
		// return id + " " + name + " " + description + " " + price;
		return name;
	}

}
