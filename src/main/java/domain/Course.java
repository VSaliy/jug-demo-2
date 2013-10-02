package domain;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * Represents a public Spring training course.
 * <p>
 * The <tt>@Data</tt> annotation is a piece of 'magic' from project Lombok (a
 * really beautiful island in Indonesia BTW) that automatically generates
 * getters, setters and other tedious boiler plate methods. Makes Java almost
 * Groovy!
 * 
 * @author Paul Chapman
 * @see projectlombok.org
 */
@Entity
public @Data
class Course {

	@Id
	protected long id;

	protected String name;

	protected String number;

	protected String location;

	protected Date start;

	protected int duration;

	protected BigDecimal price;

	@Column(name = "discount")
	protected int earlyBirdDiscount;

	public String toString() {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(start);
		calendar.add(Calendar.DATE, duration);
		Date end = calendar.getTime();

		return String
				.format("%s (%s)\t%-15s\t%4$ta %4$tb %4$te\t$%5$7.2f (early-bird discount %% %6$d)",
						name, number, location, start, price, earlyBirdDiscount);
	}
}
