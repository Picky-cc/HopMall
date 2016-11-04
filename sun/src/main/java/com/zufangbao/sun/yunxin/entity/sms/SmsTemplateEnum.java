package com.zufangbao.sun.yunxin.entity.sms;

public enum SmsTemplateEnum {
	/**
	 * 扣款成功短信
	 * 尊敬的%s，您的本月还款账单（还款编号：%s）本息合计金额为%s元，于%s从尾号%s的银行卡还款成功。如有疑问，请拨打400-688-0909。
	 */
	LOAN_REPAY_SUCC("LOAN_REPAY_SUCC", "enum.sms-template.loan-repay-succ"),
	/**
	 * 扣款失败短信
	 * 尊敬的%s，您的本月还款账单（还款编号：%s）应于%s还款%s元，实际于%s从尾号%s的银行卡自动还款失败，请及时将足量款项转至该账户。若已逾期将会产生罚息，请以实际扣款金额为准。若已还款，请忽略本条短信。如有疑问，请拨打400-688-0909。
	 */
	LOAN_REPAY_FAIL("LOAN_REPAY_FAIL", "enum.sms-template.loan-repay-fail"),
	/**
	 * 还款提醒短信
	 * (尊敬的%s，您在云南信托办理的个人消费贷款，本月还款账单（还款编号：%s）还款金额本息合计%s元，需于 %s 10:00前还款，请保证尾号%s的银行卡内有充足资金，以便及时还款。如有疑问，请拨打400-688-0909。)
	 */
	LOAN_REPAY_REMINDER("LOAN-REPAY-REMINDER", "enum.sms-template.loan-repay-remind"),
	/**
	 * 暂时不做
	 * 逾期提醒短信
	 * 尊敬的%s，您在云南信托办理的个人消费贷款，本月还款账单（还款编号：%s）逾期未还，已产生相应的罚息，罚息比例%s，需于%s10:00前还款，请保证尾号%s的银行卡内有充足资金，以便及时还款。如有疑问，请拨打400-688-0909。
	 */
	LOAN_REPAY_OVERDUE("LOAN_REPAY_OVERDUE","enum.sms-template.loan-repay-overdue");

	private String code;
	private String desc;

	private SmsTemplateEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static SmsTemplateEnum fromValue(int value) {

		for (SmsTemplateEnum item : SmsTemplateEnum.values()) {
			if (item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}

}