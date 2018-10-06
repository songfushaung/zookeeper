package com.cn.order;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.cn.utils.OrderNumGenerator;
import com.cn.zklock.SelfLock;
import com.cn.zklock.ZookeeperLock;
/***
 * 基本锁实现数据一致性
 * @author Administrator
 *
 */
public class OrderService implements Runnable{
	OrderNumGenerator gender=new OrderNumGenerator();
	//private Lock lock=new ReentrantLock();
	SelfLock lock=new ZookeeperLock();
	@Override
	public void run() {
      /*    synchronized (this) {
			//模拟用户生成订单号
			String number=gender.getNumber();
			System.out.println(Thread.currentThread().getName()+"########生成唯一订单号:"+number);
		}*/
		try {
			lock.getLock();
			String number=gender.getNumber();
			System.out.println(Thread.currentThread().getName()+"########生成唯一订单号:"+number);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			lock.unLock();
		}
	}
	/**
	 * 多线程生成订单号
	 * @param args
	 */
	public static void main(String[] args) {
		OrderService order=new OrderService();
		for(int i=0;i<1;i++){
			new Thread(new OrderService()).start();
		}
	}

}
