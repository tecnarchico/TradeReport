package com.mypackage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.mypackage.bo.Instruction;
import com.mypackage.bo.OutputData;

public class Main {

	/**
	 * Create a report that shows
	 * Amount in USD settled incoming everyday
	 * Amount in USD settled outgoing everyday
	 * Ranking of entities based on incoming and outgoing amount. Eg: If entity foo instructs the highest
	 * amount for a buy instruction, then foo is rank 1 for outgoing
	 */
	private final static DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");

	public final static String INCOMING = "incoming";
	public final static String OUTGOING = "outgoing";
	

	public static void main(String[] args) {
		List<Instruction> instructions;
		try {
			instructions = loadData();
			OutputData outputData = elab(instructions);
			printOutputData(outputData);
		} catch (ParseException e) {
			System.out.println("There was an error parsing the data");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static List<Instruction> loadData() throws ParseException {
		
		List<Instruction> instructions = new ArrayList<>();
 
		//Expected output:
		//
		//		=============== Amount in USD settled incoming everyday
		//		date, amount
		//		01/Jan/2016, 10025.0
		//		04/Jan/2016, 1704.25
		//		05/Jan/2016, 1704.25
		//		11/Jan/2016, 60150.0
		//		15/Jan/2016, 275687.5
		//		18/Jan/2016, 5012.5
		//		=============== Amount in USD settled outgoing everyday
		//		date, amount
		//		07/Jan/2016, 16290.12
		//		10/Jan/2016, 2483.25
		//		13/Jan/2016, 2317.7
		//		17/Jan/2016, 1523.06
		//		19/Jan/2016, 2880.57
		//		=============== Ranking of entities based on incoming amount
		//		rank, entity, amount
		//		1, baz, 277391.75
		//		2, foo, 76891.75
		//		=============== Ranking of entities based on outgoing amount
		//		rank, entity, amount
		//		1, bar, 20130.88
		//		2, baz, 5363.82
		instructions.add(new Instruction("foo", true, 0.50, Instruction.SGP, dateFormat.parse("01/Jan/2016"), dateFormat.parse("01/Jan/2016"), 200, 100.25));
		instructions.add(new Instruction("bar", false, 0.22, Instruction.AED, dateFormat.parse("05/Jan/2016"), dateFormat.parse("07/Jan/2016"), 450, 150.50));
		instructions.add(new Instruction("baz", true, 0.50, Instruction.SGP, dateFormat.parse("02/Jan/2016"), dateFormat.parse("03/Jan/2016"), 34, 100.25));
		instructions.add(new Instruction("bar", false, 0.22, Instruction.AED, dateFormat.parse("06/Jan/2016"), dateFormat.parse("07/Jan/2016"), 42, 150.50));
		instructions.add(new Instruction("foo", true, 0.50, Instruction.SGP, dateFormat.parse("04/Jan/2016"), dateFormat.parse("05/Jan/2016"), 34, 100.25));
		instructions.add(new Instruction("baz", false, 0.22, Instruction.AED, dateFormat.parse("09/Jan/2016"), dateFormat.parse("10/Jan/2016"), 75, 150.50));
		instructions.add(new Instruction("foo", true, 0.50, Instruction.SGP, dateFormat.parse("10/Jan/2016"), dateFormat.parse("11/Jan/2016"), 1200, 100.25));
		instructions.add(new Instruction("bar", false, 0.22, Instruction.AED, dateFormat.parse("12/Jan/2016"), dateFormat.parse("13/Jan/2016"), 70, 150.50));
		instructions.add(new Instruction("baz", true, 0.50, Instruction.SGP, dateFormat.parse("14/Jan/2016"), dateFormat.parse("15/Jan/2016"), 5500, 100.25));
		instructions.add(new Instruction("bar", false, 0.22, Instruction.AED, dateFormat.parse("16/Jan/2016"), dateFormat.parse("16/Jan/2016"), 46, 150.50));
		instructions.add(new Instruction("foo", true, 0.50, Instruction.SGP, dateFormat.parse("17/Jan/2016"), dateFormat.parse("18/Jan/2016"), 100, 100.25));
		instructions.add(new Instruction("baz", false, 0.22, Instruction.AED, dateFormat.parse("20/Jan/2016"), dateFormat.parse("19/Jan/2016"), 87, 150.50));
		
		return instructions;
	} 
	
	public static OutputData elab(List<Instruction> instructions) {
		Map<Date, Double> settledIncomingByDate = new HashMap<>();
		Map<Date, Double> settledOutgoingByDate = new HashMap<>();
		Map<String, Double> settledIncomingByEntity = new HashMap<>();
		Map<String, Double> settledOutgoingByEntity = new HashMap<>();
		
		String entity;
		Date settlementDate;
		double amount;
		double totAmountByDate;
		double totAmountByEntity;
		
		for (Instruction instruction : instructions) {
			
			entity = instruction.getEntity();
			settlementDate = instruction.getSettlementDate();
			amount = instruction.getAmount();
			
			if (instruction.isBuy()) {

				if (!settledIncomingByDate.containsKey(settlementDate)) {
					settledIncomingByDate.put(settlementDate, 0.0);
				}
				totAmountByDate = settledIncomingByDate.get(settlementDate);
				settledIncomingByDate.put(settlementDate, totAmountByDate+amount);
				
				
				if (!settledIncomingByEntity.containsKey(entity)) {
					settledIncomingByEntity.put(entity, 0.0);
				}
				totAmountByEntity = settledIncomingByEntity.get(entity);
				settledIncomingByEntity.put(entity, totAmountByEntity+amount);
				
			} else {
				
				if (!settledOutgoingByDate.containsKey(settlementDate)) {
					settledOutgoingByDate.put(settlementDate, 0.0);
				}
				totAmountByDate = settledOutgoingByDate.get(settlementDate);
				settledOutgoingByDate.put(settlementDate, totAmountByDate+amount);
				
				
				if (!settledOutgoingByEntity.containsKey(entity)) {
					settledOutgoingByEntity.put(entity, 0.0);
				}
				totAmountByEntity = settledOutgoingByEntity.get(entity);
				settledOutgoingByEntity.put(entity, totAmountByEntity+amount);
			}
		}
		
		
		//sort
		settledIncomingByDate = new TreeMap<>(settledIncomingByDate);
		settledOutgoingByDate = new TreeMap<>(settledOutgoingByDate);
		settledIncomingByEntity = sortByValue(settledIncomingByEntity, false);
		settledOutgoingByEntity = sortByValue(settledOutgoingByEntity, false);

		return new OutputData(settledIncomingByDate, settledOutgoingByDate,
				settledIncomingByEntity, settledOutgoingByEntity);
	}

	
	private static void printOutputData(OutputData outputData) {
		printSettledIncomingByDate(outputData.getSettledIncomingByDate());
		printSettledOutgoingByDatee(outputData.getSettledOutgoingByDate());
		printSettledIncomingByEntity(outputData.getSettledIncomingByEntity());
		printSettledOutgoingByEntity(outputData.getSettledOutgoingByEntity());
	}
	


	private static void printSettledIncomingByDate(Map<Date, Double> settledIncomingByDate) {
		printSettledByDatee(settledIncomingByDate, INCOMING);
	}
	
	private static void printSettledOutgoingByDatee(Map<Date, Double> settledOutgoingByDate) {
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
	
	private static void printSettledIncomingByEntity(Map<String, Double> settledIncomingByEntity) {
		printSettledByEntity(settledIncomingByEntity, INCOMING);
	}
	
	private static void printSettledOutgoingByEntity(Map<String, Double> settledOutgoingByEntity) {
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
	
	
	// https://stackoverflow.com/a/2581754
	private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean asc) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        if (asc) list.sort((o1, o2) -> o1.getValue().compareTo(o2.getValue()));
        else list.sort((o1, o2) -> - o1.getValue().compareTo(o2.getValue()));

        
        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
	

}
