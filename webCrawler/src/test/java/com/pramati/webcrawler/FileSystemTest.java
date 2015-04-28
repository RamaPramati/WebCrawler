package com.pramati.webcrawler;

import java.net.URL;
import org.junit.*;

public class FileSystemTest {

	@Test
	public void runTest()
		throws Exception {
		FileSystem fixture = new FileSystem(new URL(""));
		fixture.run();
	}

	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(FileSystemTest.class);
	}
}