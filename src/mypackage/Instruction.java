package mypackage;

import java.util.Calendar;
import java.util.Date;

public class Instruction {

	public final static String AED = "AED";
	public final static String SGP = "SGP";
	public final static String SAR = "SAR";
	
	private String entity;
	private boolean buy;
	private double agreedFx;
	private String currency;
	private Date instructionDate;
	private Date settlementDate;
	private int units;
	private double pricePerUnit;
	
	public Instruction(String entity, boolean buy, double agreedFx, String currency, Date instructionDate,
			Date settlementDate, int units, double pricePerUnit) {
		
		super();
		this.entity = entity;
		this.buy = buy;
		this.agreedFx = agreedFx;
		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.units = units;
		this.pricePerUnit = pricePerUnit;
		
		fixSettlementDate();
	}

	public String getEntity() {
		return entity;
	}

	public boolean isBuy() {
		return buy;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public double getAmount() {
		return pricePerUnit*units*agreedFx;
	}

	/**
	 * A work week starts Monday and ends Friday, unless the currency of the trade is AED or SAR, where
	 * the work week starts Sunday and ends Thursday. No other holidays to be taken into account. 
	 * A trade can only be settled on a working day.
	 * If an instructed settlement date falls on a weekend, then the settlement date should be changed to
	 * the next working day.
	 */
	private void fixSettlementDate() {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(settlementDate);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		if (currency.equals(AED) || currency.equals(SAR)) {
			if (dayOfWeek == Calendar.FRIDAY) {
				cal.add(Calendar.DATE, 2);
			} else if (dayOfWeek == Calendar.SATURDAY) {
				cal.add(Calendar.DATE, 1);
			}
		} else {
			if (dayOfWeek == Calendar.SATURDAY) {
				cal.add(Calendar.DATE, 2);
			} else if (dayOfWeek == Calendar.SUNDAY) {
				cal.add(Calendar.DATE, 1);
			}
		}
		
		settlementDate = cal.getTime();
	}

	@Override
	public String toString() {
		return "Instruction [entity=" + entity + ", buy=" + buy + ", agreedFx=" + agreedFx + ", currency=" + currency
				+ ", instructionDate=" + instructionDate + ", settlementDate=" + settlementDate + ", units=" + units
				+ ", pricePerUnit=" + pricePerUnit + "]";
	}
	
	
}