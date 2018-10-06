package com.cn.zklock;

import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

//将重复代码写入子类中
public  class ZookeeperLock implements SelfLock {
	// zk连接地址
	public static final String CONNECTSTRING = "127.0.0.1:2181";
	// 创建zk客户端连接
	public ZkClient zkClient = new ZkClient(CONNECTSTRING);
	//创建的节点
	public static final String PATH = "/lock";
	//信号量,阻塞程序执行,
	public CountDownLatch countDownLatch = null;
	    /**
	     * 获取到锁的资源
	     */
		public void getLock() {
			if (tryLock()) {
				System.out.println("###获取锁成功#####");
			} else {
				//创建相同的临时节点 如果临时节点存在 就等待
				// 等待
				waitLock();
				// 重新获取锁
				getLock();
			}
		}

		/**
		 * 创建锁是否成功
		 * @return
		 */
		public boolean tryLock() {
			try {
				zkClient.createEphemeral(PATH);
				//false试一试a
				return true;
			} catch (Exception e) {
				//创建相同的临时节点 如果临时节点存在 就是失败 只能等待
				return false;
			}
		}
	
		/**
		 *  等待
		 */
		public void waitLock() {
			// 使用事件监听，获取到节点被删除，
			IZkDataListener iZkDataListener = new IZkDataListener() {
				// 当节点被删除
				public void handleDataDeleted(String dataPath) throws Exception {
					if (countDownLatch != null) {
						// 唤醒
						countDownLatch.countDown();
					}

				}

				// 当节点发生改变
				public void handleDataChange(String dataPath, Object data) throws Exception {

				}
			};

			// 监听节点信息
			zkClient.subscribeDataChanges(PATH, iZkDataListener);
			if (zkClient.exists(PATH)) {
				// 创建信号量
				countDownLatch = new CountDownLatch(1);
				try {
					// 等待
					countDownLatch.await();
				} catch (Exception e) {

				}

			}
			// 删除事件监听
			zkClient.unsubscribeDataChanges(PATH, iZkDataListener);
		}

		 /**
	     * 释放锁
	     */
		public void unLock() {
			if (zkClient != null) {
				zkClient.close();
			  System.out.println("释放锁资源");
			}
		}
}