package com.example.backend.service;

import com.example.backend.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailSenderService {

    @Value("${frontend.url}")
    private String baseURL;
    @Autowired
    JavaMailSender mailSender;

    @Autowired
    UserRepository userRepository;

    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("peekemploymentservice@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
    }

    public void sendHtmlEmail(String toEmail, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("peekemploymentservice@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    public void sendInvitationEmail(String toEmail, String url) {
        String subject = "Invitation to join PEEK";

        String body = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Join PEEK!</title>\n" +
                "    <style type=\"text/css\">\n" +
                "        body {font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #F4F4F4;}\n" +
                "        .container {width: 80%; margin: auto; background-color: #ffffff; padding: 20px;}\n" +
                "        .header {background-color: #011E3C; color: white; padding: 10px; text-align: center;}\n" +
                "        .content {padding: 20px;}\n" +
                "        .footer {background-color: #333; color: white; padding: 10px; text-align: center;}\n" +
                "        a {color: #4CAF50; text-decoration: none;}\n" +
                "        .button {background-color: #011E3C; color: white; padding: 10px 20px; text-align: center; display: inline-block; border-radius: 5px; text-decoration: none;}\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>Welcome to PEEK!</h1>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>Dear Expert,</p>\n" +
                "            <p>We are thrilled to invite you to join PEEK, the perfect platform for matching Experts with Projects.</p>\n" +
                "            <p>Getting started is easy:</p>\n" +
                "            <ol>\n" +
                "                <li>Click the link below to sign up.</li>\n" +
                "                <li>Set up your profile.</li>\n" +
                "                <li>Start exploring all the features!</li>\n" +
                "            </ol>\n" +
                "            <p style=\"text-align: center;\"><a href=\"" + baseURL + url + "\" class=\"button\">Join PEEK</a></p>\n" +
                "            <p>We can't wait to see how you use PEEK! If you have any questions or need assistance, feel free to contact us.</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Best regards,</p>\n" +
                "            <p>Your PEEK Team</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        sendHtmlEmail(toEmail, subject, body);
    }



    public void sendResetPasswordEmail(String toEmail, String url) {
        String subject = "Reset Password Request for PEEK";

        String body = "<html dir=\"ltr\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"en\" style=\"padding:0;Margin:0\"><head><meta charset=\"UTF-8\"><meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"><meta name=\"x-apple-disable-message-reformatting\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta content=\"telephone=no\" name=\"format-detection\"><title>New email template 2024-01-07</title> <!--[if (mso 16)]><style type=\"text/css\">     a {text-decoration: none;}     </style><![endif]--> <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--> <!--[if gte mso 9]><xml> <o:OfficeDocumentSettings> <o:AllowPNG></o:AllowPNG> <o:PixelsPerInch>96</o:PixelsPerInch> </o:OfficeDocumentSettings> </xml>\n" +
                "<![endif]--><style type=\"text/css\">#outlook a { padding:0;}.ExternalClass { width:100%;}.ExternalClass,.ExternalClass p,.ExternalClass span,.ExternalClass font,.ExternalClass td,.ExternalClass div { line-height:100%;}.es-button { mso-style-priority:100!important; text-decoration:none!important;}a[x-apple-data-detectors] { color:inherit!important; text-decoration:none!important; font-size:inherit!important; font-family:inherit!important; font-weight:inherit!important; line-height:inherit!important;}.es-desk-hidden { display:none; float:left; overflow:hidden; width:0; max-height:0; line-height:0; mso-hide:all;}.es-button-border:hover a.es-button, .es-button-border:hover button.es-button { background:#ffffff!important;} .es-button-border:hover { background:#ffffff!important; border-style:solid solid solid solid!important; border-color:#3d5ca3 #3d5ca3 #3d5ca3 #3d5ca3!important;}\n" +
                "td .es-button-border-1704660221039:hover { border-color:#333333 #333333 #333333 #333333!important; background:#ffffff!important;}@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120%!important } h1 { font-size:20px!important; text-align:center } h2 { font-size:16px!important; text-align:left } h3 { font-size:20px!important; text-align:center } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:20px!important } h2 a { text-align:left } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:16px!important } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important } .es-menu td a { font-size:14px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:10px!important }\n" +
                " .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:12px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:block!important } a.es-button, button.es-button { font-size:14px!important; display:block!important; border-left-width:0px!important; border-right-width:0px!important }\n" +
                " .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important }\n" +
                " tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } .es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; max-height:inherit!important } }@media screen and (max-width:384px) {.mail-message-content { width:414px!important } }</style>\n" +
                " </head> <body style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\"><div dir=\"ltr\" class=\"es-wrapper-color\" lang=\"en\" style=\"background-color:#FAFAFA\"> <!--[if gte mso 9]><v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\"> <v:fill type=\"tile\" color=\"#fafafa\"></v:fill> </v:background><![endif]--><table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#FAFAFA\"><tr style=\"border-collapse:collapse\">\n" +
                "<td valign=\"top\" style=\"padding:0;Margin:0\"><table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"><tr style=\"border-collapse:collapse\"><td class=\"es-adaptive\" align=\"center\" style=\"padding:0;Margin:0\"><table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\"><tr style=\"border-collapse:collapse\"><td align=\"left\" style=\"padding:10px;Margin:0\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"><tr style=\"border-collapse:collapse\">\n" +
                "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:580px\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"><tr style=\"border-collapse:collapse\"><td align=\"center\" class=\"es-infoblock\" style=\"padding:0;Margin:0;line-height:14px;font-size:12px;color:#CCCCCC\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:14px;color:#CCCCCC;font-size:12px\">PeekEmploymentService</p> </td></tr></table></td></tr></table></td></tr></table></td></tr></table>\n" +
                " <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"><tr style=\"border-collapse:collapse\"><td class=\"es-adaptive\" align=\"center\" style=\"padding:0;Margin:0\"><table class=\"es-header-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#3d5ca3;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#3d5ca3\" align=\"center\"><tr style=\"border-collapse:collapse\">\n" +
                "<td style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px;background-color:#ffffff\" bgcolor=\"#ffffff\" align=\"left\"><table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"><tr style=\"border-collapse:collapse\"><td align=\"left\" style=\"padding:0;Margin:0;width:560px\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"><tr style=\"border-collapse:collapse\">\n" +
                "<td class=\"es-m-p0l es-m-txt-c\" align=\"left\" style=\"padding:0;Margin:0;font-size:0px\"><a href=\"https://ewa-frontend-app.onrender.com/#/\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#1376C8;font-size:14px\"><img src=\"https://ecdjqhz.stripocdn.email/content/guids/CABINET_3e7bc297eb20a631f62799d16d262852d68102079f957368e43f079369115e17/images/screenshot_20240107_212403.jpg\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" height=\"64\"></a> </td></tr></table></td></tr></table></td></tr></table></td></tr></table> <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"><tr style=\"border-collapse:collapse\">\n" +
                "<td style=\"padding:0;Margin:0;background-color:#fafafa\" bgcolor=\"#fafafa\" align=\"center\"><table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\"><tr style=\"border-collapse:collapse\"><td style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px;padding-top:40px;background-color:#fbfafa\" bgcolor=\"#fbfafa\" align=\"left\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"><tr style=\"border-collapse:collapse\">\n" +
                "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\"><table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-position:left top\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tr style=\"border-collapse:collapse\"><td align=\"center\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:5px;font-size:0px\"><a target=\"_blank\" href=\"https://ewa-frontend-app.onrender.com/#/\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#0B5394;font-size:16px\"><img src=\"https://ecdjqhz.stripocdn.email/content/guids/7cdd5ee9-e35f-4d45-8973-b89045ebe002/images/resetpassword1.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"175\"></a> </td></tr><tr style=\"border-collapse:collapse\">\n" +
                "<td align=\"center\" style=\"padding:0;Margin:0;padding-top:15px;padding-bottom:15px\"><h1 style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#333333\"><b>FORGOT YOUR </b></h1><h1 style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#333333\"><b>PASSWORD?</b></h1></td></tr><tr style=\"border-collapse:collapse\"><td align=\"center\" style=\"padding:0;Margin:0;padding-left:40px;padding-right:40px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:24px;color:#666666;font-size:16px\">Hi,</p></td></tr> <tr style=\"border-collapse:collapse\">\n" +
                "<td align=\"center\" style=\"padding:0;Margin:0;padding-right:35px;padding-left:40px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:24px;color:#666666;font-size:16px\">There was a request to change your password!</p></td></tr><tr style=\"border-collapse:collapse\"><td align=\"center\" style=\"padding:0;Margin:0;padding-top:25px;padding-left:40px;padding-right:40px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:24px;color:#666666;font-size:16px\">If you did not make this request, just ignore this email. Otherwise, please click the button below to change your password:</p></td></tr> <tr style=\"border-collapse:collapse\">\n" +
                "<td align=\"center\" style=\"Margin:0;padding-left:10px;padding-right:10px;padding-top:40px;padding-bottom:40px\"><span class=\"es-button-border-1704660221039 es-button-border\" style=\"border-style:solid;border-color:#333333;background:#ffffff;border-width:2px;display:inline-block;border-radius:10px;width:auto\"><a href=\""+url+"\" class=\"es-button\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;color:#333333;font-size:16px;display:inline-block;background:#FFFFFF;border-radius:10px;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-weight:bold;font-style:normal;line-height:19px;width:auto;text-align:center;padding:15px 20px 15px 20px;mso-padding-alt:0;mso-border-alt:10px solid #FFFFFF\">RESET PASSWORD</a></span></td></tr></table></td></tr> </table></td></tr>\n" +
                "<tr style=\"border-collapse:collapse\"><td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"><tr style=\"border-collapse:collapse\"><td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"><tr style=\"border-collapse:collapse\"><td align=\"center\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#000000;font-size:14px\"><strong>PEEK</strong></p>\n" +
                " <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#000000;font-size:14px\">Reliably connect with projects and experts</p></td></tr></table></td></tr></table></td></tr> <tr style=\"border-collapse:collapse\"><td align=\"left\" style=\"Margin:0;padding-left:10px;padding-right:10px;padding-top:20px;padding-bottom:20px\"> <!--[if mso]><table style=\"width:580px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:280px\" valign=\"top\"><![endif]--><table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\"><tr style=\"border-collapse:collapse\">\n" +
                "<td align=\"left\" style=\"padding:0;Margin:0;width:280px\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"><tr style=\"border-collapse:collapse\"><td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:20px;color:#333333;font-size:13px\"><strong>Discover and Engage</strong></p> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:18px;color:#333333;font-size:12px\">Explore a variety of projects and build a personalized list of favorites. While our admin team activates our intelligent matching tool, stay updated on project developments.</p></td></tr>\n" +
                "</table></td></tr></table> <!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:280px\" valign=\"top\"><![endif]--><table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\"><tr style=\"border-collapse:collapse\"><td align=\"left\" style=\"padding:0;Margin:0;width:280px\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"><tr style=\"border-collapse:collapse\"><td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:20px;color:#333333;font-size:13px\"><strong>Connect with other Experts</strong></p>\n" +
                " <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:18px;color:#333333;font-size:12px\">Engage with a dynamic community of experts using our intuitive interface. Send and receive collaboration invitations to foster connections that align with your professional goals.</p></td></tr></table></td></tr></table> <!--[if mso]></td></tr></table><![endif]--></td></tr> <tr style=\"border-collapse:collapse\"><td align=\"left\" bgcolor=\"#fafafa\" style=\"padding:20px;Margin:0;background-color:#fafafa\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"><tr style=\"border-collapse:collapse\">\n" +
                "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"><tr style=\"border-collapse:collapse\">\n" +
                "<td align=\"center\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#666666;font-size:14px\">Contact us: <a target=\"_blank\" href=\"mailto:peekemploymentservice@gmail.com\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#666666;font-size:14px\">peekemploymentservice@gmail.com</a></p> </td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></div></body></html>";

        sendHtmlEmail(toEmail, subject, body);
    }

    public String generateUrl() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        while (stringBuilder.length() < 18) {
            int index = (int) (random.nextFloat() * chars.length());
            stringBuilder.append(chars.charAt(index));
        }
        return stringBuilder.toString();
    }

}
