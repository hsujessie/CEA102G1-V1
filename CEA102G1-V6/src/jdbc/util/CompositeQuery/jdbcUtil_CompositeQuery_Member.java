


package jdbc.util.CompositeQuery;

import java.util.*;

public class jdbcUtil_CompositeQuery_Member {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;

		if ("mem_No".equals(columnName))  
			aCondition = columnName + "=" + value;
		else if("mem_Name".equals(columnName) || "mem_Account".equals(columnName) || "mem_Mail".equals(columnName)) 
			aCondition = columnName + " like '%" + value + "%'";
//		else if ("hiredate".equals(columnName))                         
//			aCondition = "to_char(" + columnName + ",'yyyy-mm-dd')='" + value + "'";
		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());

				if (count == 1)
					whereCondition.append(" where " + aCondition);
				else
					whereCondition.append(" and " + aCondition);

				System.out.println("有送出查詢資料的欄位數count = " + count);
			}
		}
		
		return whereCondition.toString();
	}
	
//	<測試用>
//	public static void main(String argv[]) {

		
//		Map<String, String[]> map = new TreeMap<String, String[]>();
//		map.put("mem_No", new String[] { "1" });
//		map.put("mem_Name", new String[] { "Jack" });
//		map.put("mem_Account", new String[] { "abc" });
//		map.put("mem_Mail", new String[] { "qwe@gmail.com" });
//		map.put("sal", new String[] { "5000.5" });
//		map.put("comm", new String[] { "0.0" });
//		map.put("deptno", new String[] { "10" });
//		map.put("action", new String[] { "getXXX" });

//		String finalSQL = "select * from member"
//				          + jdbcUtil_CompositeQuery_Member.get_WhereCondition(map)
//				          + "order by memNo";
//		System.out.println("●●finalSQL (main) = " + finalSQL);
//
//	}
}
