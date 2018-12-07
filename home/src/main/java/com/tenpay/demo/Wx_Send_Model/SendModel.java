package com.tenpay.demo.Wx_Send_Model;

import com.alibaba.fastjson.JSONObject;
import com.guangde.entry.ApiDonateRecord;
import com.tenpay.demo.TemplateData;
import com.tenpay.demo.WxTemplate;
import com.tenpay.utils.TenpayUtil;

import java.util.HashMap;
import java.util.Map;

public class SendModel {

	private final static String url ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	
	/**
	 * 对于善款捐赠通知的模版封装
	 */
	public JSONObject Donation_Success_Notice(String accessToken,String openId,String returnUrl,ApiDonateRecord record){
		//没有额外的操作
		return Donation_Success_Notice(accessToken,openId,returnUrl,record,0,null);
	}
	/**
	 * 对于善款捐赠通知的模版封装
	 */
	public JSONObject Donation_Success_Notice(String accessToken,String openId,String returnUrl,ApiDonateRecord record,int type,Object object){
		String sendurl = url + accessToken;
		WxTemplate wxTemplate = new WxTemplate();
		wxTemplate.setTemplate_id("N1bknb5srKF7kBoKH4SQ4z89K4xAidl00FSxc7HpHJE");
		//wxTemplate.setTouser(record.getOpenId());
		wxTemplate.setUrl(returnUrl);
		wxTemplate.setTopcolor("#FF0000");
		Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
		TemplateData first = new TemplateData();
		first.setValue("亲爱的"+record.getNickName()+"，赠人玫瑰，手有余香，您已成功捐赠"+record.getDonatAmount()+"元。感谢有你！");
		first.setColor("#173177");
		m.put("first", first);
		TemplateData DonateNum = new TemplateData();
		DonateNum.setValue(record.getProjectTitle()+"\n");
		DonateNum.setColor("#F1451C");
		m.put("DonateNum", DonateNum);
		TemplateData DonateSum = new TemplateData();
		DonateSum.setValue(record.getDonatAmount().toString()+"元");
		DonateSum.setColor("#F1451C");
		m.put("DonateSum", DonateSum);
		TemplateData remark = new TemplateData();
		remark.setValue("\n我们会实时公布善款执行明细，敬请关注。");
		remark.setColor("#173177");
		m.put("remark", remark);
		wxTemplate.setData(m);
		return TenpayUtil.httpRequest(sendurl,"GET",JSONObject.toJSONString(wxTemplate));
	}
	
	/**
	 * 对于善款使用说明的模版封装
	 */
	public JSONObject Use_Donation_Explain(String accessToken,String openId,String returnUrl,ApiDonateRecord record){
		String sendurl = url + accessToken;
		WxTemplate wxTemplate = new WxTemplate();
		wxTemplate.setTemplate_id("UPjGFTCzZqDtZV8jgP7NgIPCvwY1GHnySjE3FV8cum0");
		wxTemplate.setTouser(openId);
		wxTemplate.setUrl(returnUrl);
		wxTemplate.setTopcolor("#FF0000");
		Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
		TemplateData first = new TemplateData();
		first.setValue("亲爱的"+record.getNickName()+"，您捐助的公益项目有新反馈了，包括善款公开、项目执行等详细内容。请点击本消息查看。");
		first.setColor("#173177");
		m.put("first", first);
		TemplateData ProjName = new TemplateData();
		ProjName.setValue(record.getProjectTitle());
		ProjName.setColor("#F1451C");
		m.put("ProjName", ProjName);
		TemplateData Report = new TemplateData();
		Report.setValue(record.getDonatAmount());
		Report.setColor("#F1451C");
		m.put("Report", Report);
		TemplateData remark = new TemplateData();
		remark.setValue("公开、透明，我们一直在努力！");
		remark.setColor("#173177");
		m.put("remark", remark);
		wxTemplate.setData(m);
		return TenpayUtil.httpRequest(sendurl,"GET",JSONObject.toJSONString(wxTemplate));
	}
	
}
