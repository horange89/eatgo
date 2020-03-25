package kr.co.fastcampus.eatgo.application;

/**
 *      Email 존재하지 않는 경우에 대한 예외처리
 * */
public class EmailNotExistedException extends RuntimeException {

    EmailNotExistedException(String email) {
        super("Email is not registered : " + email);
    }
}
