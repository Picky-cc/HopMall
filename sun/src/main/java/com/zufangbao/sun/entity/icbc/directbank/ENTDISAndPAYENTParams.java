package com.zufangbao.sun.entity.icbc.directbank;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/***
 * 扣款服务参数
 * @author wk
 *
 */
@Scope("prototype")
@Component
public class ENTDISAndPAYENTParams extends BasicParams {
		/**总笔数*/
		private int totalNum;
		/**总金额*/
		private String totalAmt; 
		/**入账方式*/
		private int settleMode;
		/**收方账号*/
		private String recAccNo;
		/**收方账号中文名称*/
		private String recAccNameCN;
		/**付方账号*/
		private String payAccNo;
		/**付方账户中文名称*/
		private String payAccNameCN;
		/**付方账号开户行*/
		private String payBranch;
		/**金额*/
		private String payAmt;
		/**支付方式*/
		private int payType;
		/**币种*/
		private String currType;
		/**签名时间*/
		private Date signTime;
		/**指令包序列号*/
		private String iSeqNo;
		/**中文备注*/
		private String useCN;
		/**附言*/
		private String postScript;
		/**系统内外标志*/
		private String sysIOFlg;
		/**同城异地标志*/
		private String isSameCity = "";
		/**对公对私标志*/
		private String prop = "";
    /**收款方所在城市名称*/
    private String recCityName = "";
		/**对方行行号*/
		private String recBankNo = "";
		/**交易对方银行名称*/
		private String recBankName = "";
		
		
		/**
		 * @param totalNum
		 * @param totalAmt
		 * @param recAccNo
		 * @param recAccNameCN
		 * @param payAccNo
		 * @param payAccNameCN
		 * @param payBranch
		 * @param payAmt
		 * @param postScript
		 */
		public ENTDISAndPAYENTParams initialize (int totalNum, String totalAmt,
				String recAccNo, String recAccNameCN, String payAccNo,
				String payAccNameCN, String payBranch, String payAmt,
				String postScript) {
			this.totalNum = totalNum;
			this.totalAmt = totalAmt;
			this.recAccNo = recAccNo;
			this.recAccNameCN = recAccNameCN;
			this.payAccNo = payAccNo;
			this.payAccNameCN = payAccNameCN;
			this.payBranch = payBranch;
			this.payAmt = payAmt;
			this.postScript = postScript;
			/**0 逐笔记账 2：并笔记账*/
			this.settleMode = 0;
			this.signTime = new Date(System.currentTimeMillis());
			this.iSeqNo = new TransParams().getPackageID();
			this.currType = "001";
			/**1:加急2：普通*/
			this.payType =1;
			return this;
		}
		
		/**
		 * @param recAccNo
		 * @param recAccNameCN
		 * @param payAccNo
		 * @param payAccNameCN
		 * @param payAmt
		 * @param postScript
		 */
		public ENTDISAndPAYENTParams initialize(String recAccNo, String recAccNameCN,
				String payAccNo, String payAccNameCN, String sysIOFlg,String payAmt,
				String postScript) {
			this.recAccNo = recAccNo;
			this.recAccNameCN = recAccNameCN;
			this.payAccNo = payAccNo;
			this.payAccNameCN = payAccNameCN;
			this.sysIOFlg = sysIOFlg;
			this.payAmt = payAmt;
			this.postScript = postScript;
			/**0 逐笔记账 2：并笔记账*/
			this.settleMode = 0;
			this.signTime = new Date(System.currentTimeMillis());
			this.iSeqNo = new TransParams().getiSeqNo();
			this.currType = "001";
			this.totalAmt = this.payAmt;
			this.totalNum = 1;
			/**1:加急2：普通*/
			this.payType = 1;
			return this;
		}
		
		
    public ENTDISAndPAYENTParams initialize(String recAccNo, String recAccNameCN,
        String payAccNo, String payAccNameCN, String sysIOFlg, String isSameCity, String prop,String recCityName,String recBankNo,String recBankName, String payAmt,
        String postScript) {
      this.recAccNo = recAccNo;
      this.recAccNameCN = recAccNameCN;
      this.payAccNo = payAccNo;
      this.payAccNameCN = payAccNameCN;
      this.sysIOFlg = sysIOFlg;
      this.isSameCity = isSameCity;
      this.prop = prop;
      this.recCityName = recCityName;
      this.recBankNo = recBankNo;
      this.recBankName = recBankName;
      this.payAmt = payAmt;
      this.postScript = postScript;
      /**0 逐笔记账 2：并笔记账*/
      this.settleMode = 0;
      this.signTime = new Date(System.currentTimeMillis());
      this.iSeqNo = new TransParams().getiSeqNo();
      this.currType = "001";
      this.totalAmt = this.payAmt;
      this.totalNum = 1;
      /**1:加急2：普通*/
      this.payType = 1;
      return this;
    }
		

		/**
		 * @return the totalNum
		 */
		public int getTotalNum() {
			return totalNum;
		}
		/**
		 * @param totalNum the totalNum to set
		 */
		public void setTotalNum(int totalNum) {
			this.totalNum = totalNum;
		}
		/**
		 * @return the totalAmt
		 */
		public String getTotalAmt() {
			return totalAmt;
		}
		/**
		 * @param totalAmt the totalAmt to set
		 */
		public void setTotalAmt(String totalAmt) {
			this.totalAmt = totalAmt;
		}
		/**
		 * @return the settleMode
		 */
		public int getSettleMode() {
			return settleMode;
		}
		/**
		 * @param settleMode the settleMode to set
		 */
		public void setSettleMode(int settleMode) {
			this.settleMode = settleMode;
		}
		/**
		 * @return the recAccNo
		 */
		public String getRecAccNo() {
			return recAccNo;
		}
		/**
		 * @param recAccNo the recAccNo to set
		 */
		public void setRecAccNo(String recAccNo) {
			this.recAccNo = recAccNo;
		}
		/**
		 * @return the recAccNameCN
		 */
		public String getRecAccNameCN() {
			return recAccNameCN;
		}
		/**
		 * @param recAccNameCN the recAccNameCN to set
		 */
		public void setRecAccNameCN(String recAccNameCN) {
			this.recAccNameCN = recAccNameCN;
		}
		/**
		 * @return the payAccNo
		 */
		public String getPayAccNo() {
			return payAccNo;
		}
		/**
		 * @param payAccNo the payAccNo to set
		 */
		public void setPayAccNo(String payAccNo) {
			this.payAccNo = payAccNo;
		}
		/**
		 * @return the payAccNameCN
		 */
		public String getPayAccNameCN() {
			return payAccNameCN;
		}
		/**
		 * @param payAccNameCN the payAccNameCN to set
		 */
		public void setPayAccNameCN(String payAccNameCN) {
			this.payAccNameCN = payAccNameCN;
		}
		/**
		 * @return the payBranch
		 */
		public String getPayBranch() {
			return payBranch;
		}
		/**
		 * @param payBranch the payBranch to set
		 */
		public void setPayBranch(String payBranch) {
			this.payBranch = payBranch;
		}
		/**
		 * @return the payAmt
		 */
		public String getPayAmt() {
			return payAmt;
		}
		/**
		 * @param payAmt the payAmt to set
		 */
		public void setPayAmt(String payAmt) {
			this.payAmt = payAmt;
		}
		/**
		 * @return the payType
		 */
		public int getPayType() {
			return payType;
		}
		/**
		 * @param payType the payType to set
		 */
		public void setPayType(int payType) {
			this.payType = payType;
		}
		/**
		 * @return the currType
		 */
		public String getCurrType() {
			return currType;
		}
		/**
		 * @param currType the currType to set
		 */
		public void setCurrType(String currType) {
			this.currType = currType;
		}
		/**
		 * @return the signTime
		 */
		public Date getSignTime() {
			return signTime;
		}
		/**
		 * @param signTime the signTime to set
		 */
		public void setSignTime(Date signTime) {
			this.signTime = signTime;
		}
		/**
		 * @return the iSeqNo
		 */
		public String getiSeqNo() {
			return iSeqNo;
		}
		/**
		 * @param iSeqNo the iSeqNo to set
		 */
		public void setiSeqNo(String iSeqNo) {
			this.iSeqNo = iSeqNo;
		}
		/**
		 * @return the useCN
		 */
		public String getUseCN() {
			return useCN;
		}
		/**
		 * @param useCN the useCN to set
		 */
		public void setUseCN(String useCN) {
			this.useCN = useCN;
		}
		/**
		 * @return the postScript
		 */
		public String getPostScript() {
			return postScript;
		}
		/**
		 * @param postScript the postScript to set
		 */
		public void setPostScript(String postScript) {
			this.postScript = postScript;
		}

    /**
     * @return the recCityName
     */
    public String getRecCityName() {
      return recCityName;
    }

    /**
     * @param recCityName the recCityName to set
     */
    public void setRecCityName(String recCityName) {
      this.recCityName = recCityName;
    }

    /**
     * @return the recBankNo
     */
    public String getRecBankNo() {
      return recBankNo;
    }

    /**
     * @param recBankNo the recBankNo to set
     */
    public void setRecBankNo(String recBankNo) {
      this.recBankNo = recBankNo;
    }

    /**
     * @return the recBankName
     */
    public String getRecBankName() {
      return recBankName;
    }

    /**
     * @param recBankName the recBankName to set
     */
    public void setRecBankName(String recBankName) {
      this.recBankName = recBankName;
    }

    /**
     * @return the sysIOFlg
     */
    public String getSysIOFlg() {
      return sysIOFlg;
    }

    /**
     * @param sysIOFlg the sysIOFlg to set
     */
    public void setSysIOFlg(String sysIOFlg) {
      this.sysIOFlg = sysIOFlg;
    }

    /**
     * @return the isSameCity
     */
    public String getIsSameCity() {
      return isSameCity;
    }

    /**
     * @param isSameCity the isSameCity to set
     */
    public void setIsSameCity(String isSameCity) {
      this.isSameCity = isSameCity;
    }

    /**
     * @return the prop
     */
    public String getProp() {
      return prop;
    }

    /**
     * @param prop the prop to set
     */
    public void setProp(String prop) {
      this.prop = prop;
    }
		
}
