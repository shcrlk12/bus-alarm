package kjwon.mytoy.busalarm.service;

import kjwon.mytoy.busalarm.entity.PhoneAthu;
import kjwon.mytoy.busalarm.exception.RepositoryException;
import kjwon.mytoy.busalarm.repository.PhoneAthuRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class SmsServiceImpl implements SmsService{

    private final PhoneAthuRepository phoneAthuRepository;

    private final DefaultMessageService messageService;

    /**
     * 발급받은 API KEY와 API Secret Key를 사용해주세요.
     * @param phoneAthuRepository
     */
    public SmsServiceImpl(PhoneAthuRepository phoneAthuRepository) {
        this.phoneAthuRepository = phoneAthuRepository;
        this.messageService = NurigoApp.INSTANCE.initialize("NCSLJQYS8OBY5KZF", "NBTIUNSSGXVUSRHJSGSEA2CETMPNXR7S", "https://api.coolsms.co.kr");
    }

    @Override
    public void getPhoneAthu(String phone) {

        Optional<PhoneAthu> phoneAthuOptional = phoneAthuRepository.findByPhone(phone);

        phoneAthuOptional.ifPresent(phoneAthuRepository::delete);

        int athu = (int)(Math.random()*1000000);

        PhoneAthu phoneAthu = PhoneAthu.builder()
                .ahtuNumber(Integer.toString(athu))
                .phone(phone)
                .regDt(LocalDateTime.now())
                .isAcitve(false)
                .build();

        Message message = new Message();
        message.setFrom("01090897208");
        message.setTo(phone);
        message.setText("인증번호를 입력 하세요." + "[" + athu + "]");

        this.messageService.sendOne(new SingleMessageSendingRequest(message));

        phoneAthuRepository.save(phoneAthu);
    }

    @Override
    public boolean checkPhoneAhtu(String phone, String ahtuNumber) {
        Optional<PhoneAthu> phoneAthuOptional = phoneAthuRepository.findByPhone(phone);

        PhoneAthu phoneAthu = phoneAthuOptional.orElseThrow(() ->
                new RepositoryException(1, "Phone number가 없습니다.")
        );

        if (ahtuNumber.equals(phoneAthu.getAhtuNumber())){
            phoneAthu.setAcitve(true);
            phoneAthuRepository.save(phoneAthu);
            return true;
        }
        return false;
    }
}
