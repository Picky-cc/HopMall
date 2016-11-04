package com.zufangbao.earth.yunxin.unionpay.model;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayResult;
import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayRtnInfo;
import com.zufangbao.sun.utils.FinanceUtils;

/**
 * 交易明细查询结果
 * @author zhanghongbing
 *
 */
public class TransactionDetailQueryResult {

	@XStreamAlias("BEGIN_DATE")
	private String beginDate; //开始日期，格式：YYYYMMDD
	
	@XStreamAlias("END_DATE")
	private String endDate; //结束日期，格式：YYYYMMDD
	
	@XStreamAlias("TOTAL_ITEM")
	private String totalItem; //总项数
	
	@XStreamAlias("TOTAL_SUM")
	private String totalSum; //总金额
	
	@XStreamImplicit(itemFieldName = "RET_DETAIL")
	private List<TransactionDetailNode> detailNodes;
	
	private static XStream xStream;
	
	static{
		xStream = new XStream();
		xStream.autodetectAnnotations(true);
		xStream.ignoreUnknownElements();

		xStream.alias("QUERY_TRANS", TransactionDetailQueryResult.class);
		xStream.alias("RET_DETAILS", TransactionDetailQueryResult.class);
		xStream.alias("INFO", GZUnionPayRtnInfo.class);
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(String totalItem) {
		this.totalItem = totalItem;
	}

	public BigDecimal getTotalSum() {
		this.totalSum = StringUtils.isEmpty(totalSum) ? "0" : totalSum;
		BigDecimal bdTotalSum = new BigDecimal(this.totalSum);
		return FinanceUtils.convert_cent_to_yuan(bdTotalSum);
	}

	public void setTotalSum(String totalSum) {
		this.totalSum = totalSum;
	}

	public List<TransactionDetailNode> getDetailNodes() {
		return detailNodes;
	}

	public void setDetailNodes(List<TransactionDetailNode> detailNodes) {
		this.detailNodes = detailNodes;
	}

	public TransactionDetailQueryResult() {
		super();
	}
	
	public static TransactionDetailQueryResult initialization(GZUnionPayResult result) {
		TransactionDetailQueryResult queryResult = new TransactionDetailQueryResult();
		
		String rspPacket = result.getResponsePacket();
		try {
			Document document = DocumentHelper.parseText(rspPacket);
			Node queryTransNode = document.selectSingleNode("GZELINK/BODY/QUERY_TRANS");
			Node retDetailNodel = document.selectSingleNode("GZELINK/BODY/RET_DETAILS");
			
			if(queryTransNode != null && retDetailNodel != null) {
				String queryTransXml = queryTransNode.asXML();
				String retDetailsXml = retDetailNodel.asXML();

				queryResult = (TransactionDetailQueryResult) xStream.fromXML(queryTransXml, queryResult);
				queryResult = (TransactionDetailQueryResult) xStream.fromXML(retDetailsXml, queryResult);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return queryResult;
	}
	
	public static GZUnionPayRtnInfo parseInfo(GZUnionPayResult result){
		String rspPacket = result.getResponsePacket();
		try {
			Document document = DocumentHelper.parseText(rspPacket);
			Node infoNode = document.selectSingleNode("GZELINK/INFO");
			
			if(infoNode != null ) {
				String infoXml = infoNode.asXML();

				GZUnionPayRtnInfo gzUnionPayRtnInfo = (GZUnionPayRtnInfo)xStream.fromXML(infoXml);
				return gzUnionPayRtnInfo;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public TransactionDetailNode getFirstTransactionDetailNode() {
		if(CollectionUtils.isEmpty(this.detailNodes)) {
			return null;
		}
		return this.detailNodes.get(0);
	}
	
}
