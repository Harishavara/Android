import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import java.util.Map.Entry;

public class HitServlet extends HttpServlet {
	private int mCount;
	static final String[] MOBILE_SPECIFIC_SUBSTRING = {
      "iPhone","Android","MIDP","Opera Mobi",
      "Opera Mini","BlackBerry","HP iPAQ","IEMobile",
      "MSIEMobile","Windows Phone","HTC","LG",
      "MOT","Nokia","Symbian","Fennec",
      "Maemo","Tear","Midori","armv",
      "Windows CE","WindowsCE","Smartphone","240x320",
      "176x220","320x320","160x160","webOS",
      "Palm","Sagem","Samsung","SGH",
      "SIE","SonyEricsson","MMP","UCWEB"};
  
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
			if (checkMobile(request, response))
			{
				buildMobileSiteView(request, response);
			} 
			else 
			{
				buildRegularSiteView(request, response);
			}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
			executePost(request, response);
	}
	
	private boolean checkMobile(HttpServletRequest request, HttpServletResponse response) {
		String userAgent = request.getHeader("user-agent");
		for (String mobile: MOBILE_SPECIFIC_SUBSTRING){
			if (userAgent.contains(mobile)
				|| userAgent.contains(mobile.toUpperCase())
				|| userAgent.contains(mobile.toLowerCase())){
					return true;
			}
		}
		return false;
	}
	
	public void buildMobileSiteView(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			response.setContentType("text/html;charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			
			out.println("Request Method = " + request.getMethod());
			out.println("Request Method = " + request.getProtocol());
			
			Enumeration headerNames = request.getHeaderNames();
			
			while(headerNames.hasMoreElements()) {
				String headerName = (String)headerNames.nextElement();
				String capitalizeHeaderName = headerName.substring(0,1).toUpperCase() + headerName.substring(1);
				
				out.println(capitalizeHeaderName + " = " + request.getHeader(headerName));
			}
			
			String authHeader = request.getHeader("authorization");
			String encodedValue = authHeader.split(" ")[1];
			
			out.println("Base62-Encode Value = " + encodedValue);
	}
	
	public void buildRegularSiteView(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			response.setContentType("text/html;charset=UTF-8");
		
			PrintWriter out = response.getWriter();
		
			String title = "Servlet Example: Showing Request Headers";
			out.println("<TABLE BORDER=1 ALIGN=CENTER>\n" +
						"<TR BGCOLOR=\"#FFAD00\">\n" +
						"<TH>Header Name</TH><TH>Header Value</TH>");
			out.println("<TR>");
			out.println("<TD>" + "Request Method" + "</TD>");
			out.println("<TD>" + request.getMethod() + "</TD>");
			out.println("</TR>");
			out.println("<TR>");
			out.println("<TD>" + "Request Protocol" + "</TD>");
			out.println("<TD>" + request.getProtocol() + "</TD>");
			out.println("</TR>");
			
			Enumeration headerNames = request.getHeaderNames();
			
			while(headerNames.hasMoreElements()) {
				String headerName = (String)headerNames.nextElement();
				String capitalizeHeaderName = headerName.substring(0,1).toUpperCase() + headerName.substring(1);
				
				out.println("<TR>");
				out.println("<TD>" + capitalizeHeaderName + "</TD>");
				out.println("<TD>" + request.getHeader(headerName)+ "</TD>");
				out.println("</TR>");
			}
			
		String authHeader = request.getHeader("authorization");
		String encodedValue = authHeader.split(" ")[1];
		out.println("<TR>");
		out.println("<TD>" + "Base62-Encode Value" + "</TD>");
		out.println("<TD>" + encodedValue + "</TD>");
		out.println("</TABLE>\n</BODY></HTML>");
	}
	
	public void executePost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		Map<String, String[]> map = request.getParameterMap();
		
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		out.print("<html><body>");
		out.print("<h1> Your Order...</h1>");
		out.println("<table border=\"1\" cellpadding = \"5\"" +
				" cellspacing = \"5\">");
		out.println("<tr> <th>Parameter Name</th>" +
				"<th>Parameter Value</th></tr>");
		Set set = map.entrySet();
		Iterator it = set.iterator();
		
		while (it.hasNext()) {
			Map.Entry<String, String[]> entry =
				(Entry<String, String[]>) it.next();
			String paramName = entry.getKey();
			out.print("<tr><td>" + paramName + "</td><td>");
			String[] paramValues = entry.getValue();
			if (paramValues.length == 1)
			{
				String paramValue = paramValues[0];
				if (paramValue.length() == 0)
					out.println("<b>No Value</b>");
				else
					out.println(paramValue);
			} 
			else 
			{
				out.println("<ul>");
				for (int i = 0; i < paramValues.length; i++) {
					out.println("<li>" + paramValues[i] + "</li>");
				}
				out.println("</ul>");
			}
			out.print("</td></tr>");
		}
		out.println("</table></body></html>");
	}
}
