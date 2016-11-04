package com.zufangbao.gluon.opensdk;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * @author wukai
 *
 */
public class QueryStringBuilderUtils {
	
	private static final String AND = " AND ";
	
	private static final String OR = " OR ";
	
	private static final String EMEPTY = StringUtils.EMPTY;
	
	private static final String WHERE = " WHERE	";
	
	private static QueryStringBuilderUtils queryBuilderUtils;
	
	private StringBuffer querySentence = new StringBuffer();
	
	private Queue<Boolean> queue = new LinkedList<Boolean>();
	
	private boolean ifTrue = true;
	
	private boolean needOthwise = true;
	
	private Object leftValue;
	
	private QueryStringBuilderUtils(){
		
	}
	public static QueryStringBuilderUtils getInstance(){
		
		if(null == queryBuilderUtils){
			
			queryBuilderUtils = new QueryStringBuilderUtils();
		}
		return queryBuilderUtils;
	}
	
	public QueryStringBuilderUtils QUERY(String queryString){
		
		this.querySentence.append(queryString).append(WHERE).append("1=1");
		
		return this;
		
	}
	public QueryStringBuilderUtils AND(String queryString){
		
		if(queue.poll()){
			
			this.querySentence.append(AND).append(queryString).append(EMEPTY);
		}
		
		return this;
	}
	public QueryStringBuilderUtils OR(String queryString){
		
		if(queue.poll()){
			
			this.querySentence.append(OR).append(queryString).append(EMEPTY);
			
		}
		return this;
	}
	
	public QueryStringBuilderUtils IF(boolean querySwitch){
		
		ifTrue = querySwitch;
		
		queue.offer(ifTrue);
		
		return this;
	}
	public QueryStringBuilderUtils ELSEAND(String querySwitch){
		
		if(!ifTrue){
			
			queue.offer(!ifTrue);
			
			return AND(querySwitch);
		}
		return this;
	}
	public QueryStringBuilderUtils ELSEOR(String querySwitch){
		
		if(!ifTrue){
			
			queue.offer(!ifTrue);
			
			return OR(querySwitch);
		}
		return this;
	}
	public QueryStringBuilderUtils CASE(Object leftValue){
		
		this.leftValue = leftValue;
		
		return this;
	}
	public QueryStringBuilderUtils WHEN(Object rightValue){
		
		boolean isEqual = this.leftValue == rightValue;
		
		queue.offer(isEqual);
		
		if(isEqual){
			
			this.needOthwise = false;
			
		}
		return this;
		
	}
	public QueryStringBuilderUtils OTHERWISEAND(String queryString){
		
		queue.offer(needOthwise);
		
		if(needOthwise){
			
			
			return AND(queryString);
		}
		return this;
	}
	public QueryStringBuilderUtils OTHERWISEOR(String queryString){
		
		queue.offer(needOthwise);
		
		if(needOthwise){
			
			return OR(queryString);
		}
		return this;
	}
	
	public String build(){
		
		return this.querySentence.toString();
	}
}
