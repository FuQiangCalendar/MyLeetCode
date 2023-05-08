package org.example.structure.chatgpt;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
public class DymicProxyFactory {
    /**
     * 创建代理对象
     * @param target 目标对象
     * @return 代理对象
     */
    public static Object createProxy(Object target) {
        Class<?> targetClass = target.getClass();
        // 如果目标对象实现了接口，则使用JDK动态代理
        if (targetClass.getInterfaces().length > 0) {
            return createJdkProxy(target);
        }
        // 否则，使用Cglib动态代理
        return createCglibProxy(target);
    }
    /**
     * 创建JDK动态代理
     * @param target 目标对象
     * @return 代理对象
     */
    private static Object createJdkProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new JdkInvocationHandler(target));
    }
    /**
     * 创建Cglib动态代理
     * @param target 目标对象
     * @return 代理对象
     */
    private static Object createCglibProxy(Object target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibMethodInterceptor(target));
        return enhancer.create();
    }
    /**
     * JDK动态代理InvocationHandler实现类
     */
    private static class JdkInvocationHandler implements InvocationHandler {
        // 目标对象
        private final Object target;
        public JdkInvocationHandler(Object target) {
            this.target = target;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 调用目标对象的方法
            return method.invoke(target, args);
        }
    }
    /**
     * Cglib动态代理MethodInterceptor实现类
     */
    private static class CglibMethodInterceptor implements MethodInterceptor {
        // 目标对象
        private final Object target;
        public CglibMethodInterceptor(Object target) {
            this.target = target;
        }
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            // 调用目标对象的方法
            return method.invoke(target, args);
        }
    }
    // 测试方法
    public static void main(String[] args) {
        // 代理接口
        UserService userService = new UserServiceImpl();
        UserService proxy1 = (UserService) DymicProxyFactory.createProxy(userService);
        proxy1.sayHello(); // 输出：Hello, World!

        HelloWorld helloWorld = (HelloWorld) DymicProxyFactory.createProxy(new HelloWorld());
        String xiaoq = helloWorld.sayHello("小强");
        System.out.println(xiaoq);
    }
}

interface UserService {
    void sayHello();
}

class UserServiceImpl implements UserService {

    @Override
    public void sayHello() {
        System.out.println("Hello, World!");
    }
}

class HelloWorld  {
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