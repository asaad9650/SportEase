package com.fyp.sporteaze.Academy.Invoice;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fyp.sporteaze.Model.Invoice;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class AcademyGenerateInvoice extends AppCompatActivity {
    EditText et_generate_invoice_event , et_generate_invoice_basic_charges, et_generate_invoice_medical_trainer_charges, et_generate_invoice_analyst_charges, et_generate_invoice_total_charges;
    Button btn_generate_invoice ;
    String academy_email , academy_id, academy_name;
    ProgressBar pgsBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academy_generate_invoice);
        pgsBar =findViewById(R.id.pBar);
        et_generate_invoice_event = findViewById(R.id.et_generate_invoice_event);
        et_generate_invoice_basic_charges = findViewById(R.id.et_generate_invoice_basic_charges);
        et_generate_invoice_medical_trainer_charges = findViewById(R.id.et_generate_invoice_medical_charges);
        et_generate_invoice_analyst_charges = findViewById(R.id.et_generate_invoice_analyst_charges);
        et_generate_invoice_total_charges = findViewById(R.id.et_generate_invoice_total_charges);
        btn_generate_invoice = findViewById(R.id.btn_generate_invoice);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        academy_email = extras.getString("academy_email");
        academy_id = extras.getString("academy_id");
        academy_name = extras.getString("academy_name");

        btn_generate_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String basic_charges = et_generate_invoice_basic_charges.getText().toString().trim();
                String event = et_generate_invoice_event.getText().toString().trim();
                String medical_charges = et_generate_invoice_medical_trainer_charges.getText().toString().trim();
                String analyst_charges = et_generate_invoice_analyst_charges.getText().toString().trim();
                String total_charges = et_generate_invoice_total_charges.getText().toString().trim();

                if(basic_charges.equals("") || event.equals("") || medical_charges.equals("") || analyst_charges.equals("") || total_charges.equals("")){
                    Toast.makeText(AcademyGenerateInvoice.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(basic_charges.matches("[0-9]+") && medical_charges.matches("[0-9]+") && analyst_charges.matches("[0-9]+") && total_charges.matches("[0-9]+")){
                        if (basic_charges.length()<= 6 && medical_charges.length()<= 6 && analyst_charges.length()<= 6 && total_charges.length()<= 6){
                            if(event.length()<= 18){

                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference root = db.getReference();
                                String key = root.push().getKey();
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
                                String strDate = sdf.format(c.getTime());
                                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                                String strTime = sdf2.format(c.getTime());
                                Invoice invoice = new Invoice(Calendar.getInstance().getTime().toString() , event , basic_charges , medical_charges, analyst_charges, total_charges , key);
                                root.child("Invoices").child(academy_id).push().setValue(invoice);
                                createPdf(view, "INVOICE" , event ,basic_charges, medical_charges , analyst_charges , total_charges , key+".pdf" , strDate , strTime);
                                et_generate_invoice_analyst_charges.setText("");
                                et_generate_invoice_basic_charges.setText("");
                                et_generate_invoice_event.setText("");
                                et_generate_invoice_total_charges.setText("");
                                et_generate_invoice_medical_trainer_charges.setText("");
//                                pgsBar.setVisibility(view.GONE);
                            }
                            else{
                                Toast.makeText(AcademyGenerateInvoice.this, "Event should contain maximum 12 characters", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else{
                            Toast.makeText(AcademyGenerateInvoice.this, "Charges should be maximum 6 characters", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(AcademyGenerateInvoice.this, "Charges field should contain numbers only", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });

    }

    private void createPdf( View view , String heading ,String _event ,String _basic_charges , String _medical_charges , String _analyst_charges , String _total_charges , String filename , String _date , String _time){
        // create a new document
        PdfDocument document = new PdfDocument();
        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
//        paint.setTextAlign(Paint.Align.CENTER);
//        paint.setColor(Color.RED);
//        canvas.drawCircle(50, 50, 30, paint);


        paint.setColor(Color.BLACK);
//        paint.setTextAlign(Paint.Align.CENTER);
        int xPos = (canvas.getWidth() / 2);
//        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;

        canvas.drawText("--------" , 10 , 10 , paint);
        canvas.drawText(heading,10 ,20 , paint);
        canvas.drawText("--------" , 10 , 30 , paint);
        canvas.drawText("Date: " ,10,  70 , paint);
        canvas.drawText("Time: " ,10, 90, paint);
        canvas.drawText("Event: ",10, 110, paint);
        canvas.drawText("Analyst Charges: ",10, 130, paint);
        canvas.drawText("Medical Charges: " , 10 , 150 , paint );
        canvas.drawText("Basic Charges " , 10 , 170 , paint );
        canvas.drawText("Total Charges: ",10, 210, paint);

        canvas.drawText(_date, canvas.getWidth()-100, 70, paint);
        canvas.drawText(_time, canvas.getWidth()-100, 90, paint);
        canvas.drawText(_event, canvas.getWidth()-100, 110, paint);
        canvas.drawText(_analyst_charges, canvas.getWidth()-100, 130, paint);
        canvas.drawText(_medical_charges, canvas.getWidth()-100, 150, paint);
        canvas.drawText(_basic_charges, canvas.getWidth()-100, 170, paint);
        canvas.drawText(_total_charges + " PKR", canvas.getWidth()-100, 210, paint);

        //canvas.drawt
        // finish the page
        document.finishPage(page);
// draw text on the graphics object of the page
        // Create Page 2
        pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 2).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        document.finishPage(page);
        // write the document content
        String directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "/Sporteaze/Invoices/";
//        Toast.makeText(this, directory_path, Toast.LENGTH_SHORT).show();
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();

        }
        String targetPdf = directory_path+filename;
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

            try {
                String host = "smtp.gmail.com";
                String port = "587";
                String mailFrom = "coffeeline9@gmail.com";
                String password = "dwcqckylgqngqoxr";

                // message info
                String mailTo = "asaad9650@gmail.com";
                String subject = "Invoice";
                String message = "";
                String attachFiles =filePath.toString();
//                Bundle
//                attachFiles[0] = filePath.toString();

                sendEmailWithAttachments( view , host, port, mailFrom, password, mailTo,
                        subject, message, attachFiles);
//                SendMail(filePath.toString());
            } catch (MessagingException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();

        }
        // close the document
        document.close();
//        Uri path = Uri.fromFile(filePath);

    }

//    public void SendMail(String filenamee) throws MessagingException {
//        String host = "smtp.gmail.com";
//        String Password = "dwcqckylgqngqoxr";
//        String from = "coffeeline9@gmail.com";
//        String toAddress = "asaad9650@gmail.com";
//        String filename = filenamee;
//        // Get system properties
//        Properties props = System.getProperties();
//        props.put("mail.smtp.host", host);
//        props.put("mail.smtps.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        Session session = Session.getInstance(props, null);
//
//        MimeMessage message = new MimeMessage(session);
//        message.setFrom(new InternetAddress(from));
//        message.setRecipients(Message.RecipientType.TO, toAddress);
//        message.setSubject("JavaMail Attachment");
//        BodyPart messageBodyPart = new MimeBodyPart();
//        messageBodyPart.setText("Here's the file");
//        Multipart multipart = new MimeMultipart();
//        multipart.addBodyPart(messageBodyPart);
//        messageBodyPart = new MimeBodyPart();
//        FileDataSource source = new FileDataSource(filename);
//        messageBodyPart.setDataHandler(new DataHandler(source));
//        messageBodyPart.setFileName(filename);
//        multipart.addBodyPart(messageBodyPart);
//        message.setContent(multipart);
//
//        try {
//            Transport tr = session.getTransport("smtps");
//            tr.connect(host, from, Password);
//            tr.sendMessage(message, message.getAllRecipients());
//            System.out.println("Mail Sent Successfully");
//            tr.close();
//        } catch (SendFailedException sfe) {
//            System.out.println(sfe);
//        }
//    }

    public void sendEmailWithAttachments(View view , String host, String port,
                                                final String userName, final String password, String toAddress,
                                                String subject, String message, String file)
            throws AddressException, MessagingException {


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            pgsBar.setVisibility(view.VISIBLE);
            btn_generate_invoice.setVisibility(view.GONE);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
//            sendEmailWithAttachments();
            //your codes here

            Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.user", userName);
            properties.put("mail.password", password);

            // creates a new session with an authenticator
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            };
            Session session = Session.getInstance(properties, auth);

            // creates a new e-mail message
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(userName));
            InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, "text/html");

            // creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // adds attachments
//            if (attachFiles != null && attachFiles.length > 0) {
//                for (String filePath : attachFiles) {
                    MimeBodyPart attachPart = new MimeBodyPart();

                    try {
                        attachPart.attachFile(file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    multipart.addBodyPart(attachPart);
//                }
//            }

            // sets the multi-part as e-mail's content
            msg.setContent(multipart);

            // sends the e-mail
            Transport.send(msg);

            Toast.makeText(this, "Email Sent successfully", Toast.LENGTH_SHORT).show();
            pgsBar.setVisibility(view.GONE);
            btn_generate_invoice.setVisibility(view.VISIBLE);




        }


        // sets SMTP server properties


    }
//    public void onViewCreated(View view, Bundle savedInstanceState)
//    {
//        int SDK_INT = android.os.Build.VERSION.SDK_INT;
//        if (SDK_INT > 8)
//        {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//                    .permitAll().build();
//            StrictMode.setThreadPolicy(policy);
////            sendEmailWithAttachments();
//            //your codes here
//
//        }
//    }
}
