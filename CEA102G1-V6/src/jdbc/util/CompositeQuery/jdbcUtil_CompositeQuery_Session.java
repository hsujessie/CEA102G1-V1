package jdbc.util.CompositeQuery;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class jdbcUtil_CompositeQuery_Session {
	public static String get_aCondition_For_MySql(String columnName, String value) {
		String aCondition = null;
//		System.out.println("columnName: " + columnName);
//		System.out.println("value: " + value);
		
		if("movNo".equals(columnName)) {
			columnName = "mov_no";
			aCondition = columnName + "=" + value;
		}
		else if("sesDateBegin".equals(columnName)) {
			columnName = "ses_date";
			aCondition = columnName + " >= " + "'" + value + "'" ;
		}
		else if("sesDateEnd".equals(columnName)) {
			columnName = "ses_date";
			aCondition = columnName + " <= " + "'" + value + "'" ;
		}

		return aCondition + " ";
	}
	
	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for(String key : keys) {
			String value = map.get(key)[0];
			if(value != null && value.trim().length() != 0 && !"action".contentEquals(key)) {
				count++;
				String aCondition = get_aCondition_For_MySql(key, value.trim());
				
				if(count == 1) {
					whereCondition.append(" where " + aCondition);
				}else {
					whereCondition.append(" and " + aCondition);
				}

//				System.out.println("有送出查詢資料的欄位數count = " + count);
			}
		}
		
		return whereCondition.toString();
	}

	public static void main(String args[]) {
		// 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("mov_no", new String[] { "1" });
		map.put("action", new String[] { "getXXX" }); // 注意Map裡面會含有action的key

		String finalSQL = "select * from session "
			       		   + jdbcUtil_CompositeQuery_Session.get_WhereCondition(map)
			       		   + " order by ses_time";
		System.out.println("●●finalSQL (main) = " + finalSQL);
	}

}
