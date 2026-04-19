package com.tejastomar.journalapp;

import com.tejastomar.journalapp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class   JournalAppApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Disabled
	@Test
	public void testFindByUserName(){
//		assertEquals(4,2+1);
		assertNotNull(userRepository.findByUserName("ram"));
	}

	@ParameterizedTest
	@CsvSource({
			"ram",
			"shyam",
			"Tejas"
	})
	public void testFindByUserName(String name){
		assertNotNull(userRepository.findByUserName(name),"failed for: "+name);
	}

	@ParameterizedTest
	@CsvSource({
			"1,2,3",
			"2,10,12",
			"3,3,9"
	})
	public void test(int a,int b,int expected){
		assertEquals(expected,a+b);
	}
}
