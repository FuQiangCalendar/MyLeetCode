package org.example.structure.chatgpt;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
public class ProxyFactory {
    public static <T> T create(Class<T> clazz) {
        if (clazz.isInterface()) {
            return createJdkProxy(clazz);
        } else {
            return createCglibProxy(clazz);
        }
    }
    @SuppressWarnings("unchecked")
    private static <T> T createJdkProxy(Class<T> clazz) {
        InvocationHandler handler = new JdkProxyHandler();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, handler);
    }
    private static <T> T createCglibProxy(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new CglibProxyInterceptor());
        return (T) enhancer.create();
    }
    private static class JdkProxyHandler implements InvocationHandler {
        /*private Object target;//反射代理目标类(被代理，解耦的目标类)

        public JdkProxyHandler(Object target) {
            this.target = target;
        }*/

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("JDK动态代理前置逻辑");
            Object result = method.invoke(null, args);
            System.out.println("JDK动态代理后置逻辑");
            return result;
        }
    }
    private static class CglibProxyInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("Cglib动态代理前置逻辑");
            Object result = proxy.invokeSuper(obj, args);
            System.out.println("Cglib动态代理后置逻辑");
            return result;
        }
    }
}

interface HelloService {
    String sayHello(String... args);
}

class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String... args) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        if (args != null || args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                sb.append(" ").append(args[i]);
            }
        }
        return sb.toString();
    }
}

class Hello {
    public String sayHello(String... args) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        if (args != null || args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                sb.append(" ").append(args[i]);
            }
        }
        return sb.toString();
    }
}

class TestProxyFactory {
    public static void main(String[] args) {
        Hello hello = ProxyFactory.create(Hello.class);
        String s = hello.sayHello("world");
        System.out.println(s);

        HelloService helloService = ProxyFactory.create(HelloService.class);
        String str = helloService.sayHello("小强");
        System.out.println(str);
    }
}