package com.dislinkt.accountsapi.constants;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;

import com.dislinkt.accountsapi.domain.account.Gender;

public class AccountConstants {
	
	public static final Long DB_ACCOUNT_ID_1 = 1L;
	public static final String DB_ACCOUNT_NAME_1 = "Jane J";
	public static final String DB_ACCOUNT_EMAIL_1 = "strawberry@gmail.com";
	public static final String DB_ACCOUNT_UUID_1 = "7c20fb12-40d8-4322-ba33-9c05203868e9";
	public static final String DB_ACCOUNT_USERNAME_1 = "strawberry";
	
	public static final Boolean DB_ACCOUNT_IS_PUBLIC_1 = true;
	public static final LocalDateTime DB_ACCOUNT_DATE_OF_BIRTH_1 = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0, 0);
	public static final String DB_ACCOUNT_BIOGRAPHY_1 = "living in Belgradde";
	public static final Gender DB_ACCOUNT_GENDER_1 = Gender.FEMALE;

	
	public static final Long DB_ACCOUNT_ID_2 = 2L;
	public static final String DB_ACCOUNT_NAME_2 = "Claudia C";
	public static final String DB_ACCOUNT_UUID_2 = "7c20fb12-50d8-4322-ba33-9c05203868e9";
	public static final String DB_ACCOUNT_EMAIL_2 = "coco@gmail.com";
	public static final String DB_ACCOUNT_USERNAME_2 = "coco";
	public static final boolean DB_ACCOUNT_IS_PUBLIC_2 = true;
	
	// Elon Musk private
	public static final String DB_ACCOUNT_UUID_3 = "7c20fb12-60d8-4322-ba33-9c05203868e9";
	public static final String DB_ACCOUNT_USERNAME_3 = "spacex";
	
	public static final String DB_ACCOUNT_UUID_4 = "7c20fb12-70d8-4322-ba33-9c05203868e9";
	
	public static final Long NEW_ACCOUNT_ID= 5L;
	public static final String NEW_ACCOUNT_EMAIL = "newaccount@gmail.com";
	public static final String NEW_ACCOUNT_NAME = "New Account";
	public static final String NEW_ACCOUNT_USERNAME = "newaccount";
	public static final String NEW_ACCOUNT_PHONE = "00000000";
	public static final String NEW_ACCOUNT_UUID = "6c20fb12-50d8-4322-ba33-9c05203868e9";
	public static final LocalDateTime NEW_ACCOUNT_DATE_OF_BIRTH = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0, 0);
	public static final Gender NEW_ACCOUNT_GENDER = Gender.MALE;
	public static final boolean NEW_ACCOUNT_IS_PUBLIC = true;
	
	public static final String DB_ACCOUNT_EMAIL_DoesntExist = "doesntexist@gmail.com";
	public static final int DB_ACCOUNT_SIZE = 4;
	public static final String DB_ACCOUNT_UUID_DoesntExist = "7c20fb12-50d8-4322-ba33-99999999";

	public static final Long  DB_ACCOUNT_ID_DoesntActive = 7L;
	
	public static final String NEW_BIOGRAPHY = "living in Novi Sad";
	
	public static final Integer PAGEABLE_PAGE = 0;
    public static final Integer PAGEABLE_SIZE = 5;
	

}
