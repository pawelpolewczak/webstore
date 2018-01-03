package com.packt.webstore.interceptor;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AuditingInterceptor extends HandlerInterceptorAdapter {
    Logger logger = Logger.getLogger("auditLogger");
    private String user;
    private String productID;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object handler) throws Exception{
        if(request.getRequestURI().endsWith("products/add") && request.getMethod().equals("POST")){
            user = request.getRemoteUser();
            productID = request.getParameterValues("productID")[0];
        }
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception arg3) throws Exception{
        if(request.getRequestURI().endsWith("products/add") && response.getStatus() == 302){
            logger.info(String.format("Nowy produkt [%s] dodany przez %s dnia %s", productID, user, getCurrentTime()));
        }
    }

    private String getCurrentTime() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy 'o' hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return formatter.format(calendar.getTime());
    }
}
