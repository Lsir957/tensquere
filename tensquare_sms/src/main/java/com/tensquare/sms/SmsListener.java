package com.tensquare.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
@ConfigurationProperties(prefix = "aliyun.sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    //读取配置文件的参数
    private String tempCode;
    private String signName;

    public void setTempCode(String tempCode) {
        this.tempCode = tempCode;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    @RabbitHandler
    public void snedSms(Map<String,Object> message) {
        String mobile = (String) message.get("mobile");
        String code = (String) message.get("code");
        System.out.println("手机号：" + mobile);
        System.out.println("验证码：" + code);

        //发送短信
        try {
            SendSmsResponse resp = smsUtil.sendSms(mobile,tempCode,signName,"{\"code\":\""+code+"\"}");

            if(resp.getCode().equals("OK")){
                System.out.println("短信发送成功");

            }else{
                System.out.println("短信发送失败："+resp.getCode());
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}
