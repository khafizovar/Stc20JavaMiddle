package part1.lesson12.task2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author KhafizovAR on 27.11.2019.
 * @project Stc20JavaMiddle
 */
public class SomeInterfaceInvocationHandler implements InvocationHandler {
    public SomeClass aMyClassObj;

    public SomeInterfaceInvocationHandler(Object impl){
        this.aMyClassObj = (SomeClass) impl;
    }

    @Override
    public Object invoke(Object invocationProxy, Method method, Object[] arguments) throws Throwable {
        return method.invoke(aMyClassObj, arguments);
    }
}