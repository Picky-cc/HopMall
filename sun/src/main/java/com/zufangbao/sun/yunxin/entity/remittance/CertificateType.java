package com.zufangbao.sun.yunxin.entity.remittance;

public enum CertificateType {

	ID_CARD("enum.certificate_type.id_card"),
	HOUSEHOLD_REGISTER("enum.certificate_type.hosuehold_register"),
	PASSPORT("enum.certificate_type.passport"),
	CERTIFICATEOFOFFICERS("enum.certificate_type.certificate_of_officers"),
	SOLDIERCARD("enum.certificate_type.soldier_card"),
	HOME_RETURN_PERMIT("enum.certificate_type.home_return_permit"),
	TAIWAN_COMPATRIOTS_FORM_THE_MAINLAND_PASS("enum.certificate_type.taiwan_compatriots_from_the_mainland_pass"),
	TEMPORARY_ID_CARD("enum.certificate_type.temporary_id_card"),
	RESIDENCE_PERMIT_FOR_FOREIGNERS("enum.certificate_type.pesidence_permit_for_foreigners"),
	POLICE_OFFICER_CERTIFICATE("enum.certificate_type.police_officer_certificate"),
	OTHER("enum.certificate_type.other");
	
	private String key;
	
	public String getKey(){
		return  key;
	}
	

	public void setKey(String key) {
		this.key = key;
	}

	private CertificateType(String key) {
		this.key = key;
	}
}
