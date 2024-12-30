package com.utility;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class RobotClass {
	Robot r;
public void handleKeyPress() throws InterruptedException, AWTException {
	 r=new Robot();
	   r.keyPress(KeyEvent.VK_ENTER);
	    r.keyRelease(KeyEvent.VK_ENTER);
	    Thread.sleep(2000);		 
	}
public void handleTabPress() throws InterruptedException, AWTException {
 r=new Robot();
	   r.keyPress(KeyEvent.VK_TAB);
	  // r.keyRelease(KeyEvent.VK_TAB);
	   // Thread.sleep(2000);		 
	}
}
