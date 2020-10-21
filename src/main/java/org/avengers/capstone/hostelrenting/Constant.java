package org.avengers.capstone.hostelrenting;

public class Constant {
    public static class Message {
        /**
         * Example: Province has been created successfully
         */
        public static final String CREATE_SUCCESS = "%s has been created successfully";
        /**
         * Example: Province has been updated successfully
         */
        public static final String UPDATE_SUCCESS = "%s has been updated successfully";
        /**
         * Example: Province has been retrieved successfully
         */
        public static final String GET_SUCCESS = "%s(s) has been retrieved successfully";

        /*Error message*/

        public static final String DATABASE_ERROR = "Database error";
        /**
         * combine with {@link org.avengers.capstone.hostelrenting.exception.EntityNotFoundException}
         * Example: Province was not found for parameters {id=9}
         */
        public static final String NOT_FOUND_ERROR = " was not found for parameters ";

        public static final String DUPLICATED_ERROR = "Key (%s)=(%s) already exists";
    }

    public static class Pagination {
        public static final String DEFAULT_SIZE = "50";
        public static final String DEFAULT_PAGE = "1";
    }

    /**
     * Radius of earth in miles
     */
    public static final double EARTH_RADIUS = 6371;

    /**
     * distance to collect hostelgroups
     */
    public static final double DEFAULT_RANGE = 5;

    public static class Contract {
        public final static String UNICODE_TRUETYPE_SOURCE_SANS_PRO = "'Source Sans Pro'";
        public final static String TEMPLATE_NAME = "contract_template";
        public final static String VENDOR_NAME = "vendorName";
        public final static String VENDOR_YEAR_OF_BIRTH = "vendorYob";
        public final static String VENDOR_ID_NUMBER = "vendorIdNumber";
        public final static String VENDOR_ID_ISSUED_DATE = "vendorIdIssuedDate";
        public final static String VENDOR_ID_ISSUED_LOCATION = "vendorIdIssuedLocation";
        public final static String VENDOR_HOUSEHOLD_ADDRESS = "vendorHouseholdAddress";
        public final static String VENDOR_CURRENT_ADDRESS = "vendorCurrentAddress";
        public final static String VENDOR_PHONE_NUMBER = "vendorPhoneNumber";
        public final static String DATE_TIME_STRING_PATTERN = "%s/%s/%-4s";
    }
    public static class Extension{
        public final static String HTML = ".html";
        public final static String PDF = ".pdf";
    }
    public static class Symbol{
        public final static String UNDERSCORE = "_";
    }
    public static class ContentType{
        public final static String PDF = "application/pdf";
    }
}
