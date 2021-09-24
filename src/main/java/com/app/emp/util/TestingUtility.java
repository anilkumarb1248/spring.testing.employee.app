package com.app.emp.util;

import org.springframework.stereotype.Component;

@Component
public class TestingUtility {

	private int privateMethod(int a, int b) {
		return a + b;
	}

	public static String noArgStaticMethod() {
		return "Hello Anil";
	}

	public static int argStaticMethod(int a, int b) {
		return a + b;
	}

	public void voidMethod(boolean flag) {
		if (flag) {
			System.out.println("Need to perform some task");
		} else {
			throw new RuntimeException("No Work");
		}
	}

}
