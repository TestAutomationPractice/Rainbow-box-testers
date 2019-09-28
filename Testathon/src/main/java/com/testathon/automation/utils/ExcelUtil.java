package com.testathon.automation.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

/**
 * This class is to read data from excel file using sql queries.
 * @throws FilloException 
 * 
 * 
 * 
 */

public class ExcelUtil {
		
	private static Logger log = Logger.getLogger(ExcelUtil.class);
	/**
	 * getTestDataFromExcel
	 * This function is to get Test Data from excel file using sql queries in hashmap.
	 * @param sheetPath The path of the excel workbook
	 * @param strTestSuiteName The name of the excel sheet
	 * @param strTCName The name of the test case in excel sheet
	 * @return hash This function returns the values in hashmap 
	 * @throws FilloException 
	 */
	public HashMap<String,String> getTestDataFromExcel(String SheetPath, String strTestSuiteName, String strTCName) throws FilloException  {

		   Fillo fillo=new Fillo();           
           String value=null;
           String k2=null;

           Connection connection=fillo.getConnection(SheetPath);           
           String strQuery="Select * from "+strTestSuiteName +" where TestCaseName='"+strTCName+"'" ;
           /*System.out.println(strQuery);*/

           Recordset recordset=connection.executeQuery(strQuery);
           HashMap<String,String> hash=new HashMap<String,String>();  

           ArrayList<String> keys=recordset.getFieldNames();
                  while(recordset.next()){
                	  for (String k1:keys){
                		  k2=k1;
                		  value=recordset.getField(k1);
                		  hash.put(k2, value);
                	  }
                  }                   
           recordset.close();
           connection.close();
           return hash;

    }

	public static Recordset getTestDataFromExcel1(String sheetPath, String sheetName, String strQuery) throws FilloException  {

		Fillo fillo=new Fillo();           
		Recordset recordset = null;

        Connection connection=fillo.getConnection(sheetPath);           
        if(checkIfSheetExists(sheetPath,sheetName))
        {
      //  String strQuery="Select * from "+ sheetName ;
        recordset=connection.executeQuery(strQuery);
        }
        
        //recordset.close();
        connection.close();
        
        return recordset;

 }
	 /**
     * This method treats excel as a database.<BR>You can get data from the excel. 
     * file just using a basic query.<BR>
     *
     * @author 
     * @param fileName
     * @param query
     * @return
     * @throws FilloException
     */
	public static List<List<String>> executeExcelQuery(String fileName, String query) throws FilloException {
        List<List<String>> listOfLists = new ArrayList<List<String>>();
        List<String> someList = new ArrayList<String>();

        Fillo fillo = new Fillo();
        Connection connection = fillo.getConnection(fileName);
        query = query.toUpperCase();
        log.debug(query);
        Recordset recordset = connection.executeQuery(query);

        log.debug("total number of row returned " + recordset.getCount());
        while (recordset.next()) {
            ArrayList<String> dataColl = recordset.getFieldNames();
            log.debug("Total data column " + dataColl);
            Iterator<String> dataIterator = dataColl.iterator();

            String[] columns = columnsSplit(query);

            int width = 0;
            // Width size
            if (query.contains("*")) {
                width = dataColl.size();
                log.debug(width);
            } else {
                width = columns.length;
                log.debug(width);
            }

            String[] rowByRow = new String[width];
            int rowNo = 0;
            while (dataIterator.hasNext()) {
                for (int i = 0; i <= dataColl.size() - 1; i++) {
                    String data = dataIterator.next();

                    if (query.contains("*")) {

                        String dataVal = recordset.getField(data);
                        log.debug(dataVal);
                        rowByRow[rowNo] = dataVal;
                        rowNo++;

                    } else {

                        log.debug(columns.length);
                        for (String column : columns) {
                            if (column.length() > 0) {
                                if (data.equalsIgnoreCase(column)) {

                                    String dataVal = recordset.getField(column);
                                    rowByRow[rowNo] = dataVal;
                                    log.debug(dataVal);
                                    rowNo++;

                                }

                            }
                        }

                    }

                }
                listOfLists.add(Arrays.asList(rowByRow));
                rowNo = 0;
                someList.iterator();
                break;
            }
        }
        recordset.close();
        connection.close();

        return listOfLists;
    }
	
	 /**
     * 
     * @author 
     * @param query
     * @return
     */
    private static String[] columnsSplit(String query) {
        int start = 7;
        int end = query.indexOf(" FROM");
        String columnsWithComma = query.substring(start, end);
        return columnsWithComma.split(",");

    }
	/**
	 * getTCExecuteStatus
	 * This function is to get Test Case runmode from excel file using sql queries.
	 * @param sheetPath The path of the excel workbook
	 * @param strTestSuiteName The name of the excel sheet
	 * @param strTCName The name of the test case in excel sheet
	 * @throws FilloException 
	 */
	public String getTCExecuteStatus(String sheetPath, String strTestSuiteName, String strTCName) throws FilloException {
		   Fillo fillo=new Fillo();
	       String runMode = null;	       
	       Connection connection=fillo.getConnection(sheetPath);
	       String strQuery="Select * from "+strTestSuiteName +" where TestCaseName='"+strTCName+"'" ;
	       Recordset recordset=connection.executeQuery(strQuery);
	       if(recordset.next()){
	    	   runMode = recordset.getField("RunMode");
	       }
	       recordset.close();
	       connection.close();
	       return runMode;

	}
	
	/**
	 * getTSExecuteStatus
	 * This function is to get Test Suite runmode from excel file using sql queries.
	 * @param sheetPath The path of the excel workbook
	 * @param strTestSuiteName The name of the test suite in excel sheet
	 * @throws FilloException 
	 */

	public String getTSExecuteStatus(String sheetPath, String strTestSuiteName) throws FilloException      {
	       Fillo fillo=new Fillo();
	       String runMode = null;
	       Connection connection=fillo.getConnection(sheetPath);
	       String strQuery="Select * from DriverSheet where TestSuiteName='"+strTestSuiteName+"'" ;
	       Recordset recordset=connection.executeQuery(strQuery);
	       if(recordset.next()){
	    	   runMode = recordset.getField("RunMode");
	       }
	       recordset.close();
	       connection.close();
	       return runMode;

	}
	
	/**
	 * getErrorDetailsFromExcel
	 * This function is to get Error messages details from excel file using sql queries.
	 * @param sheetPath The path of the excel workbook
	 * @param SheetName The name of the sheet containing error details in excel sheet
	 * @param ColumnName The column names, data for which is to be fetched
	 * @return list_error_from_excel The list containing the error details row wise
	 * @throws FilloException 
	 */
	public static ArrayList<String> getErrorDetailsFromExcel(String SheetPath, String SheetName, String ColumnName) throws FilloException  {
		Fillo fillo=new Fillo();           
        String row=null;       
        Connection connection=fillo.getConnection(SheetPath);      
        String strQuery="Select "+ColumnName+" from "+SheetName ;
//        System.out.println(strQuery);        
        Recordset recordset=connection.executeQuery(strQuery);
//        System.out.println("---");
        ArrayList<String> list_error_from_excel=new ArrayList<String>();          
        ArrayList<String> keys=recordset.getFieldNames();
               while(recordset.next()){
            	   String con="";
             	  		for (String k1:keys){                 		  
             	  			row=recordset.getField(k1);
//             		 		list_error_from_excel.add(row);
             	  			con+=row+" ";
             	  		}
             	 if(!con.trim().isEmpty()){ 
             	 list_error_from_excel.add(con.trim());
             	 }
               }                   
        recordset.close();
        connection.close();
        return list_error_from_excel;
 }
	
	public ArrayList<String> getMultipleRowsOfDataFromExcel(String SheetPath, String sheetName, String columnName, String whereCriteria ) throws FilloException  {

		Fillo fillo=new Fillo();           
        String value=null;

        Connection connection=fillo.getConnection(SheetPath);           
        String strQuery="Select "+columnName+" from "+sheetName +" where "+whereCriteria ;
        /*System.out.println(strQuery);*/

        Recordset recordset=connection.executeQuery(strQuery);
        ArrayList<String> list_from_excel=new ArrayList<String>();  

        ArrayList<String> keys=recordset.getFieldNames();
               while(recordset.next()){
             	  for (String k1:keys){
             		  value=recordset.getField(k1);
             		 list_from_excel.add(value);
             	  }
               }                   
        recordset.close();
        connection.close();
        return list_from_excel;

 }
	
	public String getCellValueFromExcel(String sheetPath, String sheetName, String columnName, String whereCriteria) throws FilloException      {
	       Fillo fillo=new Fillo();
	       String cellValue = null;
	       Connection connection=fillo.getConnection(sheetPath);
	       String strQuery="Select "+columnName+" from "+sheetName+" where "+whereCriteria ;
	       Recordset recordset=connection.executeQuery(strQuery);	        
	        ArrayList<String> keys=recordset.getFieldNames();
	               while(recordset.next()){
	             	  for (String k1:keys){
	             		 cellValue=recordset.getField(k1);	        
	             	  }
	               }
	       recordset.close();
	       connection.close();
	       return cellValue;

	}
	
	public void updateCellValueIntoExcel(String sheetPath, String sheetName, String columnNameAndUpdatedValue, String whereCriteria) throws FilloException      {
	       Fillo fillo=new Fillo();
	       Connection connection=fillo.getConnection(sheetPath);

			String strQuery="Update "+sheetName+" Set "+columnNameAndUpdatedValue+" where "+whereCriteria;
			 
			connection.executeUpdate(strQuery);
			 
			connection.close();

	}
	
	public static boolean checkIfSheetExists(String sheetPath, String sheetName)    {
		try{
			Fillo fillo=new Fillo();
		
		Connection connection=fillo.getConnection(sheetPath);
		String strQuery="Select * from " + sheetName;
		Recordset recordset=connection.executeQuery(strQuery);
		recordset.close();
        connection.close();
		return true;
		}
		catch(FilloException e) {
			log.error(e);
		return false;
		}
	}
	
}
