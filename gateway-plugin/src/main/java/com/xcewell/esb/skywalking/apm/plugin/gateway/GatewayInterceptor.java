package com.xcewell.esb.skywalking.apm.plugin.gateway;

import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import org.springframework.web.server.ServerWebExchange;

import java.lang.reflect.Method;
import java.net.URI;

/**
 * @author tyfann
 * @date 2021/2/3 4:20 下午
 */
public class GatewayInterceptor implements InstanceMethodsAroundInterceptor {
    public void beforeMethod(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes,
                             MethodInterceptResult methodInterceptResult) throws Throwable {
        ServerWebExchange exchange = (ServerWebExchange) objects[0];


        URI uri = exchange.getRequest().getURI();

        AbstractSpan span;

        final String host = uri.getHost();
        final int port = uri.getPort();
    }

    public Object afterMethod(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, Object o) throws Throwable {
        return null;
    }

    public void handleMethodException(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, Throwable throwable) {

    }
}
