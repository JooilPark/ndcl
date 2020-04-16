package javautils;

import java.io.File;


public class textparser {

	
	public static void main(String[] args) {
		parser mParser = new parser();
		String rootPath = System.getProperty("user.dir");
		File allAddrDb = new File(rootPath, "allAddrDb");

		for (File f : allAddrDb.listFiles()) {
			if (f.getName().contains("build_")) {
				
				mParser.LineParser(f);
				break;
			
			}

		}

		

	}
	
	

	

}
