package com.cn.zklock;
/**
 * 自定义分布式锁
 * @author Administrator
 *
 */
public interface SelfLock {
	    /**
	     * 获取到锁的资源
	     */
		public void getLock();
	    /**
	     * 释放锁
	     */
		public void unLock();
		/**
		 * 创建锁是否成功
		 * @return
		 */
		public boolean tryLock();
		/**
		 *  等待
		 */
		public void waitLock();
}
