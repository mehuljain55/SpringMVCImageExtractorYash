package com.ImageExtractor.Service;

import com.ImageExtractor.Entity.UserImage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private SessionFactory sessionFactory;

    public String uploadImage(MultipartFile file) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            UserImage userImage = new UserImage();
            Blob blob = new SerialBlob(file.getBytes());
            userImage.setImage(blob);
            transaction = session.beginTransaction();
            session.save(userImage);
            transaction.commit();
            return "Success";
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return "Failed";
        }
    }

    public List<UserImage> getAllImages() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM UserImage";
            Query<UserImage> query = session.createQuery(hql, UserImage.class);
            List<UserImage> images = query.getResultList();


            for (UserImage userImage : images) {
                if (userImage.getImage() != null) {
                    Blob blob = userImage.getImage();
                    try (InputStream in = blob.getBinaryStream()) {
                        byte[] bytes = convertInputStreamToByteArray(in);
                        String base64Image = Base64.getEncoder().encodeToString(bytes);
                        userImage.setBase64Image(base64Image);
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return images;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] convertInputStreamToByteArray(InputStream in) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    private String getMimeType(byte[] bytes) {
        try {
            String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(bytes));
            return mimeType != null ? mimeType : "application/octet-stream"; // Default MIME type
        } catch (IOException e) {
            e.printStackTrace();
            return "application/octet-stream";
        }
    }


}

