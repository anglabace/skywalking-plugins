package com.xcewell.esb.skywalking.apm.plugin.gateway;

import org.apache.skywalking.apm.agent.core.context.CarrierItem;
import org.apache.skywalking.apm.agent.core.context.ContextCarrier;
import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.context.tag.StringTag;
import org.apache.skywalking.apm.agent.core.context.tag.Tags;
import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;
import org.apache.skywalking.apm.agent.core.context.trace.SpanLayer;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import org.apache.skywalking.apm.network.trace.component.ComponentsDefine;
import org.apache.skywalking.apm.plugin.spring.mvc.commons.ReactiveRequestHolder;
import org.apache.skywalking.apm.plugin.spring.mvc.commons.ReactiveResponseHolder;
import org.springframework.web.server.ServerWebExchange;

import java.lang.reflect.Method;
import java.net.URI;

import static org.apache.skywalking.apm.plugin.spring.mvc.commons.Constants.REQUEST_KEY_IN_RUNTIME_CONTEXT;
import static org.apache.skywalking.apm.plugin.spring.mvc.commons.Constants.RESPONSE_KEY_IN_RUNTIME_CONTEXT;

/**
 * @author tyfann
 * @date 2021/2/3 4:20 下午
 */
public class GatewayInterceptor implements InstanceMethodsAroundInterceptor {
    public void beforeMethod(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes,
                             MethodInterceptResult methodInterceptResult) throws Throwable {
        ServerWebExchange exchange = (ServerWebExchange) objects[0];
        ContextCarrier contextCarrier = new ContextCarrier();

        System.out.println("服务感知成功!!!");

        CarrierItem next = contextCarrier.items();
//        while (next.hasNext()) {
//            next = next.next();
//            next.setHeadValue(request.getHeader(next.getHeadKey()));
//        }


//        ContextManager.getRuntimeContext()
//                .put(RESPONSE_KEY_IN_RUNTIME_CONTEXT, new ReactiveResponseHolder(exchange.getResponse()));
//        ContextManager.getRuntimeContext()
//                .put(REQUEST_KEY_IN_RUNTIME_CONTEXT, new ReactiveRequestHolder(exchange.getRequest()));


//        URI uri = exchange.getRequest().getURI();
//
//
        AbstractSpan span = ContextManager.createEntrySpan(exchange.getRequest().toString(),contextCarrier);
        Tags.URL.set(span, exchange.getRequest().toString());
        Tags.HTTP.HEADERS.set(span,exchange.getRequest().getHeaders().toString());
        SpanLayer.asHttp(span);
//
//        final String host = uri.getHost();
//        final int port = uri.getPort();

//        ContextCarrier contextCarrier = new ContextCarrier();
//        CarrierItem next = contextCarrier.items();
//        while (next.hasNext()){
//            next = next.next();
//        }
//        span = ContextManager;


//        System.out.println("项目启动!!!");
//        span.setComponent(ComponentsDefine.TOMCAT);
//        span.tag(new StringTag(1000,"params"),objects[0].toString());
//
//        span.setLayer(SpanLayer.CACHE);
    }

    public Object afterMethod(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, Object o) throws Throwable {
//        String retString = (String) o;
//        AbstractSpan span = ContextManager.activeSpan();
//
//        Tags.STATUS_CODE.set(span,"20000");
//        ContextManager.stopSpan();
//        return retString;
        return o;
    }

    public void handleMethodException(EnhancedInstance enhancedInstance, Method method, Object[] objects,
                                      Class<?>[] classes, Throwable throwable) {
        dealException(throwable);
    }

    private void dealException(Throwable throwable) {
        AbstractSpan span = ContextManager.activeSpan();
        span.log(throwable);
        span.errorOccurred();
    }
}
