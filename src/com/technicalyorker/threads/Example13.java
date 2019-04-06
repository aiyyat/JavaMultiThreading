package com.technicalyorker.threads;

import java.util.LinkedList;
import java.util.List;

public class Example13 {
	List<Double> i = new LinkedList<Double>();
	Object lock = new Object();

	public static void main(String[] args) {
		new Example13().perform();
	}

	private void perform() {
		Thread producer = new Thread() {
			@Override
			public void run() {
				while (true)
					add();
			}
		};
		Thread consumer = new Thread() {
			@Override
			public void run() {
				while (true)
					remove();
			}
		};
		producer.start();
		consumer.start();
		try {
			producer.join();
			consumer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void add() {
		try {
			synchronized (lock) {
				if (i.size() == 10) {
					lock.wait();
				} else {
					i.add(Math.random());
					System.out.println("After Add Size: " + i.size());
					lock.notify();
				}
			}
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void remove() {
		try {
			synchronized (lock) {
				if (i.size() == 0) {
					lock.wait();
				} else {
					i.remove(0);
					System.out.println("After Remove Size: " + i.size());
				}
				lock.notify();
			}
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}