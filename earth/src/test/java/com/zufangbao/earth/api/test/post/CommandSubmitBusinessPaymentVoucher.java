package com.zufangbao.earth.api.test.post;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.yunxin.api.model.command.BusinessPaymentVoucherCommandModel;
import com.zufangbao.earth.yunxin.api.model.command.BusinessPaymentVoucherDetail;
@Controller
@RequestMapping("/api/command")
public class CommandSubmitBusinessPaymentVoucher extends BaseApiTestPost {

	@Test
	public void testSubmitBusinessPaymentVoucher(){
		
		List<BusinessPaymentVoucherDetail> details = new ArrayList<>();
		
		BusinessPaymentVoucherDetail detail = new BusinessPaymentVoucherDetail();
		detail.setAmount(new BigDecimal("500"));
		detail.setUniqueId("e96bdb77-c051-4db0-b046-daca68baa203");
		detail.setRepaymentPlanNo("ZC2743EB64B56E6B06");
		detail.setPayer(0);
		
		BusinessPaymentVoucherDetail detail1 = new BusinessPaymentVoucherDetail();
		detail1.setAmount(new BigDecimal("600"));
		detail1.setUniqueId("e96bdb77-c051-4db0-b046-daca68baa203");
		detail1.setRepaymentPlanNo("ZC2743EB64B56E6B06");
		detail1.setPayer(0);
		details.add(detail1);
		String jsonString = JsonUtils.toJsonString(details);
		
		BusinessPaymentVoucherCommandModel model = new BusinessPaymentVoucherCommandModel();
		model.setRequestNo("1323456");
		model.setTransactionType(0);
		model.setVoucherType(0);
		model.setVoucherAmount(new BigDecimal("501"));
		model.setFinancialContractNo("164864811");
		model.setReceivableAccountNo("486411356");
		model.setPaymentAccountNo("146474156");
		model.setPaymentName("张三");
		model.setPaymentBank("招商银行");
		model.setBankTransactionNo("169264617");
		model.setDetail(jsonString);
		model.setCheckFailedMsg("校验失败信息");
		
		
		
	}
}
