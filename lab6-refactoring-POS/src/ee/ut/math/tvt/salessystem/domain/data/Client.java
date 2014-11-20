package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * System client. Every client has a name and a discount percentage. This object
 * is used by hibernate and corresponds to a table in database.
 */
@Entity
@Table(name = "CLIENT")
public class Client implements DisplayableItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "discount")
	private Integer discountPercentage;

	public Integer getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Integer discountPercentage)
			throws IllegalArgumentException {
		if (discountPercentage < 0) {
			throw new IllegalArgumentException();
		} else {
			this.discountPercentage = discountPercentage;
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws IllegalArgumentException {
		if (firstName == null) {
			throw new IllegalArgumentException();
		}
		if (firstName.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.firstName = firstName;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return firstName;
	}
}
