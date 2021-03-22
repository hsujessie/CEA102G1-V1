package jdbc.util.CompositeQuery;

import java.util.Map;
import java.util.Set;


public class jdbcUtil_CompositeQuery_Admin {
	

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;

		if ("adm_status".equals(columnName)) // 用於其他
			aCondition = columnName + "=" + value;
		else if ("adm_name".equals(columnName) || "adm_account".equals(columnName)) // 用於varchar
			aCondition = columnName + " like '%" + value + "%'";

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
			}
		}
		
		return whereCondition.toString();
	}
	
}
