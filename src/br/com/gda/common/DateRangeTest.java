package br.com.gda.common;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class DateRangeTest {
	private LocalDate validBeginDate = LocalDate.of(2018, 6, 22);
	private LocalDate validEndDate = LocalDate.of(2018, 12, 1);
	
	private LocalDate lowerBeginDate = LocalDate.of(2018, 6, 21);
	private LocalDate higherEndDate = LocalDate.of(2018, 12, 2);

	private LocalDate validBeginDateToComprare = LocalDate.of(2018, 1, 1);
	private LocalDate validEndDateToComprare = LocalDate.of(2018, 12, 31);
	
	private LocalDate missingBoundaryBeginDate = LocalDate.of(2018, 7, 1);
	private LocalDate missingBoundaryEndDate = LocalDate.of(2018, 10, 12);
	
	LocalDate invalidBeginDate = LocalDate.of(2018, 6, 30);
	private LocalDate invalidEndDate = LocalDate.of(2018, 6, 22);
	
	private LocalDate equalDate = LocalDate.of(2018, 6, 30);
	
	private DateRange range;
	
	

	@Test (expected = NullPointerException.class)
	public void constructorNullBeginDate() {
		new DateRange(null, validEndDate);
	}

	
	
	@Test (expected = NullPointerException.class)
	public void constructorNullEndDate() {
		new DateRange(validBeginDate, null);
	}
	
	
	
	@Test (expected = IllegalArgumentException.class)
	public void constructorInvalidRange() {
		new DateRange(invalidBeginDate, invalidEndDate);
	}
	
	
	
	@Test
	public void constructorEqualBoundary() {
		range = new DateRange(equalDate, equalDate);
		assertTrue(range.getBeginDate().isEqual(equalDate));
		assertTrue(range.getEndDate().isEqual(equalDate));
	}
	
	
	
	@Test
	public void constructorValidRange() {
		range = new DateRange(validBeginDate, validEndDate);
		assertTrue(range.getBeginDate().isEqual(validBeginDate));
		assertTrue(range.getEndDate().isEqual(validEndDate));
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void nullBeginDate() {
		range = initializeValidRange();		
		range.getMissingRanges(null, validEndDateToComprare);
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void nullBeginEnd() {
		range = initializeValidRange();		
		range.getMissingRanges(validBeginDateToComprare, null);
	}
	
	
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidRange() {
		range = initializeValidRange();		
		range.getMissingRanges(invalidBeginDate, invalidEndDate);
	}
	
	
	
	@Test
	public void equalBoundary() {
		range = initializeValidRange();		
		List<DateRange> missingRanges = range.getMissingRanges(equalDate, equalDate);
		assertTrue(missingRanges == Collections.EMPTY_LIST);
	}
	
	
	
	@Test
	public void lowerBeginBoundary() {
		range = initializeValidRange();		
		List<DateRange> missingRanges = range.getMissingRanges(lowerBeginDate, validEndDate);
		assertTrue(missingRanges == Collections.EMPTY_LIST);
	}
	
	
	
	@Test
	public void higherEndBoundary() {
		range = initializeValidRange();		
		List<DateRange> missingRanges = range.getMissingRanges(validBeginDate, higherEndDate);
		assertTrue(missingRanges == Collections.EMPTY_LIST);
	}
	
	
	
	@Test
	public void lowerAndHigherBoundary() {
		range = initializeValidRange();		
		List<DateRange> missingRanges = range.getMissingRanges(lowerBeginDate, higherEndDate);
		assertTrue(missingRanges == Collections.EMPTY_LIST);
	}
	
	
	
	@Test
	public void missingBoudaryBegin() {
		range = initializeValidRange();		
		List<DateRange> missingRanges = range.getMissingRanges(missingBoundaryBeginDate, validEndDate);
		
		assertTrue(missingRanges.get(0).getBeginDate().isEqual(LocalDate.of(2018, 06, 22)));
		assertTrue(missingRanges.get(0).getEndDate().isEqual(LocalDate.of(2018, 06, 30)));
		assertTrue(missingRanges.size() == 1);
	}
	
	
	
	@Test
	public void missingBoudaryEnd() {
		range = initializeValidRange();		
		List<DateRange> missingRanges = range.getMissingRanges(validBeginDate, missingBoundaryEndDate);
		
		assertTrue(missingRanges.get(0).getBeginDate().isEqual(LocalDate.of(2018, 10, 13)));
		assertTrue(missingRanges.get(0).getEndDate().isEqual(LocalDate.of(2018, 12, 01)));
		assertTrue(missingRanges.size() == 1);
	}
	
	
	
	@Test
	public void missingBoudary() {
		range = initializeValidRange();		
		List<DateRange> missingRanges = range.getMissingRanges(missingBoundaryBeginDate, missingBoundaryEndDate);
		
		assertTrue(missingRanges.get(0).getBeginDate().isEqual(LocalDate.of(2018, 06, 22)));
		assertTrue(missingRanges.get(0).getEndDate().isEqual(LocalDate.of(2018, 06, 30)));
		
		assertTrue(missingRanges.get(1).getBeginDate().isEqual(LocalDate.of(2018, 10, 13)));
		assertTrue(missingRanges.get(1).getEndDate().isEqual(LocalDate.of(2018, 12, 01)));
		assertTrue(missingRanges.size() == 2);
	}
	
	
	
	private DateRange initializeValidRange() {
		return new DateRange(validBeginDate, validEndDate);
	}
}
