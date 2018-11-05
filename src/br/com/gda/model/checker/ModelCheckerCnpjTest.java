package br.com.gda.model.checker;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.gda.common.SystemCode;
import br.com.gda.common.SystemMessage;
import br.com.gda.model.checker.common.ModelCheckerCnpj;

public class ModelCheckerCnpjTest {
	
	@Test
	public void invalidSequence() {
		ModelCheckerCnpj checker = new ModelCheckerCnpj();
		checker.check("00000000000000");
		assertFalse(checker.getResult());
		assertTrue(checker.getFailCode() == SystemCode.CNPJ_INVALID);
		assertTrue(checker.getFailMessage().equals(SystemMessage.CNPJ_INVALID));
		
		checker.check("11111111111111");
		assertFalse(checker.getResult());
		
		checker.check("22222222222222");
		assertFalse(checker.getResult());
		
		checker.check("33333333333333");
		assertFalse(checker.getResult());
		
		checker.check("44444444444444");
		assertFalse(checker.getResult());
		
		checker.check("55555555555555");
		assertFalse(checker.getResult());
		
		checker.check("66666666666666");
		assertFalse(checker.getResult());
		
		checker.check("77777777777777");
		assertFalse(checker.getResult());
		
		checker.check("88888888888888");
		assertFalse(checker.getResult());
		
		checker.check("99999999999999");
		assertFalse(checker.getResult());
		
		checker.check("00000000000000");
		assertFalse(checker.getResult());
	}
	
	
	
	@Test
	public void invalidLength() {
		ModelCheckerCnpj checker = new ModelCheckerCnpj();
		checker.check("1234567890");
		assertFalse(checker.getResult());
		assertTrue(checker.getFailCode() == SystemCode.CNPJ_INVALID);
		assertTrue(checker.getFailMessage().equals(SystemMessage.CNPJ_INVALID));
		
		checker.check("123456789012345");
		assertFalse(checker.getResult());
	}
	
	
	
	@Test
	public void validNull() {
		String nullValue = null;
		ModelCheckerCnpj checker = new ModelCheckerCnpj();
		checker.check(nullValue);
		assertTrue(checker.getResult());
	}
	
	
	
	@Test
	public void invalidLetter() {
		ModelCheckerCnpj checker = new ModelCheckerCnpj();
		checker.check("0001234567890a");
		assertFalse(checker.getResult());
		assertTrue(checker.getFailCode() == SystemCode.CNPJ_INVALID);
		assertTrue(checker.getFailMessage().equals(SystemMessage.CNPJ_INVALID));
		
		checker.check("a0001234567890");
		assertFalse(checker.getResult());
		assertTrue(checker.getFailCode() == SystemCode.CNPJ_INVALID);
		
		checker.check("000123.4567890");
		assertFalse(checker.getResult());
		assertTrue(checker.getFailCode() == SystemCode.CNPJ_INVALID);
	}
	
	
	
	@Test
	public void invalidCnpj() {
		ModelCheckerCnpj checker = new ModelCheckerCnpj();
		checker.check("12345678900123");
		assertFalse(checker.getResult());
		assertTrue(checker.getFailCode() == SystemCode.CNPJ_INVALID);
		assertTrue(checker.getFailMessage().equals(SystemMessage.CNPJ_INVALID));
		
		checker.check("06234152000104");
		assertFalse(checker.getResult());
		
		checker.check("30234152000104");
		assertFalse(checker.getResult());
		
		checker.check("36034152000104");
		assertFalse(checker.getResult());
		
		checker.check("36204152000104");
		assertFalse(checker.getResult());
		
		checker.check("36230152000104");
		assertFalse(checker.getResult());
		
		checker.check("36234052000104");
		assertFalse(checker.getResult());
		
		checker.check("36234102000104");
		assertFalse(checker.getResult());
		
		checker.check("36234150000104");
		assertFalse(checker.getResult());
		
		checker.check("36234152100104");
		assertFalse(checker.getResult());
		
		checker.check("36234152010104");
		assertFalse(checker.getResult());
		
		checker.check("36234152000004");
		assertFalse(checker.getResult());		
		
		checker.check("36234152000114");
		assertFalse(checker.getResult());
		
		checker.check("36234152000100");
		assertFalse(checker.getResult());
	}
	
	
	
	@Test
	public void validCnpj() {
		ModelCheckerCnpj checker = new ModelCheckerCnpj();
		checker.check("36234152000104");
		assertTrue(checker.getResult());
		
		checker.check("74372651000149");
		assertTrue(checker.getResult());
		
		checker.check("34402744000154");
		assertTrue(checker.getResult());
		
		checker.check("68431776000108");
		assertTrue(checker.getResult());
		
		checker.check("17812230000158");
		assertTrue(checker.getResult());
		
		checker.check("66347815000150");
		assertTrue(checker.getResult());
	}

}
