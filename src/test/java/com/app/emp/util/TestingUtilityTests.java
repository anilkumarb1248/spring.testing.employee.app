package com.app.emp.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TestingUtilityTests {

	@InjectMocks
	TestingUtility testingUtility;

	@Test
	void testPrivateMethod() {
		int sum = ReflectionTestUtils.invokeMethod(testingUtility, "privateMethod", 10, 20);
		Assertions.assertThat(sum).isEqualTo(30);
	}

	@Test
	void testNoArgStatic() {
		Assertions.assertThat(TestingUtility.noArgStaticMethod()).isEqualTo("Hello Anil");
		try (MockedStatic<TestingUtility> staticUtilities = Mockito.mockStatic(TestingUtility.class)) {
			staticUtilities.when(TestingUtility::noArgStaticMethod).thenReturn("Eugen");
			Assertions.assertThat(TestingUtility.noArgStaticMethod()).isEqualTo("Eugen");
		}
		Assertions.assertThat(TestingUtility.noArgStaticMethod()).isEqualTo("Hello Anil");
	}

	@Test
	void testSomeArgStatic() {
		Assertions.assertThat(TestingUtility.argStaticMethod(10, 20)).isEqualTo(30);
		try (MockedStatic<TestingUtility> staticUtilities = Mockito.mockStatic(TestingUtility.class)) {
			staticUtilities.when(() -> TestingUtility.argStaticMethod(10, 20)).thenReturn(30);
			Assertions.assertThat(TestingUtility.argStaticMethod(10, 20)).isEqualTo(30);
		}
		Assertions.assertThat(TestingUtility.argStaticMethod(20, 30)).isEqualTo(50);
	}

	@Test
	void testVoidMethod() {
		TestingUtility utility = Mockito.mock(TestingUtility.class);
		Mockito.doNothing().when(utility).voidMethod(true);
		utility.voidMethod(true);

		Mockito.verify(utility, Mockito.times(1)).voidMethod(true);
	}
	
//	@Test(expected = RuntimeException.class) // it is removed in JUnit 5
	@Test
	void testVoidMethodForExcepiton() {
		TestingUtility utility = Mockito.mock(TestingUtility.class);
		Mockito.doThrow(RuntimeException.class).when(utility).voidMethod(false);
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
			utility.voidMethod(false);
		  });
	}

}
