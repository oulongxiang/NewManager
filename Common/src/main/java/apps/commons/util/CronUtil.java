package apps.commons.util;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.pattern.CronPatternUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * cron工具
 *
 * @author zengyu
 * @date 2021-09-17 14:15
 **/
public class CronUtil {

    /***
     * 获取cron表达式的时间间隔 (单位-分钟)
     * @author zengyu
     * @date 2021年09月17日 14:16
     * @param cron cron 表达式
     * @return 时间间隔 (单位-分钟)
     */
    public static long getIntervalToMin(String cron){
        if(StrUtil.isBlank(cron)){
            return 0;
        }
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 2);
        List<Date> dates = CronPatternUtil.matchedDates(cron, new Date(), cal.getTime(), 2, false);
        if(IterUtil.isNotEmpty(dates) && dates.size()==2){
            return DateUtil.between(dates.get(0), dates.get(1), DateUnit.MINUTE);
        }
        return 0;
    }
}
