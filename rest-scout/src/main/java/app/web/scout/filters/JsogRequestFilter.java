package app.web.scout.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import app.web.scout.util.XSSUtils;
import lombok.extern.java.Log;

@Component
@Order(1)
@Log
public class JsogRequestFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
        log.info( "Request URL : " + req.getRequestURI());
        log.info( "Request body : " + req);
  
        chain.doFilter(request, response);
        
        log.info( "Committing a transaction for req : {}" + req.getRequestURI());

        
        JsogRequestWrapper wrappedRequest = new JsogRequestWrapper(
                (HttpServletRequest) request);

        String body = IOUtils.toString(wrappedRequest.getReader());


        if(!"".equals(body)) {
            JSONObject oldJsonObject = new JSONObject(body);
            JSONObject newJsonObject = new JSONObject();

            for(Object key : oldJsonObject.keySet()) {
                newJsonObject.put(key.toString(), XSSUtils.stripXSS(oldJsonObject.get(key.toString()).toString()));
            }
            wrappedRequest.resetInputStream(newJsonObject.toString().getBytes());

        }


        chain.doFilter(wrappedRequest, response);
        
	}

	@Override
	public void destroy() {
		
	}

}
