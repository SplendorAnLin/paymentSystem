package com.yl.boss.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.crypto.KeyGenerator;
import javax.ejb.Remove;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.pay.common.util.DesUtil;
import com.pay.common.util.ISO8583.ISO8583Utile;
import com.yl.boss.dao.PosDao;
import com.yl.boss.dao.PosSynchronizationDao;
import com.yl.boss.dao.SecretKeyDao;
import com.yl.boss.entity.Agent;
import com.yl.boss.entity.Customer;
import com.yl.boss.entity.Pos;
import com.yl.boss.entity.PosPurchase;
import com.yl.boss.entity.PosSynchronization;
import com.yl.boss.entity.SecretKey;
import com.yl.boss.entity.Shop;
import com.yl.boss.enums.RunStatus;
import com.yl.boss.enums.Status;
import com.yl.boss.enums.SyncType;
import com.yl.boss.enums.TransType;
import com.yl.boss.service.AgentService;
import com.yl.boss.service.CustomerService;
import com.yl.boss.service.PosService;
import com.yl.boss.service.ShopService;
import com.yl.boss.service.SyncInfoService;
import com.yl.boss.utils.CodeBuilder;

/**
 * POS终端业务接口实现
 *
 * @author 聚合支付有限公司
 * @since 2017年7月10日
 * @version V1.0.0
 */
public class PosServiceImpl implements PosService {

	private static final Logger logger = LoggerFactory.getLogger(PosServiceImpl.class);

	private static final String KEY = "1111111111111111";
	private static final String CHECK_KEY = "0000000000000000";
	private static final String ZMK = "5EAE19A86AEA9E40";
	private static final String ZMK_CHECK = "A8795C4590A31E24";
	@Resource
	PosDao posDao;

	@Resource
	ShopService shopService;

	@Resource
	SyncInfoService syncInfoService;

	@Resource
	SecretKeyDao secretKeyDao;
	
	@Resource
	AgentService agentService;
	
	@Resource
	CustomerService customerService;
	
	@Resource
	PosSynchronizationDao posSynchronizationDao;
	
	@Override
	public void posAdd(Pos pos) {
		try {
			pos.setCreateTime(new Date());
			pos.setRunStatus(RunStatus.SIGN_OFF);
			pos.setStatus(Status.TRUE);
			pos.setRouteType(TransType.NORMAL);
			posDao.posAdd(pos);
			
			KeyGenerator kg=KeyGenerator.getInstance("DES");
			kg.init(56);
			
			/**
			 * 生成主密钥
			 */
			String zmk=ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded());
			String zmkKey=DesUtil.desEncrypt(zmk, KEY);
			String zmkCheck=DesUtil.desEncrypt(CHECK_KEY,zmkKey);
			SecretKey zmkSecretKey = new SecretKey();
			zmkSecretKey.setKeyName("pos." + pos.getPosCati() + ".zmk");
			zmkSecretKey.setKey(ZMK);
			zmkSecretKey.setCheckValue(ZMK_CHECK);
			secretKeyDao.secretKeyAdd(zmkSecretKey);
			syncInfoService.syncInfoAddFormPosp(SyncType.SECRET_KEY_SYNC, JsonUtils.toJsonString(zmkSecretKey), Status.TRUE);
			logger.info("zmk:"+zmk+",zmkKey:"+zmkKey+",zmkCheck:"+zmkCheck);
			
			/**
			 * 生成pin秘钥
			 */
			String zpk=ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded());
			String zpkKey=DesUtil.desEncrypt(zpk, zmkKey);
			String zpkCheck=DesUtil.desEncrypt(CHECK_KEY,zpkKey);
			SecretKey zpkSecretKey = new SecretKey();
			zpkSecretKey.setKeyName("pos." + pos.getPosCati() + ".zpk");
			zpkSecretKey.setKey(zpkKey);
			zpkSecretKey.setCheckValue(zpkCheck);
			secretKeyDao.secretKeyAdd(zpkSecretKey);
			syncInfoService.syncInfoAddFormPosp(SyncType.SECRET_KEY_SYNC, JsonUtils.toJsonString(zpkSecretKey), Status.TRUE);
			logger.info("zpk:"+zpk+",zpkKey:"+zpkKey+",zpkCheck:"+zpkCheck);
			/**
			 * 生成mac秘钥
			 */
			String zak=ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded());
			String zakKey=DesUtil.desEncrypt(zak, zmkKey);
			String zakCheck=DesUtil.desEncrypt(CHECK_KEY,zakKey);
			SecretKey zakSecretKey = new SecretKey();
			zakSecretKey.setKeyName("pos." + pos.getPosCati() + ".zak");
			zakSecretKey.setKey(zakKey);
			zakSecretKey.setCheckValue(zakCheck);
			secretKeyDao.secretKeyAdd(zakSecretKey);
			syncInfoService.syncInfoAddFormPosp(SyncType.SECRET_KEY_SYNC, JsonUtils.toJsonString(zakSecretKey), Status.TRUE);
			logger.info("zak:"+zak+",zakKey:"+zakKey+",zakCheck:"+zakCheck);
		} catch (Exception e) {
			
		}
	}

	@Override
	public Pos posById(Long id) {
		return posDao.posById(id);
	}

	@Override
	public void posUpdate(Pos pos) {
		try {
			Pos posResult = posDao.posById(pos.getId());

			if (posResult != null) {

				posResult.setPosBrand(pos.getPosBrand());
				posResult.setType(pos.getType());
				posResult.setMKey(pos.getMKey());
				posResult.setSoftVersion(pos.getSoftVersion());
				posResult.setParamVersion(pos.getParamVersion());
				posResult.setPosType(pos.getPosType());

				posDao.posUpdate(posResult);

				syncInfoService.syncInfoAddFormPosp(SyncType.POS_SYNC, JsonUtils.toJsonString(posResult), Status.FALSE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean posCatiBool(String posCati) {
		return posDao.posCatiBool(posCati);
	}

	@Override
	public List<Pos> findPos(String customerNo) {
		return posDao.findPos(customerNo);
	}
	
	@Override
	public void posPurchaseAdd(PosPurchase posPurchase) {
		posDao.posPurchaseAdd(posPurchase);
	}

	@Override
	public String posAllAdd(Pos pos, int posSnNumber, String operator) {
		String msg = "true";
		try {
			
			String batchNoLock = CacheUtils.get("POS-ADD-"+pos.getBatchNo(), String.class);
			if(StringUtils.isBlank(batchNoLock)){
				try {
					CacheUtils.setEx("POS-ADD-"+pos.getBatchNo(), "batchNoLock", 120);
					logger.info("运营账号：" + operator + "，POS批次号：" + pos.getBatchNo() + " 加锁 ");
					
					List<String> posSnArrays = null;
					
					int posSn = Integer.parseInt(pos.getPosSn());

					posSnArrays = new ArrayList<>();
					
					
					String posSnString = "";
					
					for (int i = 0; i < posSnNumber; i++) {
						posSnArrays.add(String.valueOf(posSn + i));
						if(posSnNumber - 1 != i){
							posSnString += posSnArrays.get(i) + ",";
						}else {
							posSnString += posSnArrays.get(i);
						}
					}
					
					List<String> repeatedPosSn = posDao.posPosSnExists(posSnString);
					
					if(repeatedPosSn != null && repeatedPosSn.size() > 0){
						
						msg = JsonUtils.toJsonString(repeatedPosSn);
						
						for (int i = 0; i < repeatedPosSn.size(); i++) {
							for (int j = 0; j < posSnArrays.size(); j++) {
								if(repeatedPosSn.get(i).equals(posSnArrays.get(i))){
									posSnArrays.remove(i);
									break;
								}
							}
						}
					}
					
					//批量生成posSn
					for (int i = 0; i < posSnArrays.size(); i++) {
//						posSnArrays[i] = String.valueOf(posSn + i);
						
						if(posSnArrays.get(i) != null && !"".equals(posSnArrays.get(i))){
							try {
								pos.setId(null);
								pos.setPosCati(null);
								pos.setPosSn(posSnArrays.get(i));
								posAddInfo(pos);
							} catch (Exception e) {
								logger.error(e.getMessage());
							}
						}
						
					}
				} catch (Exception e) {
					msg = "false";
					logger.error("系统异常", e);
				}finally {
					CacheUtils.del("POS-ADD-"+pos.getBatchNo());
					logger.info("运营账号：" +  operator + "，POS批次号：" + pos.getBatchNo() + " 解锁 ");
				}
			}else {
				logger.info("批次号：" + pos.getBatchNo() + ",重复提交!");			
			}
		} catch (Exception e) {
			msg = "false";
			logger.error("系统异常:", e);
		}
		return msg;

	}
	
	synchronized void posAddInfo(Pos pos){
		posAdd(pos);
	}

	@Override
	public String posCatiMax() {
		return posDao.posCatiMax();
	}

	@Override
	public void posBatchDelivery(String[] posCatiArrays, String agentNo, String operator) {
		try {
			
			//服务商效验
			Agent agent = agentService.findByNo(agentNo);
			
			if(agent != null){
				
				if(agent.getAgentLevel() == 1){
					
					Pos pos = null;
					
					//循环遍历批量POS终端号
					for (String posCati : posCatiArrays) {
						//根据终端号查询POS信息
						pos = posDao.posByPosCati(posCati);
						if(pos != null){
							try {
								String posSnLock = CacheUtils.get("POS-Delivery-"+pos.getPosSn(), String.class);
								
								if(StringUtils.isBlank(posSnLock)){
									
									CacheUtils.setEx("POS-Delivery-"+pos.getPosSn(), "posSnLock", 120);
									
									logger.info("运营账号：" + operator + "，posSn号：" + pos.getPosSn() + " 加锁 ");
									
									pos.setAgentNo(agent.getAgentNo());
									
									posDao.posUpdate(pos);
									
								}else {
									logger.info("posSn号：" + pos.getPosSn() + ",重复提交!");			
								}
								
							} catch (Exception e) {
								logger.error("系统异常", e);
							}finally {
								CacheUtils.del("POS-Delivery-"+pos.getPosSn());
								logger.info("运营账号：" +  operator + "，posSn号：" + pos.getPosSn() + " 解锁 ");
							}
						}
					}
					
				}else {
					logger.error("posBatchDelivery agentLevel is not 1");
				}
				
			}else {
				logger.error("posBatchDelivery agent is null!");
			}
			
		} catch (Exception e) {
			logger.error("系统异常", e);
		}
	}

	@Override
	public void posBind(String[] posCatiArrays, String customerNo, String operator) {
		try {
			
			//商户效验
			Customer customer = customerService.findByCustNo(customerNo);
			
			if(customer != null){

				Pos pos = null;

				//循环遍历批量POS终端号
				for (String posCati : posCatiArrays) {
					//根据终端号查询POS信息
					pos = posDao.posByPosCati(posCati);
					if(pos != null){
						try {
							String posSnLock = CacheUtils.get("POS-Bind-"+pos.getPosSn(), String.class);

							if(StringUtils.isBlank(posSnLock)){

								CacheUtils.setEx("POS-Bind-"+pos.getPosSn(), "posSnLock", 120);

								logger.info("运营账号：" + operator + "，posSn号：" + pos.getPosSn() + " 加锁 ");

								pos.setCustomerNo(customerNo);

								Shop shop = shopService.queryShopByCustNo(customerNo);

								//判断当前商户是否存在网点，若不存在则自动添加一条网点
								if(shop != null){
									
									pos.setShop(shop);
									pos.setShopNo(shop.getShopNo());
									
								}else {
									
									try {
										shop=new Shop();
										shop.setBindPhoneNo("1");
										shop.setCustomer(customer);
										shop.setPrintName(customer.getFullName());
										shop.setShopName(customer.getFullName());
										shop.setShopNo(CodeBuilder.buildNumber(15));
										shop.setStatus(Status.TRUE);
										shopService.shopAdd(shop);
										
										pos.setShop(shop);
										pos.setShopNo(shop.getShopNo());
									} catch (Exception e) {
										logger.error("posBind shopAdd failed!", e);
									}
									
								}
								
								posDao.posUpdate(pos);
								
								//同步POS信息
								PosSynchronization posSynchronization = new PosSynchronization();
								posSynchronization.setCustomerNo(pos.getCustomerNo());
								posSynchronization.setPosCati(posCati);
								posSynchronization.setStatus(Status.FALSE);
								posSynchronizationDao.add(posSynchronization);
								
								syncInfoService.syncInfoAddFormPosp(SyncType.POS_SYNC, JsonUtils.toJsonString(pos), Status.TRUE);

							}else {
								logger.info("posSn号：" + pos.getPosSn() + ",重复提交!");			
							}

						} catch (Exception e) {
							logger.error("系统异常", e);
						}finally {
							CacheUtils.del("POS-Bind-"+pos.getPosSn());
							logger.info("运营账号：" +  operator + "，posSn号：" + pos.getPosSn() + " 解锁 ");
						}
					}
				}

			}else {
				logger.error("posBind customer is null!");
			}
			
		} catch (Exception e) {
			logger.error("系统异常", e);
		}
	}

	@Override
	public void posBatchDeliveryOuter(String[] posCatiArrays, String agentNo, String agentChildNo, String operator) {
		try {

			//服务商效验
			Agent agent = agentService.findByNo(agentNo);
			
			Agent agentChild = agentService.findByNo(agentChildNo);

			if(agent != null){
				
				//判断当前出库的服务商上级是否正确
				if(agentChild.getParenId().equals(agent.getAgentNo())){
					
					if(agent.getAgentLevel() != 3){
						
						String agent_no = null;
						
						if(agent.getAgentLevel() == 2){
							
							agent_no = agent.getParenId() + "," +  agent.getAgentNo() + "," + agentChildNo;
							
						}else{
							agent_no = agent.getAgentNo() + "," + agentChildNo;
						}
						
						Pos pos = null;

						//循环遍历批量POS终端号
						for (String posCati : posCatiArrays) {
							//根据终端号查询POS信息
							pos = posDao.posByPosCati(posCati);
							if(pos != null){
								try {
									String posSnLock = CacheUtils.get("POS-Delivery-"+pos.getPosSn(), String.class);

									if(StringUtils.isBlank(posSnLock)){

										CacheUtils.setEx("POS-Delivery-"+pos.getPosSn(), "posSnLock", 120);

										logger.info("运营账号：" + operator + "，posSn号：" + pos.getPosSn() + " 加锁 ");

										pos.setAgentNo(agent_no);

										posDao.posUpdate(pos);

									}else {
										logger.info("posSn号：" + pos.getPosSn() + ",重复提交!");			
									}

								} catch (Exception e) {
									logger.error("系统异常", e);
								}finally {
									CacheUtils.del("POS-Delivery-"+pos.getPosSn());
									logger.info("运营账号：" +  operator + "，posSn号：" + pos.getPosSn() + " 解锁 ");
								}
							}
						}
						
					}else {
						logger.error("posBatchDeliveryOuter agentChildNo is level 3");
					}
					
				}else {
					logger.error("posBatchDeliveryOuter agentChildNo not match");
				}
				
			}else {
				logger.error("posBatchDelivery agent is null!");
			}
			
		} catch (Exception e) {
			logger.error("系统异常", e);
		}
	}
	
	@Override
	public List findPosOemByCustomerNo(String customerNo) {
		return posDao.findPosOemByCustomerNo(customerNo);
	}

	public Pos findPosByPosSn(String posSn){
		return posDao.findPosByPosSn(posSn);
	}

	public Pos findPosByPosCati(String posCati){
		return posDao.posByPosCati(posCati);
	}

}
