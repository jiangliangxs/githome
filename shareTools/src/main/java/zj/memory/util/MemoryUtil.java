package zj.memory.util;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
public class MemoryUtil {
	private static Runtime runtime = Runtime.getRuntime();
	/** 写文件换行标识 **/
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	/** 系统名 **/
	public static final String OS_NAME = System.getProperty("os.name");
	private static final int FAULTLENGTH = 10;
	private static final int CPUTIME = 30;
	private static final int PERCENT = 100;

	/**
	 * 内存信息
	 * 
	 * @return
	 */
	public static String getMemoryByMB() {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("系统名:" + OS_NAME);
		localStringBuffer.append(LINE_SEPARATOR);
		localStringBuffer.append("系统总共可用内存:" + getMaxMemoryByMB());
		localStringBuffer.append(LINE_SEPARATOR);
		localStringBuffer.append("系统已使用内存:" + getTotleMemoryByMB());
		localStringBuffer.append(LINE_SEPARATOR);
		localStringBuffer.append("系统空闲内存:" + getFreeMemoryByMB());
		localStringBuffer.append(LINE_SEPARATOR);
		localStringBuffer.append("系统还可分配给java虚拟机的内存:" + getSurplusMemoryByMB());
		localStringBuffer.append(LINE_SEPARATOR);
		localStringBuffer.append("系统线程数:" + getTotalThread());
		return localStringBuffer.toString();
	}

	/**
	 * 系统总共可用内存
	 * 
	 * @return
	 */
	public static long getMaxMemory() {
		return runtime.maxMemory();
	}

	/**
	 * 系统已使用内存
	 * 
	 * @return
	 */
	public static long getTotleMemory() {
		return runtime.totalMemory();
	}

	/**
	 * 系统空闲内存
	 * 
	 * @return
	 */
	public static long getFreeMemory() {
		return runtime.freeMemory();
	}

	/**
	 * 系统还可分配给java虚拟机的内存
	 * 
	 * @return
	 */
	public static long getSurplusMemory() {
		return getMaxMemory() - getTotleMemory();
	}

	/**
	 * 系统总共可用内存
	 * 
	 * @return
	 */
	public static String getMaxMemoryByMB() {
		return (float) getMaxMemory() / 1024.0F / 1024.0F + "M";
	}

	/**
	 * 系统已使用内存
	 * 
	 * @return
	 */
	public static String getTotleMemoryByMB() {
		return (float) getTotleMemory() / 1024.0F / 1024.0F + "M";
	}

	/**
	 * 系统空闲内存
	 * 
	 * @return
	 */
	public static String getFreeMemoryByMB() {
		return (float) getFreeMemory() / 1024.0F / 1024.0F + "M";
	}

	/**
	 * 系统还可分配给java虚拟机的内存
	 * 
	 * @return
	 */
	public static String getSurplusMemoryByMB() {
		return (float) getSurplusMemory() / 1024.0F / 1024.0F + "M";
	}

	/**
	 * 获得线程总数
	 * 
	 * @return
	 */
	public static int getTotalThread() {
		ThreadGroup parentThread;
		for (parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent() != null; parentThread = parentThread.getParent())
			;
		int totalThread = parentThread.activeCount();
		return totalThread;
	}

	/**
	 * 获得CPU使用率.
	 * 
	 * @return 返回cpu使用率
	 * @author GuoHuang
	 */
	public static double getCpuRatioForWindows() {
		try {
			String procCmd = System.getenv("windir") + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine," + "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
			// 取进程信息
			long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
			Thread.sleep(CPUTIME);
			long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
			if (c0 != null && c1 != null) {
				long idletime = c1[0] - c0[0];
				long busytime = c1[1] - c0[1];
				return Double.valueOf(PERCENT * (busytime) / (busytime + idletime)).doubleValue();
			} else {
				return 0.0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0.0;
		}
	}

	/**
	 * 
	 * 读取CPU信息.
	 * 
	 * @param proc
	 * @return
	 * @author GuoHuang
	 */
	private static long[] readCpu(final Process proc) {
		long[] retn = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = input.readLine();
			if (line == null || line.length() < FAULTLENGTH) {
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0;
			long kneltime = 0;
			long usertime = 0;
			while ((line = input.readLine()) != null) {
				if (line.length() < wocidx) {
					continue;
				}
				// 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
				// ThreadCount,UserModeTime,WriteOperation
				String caption = substring(line, capidx, cmdidx - 1).trim();
				String cmd = substring(line, cmdidx, kmtidx - 1).trim();
				if (cmd.indexOf("wmic.exe") >= 0) {
					continue;
				}
				// log.info("line="+line);
				if (caption.equals("System Idle Process") || caption.equals("System")) {
					idletime += Long.valueOf(substring(line, kmtidx, rocidx - 1).trim()).longValue();
					idletime += Long.valueOf(substring(line, umtidx, wocidx - 1).trim()).longValue();
					continue;
				}
				kneltime += Long.valueOf(substring(line, kmtidx, rocidx - 1).trim()).longValue();
				usertime += Long.valueOf(substring(line, umtidx, wocidx - 1).trim()).longValue();
			}
			retn[0] = idletime;
			retn[1] = kneltime + usertime;
			return retn;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				proc.getInputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// private static double getCpuRateForLinux() {
	// InputStream is = null;
	// InputStreamReader isr = null;
	// BufferedReader brStat = null;
	// StringTokenizer tokenStat = null;
	// try {
	// String linuxVersion = "";
	// System.out.println("Get usage rate of CUP , linux version: " + linuxVersion);
	// Process process = Runtime.getRuntime().exec("top -b -n 1");
	// is = process.getInputStream();
	// isr = new InputStreamReader(is);
	// brStat = new BufferedReader(isr);
	//
	// if (linuxVersion.equals("2.4")) {
	// brStat.readLine();
	// brStat.readLine();
	// brStat.readLine();
	// brStat.readLine();
	//
	// tokenStat = new StringTokenizer(brStat.readLine());
	// tokenStat.nextToken();
	// tokenStat.nextToken();
	// String user = tokenStat.nextToken();
	// tokenStat.nextToken();
	// String system = tokenStat.nextToken();
	// tokenStat.nextToken();
	// String nice = tokenStat.nextToken();
	//
	// System.out.println(user + " , " + system + " , " + nice);
	//
	// user = user.substring(0, user.indexOf("%"));
	// system = system.substring(0, system.indexOf("%"));
	// nice = nice.substring(0, nice.indexOf("%"));
	//
	// float userUsage = new Float(user).floatValue();
	// float systemUsage = new Float(system).floatValue();
	// float niceUsage = new Float(nice).floatValue();
	//
	// return (userUsage + systemUsage + niceUsage) / 100;
	// } else {
	// brStat.readLine();
	// brStat.readLine();
	//
	// tokenStat = new StringTokenizer(brStat.readLine());
	// tokenStat.nextToken();
	// tokenStat.nextToken();
	// tokenStat.nextToken();
	// tokenStat.nextToken();
	// tokenStat.nextToken();
	// tokenStat.nextToken();
	// tokenStat.nextToken();
	// String cpuUsage = tokenStat.nextToken();
	//
	// System.out.println("CPU idle : " + cpuUsage);
	// Float usage = new Float(cpuUsage.substring(0, cpuUsage.indexOf("%")));
	// return (1 - usage.floatValue() / 100);
	// }
	//
	// } catch (IOException ioe) {
	// System.out.println(ioe.getMessage());
	// freeResource(is, isr, brStat);
	// return 1;
	// } finally {
	// freeResource(is, isr, brStat);
	// }
	// }
	// private static void freeResource(InputStream is, InputStreamReader isr, BufferedReader br) {
	// try {
	// if (is != null)
	// is.close();
	// if (isr != null)
	// isr.close();
	// if (br != null)
	// br.close();
	// } catch (IOException ioe) {
	// System.out.println(ioe.getMessage());
	// }
	// }
	private static String substring(String src, int start_idx, int end_idx) {
		byte[] b = src.getBytes();
		String tgt = "";
		for (int i = start_idx; i <= end_idx; i++) {
			tgt += (char) b[i];
		}
		return "".equals(tgt.trim()) ? "0" : tgt;
	}
}
