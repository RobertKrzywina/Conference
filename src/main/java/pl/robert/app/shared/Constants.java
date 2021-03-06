package pl.robert.app.shared;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Conference {

        public static final int COL_LENGTH_NAME = 50;
        public static final int COL_LENGTH_DETAILS = 120;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Lecture {

        public static final int COL_LENGTH_NAME = 100;
        public static final int COL_LENGTH_LECTURER = 30;
        public static final int COL_LENGTH_TYPE = 11;
        public static final int COL_LENGTH_DAY = 2;
        public static final int COL_LENGTH_TIME = 11;

        public static final Pattern LECTURE_ID_FORMAT_REGEX =
                Pattern.compile("^-?\\d+$", Pattern.CASE_INSENSITIVE);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class User {

        public static final int COL_LENGTH_NAME = 15;

        public static final Pattern EMAIL_REGEX =
                Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*" +
                        "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    }
}
