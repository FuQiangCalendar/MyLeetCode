package org.example.structure.chatgpt;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Service {
    void execute();

    String hello(String name);
}
class ServiceImpl implements Service {
    @Override
    public void execute() {
        System.out.println("执行Service的execute方法");
    }

    @Override
    public String hello(String name) {
        return "hello " + (StringUtils.isNotBlank(name) ? name : "");
    }
}
interface ServiceFactory {
    Service create();
}
class ServiceFactoryImpl implements ServiceFactory {
    @Override
    public Service create() {
        return new ServiceImpl();
    }
}

class MyServiceFactoryImpl extends ServiceFactoryImpl {
}

class ServiceProxyFactory implements ServiceFactory {
    private final ServiceFactory delegate;
    public ServiceProxyFactory(ServiceFactory delegate) {
        this.delegate = delegate;
    }
    @Override
    public Service create() {
        return (Service) Proxy.newProxyInstance(
                ServiceProxyFactory.class.getClassLoader(),
                new Class<?>[]{Service.class},
                (proxy, method, args) -> {
                    System.out.println("执行Service方法前的代理逻辑");
                    Service service = delegate.create();
                    Object invoke = method.invoke(service, args);
                    System.out.println("执行Service方法后的代理逻辑");
                    return invoke;
                }
        );
    }
}

class ServiceCglibProxyFactory implements ServiceFactory {
    private final ServiceFactory delegate;
    public ServiceCglibProxyFactory(ServiceFactory delegate) {
        this.delegate = delegate;
    }
    @Override
    public Service create() {
        Enhancer enhancer = new Enhancer();
        Class<?> aClass = delegate.getClass();

        List<Class<?>> allInterfaces = new ArrayList<>();
        while (aClass != null) {
            Class<?>[] interfaces = aClass.getInterfaces();
            allInterfaces.addAll(Arrays.asList(interfaces));
            aClass = aClass.getSuperclass();
        }
        boolean anInterface = false;
        if (null != allInterfaces && allInterfaces.size() > 0) {
            anInterface = true;
        }
        System.out.println("代理对象为接口is " + anInterface);
        enhancer.setSuperclass(Service.class);
        enhancer.setCallback(new MethodInterceptorImpl(delegate.create()));
        return (Service) enhancer.create();
    }
    private static class MethodInterceptorImpl implements MethodInterceptor {
        private final Service delegate;
        public MethodInterceptorImpl(Service delegate) {
            this.delegate = delegate;
        }
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("Cglib执行Service方法前的代理逻辑");
            Object invoke = methodProxy.invoke(delegate, objects);
            System.out.println("Cglib执行Service方法后的代理逻辑");
            return invoke;
        }
    }
}

class factoryMain {
    public static void main(String[] args) {
        ServiceFactory serviceFactory = new ServiceProxyFactory(new ServiceFactoryImpl());
        Service service = serviceFactory.create();
        service.execute();
        String world = service.hello("world");
        System.out.println(world);

        ServiceCglibProxyFactory serviceCglibProxyFactory = new ServiceCglibProxyFactory(new MyServiceFactoryImpl());
        Service service1 = serviceCglibProxyFactory.create();
        service1.execute();
        String xiao_qiang = service1.hello("xiao qiang");
        System.out.println(xiao_qiang);
    }
}