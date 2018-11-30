package br.com.gda.model.checker;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.gda.common.SystemCode;
import br.com.gda.common.SystemMessage;
import br.com.gda.model.checker.common.ModelCheckerCpf_;

public final class ModelCheckerCpfTest {

	@Test
	public void invalidSequence() {
		ModelCheckerCpf_ checker = new ModelCheckerCpf_();
		checker.check("00000000000");
		assertFalse(checker.getResult());
		assertTrue(checker.getFailCode() == SystemCode.CPF_INVALID);
		assertTrue(checker.getFailMessage().equals(SystemMessage.CPF_INVALID));
		
		checker.check("11111111111");
		assertFalse(checker.getResult());
		
		checker.check("22222222222");
		assertFalse(checker.getResult());
		
		checker.check("33333333333");
		assertFalse(checker.getResult());
		
		checker.check("44444444444");
		assertFalse(checker.getResult());
		
		checker.check("55555555555");
		assertFalse(checker.getResult());
		
		checker.check("66666666666");
		assertFalse(checker.getResult());
		
		checker.check("77777777777");
		assertFalse(checker.getResult());
		
		checker.check("88888888888");
		assertFalse(checker.getResult());
		
		checker.check("99999999999");
		assertFalse(checker.getResult());
		
		checker.check("00000000000");
		assertFalse(checker.getResult());
	}
	
	
	
	@Test
	public void invalidLength() {
		ModelCheckerCpf_ checker = new ModelCheckerCpf_();
		checker.check("1234567890");
		assertFalse(checker.getResult());
		assertTrue(checker.getFailCode() == SystemCode.CPF_INVALID);
		assertTrue(checker.getFailMessage().equals(SystemMessage.CPF_INVALID));
		
		checker.check("123456789012");
		assertFalse(checker.getResult());
	}
	
	
	
	@Test
	public void validNull() {
		String nullValue = null;
		ModelCheckerCpf_ checker = new ModelCheckerCpf_();
		checker.check(nullValue);
		assertTrue(checker.getResult());
	}
	
	
	
	@Test
	public void invalidLetter() {
		ModelCheckerCpf_ checker = new ModelCheckerCpf_();
		checker.check("1234567890a");
		assertFalse(checker.getResult());
		assertTrue(checker.getFailCode() == SystemCode.CPF_INVALID);
		assertTrue(checker.getFailMessage().equals(SystemMessage.CPF_INVALID));
		
		checker.check("a1234567890");
		assertFalse(checker.getResult());
		assertTrue(checker.getFailCode() == SystemCode.CPF_INVALID);
		
		checker.check("123.4567890");
		assertFalse(checker.getResult());
		assertTrue(checker.getFailCode() == SystemCode.CPF_INVALID);
	}
	
	
	
	@Test
	public void invalidCpf() {
		ModelCheckerCpf_ checker = new ModelCheckerCpf_();
		checker.check("12345678900");
		assertFalse(checker.getResult());
		assertTrue(checker.getFailCode() == SystemCode.CPF_INVALID);
		assertTrue(checker.getFailMessage().equals(SystemMessage.CPF_INVALID));
		
		checker.check("09330403777");
		assertFalse(checker.getResult());
		
		checker.check("09330403708");
		assertFalse(checker.getResult());
		
		checker.check("09330403078");
		assertFalse(checker.getResult());
		
		checker.check("09330400778");
		assertFalse(checker.getResult());
		
		checker.check("09330433778");
		assertFalse(checker.getResult());
		
		checker.check("09330003778");
		assertFalse(checker.getResult());
		
		checker.check("09334403778");
		assertFalse(checker.getResult());
		
		checker.check("09300403778");
		assertFalse(checker.getResult());
		
		checker.check("09030403778");
		assertFalse(checker.getResult());
		
		checker.check("00330403778");
		assertFalse(checker.getResult());
		
		checker.check("19330403778");
		assertFalse(checker.getResult());
	}
	
	
	
	@Test
	public void validCpf() {
		ModelCheckerCpf_ checker = new ModelCheckerCpf_();
		checker.check("09330403778");
		assertTrue(checker.getResult());
		
		checker.check("77727635570");
		assertTrue(checker.getResult());
		
		checker.check("29622655378");
		assertTrue(checker.getResult());
		
		checker.check("77332014629");
		assertTrue(checker.getResult());
		
		checker.check("77657865636");
		assertTrue(checker.getResult());
		
		checker.check("77657865636");
		assertTrue(checker.getResult());
	}
}
