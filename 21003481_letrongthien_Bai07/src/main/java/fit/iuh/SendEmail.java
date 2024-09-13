package fit.iuh;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.activation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Servlet implementation class SendEmail
 */
@WebServlet()
@MultipartConfig
public class SendEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy thông tin từ form
        String to = request.getParameter("to");
        String subject = request.getParameter("subject");
        String messageText = request.getParameter("message");
        Part filePart = request.getPart("file");

        String senderEmail = "letrongthien23032003@gmail.com";  // email người gửi
        String password = "sklf tuxj dghq euxh"; // mật khẩu ứng dụng

        // Cấu hình SMTP server
        String host = "smtp.gmail.com";
        int port = 587;

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Bắt buộc STARTTLS
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Đảm bảo sử dụng TLS v1.2
        properties.put("mail.smtp.ssl.trust", host); // Tin cậy máy chủ SMTP

        // Xác thực tài khoản email
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            // Tạo đối tượng MimeMessage
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            // Tạo phần nội dung của email
            Multipart multipart = new MimeMultipart();

            // Thêm nội dung text
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(messageText);
            multipart.addBodyPart(messageBodyPart);

            // Thêm file đính kèm nếu có
            if (filePart != null && filePart.getSize() > 0) {
                MimeBodyPart attachPart = new MimeBodyPart();
                InputStream fileContent = filePart.getInputStream();
                DataSource source = new ByteArrayDataSource(fileContent, filePart.getContentType());
                attachPart.setDataHandler(new DataHandler(source));
                attachPart.setFileName(filePart.getSubmittedFileName());
                multipart.addBodyPart(attachPart);
            }

            // Thiết lập nội dung cho email
            message.setContent(multipart);

            // Gửi email
            Transport.send(message);

            // Phản hồi thành công
            response.getWriter().println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            response.getWriter().println("Failed to send email: " + e.getMessage());
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("emailForm.html");
    }
}
