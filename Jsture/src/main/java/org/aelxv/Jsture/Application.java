package org.aelxv.Jsture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
		// 获取url与类和方法的对应信息
		Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
		List<String> urlList = new ArrayList<>();
		for (RequestMappingInfo info : map.keySet()) {
			// 获取url的Set集合，一个方法可能对应多个url
			Set<String> patterns = info.getPatternsCondition().getPatterns();
			for (String url : patterns) {
				urlList.add(url);
				System.out.println("URL ： " + url);
			}
		}
	}
}
