package model;

import java.util.ArrayList;
import java.util.List;

public class CommonLogic{
     public CommonLogic() {
	}
     public int[] castInt(String[] strList) {
 		List<String> list = new ArrayList<String>();
 		int castInt = 0;
 		int n = 0;

 		if(strList == null) {
 			return null;
 		}
 		for(String strColumn :strList) {
 			if(strColumn != "") {
 				list.add(strColumn);
 			}
 			n++;
 		}
 		int [] intList = new int[list.size()];
 		n = 0;
 		for(String strVal: list) {
 			castInt = Integer.parseInt(strVal);
 			intList[n] = castInt;
 			n++;
 		}
 		return intList;
 	}
     public String[] checkParam(String[] strArray) {
 		List<String> list = new ArrayList<String>();
 		int n = 0;

 		for(String strColumn :strArray) {
 			if(strColumn != "") {
 				list.add(strColumn);
 			}
 		}
 		String [] returnStr = new String[list.size()];
 		n = 0;
 		for(String strVal: list) {
 			returnStr[n] = strVal;
 			n++;
 		}
 		return returnStr;
 	}
 	
     public List<Book> getBookList(int[] intBookIds, String[] getTitle, String[] getAuthor,
 			String[] getSalesDate, String[] getIsbn, int[] intPricess, int[] intStockss){
 		int n = 0;
 		List<Book> returnBookList = new ArrayList<>();
 		for(int intId:intBookIds) {
 			int id = intId;
 			String title = "";
 			String author = "";
 			String salesDate = "";
 			String isbn = "";
 			int price = 0;
 			int stock = 0;
 			int pageNo = 0;
 			
 			if(n < getTitle.length) {
 				title = getTitle[n];
 			}
 			
 			if(n < getAuthor.length) {
 				author = getAuthor[n];
 			}

 			if(n < getSalesDate.length) {
 				salesDate = getSalesDate[n];
 			}
 			
 			if(n < getIsbn.length) {
 				isbn = getIsbn[n];
 			}
 			
 			if(n < intPricess.length) {
 				price = intPricess[n];
 			}
 			if(n < intStockss.length) {
 				stock = intStockss[n];
 			}

 			boolean deleteflg = false;
 			Book book = new Book(id, title, author, salesDate, isbn, price, stock, deleteflg, pageNo);
 			returnBookList.add(book);
 			n++;
 		}
 		return returnBookList;
 	}

}
