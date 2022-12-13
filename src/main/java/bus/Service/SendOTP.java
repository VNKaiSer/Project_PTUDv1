package bus.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendOTP {
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    public static final String ACCOUNT_SID = "ACb7ec6c53c09fe018a2fcbac4668f0b7c";
    public static final String AUTH_TOKEN =  "359bc09f29b098bd23fd5b32fc4487b4";
    private String otp;

    public void sendOTP(String sdt) {
        otp = GenerateOTP.OTP(6);
        String body = "\nCÔNG TY MAY MẶC THIÊN NAM \nMã OTP lấy lại mật khẩu của bạn là: "+ otp;
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new PhoneNumber("+84329672303"),
                        new PhoneNumber("+16509552877"),
                        body)
                .create();

        System.out.println(otp);
    }

    public String getOTP(){
        return otp;
    }




}
