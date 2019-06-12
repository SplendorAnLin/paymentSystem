package com.lefu.online.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * 同步队列单元测试
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public class ConcurrentLinkedQueueTest {
	private static final int constants = 1000;
	public static void main(String[] args) {
		final CountDownLatch startCountDownLatch = new CountDownLatch(1);
		final CountDownLatch endCountDownLatch = new CountDownLatch(constants);
		List<String> msgs = new ArrayList<String>();
		for (int i = 0; i < constants; i++) {
			msgs.add("第" + i);
		}
		try {
			System.out.println("放完了" + msgs.size());

			final Queue<String> messages = new ConcurrentLinkedQueue<String>(msgs);
			for (int i = 0; i < messages.size(); i++) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							startCountDownLatch.await();
							System.out.println("开始消费啦：" + messages.poll());
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							endCountDownLatch.countDown();
						}
					}

				}).start();;
			}

			startCountDownLatch.countDown();
			endCountDownLatch.await();
			System.out.println("队列中剩余元素" + messages.size());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
