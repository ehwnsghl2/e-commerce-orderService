package com.brandjunhoe.orderservice.common.code

enum class ErrorException {

    /** 기본 서버 에러 **/
    HttpServerErrorException,

    /** HTTP 요청 에러 **/
    HttpMessageNotReadableException,

    /** 지원되지 않는 HTTP METHOD 에러 핸들러 **/
    HttpRequestMethodNotSupportedException,

    /** form validation 에러 **/
    FormValidationException,

    /** 데이터 등록 실패 에러핸들러 **/
    DataRegistrationFailedException,

    /** 임시 파일 적용 시 BODY가 없을 때 **/
    DataNotFoundException,

    /** 중복된 데이터 처리 에러 핸들러 **/
    DuplicateDataException,

    /** 데이터베이스 중복 키 에러 **/
    DuplicateKeyException,

    /** RestTemplate API 통신 에러 핸들러 **/
    ResourceAccessException,

    /** RestTemplate API 통신 에러 핸들러 **/
    RestClientException,

    /** 파일이 용량 제한 에러 **/
    MaxUploadSizeExceededException, //최대 100MB까지 등록할 수 있습니다.

    /** 파일이 용량 제한 에러 **/
    SizeLimitExceededException, //최대 100MB까지 등록할 수 있습니다.

    /** MultiPart 파일 전송 누락 에러 **/
    MissingServletRequestPartException,

    /** MultiPart 파일 전송 요청 에러 **/
    MultipartException,

    /** 프로세스를 더이상 진행할 수 없을 때 **/
    UnprocessableException,

    /** 지원되지 않는 기능에 대한 예외처리 **/
    NotSupportedException,

    /** JWT 토큰 복호화 ERROR 핸들러 **/
    InvalidSignatureException,

    /** 복호화 실패 처리 **/
    DecryptionException,

    /** 파라메터 유효성 체크 실패 에러 핸들러 **/
    ParameterValidationFailedException,

    /** 사용자 정의 필수 파라메터 누락 에러 핸들러 **/
    RequestParamRequiredException,

    /** Parameter Validation Error **/
    MissingServletRequestParameterException,

    /** Parameter Validation Error **/
    BindException,

    /** Parameter Validation Error **/
    MethodArgumentNotValidException,

    /** JSON 파싱 에러 **/
    JsonMappingException,

    /** Header 필수값 누락 에러 핸들러 **/
    MissingRequestHeaderException,

    /** 맵핑 에러 **/
    IllegalStateException,

    /** 인자값 형식 에러 **/
    IllegalArgumentException,

    /** Kotlin non-null 파라메터 예외 처리 **/
    BeanInstantiationException,

    /** JWT 토큰 인증정보 확인 예외 처리 **/
    AuthenticationException,

    /** 권한 거부 예외 처리 **/
    AccessDeniedException,

    /** FeignClient 통신 에러 */
    DecodeException,

    /** JWT 토큰 예외 처리 **/
    JwtException,

    /** 잘못된 파라메터 타입 */
    MethodArgumentTypeMismatchException,

    /** 입출력 예외 처리 **/
    IOException,

    /** 입출력 예외 처리 **/
    ClientAbortException,

    /** 엑셀 데이터 처리중 에러 **/
    ExcelComponentException,

    /** 엑셀 업로드 Field 에러 **/
    ExcelReaderFieldException,

    /** 엑셀 업로드시 읽을 수 없는 엑셀 파일 에러 **/
    ExcelReaderFileException,

    /** 엑셀 업로드시 엑셀 파일이 아닌 확장자 파일에 대한 에러 **/
    ExcelReaderFileExtentionException,

    /** 엑셀 업로드 시 캐치 하지 못한 그외 에러 **/
    ExcelReaderException,

    /** 파일이 존재하지 않을 때 **/
    FileNotExistException,

    /** 파일 등록 필수 파라메터 체크 **/
    FileRequestFileNotException,

    /** 파일 덮어쓰기 여부 확인 **/
    FileDuplicateException,

    /** 파일 등록 필수 파라메터 체크 **/
    FileRequestParamRequiredException,

    /** 파일 업로드 실패 에러 핸들러 **/
    FileUploadFailException,

    /** 파일 다운로드 실패 에러 핸들러 */
    FileDownloadFailException,

    /** 파일 삭제 실패 에러 핸들러 */
    FileDeleteFailException,

    /** TEMP 파일 적용 실패 */
    FileTempApplyFailException,

    /** 파일 경로 생성 실패 */
    FileNotMapePathException,

    /** AWS 권한 에러 */
    FileAwsS3AccessDeniedException,

    /** AWS S3 파일 처리 실패 */
    FileAwsS3ProcessException,

    /** 잘못된 요청 */
    BadRequestException;

}