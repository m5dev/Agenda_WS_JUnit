package br.com.gda.common;

import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TimeRangeTest {
	private LocalTime validBeginTime = LocalTime.of(10, 0);
	private LocalTime validEndTime = LocalTime.of(13, 00);
	
	private LocalTime lowerBeginTime = LocalTime.of(9, 59);
	private LocalTime higherEndTime = LocalTime.of(13, 1);

	private LocalTime validBeginTimeToComprare = LocalTime.of(9, 0);
	private LocalTime validEndTimeToComprare = LocalTime.of(13, 0);
	
	private LocalTime missingBoundaryBeginTime = LocalTime.of(10, 30);
	private LocalTime missingBoundaryEndTime = LocalTime.of(12, 30);
	
	LocalTime invalidBeginTime = LocalTime.of(14, 0);
	private LocalTime invalidEndTime = LocalTime.of(10, 0);
	
	private LocalTime equalTime = LocalTime.of(9, 0);
	
	private TimeRange range;
	
	

	@Test (expected = NullPointerException.class)
	public void constructorNullBeginTime() {
		new TimeRange(null, validEndTime);
	}

	
	
	@Test (expected = NullPointerException.class)
	public void constructorNullEndTime() {
		new TimeRange(validBeginTime, null);
	}
	
	
	
	@Test (expected = IllegalArgumentException.class)
	public void constructorInvalidRange() {
		new TimeRange(invalidBeginTime, invalidEndTime);
	}
	
	
	
	@Test
	public void constructorEqualBoundary() {
		range = new TimeRange(equalTime, equalTime);
		assertTrue(isEqual(range.getBeginTime(),equalTime));
		assertTrue(isEqual(range.getEndTime(),equalTime));
	}
	
	
	
	@Test
	public void constructorValidRange() {
		range = new TimeRange(validBeginTime, validEndTime);
		assertTrue(isEqual(range.getBeginTime(),validBeginTime));
		assertTrue(isEqual(range.getEndTime(),validEndTime));
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void nullBeginTime() {
		range = initializeValidRange();		
		range.getMissingRanges(null, validEndTimeToComprare);
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void nullBeginEnd() {
		range = initializeValidRange();		
		range.getMissingRanges(validBeginTimeToComprare, null);
	}
	
	
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidRange() {
		range = initializeValidRange();		
		range.getMissingRanges(invalidBeginTime, invalidEndTime);
	}
	
	
	
	@Test
	public void equalBoundary() {
		range = initializeValidRange();		
		List<TimeRange> missingRanges = range.getMissingRanges(equalTime, equalTime);
		assertTrue(missingRanges == Collections.EMPTY_LIST);
	}
	
	
	
	@Test
	public void lowerBeginBoundary() {
		range = initializeValidRange();		
		List<TimeRange> missingRanges = range.getMissingRanges(lowerBeginTime, validEndTime);
		assertTrue(missingRanges == Collections.EMPTY_LIST);
	}
	
	
	
	@Test
	public void higherEndBoundary() {
		range = initializeValidRange();		
		List<TimeRange> missingRanges = range.getMissingRanges(validBeginTime, higherEndTime);
		assertTrue(missingRanges == Collections.EMPTY_LIST);
	}
	
	
	
	@Test
	public void lowerAndHigherBoundary() {
		range = initializeValidRange();		
		List<TimeRange> missingRanges = range.getMissingRanges(lowerBeginTime, higherEndTime);
		assertTrue(missingRanges == Collections.EMPTY_LIST);
	}
	
	
	
	@Test
	public void missingBoudaryBegin() {
		range = initializeValidRange();		
		List<TimeRange> missingRanges = range.getMissingRanges(missingBoundaryBeginTime, validEndTime);
		
		assertTrue(isEqual(missingRanges.get(0).getBeginTime(), LocalTime.of(10, 0)));
		assertTrue(isEqual(missingRanges.get(0).getEndTime(), LocalTime.of(10, 29)));
		assertTrue(missingRanges.size() == 1);
	}
	
	
	
	@Test
	public void missingBoudaryEnd() {
		range = initializeValidRange();		
		List<TimeRange> missingRanges = range.getMissingRanges(validBeginTime, missingBoundaryEndTime);
		
		assertTrue(isEqual(missingRanges.get(0).getBeginTime(), LocalTime.of(12, 31)));
		assertTrue(isEqual(missingRanges.get(0).getEndTime(), LocalTime.of(13, 0)));
		assertTrue(missingRanges.size() == 1);
	}
	
	
	
	@Test
	public void missingBoudary() {
		range = initializeValidRange();		
		List<TimeRange> missingRanges = range.getMissingRanges(missingBoundaryBeginTime, missingBoundaryEndTime);
		
		assertTrue(isEqual(missingRanges.get(0).getBeginTime(), LocalTime.of(10, 0)));
		assertTrue(isEqual(missingRanges.get(0).getEndTime(), LocalTime.of(10, 29)));
		
		assertTrue(isEqual(missingRanges.get(1).getBeginTime(), LocalTime.of(12, 31)));
		assertTrue(isEqual(missingRanges.get(1).getEndTime(), LocalTime.of(13, 0)));
		assertTrue(missingRanges.size() == 2);
	}
	
	
	
	private TimeRange initializeValidRange() {
		return new TimeRange(validBeginTime, validEndTime);
	}
	
	
	
	private boolean isEqual(LocalTime time1, LocalTime time2) {
		if (time1.isBefore(time2) || time1.isAfter(time2))
			return false;
		
		return true;
	}
}
