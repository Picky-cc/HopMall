package com.zufangbao.earth.yunxin.handler;

import java.io.Serializable;

/**
 * 银联相关
 * 
 * @author louguanyang
 *
 */
public interface UnionPayHandler {
	
	public void startSingleDeduct(Serializable id);

	public void startSingleQueryUnionpayDeductResult();
}
