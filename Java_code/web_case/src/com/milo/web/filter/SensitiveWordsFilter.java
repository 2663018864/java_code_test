package com.milo.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

@WebFilter("/*")
public class SensitiveWordsFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //创建代理对象
        ServletRequest request_proxy = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //判断执行的方法
                if ("getParameter".equals(method.getName())) {
                    //获取返回值
                    String value = (String) method.invoke(req, args);
                    //增强返回值
                    if (value != null) {
                        for (String str : list) {
                            if (value.contains(str)) {
                                value = value.replaceAll(str, "***");
                            }
                        }
                    }
                    return value;
                }

                if ("getParameterMap".equals(method.getName())) {
                    //获取返回值
                    Map<String, String[]> map = (Map<String, String[]>) method.invoke(req, args);
                    Map<String, String[]> newMap = new HashMap<>();
                    //增强返回值
                    if (map != null && map.size() > 0) {
                        Set<String> keySet = map.keySet();
                        for (String key : keySet) {
                            String[] values = map.get(key);
                            if (values != null && values.length > 0) {
                                for (int i = 0; i < values.length; i++) {
                                    for (String str : list) {
                                        if (values[i].contains(str)) {
                                            values[i] = values[i].replaceAll(str, "***");
                                        }
                                    }
                                }
                            }
                            newMap.put(key, values);
                        }
                    }
                    return newMap;
                }

                if ("getParameterValues".equals(method.getName())) {
                    //获取返回值
                    String[] values = (String[]) method.invoke(req, args);
                    if (values != null && values.length > 0) {
                        for (int i = 0; i < values.length; i++) {
                            for (String str : list) {
                                if (values[i].contains(str)) {
                                    values[i] = values[i].replaceAll(str, "***");
                                }
                            }
                        }
                    }
                    return values;
                }
                return method.invoke(req, args);
            }
        });
        chain.doFilter(request_proxy, resp);
    }

    public void destroy() {
    }

    //创建保存敏感字符的集合
    List<String> list = new ArrayList<>();
    public void init(FilterConfig config) throws ServletException {
        try {
            //获取文件的真实路径
            String realPath = config.getServletContext().getRealPath("/WEB-INF/classes/敏感词汇.txt");
            //创建流对象读取文件
            BufferedReader br = new BufferedReader(new FileReader(realPath));
            String line = null;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
