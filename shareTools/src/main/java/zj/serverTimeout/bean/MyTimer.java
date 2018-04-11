package zj.serverTimeout.bean;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

/**
 * 用于程序超时处理
 * 
 * @author zhangjun
 * 
 */
public class MyTimer extends Thread {
	private transient static final Logger log = Logger.getLogger(MyTimer.class);
	private InArgs_MyTimer inargs_mytimer;
	private MyRunner myRunner; // 主程序
	private TimerI timerI;

	public MyTimer() {

	}

	public MyTimer(InArgs_MyTimer inargs_mytimer, MyRunner myRunner, TimerI timerI) {
		this.inargs_mytimer = inargs_mytimer;
		this.myRunner = myRunner;
		myRunner.getOutargs().setNeedRequestTime(inargs_mytimer.getNeedRequestTime());
		this.timerI = timerI;
	}

	public void run() {
		final Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (timerI != null)
					timerI.time(myRunner);
				log.debug("现在过去的时间为【" + myRunner.getOutargs().getUseredRequestTime() + "】毫秒");
				if (myRunner.getOutargs().isComplete()) {
					log.debug("程序在【" + myRunner.getOutargs().getUseredRequestTime() + "】毫秒后执行完毕");
					myRunner.getOutargs().setPrograme(true);
					timer.cancel();
				}
				if (myRunner.getOutargs().getUseredRequestTime() >= inargs_mytimer.getNeedRequestTime()) {
					/*
					 * Class[] clazzs = null; if (myRunner.getInargs_myrunner().getBreakArgs() != null && myRunner.getInargs_myrunner().getBreakArgs().length > 0) { clazzs = new Class[myRunner.getInargs_myrunner() .getBreakArgs().length]; // 得到参数类型 for (int i = 0, len = myRunner.getInargs_myrunner() .getBreakArgs().length; i < len; i++) { clazzs[i] = myRunner.getInargs_myrunner() .getBreakArgs().getClass(); } } try { Method method = myRunner .getInargs_myrunner() .getClassNewInstance() .getClass() .getDeclaredMethod( myRunner.getInargs_myrunner() .getBreakMethodname(), clazzs); method.invoke(myRunner.getInargs_myrunner() .getClassNewInstance(), myRunner .getInargs_myrunner().getBreakArgs()); } catch (Exception e) { e.printStackTrace(); }
					 */
					log.debug("程序在" + myRunner.getOutargs().getUseredRequestTime() + "毫秒后,因为执行超时被强制停止");
					myRunner.getOutargs().setPrograme(true);
					timer.cancel();
				}
				myRunner.getOutargs().setUseredRequestTime(myRunner.getOutargs().getUseredRequestTime() + 1000);
			}
		}, 0, 1000);
	}

	public MyRunner getRunner() {
		return myRunner;
	}

	public void setRunner(MyRunner myRunner) {
		this.myRunner = myRunner;
	}

	public InArgs_MyTimer getInargs_mytimer() {
		return inargs_mytimer;
	}

	public void setInargs_mytimer(InArgs_MyTimer inargs_mytimer) {
		this.inargs_mytimer = inargs_mytimer;
	}

	public MyRunner getMyRunner() {
		return myRunner;
	}

	public void setMyRunner(MyRunner myRunner) {
		this.myRunner = myRunner;
	}

}
