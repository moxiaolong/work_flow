package com.zjx.workflow.config;

import com.zjx.workflow.entity.node.Node;
import com.zjx.workflow.enums.NodeType;
import com.zjx.workflow.handler.BranchHandler;
import com.zjx.workflow.handler.DefaultHandler;
import com.zjx.workflow.handler.FinishHandler;
import com.zjx.workflow.handler.Handler;
import com.zjx.workflow.handler.business.BusinessHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.ContextLoader;

/**
 * 处理程序库
 *
 * @author cgn
 * @date 2021/06/17
 */
@Component
public class HandlerRepository implements ApplicationContextAware {
    static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static Handler getHandler(Node node) {
        NodeType type = node.getType();
        String businessHandlerClassName = node.getBusinessHandlerClassName();
        if (type == null) {
            return applicationContext.getBean(DefaultHandler.class);
        }
        switch (type) {
            case BUSINESS:
                try {
                    Class<?> forName = Class.forName(businessHandlerClassName);
                    Assert.isTrue(BusinessHandler.class.isAssignableFrom(forName), "请传入BusinessHandler子类");
                    String[] split = businessHandlerClassName.split("\\.");
                    String name = split[split.length - 1];
                    char[] chars = name.toCharArray();
                    //转小写
                    chars[0] += 32;
                    return (BusinessHandler) applicationContext.getBean(String.valueOf(chars), forName);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();

                }
            case BRANCH:
                return applicationContext.getBean(BranchHandler.class);
            case END:
                return applicationContext.getBean(FinishHandler.class);
            default:
                return applicationContext.getBean(DefaultHandler.class);
        }
    }
}
