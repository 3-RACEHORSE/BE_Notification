package com.sparos4th2.alarm.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum ResponseStatus {
    /**
     * 200: 요청 성공
     **/
    SUCCESS(200, "요청에 성공했습니다."),

    /**
     * 토큰 에러
     */
    WRONG_JWT_TOKEN(400, "다시 로그인 해주세요"),
    USER_NOT_FOUND(201, "존재하지 않는 유저입니다"),
    /**
     * 400: 이미지 요청 오류
     */
    NO_EXIST_IMAGE(404, "존재하지 않는 이미지 입니다"),
    /**
     * 500: 기타 에러
     */
    INTERNAL_SERVER_ERROR(500, "Internal server error"),

    /**
     * 결제오류 수정예정
     */
    ALREADY_PAID_ORDER_ID(1000, "이미 결제된 주문번호입니다"),
    DOSE_NOT_EXIST_PAYMENT(1001, "결제내역이 존재하지 않습니다"),
    CANCELED_AMOUNT_EXCEEDED(1002, "취소 금액 한도를 초과하였습니다"),
    PAYMENT_DATA_TRANSFER_FAILED(1003, "결제 정산 정보 전송에 실패하였습니다"),

    // Token, Code
    TOKEN_EXPIRED(401, "토큰이 만료되었습니다."),
    TOKEN_NOT_VALID(401, "토큰이 유효하지 않습니다."),
    TOKEN_NULL(401, "토큰이 존재하지 않습니다."),
    JWT_CREATE_FAILED(500, "토큰 생성에 실패했습니다."),
    JWT_VALID_FAILED(400, "토큰 검증에 실패했습니다."),
    EXPIRED_AUTH_CODE(400, "인증번호가 만료되었거나 존재하지 않는 멤버입니다."),
    WRONG_AUTH_CODE(400, "인증번호가 일치하지 않습니다."),
    LOGOUT_TOKEN(401, "로그아웃된 토큰입니다."),

    // Members
    DUPLICATE_EMAIL(400, "사용중인 이메일입니다."),
    DUPLICATED_MEMBERS(400, "이미 가입된 멤버입니다."),
    MASSAGE_SEND_FAILED(500, "인증번호 전송에 실패했습니다."),
    MASSAGE_VALID_FAILED(400, "인증번호가 일치하지 않습니다."),
    FAILED_TO_LOGIN(400, "아이디 또는 패스워드를 다시 확인하세요."),
    FAILED_TO_PASSWORD(400, "비밀번호를 다시 한번 확인 해 주세요."),
    WITHDRAWAL_MEMBERS(400, "탈퇴한 회원입니다."),
    NO_EXIST_MEMBERS(404, "존재하지 않는 멤버 정보입니다."),
    MEMBERS_STATUS_IS_NOT_FOUND(404, "존재하지 않는 멤버 상태입니다."),
    PASSWORD_SAME_FAILED(400, "현재 사용중인 비밀번호 입니다."),
    PASSWORD_CONTAIN_NUM_FAILED(400, "휴대폰 번호를 포함한 비밀번호 입니다."),
    PASSWORD_CONTAIN_EMAIL_FAILED(400, "이메일이 포함된 비밀번호 입니다."),
    NO_EXIST_AUTH(400, "인증 정보가 없습니다"),
    DUPLICATE_PHONE_NUMBER(400, "이미 가입한 회원의 전화번호입니다."),
    FAILED_TO_VERIFY_SMS_CODE(400, "인증번호가 일치하지 않습니다."),

    DUPLICATE_SNS_MEMBERS(400, "이미 사용중인 SNS 회원입니다."),
    NO_EXIST_SNS_MEMBERS(404, "가입되지 않은 SNS 멤버 정보입니다."),

    DUPLICATE_HANDLE(400, "이미 사용중인 핸들입니다."),

    /**
     * 경력
     */
    NO_EXIST_CAREER(404, "존재하지 않는 경력입니다"),
    CAREER_NOT_FOUND(404, "경력을 찾을 수 없습니다."),
    DUPLICATE_CAREER(400, "이미 등록된 경력입니다"),

    /**
     * 자격증
     */
    NO_EXIST_CERTIFICATE(404, "존재하지 않는 자격증입니다"),
    CERTIFICATE_NOT_FOUND(404, "자격증을 찾을 수 없습니다."),
    DUPLICATE_CERTIFICATE(400, "이미 등록된 자격증입니다"),

    /**
     * 신고
     */
    DUPLICATE_REPORT(400, "이미 신고한 내역이 있습니다"),

    /**
     * Review 수정예정
     */
    NO_EXIST_REVIEW(5001, "존재하지 않는 리뷰 입니다"),

    /**
     * Category Service Error 
     */
    NO_TINY_CATEGORY(404, "존재하지 않는 카테고리입니다"),

    NO_MATCHED_MEMBERS(400, "회원 목록이 일치하지 않습니다."),

    //subscribe
    DUPLICATE_SUBSCRIBE(400, "이미 구독 중입니다."),
    SELF_SUBSCRIBE(300, "자기 자신 또는 자신의 경매글입니다."),
    UNSUBSCRIBED_SELLER(400, "구독하지 않은 판매자입니다."),
    UNSUBSCRIBED_AUCTION(400, "구독하지 않은 경매글입니다."),
    DATABASE_READ_FAIL(500, "데이터베이스 데이터 조회에 실패했습니다."),
    DATABASE_UPDATE_FAIL(500, "데이터베이스 데이터 수정에 실패했습니다."),
    DATABASE_INSERT_FAIL(500, "데이터베이스 데이터 삽입에 실패했습니다."),

    NO_DATA(404, "데이터가 없습니다."),

    /**
     * 알림
     */
    NO_EXIST_ALARM(400, "알림이 존재하지 않습니다.");

    private final int code;
    private final String message;
}
