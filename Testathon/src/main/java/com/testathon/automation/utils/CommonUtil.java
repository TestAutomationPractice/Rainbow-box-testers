package com.testathon.automation.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestResult;

import com.testathon.automation.core.Config;

/**
 * This class contains various methods which are getting used in test method creation.
 */
public class CommonUtil {

	private static String methodName;
	private static Logger log = Logger.getLogger(CommonUtil.class);
	private static int fileNumber = 1;

	/**
	 * Instantiates a new util.
	 */
	private CommonUtil() {
		log.info(" : Util Constructor Called");
	}

	/**
	 * Deletes old files from specified directory.
	 *
	 * @param directory 	path of the directory contains old files.
	 * @throws IOException 	Signals that an I/O exception has occurred.
	 */
	public static void deleteOldFiles(String directory) throws IOException {
		File targetDir = new File(directory);
		FileUtils.cleanDirectory(targetDir);
	}

	/**
	 * Returns a unique text by using the date time stamp in the format
	 * yyyyMMdd_HHmmss.
	 *
	 * @return String	unique name generated.
	 */
	public static String generateUniqueName() {
		log.info(" : GenerateUniqueNameMethod Method Called");
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd_HHmmss_z");
		Date now = new Date();
		String uniqueText = sdfDate.format(now);
		return (uniqueText);
	}

	/**
	 * Captures the screenshot.
	 *
	 * @param eDriver 			webdriver object.
	 * @param screenshotName 	screenshot name.
	 * @param result 			ItestResult object.
	 * @return String			directory path where screenshot will be created.
	 */
	public static String captureScreenshot(WebDriver eDriver, String screenshotName, ITestResult result) {
		log.info(" : CaptureScreeshot Method Called");
		try {
			WebDriver augmentedDriver = new Augmenter().augment(eDriver);
	        File source = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
	        log.info("Screenshot taken.");
	        String dest = Config.ScreenShotsPath + screenshotName + "_" + result.getInstanceName()+"_"+result.getName()+".png";
			File destination = new File(dest);
			if (!result.getName().equals(methodName)) {
				methodName = result.getName();
				fileNumber = 1;
			}
			if (destination.exists()) {
				dest = Config.ScreenShotsPath + screenshotName + "_" + result.getInstanceName() + "_" + result.getName()
						+ "(" + fileNumber++ + ")" + ".png";
				destination = new File(dest);
			}
			FileUtils.copyFile(source, destination);
			return dest;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return ex.getMessage();
		}
	}

	/**
	 * Get the folder size in bytes.
	 *
	 * @param directory 	path of folder.
	 * @return long 		size in bytes.
	 */
	public static long getFolderSizeInByte(String directory) {
		File file = new File(directory);
		long size = FileUtils.sizeOfDirectory(file);
		return size;
	}

	/**
	 * Get the folder size in proper format like KB, MB, GB, Bytes.
	 *
	 * @param directory 	path of folder.
	 * @return String 		size in bytes.
	 */
	public static String getFolderSize(String directory) {
		String hrSize = null;
		Long size = getFolderSizeInByte(directory);
		double b = size;
		double k = size / 1024.0;
		double m = ((size / 1024.0) / 1024.0);
		double g = (((size / 1024.0) / 1024.0) / 1024.0);
		double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);

		DecimalFormat dec = new DecimalFormat("0.00");

		if (t > 1) {
			hrSize = dec.format(t).concat(" TB");
		} else if (g > 1) {
			hrSize = dec.format(g).concat(" GB");
		} else if (m > 1) {
			hrSize = dec.format(m).concat(" MB");
		} else if (k > 1) {
			hrSize = dec.format(k).concat(" KB");
		} else {
			hrSize = dec.format(b).concat(" Bytes");
		}
		return hrSize;
	}

	/**
	 * Zip the specified folder.
	 *
	 * @param Destloc	path to store the zipped file.
	 * @param sourceLoc path of folder to be zipped.
	 */
	public static void zipfolder(String Destloc, String sourceLoc) {
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(Destloc + ".zip"));
			zipDir(sourceLoc, zos);
			zos.close();
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

	/**
	 * Zip the specified directory.
	 *
	 * @param dir2zip	path of folder to be zipped.
	 * @param zos		content to be zipped.
	 */
	public static void zipDir(String dir2zip, ZipOutputStream zos){
		FileInputStream fis = null;

		File zipDir = new File(dir2zip);
		String[] dirList = zipDir.list();
		byte[] readBuffer = new byte[2156];
		int bytesIn = 0;
		try {
			for (int i = 0; i < dirList.length; i++) {
				File f = new File(zipDir, dirList[i]);
				if (f.isDirectory()) {
					String filePath = f.getPath();
					zipDir(filePath, zos);
					continue;
				}

				fis = new FileInputStream(f);
				ZipEntry anEntry = new ZipEntry(f.getPath());
				zos.putNextEntry(anEntry);
				while ((bytesIn = fis.read(readBuffer)) != -1) {
					zos.write(readBuffer, 0, bytesIn);
				}
			}
			log.info("Folder is zipped successfully.");
		} catch (FileNotFoundException ex) {
			log.error("Error ocurred while zipping the folder: " + ex.getMessage());
		} catch (IOException ex) {
			log.error("Error ocurred while zipping the folder: " + ex.getMessage());
		}catch (NullPointerException ex) {
			log.error("Error ocurred while zipping the folder: " + ex.getMessage());
		} catch (Exception e) {
			log.error("Error ocurred while zipping the folder: " + e.getMessage());
		}finally {
			try {
				if(fis!=null) {
					fis.close();
				}
			} catch (IOException e) {
				log.error(e.getStackTrace());
			}
		}
	}

	/**
	 * Sets the screenshot relative path.
	 */
	public static void setScreenshotRelativePath() {
		FileReader fr = null;
		FileWriter fw = null;
		log.info(" : sshotSetRelativePath Method Called");
		try {
			File f = new File(Config.ExtentReportsPath);
			ArrayList<String> lines = new ArrayList<String>();
			String line;
			fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				if (line.contains("<img")) {
					line = line.replace(Config.ScreenShotsPath, "./");
				}
				lines.add(line + "\n");
			}
			fr.close();
			br.close();
			fw = new FileWriter(f);
			BufferedWriter out = new BufferedWriter(fw);
			for (String s : lines)
				out.write(s);
			out.flush();
			fw.close();
			out.close();
		} catch (FileNotFoundException ex) {
			log.error(ex.getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

	/**
	 * Zip the screenshot created.
	 *
	 * @param screenShotsPath 	screen shots path
	 * @param zipPath 			zip path
	 * @throws Exception 		if error ocurred.
	 */
	public static void zipFolder(Path screenShotsPath, Path zipPath) throws Exception {

		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
		try {
			Files.walkFileTree(screenShotsPath, new SimpleFileVisitor<Path>() {
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					zos.putNextEntry(new ZipEntry(screenShotsPath.relativize(file).toString()));
					Files.copy(file, zos);
					zos.closeEntry();
					return FileVisitResult.CONTINUE;
				}
			});
		}catch(IOException e) {
			log.error("Error ocurred while closing the file: " + e.getMessage());
		}finally {
			zos.close();
		}

	}

	/**
	 * Sends mail.
	 *
	 * @param filePath 				path of file to be send as email.
	 * @throws MessagingException 	messaging exception
	 */
	public static void SendMail(String filePath) throws MessagingException {
		String to = Config.mailTo;
		String from = Config.mailFrom;
		final String username = Config.mailFrom;
		final String password = Config.mailPassword;
		String host = Config.mailHost;
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Saltmarsh Functional Automation test Report");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("Hi All , Please find the attached Automation Test execution Report.");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			String[] filename = filePath.split("\\\\");
			DataSource source = new FileDataSource(filePath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename[filename.length - 1]);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			log.info("Sent Test report on Email successfully....");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Records the video while test case is running.
	 *
	 * @return ATUTestRecorder 			recorder.
	 * @throws IOException 				Signals that an I/O exception has occurred.
	 * @throws ATUTestRecorderException ATU test recorder exception.
	 */
	/*public static ATUTestRecorder StartVideoRecording() throws IOException, ATUTestRecorderException {
		File file = new File(Config.TestVideoPath);
		if (file.exists() && file.isDirectory()) {
			log.info("Test Video Folder exists at the path " + Config.TestVideoPath);
		} else {
			log.info("Test Video Folder doesn't exist" + Config.TestVideoPath);
			log.info("Creating Test Video Folder");
			file.mkdir();
		}
		Long currentSize = CommonUtil.getFolderSizeInByte(Config.TestVideoPath);
		log.info("Folder Size of test Videos in Byte: " + currentSize);
		double maxSize = 1e+9 * Integer.parseInt(Config.MaxSizeVideoFilesGB);
		if (currentSize > maxSize) {
			log.info("Test Video folder size is more than 2 GB, So deleting the files");
			CommonUtil.deleteOldFiles(Config.TestVideoPath);
		} else
			log.info("size is ok");
		String videoFileName = "TestVideo-" + CommonUtil.generateUniqueName();
		// Created object of ATUTestRecorder
		ATUTestRecorder recorder = new ATUTestRecorder(Config.TestVideoPath, videoFileName, false);
		// To start video recording.
		recorder.start();
		return recorder;
	}*/

	/**
	 * Checks if folder exist at the specified path.
	 *
	 * @param filePath  path of the file.
	 */
	public static void isFolderExistAtPath(String filePath) {
		File folder = new File(filePath);
		if (folder.exists() && folder.isDirectory()) {
			log.info(folder.getName()+ " Path: "+ folder.getAbsolutePath());
		} else {
			log.info(folder.getName()+" folder doesn't exist at the path: " + folder.getAbsolutePath());
			log.info("Creating Folder");
			folder.mkdir();
			log.info(folder.getName()+ " folder created at path: "+folder.getAbsolutePath());
		}

	}
	
	/**
	 * to kill a process.
	 *
	 * @param Service Name.
	 * @throws Exception 
	 */
	public static void killAProcess(String serviceName) throws Exception {
		String KILL = "taskkill /F /IM ";
		if(isProcessRunning(serviceName))
		Runtime.getRuntime().exec(KILL + serviceName);

	}
	
	/**
	 * Checks if is process running.
	 *
	 * @param serviceName the service name
	 * @return true, if is process running
	 * @throws Exception the exception
	 */
	public static boolean isProcessRunning(String serviceName) throws Exception {
		String TASKLIST = "tasklist";

		Process p = Runtime.getRuntime().exec(TASKLIST);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {

			if (line.contains(serviceName)) {
				return true;
			}
		}

		return false;

	}
}
