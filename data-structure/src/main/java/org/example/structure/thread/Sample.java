package org.example.structure.thread;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class Sample {
	public static void main(String[] args){
		String str = new String("AmosH");
		ReferenceQueue rq = new ReferenceQueue();
		//创建一个引用队列
		PhantomReference pr = new PhantomReference(str,rq);
		//创建一个虚引用，并且将该引用和rq引用队列关联
		str = null;
		//切断"AmosH"字符串的引用
		System.out.println(pr.get());
		//output null，因为系统无法通过虚引用的get()方法获取被引用对象
		System.gc();
		System.runFinalization();
		//强制垃圾回收 此时rq.poll() 得到就是pr；即垃圾回收会将虚引用移入ReferenceQueue
		System.out.println(rq.poll() == pr);
		//output true
		//虚引用被回收
	}
}
