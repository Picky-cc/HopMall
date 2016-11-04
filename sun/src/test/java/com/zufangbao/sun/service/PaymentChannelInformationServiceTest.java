package com.zufangbao.sun.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.zufangbao.sun.entity.financial.ChannelWorkingStatus;
import com.zufangbao.sun.entity.financial.ChargeExcutionMode;
import com.zufangbao.sun.entity.financial.ChargeRateMode;
import com.zufangbao.sun.entity.financial.ChargeType;
import com.zufangbao.sun.entity.financial.PaymentChannelConfigure;
import com.zufangbao.sun.entity.financial.PaymentChannelInformation;
import com.zufangbao.sun.entity.financial.PaymentInstitutionName;
import com.zufangbao.sun.entity.financial.TransactionChannelConfigure;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class PaymentChannelInformationServiceTest {
	
	@Autowired
	private PaymentChannelInformationService paymentChannelInformationService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	
	@Test
	public void saveTest(){
		PaymentChannelConfigure paymentChannelConfigure = new PaymentChannelConfigure();
		
		TransactionChannelConfigure creditChannelConfigure = new TransactionChannelConfigure();
		creditChannelConfigure.setChannelStatus(ChannelWorkingStatus.OFF);
		creditChannelConfigure.setChargeExcutionMode(ChargeExcutionMode.BACKWARD);
		creditChannelConfigure.setChargeRatePerTranscation(new BigDecimal(0.01));
		creditChannelConfigure.setChargeType(ChargeType.BOTH);
		creditChannelConfigure.setChargeRateMode(ChargeRateMode.SINGLERATA);
	//	creditChannelConfigure.setChargePerTranscation(new BigDecimal(1));
		creditChannelConfigure.setClearingInterval(1);
		creditChannelConfigure.setLowerestChargeLimitPerTransaction(new BigDecimal(0.01));
		creditChannelConfigure.setTrasncationLimitPerTransaction(new BigDecimal(50000));
		creditChannelConfigure.setUpperChargeLimitPerTransaction(new BigDecimal(0.05));
		paymentChannelConfigure.setCreditChannelConfigure(creditChannelConfigure);
		
		TransactionChannelConfigure debitChannelConfigure = new TransactionChannelConfigure();
		debitChannelConfigure.setChannelStatus(ChannelWorkingStatus.OFF);
		debitChannelConfigure.setChargeExcutionMode(ChargeExcutionMode.BACKWARD);
		debitChannelConfigure.setChargePerTranscation(new BigDecimal(1));
		debitChannelConfigure.setChargeType(ChargeType.BOTH);
		creditChannelConfigure.setChargeRateMode(ChargeRateMode.SINGLEFIXED);
		debitChannelConfigure.setClearingInterval(1);
		debitChannelConfigure.setTrasncationLimitPerTransaction(new BigDecimal(50000));
		paymentChannelConfigure.setCreditChannelConfigure(debitChannelConfigure);
		
		paymentChannelConfigure.setDebitChannelConfigure(debitChannelConfigure);
		PaymentChannelInformation channelInformation = new PaymentChannelInformation();
		
		channelInformation.setOutlierChannelName("001053110000001");
		channelInformation.setPaymentChannelName("银联广州分公司001");
		channelInformation.setPaymentInstitutionName(PaymentInstitutionName.UNIONPAYGZ);
		channelInformation.setRelatedFinancialContractName("ces");
		channelInformation.setRelatedFinancialContractUuid("82357556-6e44-4a41-9023-9266db6e7ae9");
		channelInformation.setBy(paymentChannelConfigure);
		
		paymentChannelInformationService.save(channelInformation);
		
		String pciUuid = channelInformation.getPaymentChannelUuid();
		PaymentChannelInformation pci = paymentChannelInformationService.getPaymentChannelInformationBy(pciUuid);
		assertEquals("001053110000001", pci.getOutlierChannelName());
		assertEquals(ChannelWorkingStatus.OFF, pci.getCreditChannelWorkingStatus());
		assertEquals("银联广州分公司001", pci.getPaymentChannelName());
		assertEquals("银联广州分公司001", pci.getPaymentChannelName());
//		String financialContractUuid = pci.getRelatedFinancialContractUuid();
//		FinancialContract financialContract = financialContractService.getFinancialContractBy(financialContractUuid);
//		assertTrue(financialContract.getPaymentChannelUuidsForCredit().contains(pciUuid));
//		assertTrue(financialContract.getPaymentChannelUuidsForDebit().contains(pciUuid));
	}
	
}
