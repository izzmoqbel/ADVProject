package UserData.docs;

import UserData.ExportData.GivePath;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.Paragraph;
import exception.BadRequestException;
import exception.NotFoundException;
import exception.SystemBusyException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreatDocs implements Docs {
    private static final Logger logger = Logger.getLogger(CreatDocs.class.getName());

    private final String userID;
    private final String fileType;
    GivePath givePath = new GivePath();


    public CreatDocs(String userID, String fileType) {
        this.userID = userID;
        this.fileType = fileType;
    } 

    @Override
    public void creatDocs() throws IOException, SystemBusyException, NotFoundException, BadRequestException {



        String txtFilePath = userID + "_data/" +  givePath.path(userID,fileType);
        String pdfFilePath = userID + "_dataPDF/" + userID + "_" + fileType + ".pdf";


        Path path = Path.of(txtFilePath);
        if (Files.exists(path)) {

            CreateFolder createFolder =new CreateFolder(pdfFilePath);
            createFolder.createFolderIfNotExists();
            TextFileReader userData = new TextFileReader(path);

            try (PdfWriter writer = new PdfWriter(pdfFilePath)) {

                try (PdfDocument pdf = new PdfDocument(writer)) {

                    try (Document document = new Document(pdf)) {

                        document.add(new Paragraph(userData.readTextFile()));
                        logger.info("Document created successfully. Path: " + pdfFilePath);

                    } catch (Exception documentException) {
                        logger.log(Level.SEVERE, "Error adding content to the document", documentException);
                        throw documentException;
                    }

                } catch (Exception pdfException) {
                    logger.log(Level.SEVERE, "Error creating PDF document", pdfException);
                    throw pdfException;
                }

            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error creating PDF writer", e);
                throw e;
            }
        } else {
            logger.log(Level.WARNING, "File not found: {0}", givePath.path(userID, fileType));
        }



    }


}
