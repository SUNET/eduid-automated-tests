package se.sunet.eduid.utils;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

public class MailReader extends CommonOperations{

    private String hostName = "smtp.gmail.com";
    private String username = "eduidtest.se1@gmail.com";
    //String password = "test1eduid!";
    //Password below is for google app password
    private String password = "xvdmpygsfsichkdc";
    private String confirmationUrl;
    private String confirmationCode;

    //@Test
    public String readEmail(String getConfirmation){
        Properties sysProps = System.getProperties();
        sysProps.setProperty("mail.store.protocol", "imaps");

        try {
            //Connect to the email account
            Session session = Session.getInstance(sysProps, null);
            Store store = session.getStore();
            store.connect(hostName, username, password);

            // Open the inbox
            Folder emailInbox = store.getFolder("INBOX");
            emailInbox.open(Folder.READ_WRITE);
/*
            //Get number of messages in inbox
            messageCount = emailInbox.getMessageCount();
            System.out.println("Total Message Count: " + messageCount);

            //Get number of unread messages in inbox
            unreadMsgCount = emailInbox.getUnreadMessageCount();
            System.out.println("Unread Emails count: " + unreadMsgCount);
            emailMessage = emailInbox.getMessage(messageCount);

            //Get email subject of last email
            emailSubject = emailMessage.getSubject();
            System.out.println("Subject: " + emailSubject);
*/

            //Get number of unread messages in inbox
            //int unreadMsgCount = 0;
            //System.out.println("Unread Emails count: " + unreadMsgCount);
            /*
            int counter = 0;
            while(emailInbox.getUnreadMessageCount() != 1){
                timeoutSeconds(1);
                log.info("Unread Emails count: " + emailInbox.getUnreadMessageCount());
                counter ++;
                if(counter > 90)
                    break;
            }
*/


            //Get message body into a Message
            Message emailMessage = emailInbox.getMessage(emailInbox.getMessageCount());

            //Get message body into string
            String mailBody = getMessageContent(emailMessage);

            //Extract the confirmation url into a string
            confirmationUrl = mailBody.substring(mailBody.indexOf("link:") +5, mailBody.indexOf("If")).replaceAll("[\\n\\t ]", "");
            confirmationCode = mailBody.substring(mailBody.indexOf("code:") +5).replaceAll("[\\n\\t ]", "");
/*
            System.out.println("confirmationUrl: " +confirmationUrl);
            System.out.println("confirmationCode: " +confirmationCode);

            // Print complete message body
            System.out.println("Mail body: " +getMessageContent(mailBody));

            emailMessage.setFlag(Flags.Flag.SEEN, true);
*/
            //Delete the email
            emailMessage.setFlag(Flags.Flag.DELETED, true);
            boolean expunge = true;
            emailInbox.close(expunge);

            //emailInbox.close(true);
            store.close();

        } catch (Exception mex) {
            mex.printStackTrace();
        }

        if(getConfirmation.equals("confirmationUrl"))
            return confirmationUrl;
        else
            return confirmationCode;
    }

    private String getMessageContent(Message message) throws MessagingException {
        try {
            Object content = message.getContent();
            if (content instanceof Multipart) {
                StringBuilder messageContent = new StringBuilder();
                Multipart multipart = (Multipart) content;
                for (int i = 0; i < multipart.getCount(); i++) {
                    Part part = multipart.getBodyPart(i);
                    if (part.isMimeType("text/plain")) {
                        messageContent.append(part.getContent().toString());
                    }
                }
                return messageContent.toString();
            }
            return content.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

