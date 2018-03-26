package com.mypackage.bo;

import java.util.Date;
import java.util.Map;

public class OutputData {

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
	
	
}