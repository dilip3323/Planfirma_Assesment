package com.qa.planfirmatechnologies;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomListner extends BaseClass implements ITestListener {
	
	
	
	@Override
	public void onTestFailure(ITestResult result) {
		//[-System.out.println("Test has failed !!");
		try {
			failed(result.getMethod().getMethodName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public  void failed(String TestMethodName) throws IOException {
		// TODO Auto-generated method stub
		
		File source = sc.getScreenshotAs(OutputType.FILE);
		long yourmilliseconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
		Date resultdate = new Date(yourmilliseconds);
		System.out.println();
		File target = new File("build/screenshots/"+TestMethodName+"_"+sdf.format(resultdate)+".png");
		FileUtils.copyFile(source,target);
		
	}
	

	
}
