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
        public final static String MAIL_SMTP_AUTH="mail.smtp.auth";
        public final static String MAIL_SMTP_STARTTLS_ENABLE="mail.smtp.starttls.enable";
        public final static String MAIL_SMTP_HOST="mail.smtp.host";
        public final static String MAIL_SMTP_PORT="mail.smtp.port";
        public final static String MAIL_SMTP_DEBUG="mail.smtp.debug";
        public final static String MAIL_SMTP_SOCKET_FACTORY_PORT="mail.smtp.socketFactory.port";
        public final static String MAIL_SMTP_SOCKET_FACTORY_CLASS="mail.smtp.socketFactory.class";
        public final static String MAIL_SMTP_SOCKET_FACTORY_FALLBACK="mail.smtp.socketFactory.fallback";
    }

    public static class Notification{
        public final static String ID_FIELD_NAME = "id";
        public final static String BODY_FIELD_NAME = "body";
        public final static String CLICK_ACTION_FIELD_NAME = "clickAction";
        public final static String ICON_FIELD_NAME = "icon";
        public final static String TITLE_FIELD_NAME = "title";
        public final static String ACTION_FIELD_NAME = "action";

        public final static String NEW_BOOKING = "NEW_BOOKING";
        public final static String STATIC_NEW_BOOKING_MESSAGE = "Lịch hẹn mới bởi: ";
        public final static String CONFIRM_BOOKING = "CONFIRM_BOOKING";
        public final static String STATIC_CONFIRM_BOOKING_MESSAGE = "Lịch hẹn được xác nhận bởi: ";
        public final static String NEW_CONTRACT = "NEW_CONTRACT";
        public final static String STATIC_NEW_CONTRACT_MESSAGE = "Hợp đồng (nháp) được tạo bởi: ";
        public final static String NEW_RESERVED = "NEW_RESERVED";
        public final static String STATIC_NEW_RESERVED_MESSAGE = "Thông tin cọc (nháp) được tạo bởi: ";
        public final static String UPDATE_CONTRACT = "UPDATE_CONTRACT";
        public final static String STATIC_UPDATE_CONTRACT_MESSAGE = "Hợp đồng (nháp) được cập nhật bởi: ";
        public final static String UPDATE_RESERVED = "UPDATE_RESERVED";
        public final static String STATIC_UPDATE_RESERVED_MESSAGE = "Thông tin cọc (nháp) được cập nhật bởi: ";
        public final static String CONFIRM_CONTRACT = "CONFIRM_CONTRACT";
        public final static String STATIC_CONFIRM_CONTRACT_MESSAGE = "Hợp đồng được kích hoạt bởi: ";
        public final static String CONFIRM_RESERVED = "CONFIRM_RESERVED";
        public final static String STATIC_CONFIRM_RESERVED_MESSAGE = "Thông tin cọc được xác nhận bởi: ";
        public final static String CANCELLED_CONTRACT = "CANCELLED_CONTRACT";
        public final static String STATIC_CANCELLED_CONTRACT_MESSAGE = "Hợp đồng bị chấm dứt bởi: ";
    }

    public static class Field{
        public final static String BOOKING_ID = "bookingId";
        public final static String CONTRACT_ID= "contractId";

    }
}
