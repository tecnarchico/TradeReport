package com.mypackage;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.mypackage.bo.Instruction;
import com.mypackage.bo.OutputData;

public class TradeReportTest {

	private DateFormat dateFormat;

	@Before
	public void setUp() throws Exception {
		
		dateFormat = new SimpleDateFormat("dd/MMM/yyyy");

	}
	
	@Test
	public void testSettlementDate1() throws ParseException {
		Instruction instruction = new Instruction("foo", true, 0.50, Instruction.SGP, dateFormat.parse("23/Mar/2016"), dateFormat.parse("23/Mar/2018"), 200, 100.25);
		assertEquals(instruction.getSettlementDate(), dateFormat.parse("23/Mar/2018"));
	}
	
	@Test
	public void testSettlementDate2() throws ParseException {
		Instruction instruction = new Instruction("foo", true, 0.50, Instruction.SGP, dateFormat.parse("23/Mar/2016"), dateFormat.parse("24/Mar/2018"), 200, 100.25);
		assertEquals(instruction.getSettlementDate(), dateFormat.parse("26/Mar/2018"));
	}
	
	@Test
	public void testSettlementDate3() throws ParseException {
		Instruction instruction = new Instruction("foo", true, 0.50, Instruction.SGP, dateFormat.parse("23/Mar/2016"), dateFormat.parse("25/Mar/2018"), 200, 100.25);
		assertEquals(instruction.getSettlementDate(), dateFormat.parse("26/Mar/2018"));
	}
	
	@Test
	public void testSettlementDate4() throws ParseException {
		Instruction instruction = new Instruction("foo", true, 0.50, Instruction.SGP, dateFormat.parse("23/Mar/2016"), dateFormat.parse("26/Mar/2018"), 200, 100.25);
		assertEquals(instruction.getSettlementDate(), dateFormat.parse("26/Mar/2018"));
	}
	
	
	@Test
	public void testSettlementDate5() throws ParseException {
		Instruction instruction = new Instruction("foo", true, 0.50, Instruction.AED, dateFormat.parse("23/Mar/2016"), dateFormat.parse("22/Mar/2018"), 200, 100.25);
		assertEquals(instruction.getSettlementDate(), dateFormat.parse("22/Mar/2018"));
	}
	
	@Test
	public void testSettlementDate6() throws ParseException {
		Instruction instruction = new Instruction("foo", true, 0.50, Instruction.AED, dateFormat.parse("23/Mar/2016"), dateFormat.parse("23/Mar/2018"), 200, 100.25);
		assertEquals(instruction.getSettlementDate(), dateFormat.parse("25/Mar/2018"));
	}
	
	@Test
	public void testSettlementDate7() throws ParseException {
		Instruction instruction = new Instruction("foo", true, 0.50, Instruction.AED, dateFormat.parse("23/Mar/2016"), dateFormat.parse("24/Mar/2018"), 200, 100.25);
		assertEquals(instruction.getSettlementDate(), dateFormat.parse("25/Mar/2018"));
	}
	
	@Test
	public void testSettlementDate8() throws ParseException {
		Instruction instruction = new Instruction("foo", true, 0.50, Instruction.AED, dateFormat.parse("23/Mar/2016"), dateFormat.parse("25/Mar/2018"), 200, 100.25);
		assertEquals(instruction.getSettlementDate(), dateFormat.parse("25/Mar/2018"));
	}
	
	@Test
	public void testOutputData1() throws ParseException {
		
		List<Instruction> instructions = new ArrayList<>();
		
		instructions.add(new Instruction("foo", true, 0.50, Instruction.SGP, dateFormat.parse("23/Mar/2016"), dateFormat.parse("23/Mar/2018"), 200, 75));
		instructions.add(new Instruction("bar", false, 0.50, Instruction.SGP, dateFormat.parse("23/Mar/2016"), dateFormat.parse("23/Mar/2018"), 200, 25));
		instructions.add(new Instruction("baz", true, 0.50, Instruction.SGP, dateFormat.parse("23/Mar/2016"), dateFormat.parse("24/Mar/2018"), 100, 75));
		
		
		OutputData outputData =  Main.elab(instructions);
		
		assertEquals(outputData.getSettledIncomingByDate().get(dateFormat.parse("23/Mar/2018")), new Double(0.5*200*75));
		assertEquals(outputData.getSettledOutgoingByDate().get(dateFormat.parse("23/Mar/2018")), new Double(0.5*200*25));
		
		Map<String, Double> settledIncomingByEntity = outputData.getSettledIncomingByEntity();
		Map<String, Double> settledOutgoingByEntity = outputData.getSettledOutgoingByEntity();
		
		assertEquals(new Integer(settledIncomingByEntity.size()), new Integer(2));
		assertEquals(new Integer(settledOutgoingByEntity.size()), new Integer(1));
		
		List<String> entities = new ArrayList<>(settledIncomingByEntity.keySet());
		assertEquals(entities.get(0), "foo");
		assertEquals(entities.get(1), "baz");
	}

}
