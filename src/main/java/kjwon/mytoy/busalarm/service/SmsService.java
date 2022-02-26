package kjwon.mytoy.busalarm.service;

public interface SmsService {

    void getPhoneAthu(String phone);

    boolean checkPhoneAhtu(String phone, String ahtuNumber);
}
