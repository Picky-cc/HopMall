package com.zufangbao.gluon.spec.global;

/**
 * 
 * @author wukai
 *
 */
public class GlobalCodeSpec {
	
	public static final int CODE_SUC = 0;
	public static final int CODE_FAILURE = -1;
	
	public static class GeneralErrorCode{

		public static final int ERROR_NO_AUTHORITY = -2;
		public static final int ERROR_NETWORK_FAILURE = ERROR_NO_AUTHORITY-1;
		public static final int ERROR_ILLEGAL_PARAM = ERROR_NETWORK_FAILURE - 1;
		public static final int ERROR_SYSTEM = ERROR_ILLEGAL_PARAM - 1;
		public static final int ERROR_NO_RESOURCE = ERROR_SYSTEM-1;
		public static final int ERROR_JSON_PARSE_NULL = ERROR_SYSTEM-1;
		public static final int ERROR_FORMAT = ERROR_JSON_PARSE_NULL-1;
		public static final int ERROR_READ_TIME_OUT = ERROR_FORMAT-1;
		public static final int ERROR_NO_OBJECT_IN_DB = ERROR_READ_TIME_OUT - 1;
		public static final int ERROR_BAD_REQUEST = ERROR_NO_OBJECT_IN_DB -1;
	}

	public class VerifyCodeSpec{
		private static final int VERIFYCODE = -100;
		public static final int REQUEST_ILLEGAL = VERIFYCODE-1;
		public static final int SEND_FAIL = REQUEST_ILLEGAL-1;
	}
	public class CompareResult {
		public static final int LESS_THAN = -1;
		public static final int EQUAL = 0;
		public static final int GREATER_THAN = 1;
	}
	public class DataErrorCode{
		public static final long ERROR = -1;
		public static final long ERROR_NO_APP = ERROR-1;
	}
}
