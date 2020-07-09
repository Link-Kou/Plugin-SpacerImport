package com.linkkou.spacerimport.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class SpaceNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("spaceprocessor", new SpaceBeanDefinitionParser());
    }

}
