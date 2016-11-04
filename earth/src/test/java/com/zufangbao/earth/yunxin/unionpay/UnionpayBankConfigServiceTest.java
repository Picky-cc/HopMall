package com.zufangbao.earth.yunxin.unionpay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.zufangbao.sun.entity.unionpay.UnionpayBankConfig;
import com.zufangbao.sun.yunxin.service.UnionpayBankConfigService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class UnionpayBankConfigServiceTest {

	@Autowired
	private UnionpayBankConfigService unionpayBankConfigService;
	
	@Autowired
	private CacheManager cacheManager; 
	
	private String cacheKey = "unionpayBankConfigs";
	
	@Autowired
	private GenericDaoSupport genericDaoSupport;
	
	@Before
	public void setUp () {
		genericDaoSupport.executeHQL("DELETE FROM UnionpayBankConfig", null);
		UnionpayBankConfig unionpayBankConfig = new UnionpayBankConfig();
		unionpayBankConfig.setBankCode("102");
		unionpayBankConfig.setBankName("中国工商银行");
		unionpayBankConfig.setUseBatchDeduct(true);
		unionpayBankConfigService.save(unionpayBankConfig);
		Cache unionpayBankConfigsCache = cacheManager.getCache(cacheKey);
		unionpayBankConfigsCache.evict(SimpleKey.EMPTY);
	}
	
	@After
	public void setDown() {
		Cache unionpayBankConfigsCache = cacheManager.getCache(cacheKey);
		unionpayBankConfigsCache.evict(SimpleKey.EMPTY);
	}
	
	@Test
	public void testCacheUnionpayBankConfigs() {
		Cache unionpayBankConfigsCache = cacheManager.getCache(cacheKey);
		assertEquals(null, unionpayBankConfigsCache.get(SimpleKey.EMPTY));
		//缓存前为null
		unionpayBankConfigService.getUnionpayBankConfigs();
		List<UnionpayBankConfig> unionpayBankConfigs = (List<UnionpayBankConfig>) unionpayBankConfigsCache.get(SimpleKey.EMPTY).get();
		assertEquals(1, unionpayBankConfigs.size());
		UnionpayBankConfig unionpayBankConfig = unionpayBankConfigs.get(0);
		assertEquals("102", unionpayBankConfig.getBankCode());
		assertEquals("中国工商银行", unionpayBankConfig.getBankName());
		assertTrue(unionpayBankConfig.isUseBatchDeduct());
		//缓存后有一条数据
	}
	
	@Test
	public void testEvictCacheUnionpayBankConfings() {
		Cache unionpayBankConfigsCache = cacheManager.getCache(cacheKey);
		unionpayBankConfigService.getUnionpayBankConfigs();
		List<UnionpayBankConfig> unionpayBankConfigs = (List<UnionpayBankConfig>) unionpayBankConfigsCache.get(SimpleKey.EMPTY).get();
		assertEquals(1, unionpayBankConfigs.size());
		UnionpayBankConfig unionpayBankConfig = unionpayBankConfigs.get(0);
		assertEquals("102", unionpayBankConfig.getBankCode());
		assertEquals("中国工商银行", unionpayBankConfig.getBankName());
		assertTrue(unionpayBankConfig.isUseBatchDeduct());
		//缓存后有一条数据
		
		unionpayBankConfigService.cacheEvictUnionpayBankConfig();
		assertEquals(null, unionpayBankConfigsCache.get(SimpleKey.EMPTY));
		//驱逐缓存后为null;
	}
	
}
