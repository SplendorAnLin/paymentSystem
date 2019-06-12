package com.yl.cachecenter;

/**
 * Main
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
public class Main {

	private static Object num = 100;
	
	static class MyThread extends Thread {
		public int x = 0;

		public void  run() {
			
			synchronized(num){
				for(;x<5000;){
					if(String.valueOf(num).indexOf("_")>-1){
						System.out.println("AA");
						break;
					}
					x++;
				}
				num = x + "_A";
			}
			System.out.println("A_"+x);
		}
	}
	
	static class MyThread1 extends Thread {
		public int x = 50000001;

		public void run() {
			synchronized(num){
				for(;x>50000000;){
					if(String.valueOf(num).indexOf("_")>-1){
						System.out.println("BB");
						break;
					}
					x = x-100;
				}
				num = x + "_B";
			}
			System.out.println("B_"+x);
		}
	}
	
	public static class Test{
		/**
		 * @param args
		 */
		public static void main(String[] args) {
			Thread t1 = new MyThread();
			Thread t2 = new MyThread1();
			t1.start();
			t2.start();
			System.out.println(num);
			if(String.valueOf(num).indexOf("_")>-1){
				System.out.println("---------");
			}else{
				System.out.println("2345675432");
			}
		}
	}

}
