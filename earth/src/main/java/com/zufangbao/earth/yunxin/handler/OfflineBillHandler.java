package com.zufangbao.earth.yunxin.handler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.zufangbao.earth.yunxin.exception.OfflineBillAutidtException;
import com.zufangbao.earth.yunxin.exception.SourceDocumentNotExistException;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.yunxin.entity.IllegalInputAmountException;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.model.OfflineBillCreateModel;
import com.zufangbao.sun.yunxin.entity.model.OrderMatchModel;
import com.zufangbao.sun.yunxin.entity.model.OrderMatchQueryModel;
import com.zufangbao.sun.yunxin.entity.model.OrderMatchShowModel;
import com.zufangbao.wellsfargo.exception.OfflineBillCreateException;
import com.zufangbao.wellsfargo.exception.VoucherException;

public interface OfflineBillHandler {

	public void createOfflineBillForGuaranteOrderMatch(OfflineBillCreateModel offlineBillCreateModel) throws OfflineBillCreateException, VoucherException;
	
	public void updateOrderAndAsset(List<Order> orderList);
	
	public String extractOrderNoSet(List<Order> guaranteeOrders);
	public BigDecimal getTotalAmount(List<Order> guaranteeOrders);
	
	public void validOrderStatus(List<Order> guaranteeOrders) throws OfflineBillCreateException;
	
	public OfflineBill create_offline_bill_and_create_source_document(OfflineBillCreateModel offlineBillCreateModel);
	
	public OfflineBill create_offline_bill(OfflineBillCreateModel offlineBillCreateModel);
	
	public List<Order> smartMatchOrderListBy(String offlineBillUuid);

	public List<OrderMatchModel> showSmartMatchModelOrderList(String offlineBillUuid);

	public void buildAssociationBetweenOrderAndOfflineBill(String offlineBillUuid, Map<String, Object> map, BigDecimal connectionAmount, long userId, String ip) throws SourceDocumentNotExistException, IllegalInputAmountException, OfflineBillAutidtException;

	public List<OrderMatchModel> searchMatchModelOrderList(OrderMatchQueryModel orderMatchQueryModel);
	
	public List<OrderMatchShowModel> getOrderMatchShowModelBy(String offlineBillUuid);

}
