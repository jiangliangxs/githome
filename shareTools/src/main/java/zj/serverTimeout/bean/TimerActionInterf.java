package zj.serverTimeout.bean;
/**
 * 
 * @author zhangjun
 *
 */
public class TimerActionInterf {

	private InArgs_MyRunner inargs_myrunner;
	private InArgs_MyTimer inargs_mytimer;
	private OutArgs outargs;
	private boolean isNeedTimer; // 是否需要定时器
	public TimerActionInterf(InArgs_MyRunner inargs_myrunner,
			InArgs_MyTimer inargs_mytimer, OutArgs outargs, boolean isNeedTimer) {
		super();
		this.inargs_myrunner = inargs_myrunner;
		this.inargs_mytimer = inargs_mytimer;
		this.outargs = outargs;
		this.isNeedTimer = isNeedTimer;
	}

	public void execute(TimerI timerI) {
		MyRunner myRunner = new MyRunner(inargs_myrunner, outargs);
		MyTimer myTimer = new MyTimer(inargs_mytimer, myRunner,timerI);
		if (isNeedTimer)
			/**
			 * Thread.setDaemon的用法 
			 * 1. setDaemon需要在start方法调用之前使用 
			 * 2.线程划分为用户线程和后台(daemon)进程，setDaemon将线程设置为后台进程
			 * 3.如果jvm中都是后台进程，当前jvm将exit。（随之而来的，所有的一切烟消云散，包括后台线程啦） 
			 * 4. 主线程结束后， 1）用户线程将会继续运行 2） 如果没有用户线程，都是后台进程的话，那么jvm结束
			 */
			myRunner.setDaemon(true);
		myRunner.start();
		if (isNeedTimer)
			myTimer.start();
	}

	public boolean isNeedTimer() {
		return isNeedTimer;
	}

	public void setNeedTimer(boolean isNeedTimer) {
		this.isNeedTimer = isNeedTimer;
	}

	public InArgs_MyRunner getInargs_myrunner() {
		return inargs_myrunner;
	}

	public void setInargs_myrunner(InArgs_MyRunner inargs_myrunner) {
		this.inargs_myrunner = inargs_myrunner;
	}

	public InArgs_MyTimer getInargs_mytimer() {
		return inargs_mytimer;
	}

	public void setInargs_mytimer(InArgs_MyTimer inargs_mytimer) {
		this.inargs_mytimer = inargs_mytimer;
	}

	public OutArgs getOutargs() {
		return outargs;
	}

	public void setOutargs(OutArgs outargs) {
		this.outargs = outargs;
	}

}
