package com.qzw.demo.java.stock;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @author BG388892
 * @date 2020/3/10
 */
public class MailUtil {

    public static void sendMessage(String title, String content) throws Exception {
        String mailFrom = "2568449023@qq.com";
        String password_mailFrom = "osbuckdtcxbieafa22";
        String mailTo = "552114141@qq.com";
        String mailTittle = "生日快乐！";
        String mailText = "同学你好,生日快乐!!!";
        String mail_host = "smtp.qq.com";

        if (title != null && !title.trim().equals("")) {
            mailTittle = title;
        }

        if (content != null && !content.trim().equals("")) {
            mailText = content;
        }

        Properties prop = new Properties();
        prop.setProperty("mail.host", mail_host);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        //
        prop.setProperty("mail.smtp.auth", "true");//开启认证
        prop.setProperty("mail.debug", "true");//启用调试
        prop.setProperty("mail.smtp.timeout", "1000");//设置链接超时
        prop.setProperty("mail.smtp.port", "465");//设置端口
        prop.setProperty("mail.smtp.socketFactory.port", "465");//设置ssl端口
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // 1、创建session
        Session session = Session.getInstance(prop);
        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(false);
        // 2、通过session得到transport对象
        Transport ts = session.getTransport();
        // 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect(mail_host, mailFrom, password_mailFrom);
        // 4、创建邮件
        Message message = createSimpleMail(session, mailFrom, mailTo, mailTittle, mailText);
        // 5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     */
    public static MimeMessage createSimpleMail(Session session, String mailfrom, String mailTo, String mailTittle,
                                               String mailText) throws Exception {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress(mailfrom));
        // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        // 邮件的标题
        message.setSubject(mailTittle);
        // 邮件的文本内容
        message.setContent(mailText, "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        // 返回创建好的邮件对象
        return message;
    }
}

