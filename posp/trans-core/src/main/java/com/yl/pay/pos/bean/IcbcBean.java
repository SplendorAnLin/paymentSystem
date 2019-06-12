package com.yl.pay.pos.bean;

import java.util.Hashtable;

/**
 * 
 * Title: 广西工行交易请求bean
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class IcbcBean {

	private String termid; 				// 终端标识	card_accptr_termnl_id
	private String termtype;			// 终端类型	
	private String ip;					// IP地址
	private String flag;				// 标志
	private String fill;				// 备用
	private String zoneno;				// 地区号
	private String brno;				// 网点号
	private String tellerno;			// 柜员号
	private String trxtime;				// 交易时间
	private String trxdate;				// 交易日期
	private String appno; 				// 应用号
	private String trxcode;				// 交易码
	private String cardno;				// 卡号
	private String accname;				// 户名
	private String password;			// 个人PIN
	private String trxno;				// 终端流水号
	private String otrxno;				// 原终端流水号
	private String banktrxno;			// 银行流水号
	private String obanktrxno;			// 原银行流水号
	private String amount;				// 交易金额
	private String feeamt;				// 小费金额
	private String balance;				// 余额
	private String otrxdate;			// 原交易日期
	private String posid;				// POS终端编号
	private String merid;				// POS商场编号
	private String retcode;				// 响应代码
	private String retmsg;				// 响应信息
	private String retrieval;			// 检索号
	private String oretrieval;			// 原检索号
	private String msinfo2;				// 二磁道信息
	private String msinfo3;				// 三磁道信息
	private String pinkey;				// 密码密钥
	private String tkey;				// 通讯密钥
	private String bankinfo;			// 卡开户行信息
	private String authdate;			// 预授权日期
	private String authno;				// 预授权号
	private String depaccno;			// 对方账号
	private String depaccname;			// 对方户名
	private String depbankid;			// 对方开户行号
	private String bicetrxcode;			// bice交易码
	private String agenttype;			// 代理业务种类
	private String agentno;				// 代理业务编号
	private String custno;				// 缴费号码
	private String input1;				// 输入项1
	private String input2;				// 输入项2
	private String input3;				// 输入项3
	private String input4;				// 输入项4
	private String input5;				// 输入项5
	private String input6;				// 输入项6
	private String input7;				// 输入项7
	private String input8;				// 输入项8
	private String input9;				// 输入项9
	private String input10;				// 输出项10
	private String output1;				// 输出项1
	private String output2;				// 输出项2
	private String output3;				// 输出项3
	private String output4;				// 输出项4
	private String output5;				// 输出项5
	private String output6;				// 输出项6
	private String output7;				// 输出项7
	private String output8;				// 输出项8
	private String output9;				// 输出项9
	private String output10;			// 输出项10
	private String qflag;				// 查询条件标志
	private String display;				// 显示项
	private String print;				// 打印项
	private String pageflag;			// 翻页标志
	private String timestmp1;			// 时间戳1
	private String timestmp2;			// 时间戳2
	private String detail;				// 明细项1
	private String detail2;				// 明细项2
	
	//应工行要求加“商户名称”和“消费地点”
	private String fill4;				//商户名称
	private String fill5;				//消费地点
	private String clearPin;			//明文PIN

	public static Hashtable<String, Integer> ItemTbl;//把属性对应xml格式域号

	static{
		ItemTbl=new Hashtable<String, Integer>();
		
		ItemTbl.put("termid",12);
		ItemTbl.put("termtype",2);
		ItemTbl.put("ip",15);
		ItemTbl.put("flag",2);
		ItemTbl.put("fill",20);
		ItemTbl.put("zoneno",5);
		ItemTbl.put("brno",5);
		ItemTbl.put("tellerno",5);
		ItemTbl.put("trxtime",6);
		ItemTbl.put("trxdate",10);
		ItemTbl.put("appno",2);
		ItemTbl.put("trxcode",5);
		ItemTbl.put("cardno",19);
		ItemTbl.put("accname",50);
		ItemTbl.put("password",16);
		ItemTbl.put("trxno",7);
		ItemTbl.put("otrxno",7);
		ItemTbl.put("banktrxno",7);
		ItemTbl.put("obanktrxno",7);
		ItemTbl.put("amount",12);
		ItemTbl.put("feeamt",12);
		ItemTbl.put("balance",12);
		ItemTbl.put("otrxdate",8); 
		ItemTbl.put("posid",15);
		ItemTbl.put("merid",12);
		ItemTbl.put("retcode",5);
		ItemTbl.put("retmsg",50);
		ItemTbl.put("retrieval",23); 
		ItemTbl.put("oretrieval",23);
		ItemTbl.put("msinfo2",37);
		ItemTbl.put("msinfo3",104);
		ItemTbl.put("pinkey",32);
		ItemTbl.put("t_key",32);
		ItemTbl.put("bankinfo",60);
		ItemTbl.put("authdate",8);
		ItemTbl.put("authno",6);
		ItemTbl.put("depaccno",19);
		ItemTbl.put("depaccname",50);
		ItemTbl.put("depbankid",10);
		ItemTbl.put("bicetrxcode",5);
		ItemTbl.put("agenttype",3);
		ItemTbl.put("agentno",6);
		ItemTbl.put("custno",50);
		ItemTbl.put("input1",20);
		ItemTbl.put("input2",20);
		ItemTbl.put("input3",20);
		ItemTbl.put("input4",20);
		ItemTbl.put("input5",20);
		ItemTbl.put("input6",20);
		ItemTbl.put("input7",20);
		ItemTbl.put("input8",20);
		ItemTbl.put("input9",20);
		ItemTbl.put("input10",20);
		ItemTbl.put("output1",20);
		ItemTbl.put("output2",20);
		ItemTbl.put("output3",20);
		ItemTbl.put("output4",20);
		ItemTbl.put("output5",20);
		ItemTbl.put("output6",20);
		ItemTbl.put("output7",20);
		ItemTbl.put("output8",20);
		ItemTbl.put("output9",20);
		ItemTbl.put("output10",20);
		ItemTbl.put("qflag",2);
		ItemTbl.put("display",200);
		ItemTbl.put("print",200);
		ItemTbl.put("fill4", 500);
		ItemTbl.put("fill5", 500);
	}	
	
	public IcbcBean(){
	}
	
	
	public IcbcBean(String termid, String termtype,String ipaddr,String zoneno,String brno,String trxdate,String trxtime){
		this.termid = termid;
		this.termtype = termtype;
		this.ip = ipaddr;
		this.zoneno = zoneno;
		this.brno = brno;
		this.trxtime = trxtime;
		this.trxdate = trxdate;	
	}
		
	public String getTermid() {
		return termid;
	}

	public void setTermid(String termid) {
		this.termid = termid;
	}

	public String getTermtype() {
		return termtype;
	}

	public void setTermtype(String termtype) {
		this.termtype = termtype;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFill() {
		return fill;
	}

	public void setFill(String fill) {
		this.fill = fill;
	}

	public String getZoneno() {
		return zoneno;
	}

	public void setZoneno(String zoneno) {
		this.zoneno = zoneno;
	}

	public String getBrno() {
		return brno;
	}

	public void setBrno(String brno) {
		this.brno = brno;
	}

	public String getTellerno() {
		return tellerno;
	}

	public void setTellerno(String tellerno) {
		this.tellerno = tellerno;
	}

	public String getTrxcode() {
		return trxcode;
	}

	public void setTrxcode(String trxcode) {
		this.trxcode = trxcode;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getAccname() {
		return accname;
	}

	public void setAccname(String accname) {
		this.accname = accname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTrxno() {
		return trxno;
	}

	public void setTrxno(String trxno) {
		this.trxno = trxno;
	}

	public String getOtrxno() {
		return otrxno;
	}

	public void setOtrxno(String otrxno) {
		this.otrxno = otrxno;
	}

	public String getBanktrxno() {
		return banktrxno;
	}

	public void setBanktrxno(String banktrxno) {
		this.banktrxno = banktrxno;
	}

	public String getObanktrxno() {
		return obanktrxno;
	}

	public void setObanktrxno(String obanktrxno) {
		this.obanktrxno = obanktrxno;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFeeamt() {
		return feeamt;
	}

	public void setFeeamt(String feeamt) {
		this.feeamt = feeamt;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getTrxtime() {
		return trxtime;
	}

	public void setTrxtime(String trxtime) {
		this.trxtime = trxtime;
	}

	public String getTrxdate() {
		return trxdate;
	}

	public void setTrxdate(String trxdate) {
		this.trxdate = trxdate;
	}

	public String getOtrxdate() {
		return otrxdate;
	}

	public void setOtrxdate(String otrxdate) {
		this.otrxdate = otrxdate;
	}

	public String getPosid() {
		return posid;
	}

	public void setPosid(String posid) {
		this.posid = posid;
	}

	public String getMerid() {
		return merid;
	}

	public void setMerid(String merid) {
		this.merid = merid;
	}

	public String getRetcode() {
		return retcode;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	public String getRetmsg() {
		return retmsg;
	}

	public void setRetmsg(String retmsg) {
		this.retmsg = retmsg;
	}

	public String getRetrieval() {
		return retrieval;
	}

	public void setRetrieval(String retrieval) {
		this.retrieval = retrieval;
	}

	public String getOretrieval() {
		return oretrieval;
	}

	public void setOretrieval(String oretrieval) {
		this.oretrieval = oretrieval;
	}

	public String getMsinfo2() {
		return msinfo2;
	}

	public void setMsinfo2(String msinfo2) {
		this.msinfo2 = msinfo2;
	}

	public String getMsinfo3() {
		return msinfo3;
	}

	public void setMsinfo3(String msinfo3) {
		this.msinfo3 = msinfo3;
	}

	public String getPinkey() {
		return pinkey;
	}

	public void setPinkey(String pinkey) {
		this.pinkey = pinkey;
	}

	public String getTkey() {
		return tkey;
	}

	public void setTkey(String tkey) {
		this.tkey = tkey;
	}

	public String getBankinfo() {
		return bankinfo;
	}

	public void setBankinfo(String bankinfo) {
		this.bankinfo = bankinfo;
	}

	public String getAuthdate() {
		return authdate;
	}

	public void setAuthdate(String authdate) {
		this.authdate = authdate;
	}

	public String getAuthno() {
		return authno;
	}

	public void setAuthno(String authno) {
		this.authno = authno;
	}

	public String getDepaccno() {
		return depaccno;
	}

	public void setDepaccno(String depaccno) {
		this.depaccno = depaccno;
	}

	public String getDepaccname() {
		return depaccname;
	}

	public void setDepaccname(String depaccname) {
		this.depaccname = depaccname;
	}

	public String getDepbankid() {
		return depbankid;
	}

	public void setDepbankid(String depbankid) {
		this.depbankid = depbankid;
	}

	public String getBicetrxcode() {
		return bicetrxcode;
	}

	public void setBicetrxcode(String bicetrxcode) {
		this.bicetrxcode = bicetrxcode;
	}

	public String getAgenttype() {
		return agenttype;
	}

	public void setAgenttype(String agenttype) {
		this.agenttype = agenttype;
	}

	public String getAgentno() {
		return agentno;
	}

	public void setAgentno(String agentno) {
		this.agentno = agentno;
	}

	public String getCustno() {
		return custno;
	}

	public void setCustno(String custno) {
		this.custno = custno;
	}

	public String getInput1() {
		return input1;
	}

	public void setInput1(String input1) {
		this.input1 = input1;
	}

	public String getInput2() {
		return input2;
	}

	public void setInput2(String input2) {
		this.input2 = input2;
	}

	public String getInput3() {
		return input3;
	}

	public void setInput3(String input3) {
		this.input3 = input3;
	}

	public String getInput4() {
		return input4;
	}

	public void setInput4(String input4) {
		this.input4 = input4;
	}

	public String getInput5() {
		return input5;
	}

	public void setInput5(String input5) {
		this.input5 = input5;
	}

	public String getInput6() {
		return input6;
	}

	public void setInput6(String input6) {
		this.input6 = input6;
	}

	public String getInput7() {
		return input7;
	}

	public void setInput7(String input7) {
		this.input7 = input7;
	}

	public String getInput8() {
		return input8;
	}

	public void setInput8(String input8) {
		this.input8 = input8;
	}

	public String getInput9() {
		return input9;
	}

	public void setInput9(String input9) {
		this.input9 = input9;
	}

	public String getInput10() {
		return input10;
	}

	public void setInput10(String input10) {
		this.input10 = input10;
	}

	public String getOutput1() {
		return output1;
	}

	public void setOutput1(String output1) {
		this.output1 = output1;
	}

	public String getOutput2() {
		return output2;
	}

	public void setOutput2(String output2) {
		this.output2 = output2;
	}

	public String getOutput3() {
		return output3;
	}

	public void setOutput3(String output3) {
		this.output3 = output3;
	}

	public String getOutput4() {
		return output4;
	}

	public void setOutput4(String output4) {
		this.output4 = output4;
	}

	public String getOutput5() {
		return output5;
	}

	public void setOutput5(String output5) {
		this.output5 = output5;
	}

	public String getOutput6() {
		return output6;
	}

	public void setOutput6(String output6) {
		this.output6 = output6;
	}

	public String getOutput7() {
		return output7;
	}

	public void setOutput7(String output7) {
		this.output7 = output7;
	}

	public String getOutput8() {
		return output8;
	}

	public void setOutput8(String output8) {
		this.output8 = output8;
	}

	public String getOutput9() {
		return output9;
	}

	public void setOutput9(String output9) {
		this.output9 = output9;
	}

	public String getOutput10() {
		return output10;
	}

	public void setOutput10(String output10) {
		this.output10 = output10;
	}

	public String getQflag() {
		return qflag;
	}

	public void setQflag(String qflag) {
		this.qflag = qflag;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public String getPageflag() {
		return pageflag;
	}

	public void setPageflag(String pageflag) {
		this.pageflag = pageflag;
	}

	public String getTimestmp1() {
		return timestmp1;
	}

	public void setTimestmp1(String timestmp1) {
		this.timestmp1 = timestmp1;
	}

	public String getTimestmp2() {
		return timestmp2;
	}

	public void setTimestmp2(String timestmp2) {
		this.timestmp2 = timestmp2;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getDetail2() {
		return detail2;
	}

	public void setDetail2(String detail2) {
		this.detail2 = detail2;
	}

	public String getAppno() {
		return appno;
	}

	public void setAppno(String appno) {
		this.appno = appno;
	}

	public static Hashtable<String, Integer> getItemTbl() {
		return ItemTbl;
	}

	public static void setItemTbl(Hashtable<String, Integer> itemTbl) {
		ItemTbl = itemTbl;
	}
	
	public String getFill4() {
		return fill4;
	}

	public void setFill4(String fill4) {
		this.fill4 = fill4;
	}

	public String getFill5() {
		return fill5;
	}

	public void setFill5(String fill5) {
		this.fill5 = fill5;
	}
	public String getClearPin() {
		return clearPin;
	}

	public void setClearPin(String clearPin) {
		this.clearPin = clearPin;
	}
}
