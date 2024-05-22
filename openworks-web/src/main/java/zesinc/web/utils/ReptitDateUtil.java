/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import zesinc.core.lang.Validate;

/**
 * 년월일 문자열을 인자로 시작 년월일~종료 년월일일 사이의 모든 일자 목록을
 * 반환하는 기능을 주로하며, 부가 기능으로 년 월 주 단위의 함수로 이루어져 있다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 12. 9.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class ReptitDateUtil {

    /**
     * 현재 시간
     * 
     * @return
     */
    public static String getToHour() {

        SimpleDateFormat sdf = new SimpleDateFormat("HH");

        return sdf.format(new Date());
    }

    /**
     * 오늘 날짜를 yyyy-MM-dd 형식으로 반환
     * 
     * @return
     */
    public static String getToday() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(new Date());
    }

    /**
     * 문자열 날짜를 받아서 value 일자값 만큼 날짜를 더한 후의 날짜문자열 반환
     * 
     * @param date
     * @param value
     * @return
     */
    public static String getCalculationDay(String date, Integer value) {

        return getCalculationDay(date, value, null);
    }

    /**
     * 문자열 날짜를 받아서 value 일자값 만큼 날짜를 더한 후의 날짜문자열 반환
     * 
     * @param date
     * @param value
     * @param cal
     * @return
     */
    public static String getCalculationDay(String date, Integer value, Calendar cal) {

        if(cal == null) {
            cal = Calendar.getInstance();
        }

        if(Validate.isNotEmpty(date)) {
            String[] dateArr = date.split("-");

            int paramYear = Integer.parseInt(dateArr[0]);
            int paramMonth = Integer.parseInt(dateArr[1]) - 1;
            int paramDay = Integer.parseInt(dateArr[2]);

            cal.set(paramYear, paramMonth, paramDay);
            cal.add(Calendar.DATE, value);
        }

        Integer year = cal.get(Calendar.YEAR);
        Integer month = cal.get(Calendar.MONTH) + 1;
        Integer day = cal.get(Calendar.DATE);

        StringBuilder sb = new StringBuilder();
        sb.append(year).append("-").append(addZero(month)).append("-").append(addZero(day));

        return sb.toString();
    }

    /**
     * 해당일에서 년도를 반환
     * 
     * @return
     */
    public static String getYear(String date) {
        return getYear(date, null);
    }

    /**
     * 해당일에서 년도를 반환
     * 
     * @return
     */
    public static String getYear(String date, Calendar cal) {

        if(cal == null) {
            cal = Calendar.getInstance();
        }

        if(Validate.isNotEmpty(date)) {
            String[] dateArr = date.split("-");

            int paramYear = Integer.parseInt(dateArr[0]);
            int paramMonth = Integer.parseInt(dateArr[1]) - 1;
            int paramDay = Integer.parseInt(dateArr[2]);

            cal.set(paramYear, paramMonth, paramDay);
        }

        Integer year = cal.get(Calendar.YEAR);

        return year.toString();
    }

    /**
     * 해당일에서 월을 반환
     * 
     * @return
     */
    public static String getMonth(String date) {
        return getMonth(date, null);
    }

    /**
     * 해당일에서 월을 반환
     * 
     * @return
     */
    public static String getMonth(String date, Calendar cal) {

        if(cal == null) {
            cal = Calendar.getInstance();
        }

        if(Validate.isNotEmpty(date)) {
            String[] dateArr = date.split("-");

            int paramYear = Integer.parseInt(dateArr[0]);
            int paramMonth = Integer.parseInt(dateArr[1]) - 1;
            int paramDay = Integer.parseInt(dateArr[2]);

            cal.set(paramYear, paramMonth, paramDay);
        }

        Integer month = cal.get(Calendar.MONTH) + 1;

        return addZero(month);
    }

    /**
     * 해당일에서 일을 반환
     * 
     * @return
     */
    public static Integer getDay(String date) {
        return getDay(date, null);
    }

    /**
     * 해당일에서 일을 반환
     * 
     * @return
     */
    public static Integer getDay(String date, Calendar cal) {

        if(cal == null) {
            cal = Calendar.getInstance();
        }

        if(Validate.isNotEmpty(date)) {
            String[] dateArr = date.split("-");

            int paramYear = Integer.parseInt(dateArr[0]);
            int paramMonth = Integer.parseInt(dateArr[1]) - 1;
            int paramDay = Integer.parseInt(dateArr[2]);

            cal.set(paramYear, paramMonth, paramDay);
        }

        Integer day = cal.get(Calendar.DATE);

        return day;
    }

    /**
     * 지정한 날짜의 요일의 식별 번호 을 반환
     * date 인자 값이 null 인경우 오늘의 주 식별 번호를 반환
     * 일요일부터 토요일까지 1~7 숫자로 반환
     * 
     * @return
     */
    public static Integer getDayWeek(String date) {
        return getDayWeek(date, null);
    }

    /**
     * 지정한 날짜의 요일의 식별 번호 을 반환
     * date 인자 값이 null 인경우 오늘의 주 식별 번호를 반환
     * 일요일부터 토요일까지 1~7 숫자로 반환
     * 
     * @return
     */
    public static Integer getDayWeek(String date, Calendar cal) {

        if(cal == null) {
            cal = Calendar.getInstance();
        }

        if(Validate.isNotEmpty(date)) {
            String[] dateArr = date.split("-");

            int paramYear = Integer.parseInt(dateArr[0]);
            int paramMonth = Integer.parseInt(dateArr[1]) - 1;
            int paramDay = Integer.parseInt(dateArr[2]);

            cal.set(paramYear, paramMonth, paramDay);
        }

        Integer week = cal.get(Calendar.DAY_OF_WEEK);

        return week;
    }

    /**
     * 해당월의 시작 날짜
     * 
     * @param date
     * @return
     */
    public static String getMinMonthDate(String date) {
        return getMinMonthDate(date, null);
    }

    /**
     * 해당월의 시작 날짜
     * 
     * @param date
     * @return
     */
    public static String getMinMonthDate(String date, Calendar cal) {

        if(cal == null) {
            cal = Calendar.getInstance();
        }

        String[] dateArr = date.split("-");

        int paramYear = Integer.parseInt(dateArr[0]);
        int paramMonth = Integer.parseInt(dateArr[1]) - 1;
        int paramDay = Integer.parseInt(dateArr[2]);

        cal.set(paramYear, paramMonth, paramDay);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int minDay = cal.getActualMinimum(Calendar.DATE);

        StringBuilder sb = new StringBuilder();
        sb.append(year).append("-").append(addZero(month)).append("-").append(addZero(minDay));

        return sb.toString();
    }

    /**
     * 해당 월의 마지막 날짜
     * 
     * @param date
     * @return
     */
    public static String getMaxMonthDate(String date) {
        return getMaxMonthDate(date, null);
    }

    /**
     * 해당 월의 마지막 날짜
     * 
     * @param date
     * @return
     */
    public static String getMaxMonthDate(String date, Calendar cal) {

        if(cal == null) {
            cal = Calendar.getInstance();
        }

        String[] dateArr = date.split("-");

        int paramYear = Integer.parseInt(dateArr[0]);
        int paramMonth = Integer.parseInt(dateArr[1]) - 1;
        int paramDay = Integer.parseInt(dateArr[2]);

        cal.set(paramYear, paramMonth, paramDay);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int maxDay = cal.getActualMaximum(Calendar.DATE);

        StringBuilder sb = new StringBuilder();
        sb.append(year).append("-").append(addZero(month)).append("-").append(addZero(maxDay));

        return sb.toString();
    }

    /**
     * 해당 주의 시작 날짜
     * 
     * @param date
     * @return
     */
    public static String getMinWeekDate(String date) {
        return getMinWeekDate(date, null);
    }

    /**
     * 해당 주의 시작 날짜
     * 
     * @param date
     * @return
     */
    public static String getMinWeekDate(String date, Calendar cal) {

        if(cal == null) {
            cal = Calendar.getInstance();
        }

        String[] dateArr = date.split("-");

        int paramYear = Integer.parseInt(dateArr[0]);
        int paramMonth = Integer.parseInt(dateArr[1]) - 1;
        int paramDay = Integer.parseInt(dateArr[2]);

        cal.set(paramYear, paramMonth, paramDay);

        cal.get(Calendar.DATE);
        cal.set(Calendar.DAY_OF_WEEK, 1);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int minDay = cal.get(Calendar.DATE);

        StringBuilder sb = new StringBuilder();
        sb.append(year).append("-").append(addZero(month)).append("-").append(addZero(minDay));

        return sb.toString();
    }

    /**
     * 해당 주의 마지막 날짜
     * 
     * @param startDate
     * @return
     */
    public static String getMaxWeekDate(String date) {
        return getMaxWeekDate(date, null);
    }

    /**
     * 해당 주의 마지막 날짜
     * 
     * @param startDate
     * @return
     */
    public static String getMaxWeekDate(String date, Calendar cal) {

        if(cal == null) {
            cal = Calendar.getInstance();
        }

        String[] dateArr = date.split("-");

        int paramYear = Integer.parseInt(dateArr[0]);
        int paramMonth = Integer.parseInt(dateArr[1]) - 1;
        int paramDay = Integer.parseInt(dateArr[2]);

        cal.set(paramYear, paramMonth, paramDay);

        cal.get(Calendar.DATE);

        cal.set(Calendar.DAY_OF_WEEK, 7);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int maxDay = cal.get(Calendar.DATE);

        StringBuilder sb = new StringBuilder();
        sb.append(year).append("-").append(addZero(month)).append("-").append(addZero(maxDay));

        return sb.toString();
    }

    /**
     * 인자의 date를 기준으로 이전 prvYearCnt 년~이후 nextYearCnt 년을 년도만 목록으로 반환한다.
     * 둘다 양수만 사용하며, 0보다 작은 값이 들어오는 경우 무시한다.
     * 
     * @param date
     * @param prvYearCnt date 인자의 년도에서 이전 몇년을 구할지 지정
     * @param nextYearCnt date 인자의 년도에서 이후 몇년을 구할지 지정
     * @return
     */
    public static List<String> getRangeYearList(String date, int prvYearCnt, int nextYearCnt) {

        Calendar cal = getCalendar(date);
        // 현재 기준 년도
        int nowYear = cal.get(Calendar.YEAR);
        int prvYear = nowYear;
        int nextYear = nowYear;

        // 이전년도
        if(prvYearCnt > 0) {
            int tempYearCnt = prvYearCnt * -1;
            cal.add(Calendar.YEAR, tempYearCnt);
            prvYear = cal.get(Calendar.YEAR);
        }
        // 이후년도
        if(prvYearCnt > 0) {
            cal.add(Calendar.YEAR, nextYearCnt + prvYearCnt);
            nextYear = cal.get(Calendar.YEAR);
        }

        List<String> yearList = new ArrayList<String>();
        for(int i = prvYear ; i <= nextYear ; i++) {
            yearList.add(String.valueOf(i));
        }

        return yearList;
    }

    /**
     * 01~12 까지 월에 해당하는 목록을 반환한다. 9 이하는 앞에 '0'이 붙는다.
     * 
     * @return
     */
    public static List<String> getRangeMonthList() {

        List<String> monthList = new ArrayList<String>();
        for(int i = 1 ; i <= 12 ; i++) {
            monthList.add(addZero(i));
        }

        return monthList;
    }

    /**
     * 00~23 까지의 문자열 목록
     * 
     * @return
     */
    public static List<String> getHH24() {
        List<String> hhList = new ArrayList<String>();

        String hh;
        for(int i = 0 ; i < 24 ; i++) {
            hh = addZero(i);

            hhList.add(hh);
        }

        return hhList;
    }

    /**
     * date가 포함된 주의 시작일부터 마지막 일까지
     * 
     * @return
     */
    public static List<String> getDayOfWeekList(String date) {
        return getDayOfWeekList(date, null);
    }

    /**
     * date가 포함된 주의 시작일부터 마지막 일까지
     * 
     * @return
     */
    public static List<String> getDayOfWeekList(String date, Calendar cal) {

        if(cal == null) {
            cal = Calendar.getInstance();
        }

        String[] dateArr = date.split("-");

        int paramYear = Integer.parseInt(dateArr[0]);
        int paramMonth = Integer.parseInt(dateArr[1]) - 1;
        int paramDay = Integer.parseInt(dateArr[2]);

        cal.set(paramYear, paramMonth, paramDay);

        cal.get(Calendar.DATE);

        cal.set(Calendar.DAY_OF_WEEK, 1);

        int bgnYear = cal.get(Calendar.YEAR);
        int bgnMonth = cal.get(Calendar.MONTH) + 1;
        int bgnDay = cal.get(Calendar.DATE);

        cal.add(Calendar.DATE, 6);
        int endYear = cal.get(Calendar.YEAR);
        int endMonth = cal.get(Calendar.MONTH) + 1;
        int endDay = cal.get(Calendar.DATE);

        StringBuilder sb = new StringBuilder();
        sb.append(bgnYear).append("-").append(addZero(bgnMonth)).append("-").append(addZero(bgnDay));

        String bgngYmd = sb.toString();

        sb = new StringBuilder();
        sb.append(endYear).append("-").append(addZero(endMonth)).append("-").append(addZero(endDay));

        String endYmd = sb.toString();

        return getRangeDateList(bgngYmd, endYmd);
    }

    /**
     * 시작 년월일부터 종료년월일까지 모든 날짜 목록을 반환.
     * 단! 해가 바뀌는 것까지만 결과에 포함됨.
     * 예:
     * 2015-01-01 ~ 2016-12-31 가능
     * 2015-12-31 ~ 2017-01-01 불가
     * 
     * @return
     */
    public static List<String> getRangeDateList(String bgngYmd, String endYmd) {
        return getRangeDateList(bgngYmd, endYmd, null);
    }

    /**
     * 시작 년월일부터 종료년월일까지 모든 날짜 목록을 반환.
     * 단! 해가 바뀌는 것까지만 결과에 포함됨.
     * 예:
     * 2015-01-01 ~ 2016-12-31 가능
     * 2015-12-31 ~ 2017-01-01 불가
     * 
     * @return
     */
    public static List<String> getRangeDateList(String bgngYmd, String endYmd, Calendar cal) {

        if(cal == null) {
            cal = Calendar.getInstance();
        }
        List<String> dayList = new ArrayList<String>();

        String[] bgngYmdArr = bgngYmd.split("-");
        String[] endYmdArr = endYmd.split("-");

        int bgnYear = Integer.parseInt(bgngYmdArr[0]);
        int bgnMonth = Integer.parseInt(bgngYmdArr[1]) - 1;
        int bgnDay = Integer.parseInt(bgngYmdArr[2]);

        int endYear = Integer.parseInt(endYmdArr[0]);
        int endMonth = Integer.parseInt(endYmdArr[1]) - 1;
        int endDay = Integer.parseInt(endYmdArr[2]);

        int[] arrBgngYmd = new int[3];
        int[] arrEndYmd = new int[3];

        if(bgnYear == endYear) {
            // 같은 해 일경우 단순 월 증가 루프를 돌림
            for(int i = bgnMonth ; i <= endMonth ; i++) {
                arrBgngYmd[0] = bgnYear;
                arrBgngYmd[1] = i;
                arrEndYmd[0] = bgnYear;
                arrEndYmd[1] = i;

                if(i == bgnMonth && i == endMonth) {
                    arrBgngYmd[2] = bgnDay;
                    arrEndYmd[2] = endDay;
                } else if(i == bgnMonth) {
                    arrBgngYmd[2] = bgnDay;
                    arrEndYmd[2] = 0;
                } else if(i == endMonth) {
                    arrBgngYmd[2] = 1;
                    arrEndYmd[2] = endDay;
                } else {
                    arrBgngYmd[2] = 1;
                    arrEndYmd[2] = 0;
                }
                dayList.addAll(getRangeDayOfMonthList(arrBgngYmd, arrEndYmd, cal));
            }
        } else {

            int lastMonth = (12 + endMonth);
            // 실제 월
            int tmpMonth = 0;

            for(int i = bgnMonth ; i <= lastMonth ; i++) {

                if(i > 11) {
                    arrBgngYmd[0] = endYear;
                    arrEndYmd[0] = endYear;
                    tmpMonth = i - 12;
                } else {
                    arrBgngYmd[0] = bgnYear;
                    arrEndYmd[0] = bgnYear;
                    tmpMonth = i;
                }
                arrBgngYmd[1] = tmpMonth;
                arrEndYmd[1] = tmpMonth;

                if(tmpMonth == bgnMonth && tmpMonth == endMonth) {
                    arrBgngYmd[2] = bgnDay;
                    arrEndYmd[2] = endDay;
                } else if(tmpMonth == bgnMonth) {
                    arrBgngYmd[2] = bgnDay;
                    arrEndYmd[2] = 0;
                } else if(tmpMonth == endMonth) {
                    arrBgngYmd[2] = 1;
                    arrEndYmd[2] = endDay;
                } else {
                    arrBgngYmd[2] = 1;
                    arrEndYmd[2] = 0;
                }

                dayList.addAll(getRangeDayOfMonthList(arrBgngYmd, arrEndYmd, cal));
            }
        }

        return dayList;
    }

    /**
     * 시작 년월일부터 종료년월일까지 매년 지정범위 날짜 목록을 반환.
     * 
     * @param bgngYmd
     * @param endYmd
     * @param cal
     * @return
     */
    public static List<String> getYearReptitDateList(String bgngYmd, String endYmd, String rpttEndYmd) {
        List<String> dayList = new ArrayList<String>();

        Calendar bgngYmdCal = getCalendar(bgngYmd);
        Calendar endYmdCal = getCalendar(endYmd);
        Calendar reptitEnddeCal = getCalendar(rpttEndYmd);

        long currTimeInMillis = bgngYmdCal.getTimeInMillis();
        long endTimeInMillis = endYmdCal.getTimeInMillis();
        long reptitEndTimeInMillis = reptitEnddeCal.getTimeInMillis();

        long termTimeInMillis = endTimeInMillis - currTimeInMillis;
        int termDay = (int) (termTimeInMillis / 86400000);

        Calendar tempCal = getCalendar(bgngYmd);

        while(currTimeInMillis <= reptitEndTimeInMillis) {
            // 두 날짜의 차이 수만큼 반복
            for(int i = 0 ; i <= termDay && currTimeInMillis <= reptitEndTimeInMillis ; i++) {
                dayList.add(tempCal.get(Calendar.YEAR) + addZero(tempCal.get(Calendar.MONTH) + 1) + addZero(tempCal.get(Calendar.DATE)));

                tempCal.add(Calendar.DATE, 1);
                currTimeInMillis = tempCal.getTimeInMillis();
            }
            // 1년 증가
            bgngYmdCal.add(Calendar.YEAR, 1);
            // 1년 증가된 원본의 년월일로 새로 설정
            tempCal.set(bgngYmdCal.get(Calendar.YEAR), bgngYmdCal.get(Calendar.MONTH), bgngYmdCal.get(Calendar.DATE));
            currTimeInMillis = tempCal.getTimeInMillis();
        }

        return dayList;
    }

    /**
     * 시작 년월일부터 종료년월일까지 매월 지정범위 날짜 목록을 반환.
     * 
     * @param bgngYmd
     * @param endYmd
     * @param cal
     * @return
     */
    public static List<String> getMonthReptitDateList(String bgngYmd, String endYmd, String rpttEndYmd) {
        List<String> dayList = new ArrayList<String>();

        Calendar bgngYmdCal = getCalendar(bgngYmd);
        Calendar endYmdCal = getCalendar(endYmd);
        Calendar reptitEnddeCal = getCalendar(rpttEndYmd);

        long currTimeInMillis = bgngYmdCal.getTimeInMillis();
        long endTimeInMillis = endYmdCal.getTimeInMillis();
        long reptitEndTimeInMillis = reptitEnddeCal.getTimeInMillis();

        long termTimeInMillis = endTimeInMillis - currTimeInMillis;
        int termDay = (int) (termTimeInMillis / 86400000);

        Calendar tempCal = getCalendar(bgngYmd);

        while(currTimeInMillis <= reptitEndTimeInMillis) {

            // 두 날짜의 차이 수만큼 반복
            for(int i = 0 ; i <= termDay && currTimeInMillis <= reptitEndTimeInMillis ; i++) {
                dayList.add(tempCal.get(Calendar.YEAR) + addZero(tempCal.get(Calendar.MONTH) + 1) + addZero(tempCal.get(Calendar.DATE)));

                tempCal.add(Calendar.DATE, 1);
                currTimeInMillis = tempCal.getTimeInMillis();
            }
            // 1개월 증가
            bgngYmdCal.add(Calendar.MONTH, 1);
            // 1개월 증가된 원본의 년월일로 새로 설정
            tempCal.set(bgngYmdCal.get(Calendar.YEAR), bgngYmdCal.get(Calendar.MONTH), bgngYmdCal.get(Calendar.DATE));
            currTimeInMillis = tempCal.getTimeInMillis();
        }

        return dayList;
    }

    /**
     * 시작 년월일부터 종료년월일까지 지정된 요일의 모든 날짜 목록을 반환.
     * 
     * @param bgngYmd
     * @param endYmd
     * @param cal
     * @return
     */
    public static List<String> getWeekReptitDateList(String bgngYmd, String endYmd, List<Integer> dayOfWeek) {

        List<String> dayList = new ArrayList<String>();

        Calendar bgngYmdCal = getCalendar(bgngYmd);
        Calendar endYmdCal = getCalendar(endYmd);

        long currTimeInMillis = bgngYmdCal.getTimeInMillis();
        long endTimeInMillis = endYmdCal.getTimeInMillis();

        while(currTimeInMillis <= endTimeInMillis) {
            int weekNum = bgngYmdCal.get(Calendar.DAY_OF_WEEK);
            if(dayOfWeek.contains(new Integer(weekNum))) {
                dayList.add(bgngYmdCal.get(Calendar.YEAR) + addZero(bgngYmdCal.get(Calendar.MONTH) + 1) + addZero(bgngYmdCal.get(Calendar.DATE)));
            }

            bgngYmdCal.add(Calendar.DATE, 1);
            currTimeInMillis = bgngYmdCal.getTimeInMillis();
        }

        return dayList;
    }

    /**
     * 시작 년월일부터 종료년월일까지 매월 지정범위 날짜 목록을 반환.
     * 
     * @param bgngYmd
     * @param endYmd
     * @param cal
     * @return
     */
    public static List<String> getDayReptitDateList(String bgngYmd, String endYmd) {
        List<String> dayList = new ArrayList<String>();

        Calendar bgngYmdCal = getCalendar(bgngYmd);
        Calendar endYmdCal = getCalendar(endYmd);

        long currTimeInMillis = bgngYmdCal.getTimeInMillis();
        long endTimeInMillis = endYmdCal.getTimeInMillis();

        long termTimeInMillis = endTimeInMillis - currTimeInMillis;
        int termDay = (int) (termTimeInMillis / 86400000);

        // 두 날짜의 차이 수만큼 반복
        for(int i = 0 ; i <= termDay && currTimeInMillis <= endTimeInMillis ; i++) {
            dayList.add(bgngYmdCal.get(Calendar.YEAR) + addZero(bgngYmdCal.get(Calendar.MONTH) + 1) + addZero(bgngYmdCal.get(Calendar.DATE)));

            bgngYmdCal.add(Calendar.DATE, 1);
            currTimeInMillis = bgngYmdCal.getTimeInMillis();
        }

        return dayList;
    }

    /**
     * 지정된 달의 시작일로부터 지정된 종료일까지 yyyy-mm-dd 문자열 목록으로 반환
     * 종료일이 0 값인 경우 달의 마지막 날짜까리 구해서 반환한다.
     * 
     * @return
     */
    public static List<String> getRangeDayOfMonthList(int[] arrBgngYmd, int[] arrEndYmd, Calendar cal) {

        if(cal == null) {
            cal = Calendar.getInstance();
        }

        List<String> dayList = new ArrayList<String>();

        int start = arrBgngYmd[2];
        int lastDay = arrEndYmd[2];

        if(lastDay == 0) {
            // 마지막 날짜가 지정되지 않으면 해당월의 마지막 날짜를 설정
            cal.set(arrBgngYmd[0], arrBgngYmd[1], arrBgngYmd[2]);
            lastDay = cal.getActualMaximum(Calendar.DATE);
        }

        String date;
        for(int i = start ; i <= lastDay ; i++) {
            date = arrBgngYmd[0] + "-" + addZero(arrBgngYmd[1] + 1) + "-" + addZero(i);
            dayList.add(date);
        }

        return dayList;
    }

    /**
     * date가 포함 된 해당월의 시작일부터 마지막 일까지
     * 
     * @return
     */
    public static List<String> getDayOfMonthList(String date) {
        return getDayOfMonthList(date, null);
    }

    /**
     * date가 포함 된 해당월의 시작일부터 마지막 일까지
     * 
     * @return
     */
    public static List<String> getDayOfMonthList(String date, Calendar cal) {

        if(cal == null) {
            cal = Calendar.getInstance();
        }
        List<String> dayList = new ArrayList<String>();

        String[] dateArr = date.split("-");

        int paramYear = Integer.parseInt(dateArr[0]);
        int paramMonth = Integer.parseInt(dateArr[1]) - 1;
        int paramDay = Integer.parseInt(dateArr[2]);

        cal.set(paramYear, paramMonth, paramDay);

        int last = cal.getActualMaximum(Calendar.DATE);

        String day;
        for(int i = 1 ; i <= last ; i++) {
            day = addZero(i);

            dayList.add(day);
        }

        return dayList;
    }

    /**
     * yyyy-mm-dd 형식의 문자열을 받아 년월일을 설정한 후 Calendar 객체를 반환한다.
     * 
     * @param date yyyy-mm-dd 형식의 문자열
     * @return
     */
    public static Calendar getCalendar(String date) {
        Calendar cal = Calendar.getInstance();

        if(Validate.isNotEmpty(date)) {
            String[] dateArr = date.split("-");
            int year = Integer.parseInt(dateArr[0]);
            int month = Integer.parseInt(dateArr[1]) - 1;
            int day = Integer.parseInt(dateArr[2]);

            cal.set(year, month, day);
        }
        return cal;
    }

    /**
     * 요일에 해당 하는 수치를 한글로 반환
     * 
     * @param week
     * @return
     */
    public static String getKrWeek(Integer week) {
        String krWeek = "";
        switch(week) {
            case 1:
                krWeek = "일";
                break;
            case 2:
                krWeek = "월";
                break;
            case 3:
                krWeek = "화";
                break;
            case 4:
                krWeek = "수";
                break;
            case 5:
                krWeek = "목";
                break;
            case 6:
                krWeek = "금";
                break;
            case 7:
                krWeek = "토";
                break;
            default:
                break;
        }
        return krWeek;
    }

    /**
     * 두자리 이하의 숫자에 0을 붙여서 반환
     * 
     * @param day
     * @return
     */
    public static String addZero(int day) {

        String str = "";
        if(day < 10) {
            str = "0" + day;
        } else {
            str = Integer.toString(day);
        }

        return str;
    }

}
