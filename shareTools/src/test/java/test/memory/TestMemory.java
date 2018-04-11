package test.memory;

import java.io.IOException;

import org.junit.Test;

import zj.memory.util.MemoryUtil;

public class TestMemory {
	public static void main(String[] args) {
		String s = MemoryUtil.getMemoryByMB();
		System.out.println(s);
	}
	@Test
	public void execProcess(){
		try {
			String command = "taskkill /f /im eDiary.exe";
			command = "taskkill /f /im chrome.exe";  
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
