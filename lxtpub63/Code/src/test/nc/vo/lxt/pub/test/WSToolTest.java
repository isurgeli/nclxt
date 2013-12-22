package nc.vo.lxt.pub.test;

import junit.framework.TestCase;
import nc.bs.lxt.pub.WSTool;
import nc.vo.pub.BusinessException;

public class WSToolTest extends TestCase {
	public WSToolTest(String name) {
		super(name);
	}

	public void testWSAuth() throws BusinessException {
		WSTool.callByHttpBasicAuth(
				"http://erpdev.crmsc.com.cn:8000/sap/bc/srt/rfc/sap/zws_fkdjk/206/zws_fkdjk/zws_fkdjk",
				getSoapMsg(), "zhoujs", "zhouman");

	}

	private String getSoapMsg() {
		StringBuffer soap = new StringBuffer();
		soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:sap-com:document:sap:soap:functions:mc-style\">");
		soap.append("   <soapenv:Header/>");
		soap.append("   <soapenv:Body>");
		soap.append("      <urn:ZfiFkdjk>");
		soap.append("         <!--Optional:-->");
		soap.append("         <IAedat>");
		soap.append("            <!--Zero or more repetitions:-->");
		soap.append("            <item>");
		soap.append("               <Sign>I</Sign>");
		soap.append("               <Option>BT</Option>");
		soap.append("               <Low>20130101</Low>");
		soap.append("               <High>20131231</High>");
		soap.append("            </item>");
		soap.append("         </IAedat>");
		soap.append("         <!--Optional:-->");
		soap.append("         <IBukrs>");
		soap.append("            <!--Zero or more repetitions:-->");
		soap.append("            <item>");
		soap.append("               <Sign>I</Sign>");
		soap.append("               <Option>BT</Option>");
		soap.append("               <Low>0001</Low>");
		soap.append("               <High>2300</High>");
		soap.append("            </item>");
		soap.append("         </IBukrs>");
		soap.append("       <OTab>");
		soap.append("         </OTab>");
		soap.append("      </urn:ZfiFkdjk>");
		soap.append("   </soapenv:Body>");
		soap.append("</soapenv:Envelope>");

		return soap.toString();
	}
}