package test.ftp;

import java.util.Date;
import java.util.Vector;

import org.junit.Test;

import com.jcraft.jsch.ChannelSftp.LsEntry;

import zj.date.util.DateUtil;
import zj.ftp.util.FTP;
import zj.ftp.util.SFTP;

public class TestFtp {
	@Test
	public void m3() throws Exception {
		FTP ftp = new FTP("122.22.22.33", 22, "ftpemail", "password");
		try {
			ftp.connect();
			it.sauronsoftware.ftp4j.FTPClient fc = ftp.getClient();
//			Ftfc.list();
			System.out.println(fc);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ftp.disconnect(true);
		}
	}

	@Test
	public void m2() throws Exception {
		Vector<String> v = new Vector<String>();
		v.add("a");
		System.out.println(v.get(0));
	}

	@Test
	public void m1() throws Exception {
		SFTP sf = new SFTP("122.22.22.33", 22, "ftpemail", "password");
		try {
			sf.connect();
			System.out.println("SFTP:"+sf.getSftp());
			//sf.makeDirs("/home/ftpemail/a/b/eeee3");
			String dir = "/home/ftpemail/productsFile/"+DateUtil.dateParse(new Date(), "yyyyMMdd");
			System.out.println(dir);
			Vector<LsEntry> lsFiles = sf.listFiles(dir);
			System.out.println(lsFiles);
			for (LsEntry lsFile : lsFiles){
				System.out.println(lsFile.getFilename());
			}
			// sf.download("", "", "E:/document/ftp");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sf.disconnect();
		}
		System.out.println(sf);
	}
}
