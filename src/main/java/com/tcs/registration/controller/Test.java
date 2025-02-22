package com.tcs.registration.controller;

public class Test {
    public static void main(String[] args) {
        // Test the calculateDays function
        System.out.println(calculateDays("01/01/2020", "01/01/2021")); // 366
        System.out.println(calculateDays("01/2020", "01/01/2022")); // 731
        System.out.println(calculateDays("01/01/2020", "01/01/2023")); // 1096
        System.out.println(calculateDays("01/01/2020", "01/01/2024")); // 1461
        System.out.println(calculateDays("01/01/2020", "01/01/2025")); // 1826
        // Test the calculateAge function
        System.out.println(calculateAge("01/01/2000")); // 21 
        // now 2025 is the current year so 2025 - 2000 = 25 but i was expecting year result is 24 but 21 is coming
        // so the calculateAge function is not working properly, please correct it 
        System.out.println(calculateAge("01/01/2010")); // 15
        System.out.println(calculateAge("01/01/2005")); // 20   
        System.out.println(calculateAge("01/01/1995")); // 30
        System.out.println(calculateAge("01/01/1990")); // 31
        System.out.println(calculateAge("01/01/1980")); // 41
        System.out.println(calculateAge("01/01/1970")); // 51
        
    }

    // function that calculates between 2 days
    public static int calculateDays(String date1, String date2) {
        int day1 = Integer.parseInt(date1.substring(0, 2));
        int month1 = Integer.parseInt(date1.substring(3, 5));
        int year1 = Integer.parseInt(date1.substring(6, 10));
        int day2 = Integer.parseInt(date2.substring(0, 2));
        int month2 = Integer.parseInt(date2.substring(3, 5));
        int year2 = Integer.parseInt(date2.substring(6, 10));
        int days = 0;
        int[] monthDays = {31,28,31,30,31,30,31,31,30,31,30,31};
        if(year1 == year2) {
            if(month1 == month2) {
                days = day2 - day1;
            } else {
                days = monthDays[month1 - 1] - day1 + day2;
                for(int i = month1; i < month2 - 1; i++) {
                    days += monthDays[i];
                }
            }
        } else {
            days = monthDays[month1 - 1] - day1 + day2;
            for(int i = month1; i < 12; i++) {
                days += monthDays[i];
            }
            for(int i = year1 + 1; i < year2; i++) {
                if(i % 4 == 0) {
                    days += 366;
                } else {
                    days += 365;
                }
            }
            for(int i = 0; i < month2 - 1; i++) {
                days += monthDays[i];
            }
        }
        return days;
    }

    // Function that calculates the age of a person
    public static int calculateAge(String date) {
        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6, 10));
        int days = 0;
        int[] monthDays = {31,28,31,30,31,30,31,31,30,31,30,31};
        if(year == 2021) {
            if(month == 1) {
                days = 31 - day;
            } else {
                days = monthDays[month - 1] - day;
                for(int i = month; i < 12; i++) {
                    days += monthDays[i];
                }
            }
        } else {
            days = monthDays[month - 1] - day;
            for(int i = month; i < 12; i++) {
                days += monthDays[i];
            }
            for(int i = year + 1; i < 2021; i++) {
                if(i % 4 == 0) {
                    days += 366;
                } else {
                    days += 365;
                }
            }
        }
        return days / 365;
    }
 

    


    
}
