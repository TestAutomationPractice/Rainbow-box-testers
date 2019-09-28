package com.testathon.automation.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This Class perform various functions:
 * 1) Returns the row count in a sheet.
 * 2) Returns number of columns in a sheet.
 * 3) Returns the data from a cell.
 * 4) Returns true if data is set successfully.
 * 5) Returns true if sheet is created successfully.
 * 6) Returns true if sheet is removed successfully.
 * 7) Returns number of columns in a sheet.
 * 8) Removes a column and all the contents.
 * 9) Find whether sheets exists.
 * 10) Add hyper link.
 * 11) Gets the cell row number.
 * 12) Parses the xml.
 * 13) Gets the xml value.
 * 14) Read CSV file.
 * 15) CSV data provider.

 *
 */
public class ReadFileUtil {

	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	public static final HyperlinkType FILE = null;

	private static Logger log = Logger.getLogger(ReadFileUtil.class);

	/**
	 * Instantiates a new data source operations.
	 */
	public ReadFileUtil() {
		log.info(" : FileOperation Constructor Called");
	}

	/**
	 * Returns the row count in a sheet.
	 *
	 * @param sheetName		sheet name.
	 * @return integer, row count in a sheet.
	 */
	public int getRowCount(String sheetName) {
		log.info(" : GetRowCount Method Called");
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}
	}

	/**
	 * Returns number of columns in a sheet.
	 *
	 * @param sheetName		sheet name.
	 * @return integer, column count in a sheet.
	 */
	public int getColumnCount(String sheetName) {
		log.info(" : GetCoulmnCount Method Called");
		// check if sheet exists
		if (!isSheetExist(sheetName))
			return -1;
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		if (row == null)
			return -1;
		return row.getLastCellNum();

	}

	/**
	 * Returns the data from a cell.
	 *
	 * @param sheetName		sheet name.
	 * @param colName		column name.
	 * @param rowNum		row number.
	 * @return string, cell data.
	 */
	public String getCellData(String sheetName, String colName, int rowNum) {
		log.info(" : GetCellData Method Called");
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);
			if (cell == null)
				return "";
			if (cell.getCellTypeEnum() == CellType.STRING)
				return cell.getStringCellValue();
			else if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					double d = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
				}
				return cellText;
			} else if (cell.getCellTypeEnum() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	/**
	 * Returns the data from a cell.
	 *
	 * @param sheetName		sheet name.
	 * @param colNum		column number.
	 * @param rowNum		row number.
	 * @return string, cell data.
	 */
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";
			if (cell.getCellTypeEnum() == CellType.STRING)
				return cell.getStringCellValue();
			else if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;
				}
				return cellText;
			} else if (cell.getCellTypeEnum() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	/**
	 * Returns true if data is set successfully else false.
	 *
	 * @param sheetName		sheet name.
	 * @param colName		column name.
	 * @param rowNum		row name.
	 * @param data			data value.
	 * @return true, 		if data set successfully else false.
	 */
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		log.info(" : SetCellData Method Called");
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			if (rowNum <= 0)
				return false;
			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;
			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);
			cell.setCellValue(data);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Returns true if data is set successfully else false.
	 * 
	 * @param sheetName		sheet name.
	 * @param colName		column name.
	 * @param rowNum		row number.
	 * @param data			data value.
	 * @param url			url value.
	 * @return true, if data set successfully.
	 */
	public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {
		log.info(" : SetCellData Method Called");
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			if (rowNum <= 0)
				return false;
			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;
			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);
			cell.setCellValue(data);
			XSSFCreationHelper createHelper = workbook.getCreationHelper();
			// cell style for hyperlinks
			// by default hypelrinks are blue and underlined
			CellStyle hlink_style = workbook.createCellStyle();
			XSSFFont hlink_font = workbook.createFont();
			hlink_font.setUnderline(XSSFFont.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);
			// hlink_style.setWrapText(true);
			Hyperlink link = createHelper.createHyperlink(FILE);
			link.setAddress(url);
			cell.setHyperlink(link);
			cell.setCellStyle(hlink_style);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Returns true if sheet is created successfully else false.
	 * 
	 * @param sheetname		sheet name user wants to add.
	 * @return true, if sheet added successful.
	 */
	public boolean addSheet(String sheetname) {
		log.info(" : AddSheet Method Called");
		FileOutputStream fileOut;
		try {
			workbook.createSheet(sheetname);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Returns true if sheet is removed successfully else false if sheet does not exist.
	 * 
	 * @param sheetName		sheet name.
	 * @return true, if sheet removed successfully.
	 */
	public boolean removeSheet(String sheetName) {
		log.info(" : RemoveSheet Method Called");
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return false;
		FileOutputStream fileOut;
		try {
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Returns number of columns in a sheet.
	 * 
	 * @param sheetName		sheet name.
	 * @param colName		column name.
	 * @return true, if column added successfully.
	 */
	public boolean addColumn(String sheetName, String colName) {
		log.info(" : AddColumn Method Called");
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return false;
			XSSFCellStyle style = workbook.createCellStyle();
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);
			if (row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(colName);
			cell.setCellStyle(style);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();

		} catch (Exception ex) {
			log.error(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Removes a column and all the contents.
	 *
	 * @param sheetName		sheet name.
	 * @param colNum		column number.
	 * @return true, if column removed successfully.
	 */
	public boolean removeColumn(String sheetName, int colNum) {
		log.info(" : RemoveColumn Method Called");
		try {
			if (!isSheetExist(sheetName))
				return false;
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			XSSFCellStyle style = workbook.createCellStyle();

			for (int i = 0; i < getRowCount(sheetName); i++) {
				row = sheet.getRow(i);
				if (row != null) {
					cell = row.getCell(colNum);
					if (cell != null) {
						cell.setCellStyle(style);
						row.removeCell(cell);
					}
				}
			}
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Find whether sheets exists.
	 *
	 * @param sheetName		sheet name.
	 * @return true, if is sheet exist.
	 */
	public boolean isSheetExist(String sheetName) {
		log.info(" : IsSheetExist Method Called");
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}

	/**
	 * Add hyper link.
	 *
	 * @param sheetName			sheet name.
	 * @param screenShotColName screenshot column name.
	 * @param testCaseName		test case name.
	 * @param index 			index value.
	 * @param url 				url.
	 * @param message 			message.
	 * @return true, if successful.
	 */
	public boolean addHyperLink(String sheetName, String screenShotColName, String testCaseName, int index, String url,
			String message) {
		log.info(" : AddHyperlink Method Called");
		url = url.replace('\\', '/');
		if (!isSheetExist(sheetName))
			return false;
		sheet = workbook.getSheet(sheetName);
		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)) {
				setCellData(sheetName, screenShotColName, i + index, message, url);
				break;
			}
		}
		return true;
	}

	/**
	 * Gets the cell row number.
	 *
	 * @param sheetName		sheet name.
	 * @param colName		column name.
	 * @param cellValue 	cell value.
	 * @return integer, cell row number.
	 */
	public int getCellRowNum(String sheetName, String colName, String cellValue) {
		log.info(" : GetCellRowNum Method Called");
		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Parses the xml.
	 * 
	 * @param XMLFile	XML file.
	 * @return map.
	 * @throws ParserConfigurationException the parser configuration exception.
	 * @throws SAXException the SAX exception.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// This method is used to parse a XML with below format to a HashMap
	/*
	 * <?xml version="1.0" encoding="UTF-8"?> <OR> <Page name="LoginPage">
	 * <Object name = 'UsernameTextbox'> <type>id</type> <value>Email</value>
	 * </Object> <Object name = 'PasswordTextBox'> <type>id</type>
	 * <value>Password</value> </Object> <Object name = 'LoginButton'>
	 * <type>xpath</type> <value>//*[@id='loginFormId']/div[4]/div/input</value>
	 * </Object> </Page> </OR>
	 */

	public Map<String, List<String>> ParseXml(String XMLFile)
			throws ParserConfigurationException, SAXException, IOException {
		log.info(" : GetXMLValue Method Called");
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		File inputfile = new File(XMLFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docbuilder = dbFactory.newDocumentBuilder();
		Document doc = docbuilder.parse(inputfile);
		doc.getDocumentElement().normalize();
		NodeList pagelist = doc.getElementsByTagName("Page");
		for (int tmp = 0; tmp < pagelist.getLength(); tmp++) {
			Node pageNode = pagelist.item(tmp);
			NamedNodeMap nodeMap = pageNode.getAttributes();
			for (int j = 0; j < nodeMap.getLength(); j++) {
				for (int k = 0; k < nodeMap.getLength(); k++) {
					if (pageNode.getNodeType() == Node.ELEMENT_NODE) {
						NodeList objectList = pageNode.getChildNodes();
						for (int i = 0; i < objectList.getLength(); i++) {
							Node childNode = objectList.item(i);
							if (childNode.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) childNode;
								List<String> vals = new ArrayList<String>();
								map.put(eElement.getAttribute("name").toString(), vals);
								vals.add(nodeMap.getNamedItem("name").getNodeValue().toString());
								vals.add(eElement.getElementsByTagName("type").item(0).getTextContent().toString());
								vals.add(eElement.getElementsByTagName("value").item(0).getTextContent().toString());
							}
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * Gets the xml value.
	 *
	 * @param variablename	variablename.
	 * @param XMLFile		XML file.
	 * @param module 		module.
	 * @return list.
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParserConfigurationException the parser configuration exception.
	 * @throws SAXException the SAX exception.
	 */
	public static List<String> getXmlValue(String variablename, String XMLFile, String module)
			throws IOException, ParserConfigurationException, SAXException {
		List<String> valueOfElement = new ArrayList<String>();
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new File(XMLFile));
		doc.getDocumentElement().normalize();
		if (doc.hasChildNodes()) {
			NodeList nodeList = doc.getChildNodes();
			for (int count = 0; count < nodeList.getLength(); count++) {
				Node tempNode = nodeList.item(count);
				if (tempNode.hasChildNodes()) {
					NodeList moduleList = tempNode.getChildNodes();
					for (int j = 0; j < moduleList.getLength(); j++) {
						Node moduleNode = moduleList.item(j);
						if (moduleNode.getNodeType() == Node.ELEMENT_NODE && moduleNode.getNodeName() == module) {
							if (moduleNode.hasChildNodes()) {
								NodeList childList = moduleNode.getChildNodes();
								for (int i = 0; i < childList.getLength(); i++) {
									Node childNode = childList.item(i);
									if (childNode.getNodeType() == Node.ELEMENT_NODE
											&& childNode.getNodeName().toString() == "ElementProperty") {
										if (childNode.hasAttributes()) {
											// get attributes names and values
											NamedNodeMap nodeMap = childNode.getAttributes();
											for (int k = 0; k < nodeMap.getLength(); k++) {

												Node node = nodeMap.item(k);
												if (node.getNodeName() == "NameOfElement") {
													if (node.getNodeValue().equals(variablename)) {
														valueOfElement.add(childNode.getTextContent());
														Node propertyType = nodeMap.getNamedItem("Type");
														valueOfElement.add(propertyType.getNodeValue());
														break;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return valueOfElement;
	}

	/**
	 * Read CSV file.
	 *
	 * @param testCaseId	test case id.
	 * @param separator		separator.
	 * @param filename		filename to read.
	 * @return string[].
	 */
	public String[] ReadCSV(String testCaseId, String separator, String filename) {
		log.info(" : ReadCSV Method Called");
		BufferedReader br = null;
		String[] values = null;
		String line = "";

		try {
			File file = new File(filename);
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				if (line.trim().startsWith(testCaseId.trim())) {
					values = line.split(separator);
					break;
				}
			}
		} catch (FileNotFoundException ex) {
			log.error(ex.getMessage());
		} catch (IOException ex) {
			log.error(ex.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					log.error(ex.getMessage());
				}
			}
		}
		return values;
	}

	/**
	 * CSV data provider.
	 * 
	 * @param testCaseId		test case id.
	 * @param separator			separator.
	 * @param filename			filename.
	 * @return string[][].
	 */
	public static String[][] CSVDataProvider(String testCaseId, String separator, String filename) {
        List<String[]> dataArr = new ArrayList<String[]>();
        BufferedReader br = null;
        String[] values = null;
        String line = "";
        String[][] strArray = null;
        try {
               File file = new File(filename);
               br = new BufferedReader(new FileReader(file));
               while ((line = br.readLine()) != null) {
                      if(!line.startsWith(",")) {
                            String[] text = line.split(",");
                            if (text[0].equals(testCaseId.trim())) {
                                   line = line.substring(line.indexOf(separator) + 1);
                                   values = line.split(separator);
                                   dataArr.add(values);
                                   strArray = dataArr.toArray(new String[0][0]);
                            }
                      }
               }
        } catch (FileNotFoundException ex) {
               log.error(ex.getMessage());
        } catch (IOException ex) {
               log.error(ex.getMessage());
        } finally {
               if (br != null) {
                      try {
                            br.close();
                      } catch (IOException ex) {
                            log.error(ex.getMessage());
                      }
               }
        }
        return strArray;
  }
	
	/**
	 * Read columns from CSV.
	 *
	 * @param filename
	 *            the filename
	 * @param column
	 *            the column
	 * @return the array list
	 */
	public static ArrayList<String> readColumnsFromCSV(String filename, String column) {
		try
		{
			ArrayList<String> modelNumbers = new ArrayList<String>();
			ArrayList<String> quantities = new ArrayList<String>();
			ArrayList<String> allColumns = new ArrayList<String>();
			File file = new File(filename);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			br.readLine(); // skip the header of the file
			while ((line = br.readLine()) != null) {
				String[] arr = line.split(",");
				modelNumbers.add(arr[0]);
				quantities.add(arr[1]);
			}
			br.close();

			if(column.equalsIgnoreCase("ItemNumber")) {
				return modelNumbers;
			}
			else if(column.equalsIgnoreCase("quantity")){
				return quantities;
			}
			else if(column.equalsIgnoreCase("all")){
				for(int i=0;i<allColumns.size();i++){
					allColumns.add(modelNumbers.get(i));
					allColumns.add(quantities.get(i));
				}
				return allColumns;
			}
			else {
				return null;
			}
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}	
		return null;
	}
}
