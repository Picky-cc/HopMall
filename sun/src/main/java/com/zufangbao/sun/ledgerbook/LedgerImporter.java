package com.zufangbao.sun.ledgerbook;

import org.apache.commons.lang.StringUtils;

public class LedgerImporter {
	
	public void copyParty(LedgerTradeParty party,LedgerItem ledgerBook) {
		if(party==null ||ledgerBook==null) return;
		ledgerBook.setFirstPartyId(StringUtils.defaultString(party.getFstParty()));
		ledgerBook.setSecondPartyId(StringUtils.defaultString(party.getSndParty()));
		
	}
	public void extractParty(LedgerTradeParty party,LedgerItem ledgerBook) {
		if(party==null ||ledgerBook==null) return;
		party.setFstParty(StringUtils.defaultString(ledgerBook.getFirstPartyId()));
		party.setSndParty(StringUtils.defaultString(ledgerBook.getSecondPartyId()));
			
	}

	public void copyAssetSet(AssetCategory asset,LedgerItem ledgerBook)	{
		ledgerBook.setContractId(asset.getRelatedContractId());
		ledgerBook.setContractUuid(asset.getRelatedContractUuid());
		ledgerBook.setRelatedLv1AssetUuid(asset.getFstLvLAssetUuid());
		ledgerBook.setRelatedLv1AssetOuterIdenity(asset.getFstLvLAssetOuterIdentity());
		ledgerBook.setRelatedLv2AssetUuid(asset.getSndLvlAssetUuid());
		ledgerBook.setRelatedLv2AssetOuterIdenity(asset.getSndLvlAssetOuterIdentity());
		ledgerBook.setRelatedLv3AssetUuid(asset.getThrdLvlAssetUuid());
		ledgerBook.setRelatedLv3AssetOuterIdenity(asset.getThrdLvlAssetOuterIdentity());
	
		ledgerBook.setAmortizedDate(asset.getAmortizedDate());
		ledgerBook.setDefaultDate(asset.getDefaultDate());

	}
	public void copyBookBreif(LedgerBook bookbreif,LedgerItem book)	{
		book.setLedgerBookNo(bookbreif.getLedgerBookNo());
		book.setLedgerBookOwnerId(bookbreif.getLedgerBookOrgnizationId());
		
	}
	public void copyEmptyVoucher(LedgerItem book) {
		
		book.setJournalVoucherUuid("");		
		book.setBusinessVoucherUuid("");
		book.setSourceDocumentUuid("");
		
	}
	
	
	public void copyTAccount(TAccountInfo account,LedgerItem ledgerBook) {
		if(account==null) return;
		if(account.getFirstLevelAccount()==null||StringUtils.isEmpty(account.getFirstLevelAccount().getAccountName())||
				StringUtils.isEmpty(account.getFirstLevelAccount().getAccountCode()))return;
		ledgerBook.setFirstAccountName(account.getFirstLevelAccount().getAccountName());
		ledgerBook.setFirstAccountUuid(account.getFirstLevelAccount().getAccountCode());
		ledgerBook.setAccountSide(account.getFirstLevelAccount().getSide().getOrdinal());

		if(account.getSecondLevelAccount()==null||StringUtils.isEmpty(account.getSecondLevelAccount().getAccountName())||
				StringUtils.isEmpty(account.getSecondLevelAccount().getAccountCode()))return;
		ledgerBook.setSecondAccountName(account.getSecondLevelAccount().getAccountName());
		ledgerBook.setSecondAccountUuid(account.getSecondLevelAccount().getAccountCode());
		if(account.getThirdLevelAccount()==null||StringUtils.isEmpty(account.getThirdLevelAccount().getAccountName())||
				StringUtils.isEmpty(account.getThirdLevelAccount().getAccountCode()))return;
		ledgerBook.setThirdAccountName(account.getThirdLevelAccount().getAccountName());
		ledgerBook.setThirdAccountUuid(account.getThirdLevelAccount().getAccountCode());
	

	}
}
