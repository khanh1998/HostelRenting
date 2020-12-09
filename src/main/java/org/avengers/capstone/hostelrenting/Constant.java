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
        /* VENDOR INFORMATION*/
        public final static String VENDOR_NAME = "vendorName";
        public final static String VENDOR_YEAR_OF_BIRTH = "vendorYob";
        public final static String VENDOR_ID_NUMBER = "vendorIdNumber";
        public final static String VENDOR_ID_ISSUED_DATE = "vendorIdIssuedDate";
        public final static String VENDOR_ID_ISSUED_LOCATION = "vendorIdIssuedLocation";
        public final static String VENDOR_HOUSEHOLD_ADDRESS = "vendorHouseholdAddress";
        public final static String VENDOR_CURRENT_ADDRESS = "vendorCurrentAddress";
        public final static String VENDOR_PHONE_NUMBER = "vendorPhoneNumber";

        /* RENTER INFORMATION*/
        public final static String RENTER_NAME = "renterName";
        public final static String RENTER_YEAR_OF_BIRTH = "renterYob";
        public final static String RENTER_ID_NUMBER = "renterIdNumber";
        public final static String RENTER_ID_ISSUED_DATE = "renterIdIssuedDate";
        public final static String RENTER_ID_ISSUED_LOCATION = "renterIdIssuedLocation";
        public final static String RENTER_HOUSEHOLD_ADDRESS = "renterHouseholdAddress";
        public final static String RENTER_CURRENT_ADDRESS = "renterCurrentAddress";
        public final static String RENTER_PHONE_NUMBER = "renterPhoneNumber";
        public final static String RENTER_SCHOOL_NAME = "renterSchoolName";
        public final static String RENTER_SCHOOL_ADDRESS = "renterSchoolAddress";

        /* CONTRACT INFORMATION */
        public final static String DURATION = "contractDuration";
        public final static String START_DAY = "contractStartDay";
        public final static String START_MONTH = "contractStartMonth";
        public final static String START_YEAR = "contractStartYear";

        public final static String END_DAY = "contractEndDay";
        public final static String END_MONTH = "contractEndMonth";
        public final static String END_YEAR = "contractEndYear";

        public final static String CURRENT_DAY = "currentDay";
        public final static String CURRENT_MONTH = "currentMonth";
        public final static String CURRENT_YEAR = "currentYear";

        public final static String ROOM_NUMBER = "roomNumber";
        public final static String GROUP_SUPERFICIALITY = "groupSuperficiality";
        public final static String RENTING_PRICE = "rentingPrice";
        public final static String TYPE_DEPOSIT = "typeDeposit";

        public final static String APPENDIX_CONTRACT = "appendixContract";
        public final static String PAYMENT_DAY_IN_MONTH = "paymentDayInMonth";



        /* GROUP INFORMATION */
        public final static String ADDRESS = "groupAddress";

        public final static String DATE_TIME_STRING_PATTERN = "%s/%s/%-4s";
    }

    public static class Extension {
        public final static String HTML = ".html";
        public final static String PDF = ".pdf";
    }

    public static class Symbol {
        public final static String UNDERSCORE = "_";
    }

    public static class ContentType {
        public final static String PDF = "application/pdf";
    }

    public static class Mail {
        public final static String MAIL_SMTP_AUTH = "mail.smtp.auth";
        public final static String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
        public final static String MAIL_SMTP_HOST = "mail.smtp.host";
        public final static String MAIL_SMTP_PORT = "mail.smtp.port";
        public final static String MAIL_SMTP_DEBUG = "mail.smtp.debug";
        public final static String MAIL_SMTP_SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";
        public final static String MAIL_SMTP_SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";
        public final static String MAIL_SMTP_SOCKET_FACTORY_FALLBACK = "mail.smtp.socketFactory.fallback";
    }

    public static class Format{
        public final static String DATE_TIME_DEFAULT = "yyyy/MM/dd HH:mm:ss";
    }
}
