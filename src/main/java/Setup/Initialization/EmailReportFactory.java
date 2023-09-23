package Setup.Initialization;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.text.DecimalFormat;
import java.util.Properties;

public class EmailReportFactory {

    public static float passed,failed = 0;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static final DecimalFormat noFormat = new DecimalFormat("0");
    public static String recipients;

    public void EmailReporter(String emailRecipients) {

        Properties props = new Properties();
        recipients = emailRecipients;
        try {
            Message message = new MimeMessage(
                    authenticate(
                            "test.no.reply.report@gmail.com",
                            "automation@123",
                            setProperties(props)));
            System.out.println("Email"+message);

            Transport.send(setMessage(message));

        } catch (Exception e) {
            System.out.println("FAILED");
            System.out.println(e);
        }

    }

    private Session authenticate(String p_username, String p_password, Properties p_props){
        Session session = Session.getDefaultInstance(p_props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication(p_username, p_password);
                    }
                }
        );
        return session;
    }

    private Properties setProperties(Properties p_properties)
    {
        p_properties.put("mail.smtp.host", "smtp.gmail.com");
        p_properties.put("mail.smtp.starttls.enable", "true");
        p_properties.put("mail.smtp.auth", "true");
        p_properties.put("mail.smtp.port", "587");
        return p_properties;
    }

    private Message setMessage(Message p_message){

        try {
            float passPercent = (passed / (passed + failed)) * 100;
            float failPercent = (failed / (passed + failed)) * 100;
            p_message.setFrom(new InternetAddress("test.no.reply.report@gmail.com"));
            p_message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            p_message.setSubject("Test Execution");
            String messageString = "<head>\n" +
                    "<style>\n" +
                    "table {\n" +
                    "  font-family: arial, sans-serif;\n" +
                    "  border-collapse: collapse;\n" +
                    "  width: 60%;\n" +
                    "}\n" +
                    "\n" +
                    "td, th {\n" +
                    "  border: 3px solid #dddddd;\n" +
                    "  text-align: center;\n" +
                    "  padding: 8px;\n" +
                    "}\n" +
                    "\n" +
                    "tr:nth-child(even) {\n" +
                    "  background-color: #dddddd;\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<br><br><h1 style='text-align:center'>Test Execution Report</h1>\n" +
                    "<h3 style='text-align:center'>URL: --------------</h3>\n" +
                    "<table>\n" +
                    "  <tr>\n" +
                    "    <th>Type</th>\n" +
                    "    <th>Count</th>\n" +
                    "    <th>Percentage</th>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>Total</td>\n" +
                    "    <td>" + noFormat.format(passed + failed) + "</td>\n" +
                    "    <td>-</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>Passed</td>\n" +
                    "    <td style='color:green'>" + noFormat.format(passed) + "</td>\n" +
                    "    <td style='color:green'>" + df.format(passPercent) + "%</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>Failed</td>\n" +
                    "    <td style='color:red'>" + noFormat.format(failed) + "</td>\n" +
                    "    <td style='color:red'>" + df.format(failPercent) + "%</td>\n" +
                    "  </tr>\n" +
                    "</table>\n" +
                    "\n" +
                    "<p>For Detailed Report, Execution Report is Attached to the email.</p>\n" +
                    "\n" +
                    "<p><i>*Note: This is an automatically generated email, please do not reply to it.</i></p>\n" +
                    "\n" +
                    "</body>";

            // Create another object to add another content
            MimeBodyPart attachment = new MimeBodyPart();
            // Mention the file which you want to send
            String filename = "src/main/java/Reporting/Reports/2023-Aug-11/PSW_Report_2023-Aug-11_13-00-33.html";
            // Create data source and pass the filename
            DataSource source = new FileDataSource(filename);
            // set the handler
            attachment.setDataHandler(new DataHandler(source));
            // set the file
            attachment.setFileName(filename);

            // Create another object to add another content
            MimeBodyPart body = new MimeBodyPart();
            // set the handler
            body.setContent(messageString, "text/html");

            // Create object of MimeMultipart class
            Multipart multipart = new MimeMultipart();
            // add body part 1
            multipart.addBodyPart(attachment);
            multipart.addBodyPart(body);
            p_message.setContent(multipart);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return p_message;
    }

}
