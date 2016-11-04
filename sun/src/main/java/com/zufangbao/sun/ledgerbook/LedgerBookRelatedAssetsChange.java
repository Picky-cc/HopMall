package com.zufangbao.sun.ledgerbook;

import java.util.List;

public class LedgerBookRelatedAssetsChange {
	private List<String> invaliedAssetUuidList;
	private List<String> newCreatedAssetUuidList;
	public List<String> getInvaliedAssetUuidList() {
		return invaliedAssetUuidList;
	}
	public void setInvaliedAssetUuidList(List<String> invaliedAssetUuidList) {
		this.invaliedAssetUuidList = invaliedAssetUuidList;
	}
	public List<String> getNewCreatedAssetUuidList() {
		return newCreatedAssetUuidList;
	}
	public void setNewCreatedAssetUuidList(List<String> newCreatedAssetUuidList) {
		this.newCreatedAssetUuidList = newCreatedAssetUuidList;
	}
	
	public LedgerBookRelatedAssetsChange(){
	}
	public LedgerBookRelatedAssetsChange(List<String>invaliedAssetUuidList, List<String> newCreatedAssetUuidList){
		this.invaliedAssetUuidList = invaliedAssetUuidList;
		this.newCreatedAssetUuidList = newCreatedAssetUuidList;
	}
	
}
