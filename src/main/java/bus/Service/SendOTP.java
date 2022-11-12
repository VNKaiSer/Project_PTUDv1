package bus.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendOTP {
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    public static final String ACCOUNT_SID = "ACb7ec6c53c09fe018a2fcbac4668f0b7c";
    public static final String AUTH_TOKEN =  "fbf10ca40e15748ceb54051427b9335e";

    public static void main(String[] args) {
        String body = "\nCÔNG TY MAY MẶC THIÊN NAM \nMã OTP lấy lại mật khẩu của bạn là: "+GenerateOTP.OTP(6);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new PhoneNumber("+84 862 808 775"),
                        new PhoneNumber("+16509552877"),
                        body)
                .create();

        System.out.println(message.getSid());
    }


}
