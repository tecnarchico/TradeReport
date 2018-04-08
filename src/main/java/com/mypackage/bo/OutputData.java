package com.mypackage.bo;

import static com.mypackage.Util.dateFormat;

import java.util.Date;
import java.util.Map;

public class OutputData {

	private final static String INCOMING = "incoming";
	private final static String OUTGOING = "outgoing";

	Map<Date, Double> settledIncomingByDate;
	Map<Date, Double> settledOutgoingByDate;
	Map<String, Double> settledIncomingByEntity;
	Map<String, Double> settledOutgoingByEntity;
	
	public OutputData(	Map<Date, Double> settledIncomingByDate,
	Map<Date, Double> settledOutgoingByDate,
	Map<String, Double> settledIncomingByEntity,
	Map<String, Double> settledOutgoingByEntity) {
		super();
		this.settledIncomingByDate   = settledIncomingByDate;
		this.settledOutgoingByDate   = settledOutgoingByDate;
		this.settledIncomingByEntity = settledIncomingByEntity;
		this.settledOutgoingByEntity = settledOutgoingByEntity;
	}

	public Map<Date, Double> getSettledIncomingByDate() {
		return settledIncomingByDate;
	}

	public Map<Date, Double> getSettledOutgoingByDate() {
		return settledOutgoingByDate;
	}

	public Map<String, Double> getSettledIncomingByEntity() {
		return settledIncomingByEntity;
	}

	public Map<String, Double> getSettledOutgoingByEntity() {
		return settledOutgoingByEntity;
	}
	
	public void print() {
		printSettledIncomingByDate();
		printSettledOutgoingByDatee();
		printSettledIncomingByEntity();
		printSettledOutgoingByEntity();
	}
	

	private void printSettledIncomingByDate() {
		printSettledByDatee(settledIncomingByDate, INCOMING);
	}
	
	private void printSettledOutgoingByDatee() {
		printSettledByDatee(settledOutgoingByDate, OUTGOING);
	}
	
	private static void printSettledByDatee(Map<Date, Double> settledByDate, String direction) {
		double amount = 0.0;
		System.out.println(String.format("=============== Amount in USD settled %s everyday", direction));
		System.out.println(String.format("%s, %s", "date", "amount"));		
		for (Date date : settledByDate.keySet()) {
			amount = settledByDate.get(date);
			System.out.println(String.format("%s, %s", dateFormat.format(date), amount));
		}
	}
	
	private void printSettledIncomingByEntity() {
		printSettledByEntity(settledIncomingByEntity, INCOMING);
	}
	
	private void printSettledOutgoingByEntity() {
		printSettledByEntity(settledOutgoingByEntity, OUTGOING);
	}
	
	private static void printSettledByEntity(Map<String, Double> settledByEntity, String direction) {
		double amount = 0.0;
		int rank = 0;
		System.out.println(String.format("=============== Ranking of entities based on %s amount", direction));
		System.out.println(String.format("%s, %s, %s", "rank", "entity", "amount"));		
		for (String _entity : settledByEntity.keySet()) {
			++rank;
			amount = settledByEntity.get(_entity);
			System.out.println(String.format("%s, %s, %s", rank, _entity, amount));
		}
	}
	
}