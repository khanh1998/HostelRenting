package org.avengers.capstone.hostelrenting;

public class Constant {
    public static class Message{
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
    public static class Pagination{
        public static final String DEFAULT_SIZE = "50";
        public static final String DEFAULT_PAGE = "1";
    }
}
