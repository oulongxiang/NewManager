package apps.commons.util.tool_util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

/**
 * 自定义List排序
 * @author ZERO
 *
 */
public class ComparatorUtil implements Comparator<Map<String,Object>>{

	/**
	 * 排序字段，map中的key
	 */
	private String sortType;
	/**
	 * 排序方式：1升序，0/其他 降序
	 */
	private String descType;
	
	public ComparatorUtil(String sortType, String descType){
		this.sortType = sortType;
		this.descType = descType;
	}
	
	@Override
	public int compare(Map<String, Object> m1,
			Map<String, Object> m2) {
		Object v1 = m1.get(sortType);
		Object v2 = m2.get(sortType);
		int flag = "1".equals(descType) ? 1 : -1;
		if(v1 instanceof Integer){//数值排序
			return ((Integer)v1 > (Integer)v2 ? 1 : -1) * flag;
		}
		if(sortType.indexOf("date") > -1){//时间排序
			DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				Date d1 = fmt.parse((String) v1.toString());
				Date d2 = fmt.parse((String) v2.toString());
				return (int) ((d1.getTime() > d2.getTime() ? 1 : -1) * flag);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
