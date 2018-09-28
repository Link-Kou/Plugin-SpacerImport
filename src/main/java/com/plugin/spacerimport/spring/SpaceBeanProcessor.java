package com.plugin.spacerimport.spring;

import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import com.plugin.spacerimport.SpacerPipe;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Bean @Autowire 具体实现类
 * 通过Spring的配置注入相关的内容
 * <p>
 *      <bean class="com.SpaceBeanProcessor">
 * 			<property name="prefix" value="com.rpc" />
 * 		</bean>
 * </p>
 *
 * Created by LK on 2017/4/23.
 */
/*@Configuration*/
public class SpaceBeanProcessor extends SpaceSpringContextUtil implements BeanDefinitionRegistryPostProcessor {

	/**
	 * 工程路径
	 */
	private String[] prefix;

	/**
	 * 工程路径
	 * @param prefix 字符串 com.rpc
	 */
	public void setPrefix(String[] prefix) {
		this.prefix = prefix;
	}

	/**
	 * 实现Bean的注入到Spring容器中
	 * @param registry
	 * @throws BeansException
	 */
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		//谷歌元反射
		Reflections reflections = new Reflections(prefix);
		//获取到所有的带有RPCClientFields注解的接口
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(SpacerPipe.class);
		for (Class<?> serviceClass : annotated) {
			for (Annotation annotation : serviceClass.getAnnotations()) {
				//自定义注解RetrofitService，都需要通过retrofit创建bean
				if (annotation instanceof SpacerPipe) {
					RootBeanDefinition beanDefinition = new RootBeanDefinition();
					//注入代理
					beanDefinition.setBeanClass(SpaceProxyObjects.class);
					//是否惰性加载
					beanDefinition.setLazyInit(false);
					//是否自动注入
					beanDefinition.setAutowireCandidate(true);
					/**
					 * 设置bean里变量
					 * {@link SpaceProxyObjects#setServiceClass}
					 */
					beanDefinition.getPropertyValues().addPropertyValue("serviceClass", serviceClass);
					/**
					 * serviceClass.getName() 获取Class命名空间路径
					 * 保证在一个类中注入多次，能够完全独立开放
					 */
					registry.registerBeanDefinition(serviceClass.getName(), beanDefinition);

				}
			}
		}

	}

	/**
	 * 调用就会触发
	 * 这里提供了修改beanFacotry的机会
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

	}


}
