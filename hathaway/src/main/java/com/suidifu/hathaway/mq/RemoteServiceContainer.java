package com.suidifu.hathaway.mq;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

public abstract class RemoteServiceContainer extends VirtualContainer {

	public abstract RemoteServiceAccessor accessor(String hashIndex);

	public abstract int accessorSize();

	public String md5(String referenceUuid) {
		return null;
	}

	// public String calculateHasIndex(String referenceUuid){
	//
	// int accessorSize = this.accessorSize();
	//
	// if(StringUtils.isEmpty(referenceUuid)){
	//
	// return RandomUtils.nextInt(accessorSize)+"";
	// }
	// return Math.abs(UUID.fromString(referenceUuid).hashCode()) % accessorSize
	// +"";
	// }

}
