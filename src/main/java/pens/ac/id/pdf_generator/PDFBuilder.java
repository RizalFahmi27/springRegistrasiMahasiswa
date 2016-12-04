package pens.ac.id.pdf_generator;

import java.io.File;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import pens.ac.id.model.KartuUjian;
 
/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * @author www.codejava.net
 *
 */
public class PDFBuilder extends AbstractITextPdfView {
 
	String PATH = "C:/Users/Rizal Fahmi/workspace2/mySpring/src/main/resources/static/files/mahasiswa_baru/";
	String fotoPath = "C:/Users/Rizal Fahmi/workspace2/mySpring/src/main/resources/static/files/mahasiswa_baru/default-picture.png";
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	HttpSession session = request.getSession();
    	String filename = "C:/Users/Rizal Fahmi/workspace2/mySpring/src/main/resources/assets/image/header_pens.png";
        // get data model which is passed by the Spring container
        KartuUjian data = (KartuUjian) model.get("data");
        
        String kap = (String) session.getAttribute("KAP");
        
        Image img = Image.getInstance(filename);
        img.scaleAbsolute(250f, 80f);
        doc.add(img);
        
        PdfPCell cell2 = new PdfPCell();
        cell2.setBorder(Rectangle.RECTANGLE);
        
        
        
        doc.add(new Paragraph("\n\n"));
       
        
      
        FontSelector selector1 = new FontSelector();
        Font f1 = FontFactory.getFont(FontFactory.HELVETICA, 12);
        f1.setColor(BaseColor.BLUE);
        f1.setSize(19.0f);
        f1.setStyle(Font.BOLD);
        selector1.addFont(f1);
        Phrase ph = selector1.process("KARTU TANDA PESERTA");
        
        Paragraph paragraph1 = new Paragraph();
        paragraph1.add(ph);
        paragraph1.setAlignment(Element.ALIGN_CENTER);
        doc.add(paragraph1);
        
        doc.add(new Paragraph("\n"));
        
        FontSelector selector2 = new FontSelector();
        f1.setColor(BaseColor.BLUE);
        f1.setSize(28.0f);
        f1.setStyle(Font.BOLD);
        selector2.addFont(f1);
        Phrase ph2 = selector2.process("UMPN 2016");
        
        Paragraph paragraph2 = new Paragraph();
        paragraph2.add(ph2);
        paragraph2.setAlignment(Element.ALIGN_CENTER);
        doc.add(paragraph2);
        
        PdfPTable tableID = new PdfPTable(2);
        tableID.setWidthPercentage(100.0f);
        tableID.setWidths(new float[] {3.0f, 3.0f});
        tableID.setSpacingBefore(10);
         
       
        
        Font fontTableID = FontFactory.getFont(FontFactory.TIMES_BOLD);
        fontTableID.setStyle(Font.BOLD);
        
        Font fontTableIDField = FontFactory.getFont(FontFactory.TIMES);
         
       
        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
         
        // write table header
        cell.setPhrase(new Phrase("NOMOR UJIAN", fontTableID));
        cell.setBorder(Rectangle.LEFT|Rectangle.TOP);
        tableID.addCell(cell);
         
        cell.setPhrase(new Phrase(": "+data.getNo_peserta(), fontTableIDField));
        cell.setBorder(Rectangle.TOP|Rectangle.RIGHT);
        tableID.addCell(cell);
        
        cell.setPhrase(new Phrase("NAMA LENGKAP", fontTableID));
        cell.setBorder(Rectangle.LEFT);
        tableID.addCell(cell);
        
        cell.setPhrase(new Phrase(": "+data.getNama(), fontTableIDField));
        cell.setBorder(Rectangle.RIGHT);
        tableID.addCell(cell);
        
        cell.setPhrase(new Phrase("NAMA DI LEMBAR UJIAN",fontTableID));
        cell.setBorder(Rectangle.LEFT);
        tableID.addCell(cell);
        
        cell.setPhrase(new Phrase(": "+data.getNama_lembar_ujian(),fontTableIDField));
        cell.setBorder(Rectangle.RIGHT);
        tableID.addCell(cell);
        
        cell.setPhrase(new Phrase("TAHUN IJAZAH",fontTableID));
        cell.setBorder(Rectangle.LEFT|Rectangle.BOTTOM);
        tableID.addCell(cell);
        
        cell.setPhrase(new Phrase(": "+data.getTahun_lulus(),fontTableIDField));
        cell.setBorder(Rectangle.RIGHT|Rectangle.BOTTOM);
        tableID.addCell(cell);
        
        
        Font f3 = FontFactory.getFont(FontFactory.COURIER, 15);
        FontSelector selector3 = new FontSelector();
        selector3.addFont(f3);
        Phrase ph3 = selector3.process("\nAlamat : "+data.getAlamat()+"\n"+data.getNo_telepon());
        
        //Add Table
        doc.add(tableID);
        
        Paragraph paragraph3 = new Paragraph();
        paragraph3.add(ph3);
        doc.add(paragraph3);
        
        Font f4 = FontFactory.getFont(FontFactory.COURIER_BOLD, 10);
        FontSelector selector4 = new FontSelector();
        selector4.addFont(f4);
        Phrase ph4 = selector4.process("\nUJIAN AKAN DILAKSANAKAN PADA TANGGAL 2 DAN 3 DESEMBER 2017\n\n");
     
        Paragraph paragraph4 = new Paragraph();
        paragraph4.setAlignment(Element.ALIGN_CENTER);
        paragraph4.add(ph4);
        doc.add(paragraph4);
        
      PdfPTable tableContent = new PdfPTable(2);
      tableContent.setWidthPercentage(100.0f);
      tableContent.setWidths(new float[] {2.0f,4.0f});
      tableContent.setSpacingBefore(10);
      
      //cell.setPhrase(new Phrase("NOMOR UJIAN", fontTableID));
      
      PdfPCell cellContent = new PdfPCell();
      cellContent.setPadding(5);
      
      System.out.println("lokasi foto cetak : "+PATH + kap + "/" + data.getFoto());
      
      File fotoFile = new File(PATH + kap + "/" + data.getFoto());
      
      if(fotoFile.exists()){
    	  String mimeType = URLConnection.guessContentTypeFromName(PATH + kap + "/" + data.getFoto());
    	  if(mimeType!=null && mimeType.split("/")[0].equals("image"))
    		  fotoPath = PATH + kap + "/" + data.getFoto();
      }
     
      
      Image foto = Image.getInstance(fotoPath);
      foto.scaleAbsolute(120f, 650f);
      cellContent.addElement(foto);
      tableContent.addCell(cellContent);
      
      
      PdfPTable tableContentJadwal = new PdfPTable(3);
      tableContentJadwal.setWidthPercentage(100.0f);
      tableContentJadwal.setWidths(new float[] {3.0f,3.0f,4.0f});
      tableContentJadwal.setSpacingBefore(10);
      
      PdfPCell cellContentJadwal = new PdfPCell();
      cellContentJadwal.setPadding(5);
      paragraph1 = new Paragraph();
      
      paragraph1.add(new Phrase("HARI & TANGGAL",
    		  FontFactory.getFont(FontFactory.COURIER,8)));
      paragraph1.setAlignment(Element.ALIGN_CENTER);
      cellContentJadwal.setPhrase(paragraph1);
      
      tableContentJadwal.addCell(cellContentJadwal);
      
      
      
      cellContentJadwal = new PdfPCell();
      cellContentJadwal.setPadding(5);
      paragraph1 = new Paragraph();
      paragraph1.setAlignment(Element.ALIGN_CENTER);
      paragraph1.add(new Phrase("WIB",
    		  FontFactory.getFont(FontFactory.COURIER,8)));
      cellContentJadwal.setPhrase(paragraph1);
      
      tableContentJadwal.addCell(cellContentJadwal);
      
      cellContentJadwal = new PdfPCell();
      cellContentJadwal.setPadding(5);
      paragraph1 = new Paragraph();
      
      paragraph1.add(new Phrase("KEGIATAN",
    		  FontFactory.getFont(FontFactory.COURIER,8)));
      cellContentJadwal.setPhrase(paragraph1);
      paragraph1.setAlignment(Element.ALIGN_CENTER);
      
      tableContentJadwal.addCell(cellContentJadwal);
      
      cellContentJadwal = new PdfPCell();
      cellContentJadwal.setPadding(5);
      paragraph1 = new Paragraph();
      
      paragraph1.add(new Phrase("SELASA 2 DESEMBER 2017",
    		  FontFactory.getFont(FontFactory.COURIER,8)));
      paragraph1.setAlignment(Element.ALIGN_CENTER);
      cellContentJadwal.setPhrase(paragraph1);
      cellContentJadwal.setRowspan(5);;
      
      tableContentJadwal.addCell(cellContentJadwal);
      
      cellContentJadwal = new PdfPCell();
      cellContentJadwal.setPadding(5);
      paragraph1 = new Paragraph();
      
      paragraph1.add(new Phrase("09.45-10.00",
    		  FontFactory.getFont(FontFactory.COURIER,8)));
      cellContentJadwal.setPhrase(paragraph1);
      paragraph1.setAlignment(Element.ALIGN_CENTER);
      
      tableContentJadwal.addCell(cellContentJadwal);
      
      cellContentJadwal = new PdfPCell();
      cellContentJadwal.setPadding(5);
      paragraph1 = new Paragraph();
      
      paragraph1.add(new Phrase("MASUK RUANG UJIAN & MENGISI BIODATA\nPEMERIKSAAN IDENTITAS",
    		  FontFactory.getFont(FontFactory.COURIER,8)));
      cellContentJadwal.setPhrase(paragraph1);
      paragraph1.setAlignment(Element.ALIGN_CENTER);
      
      tableContentJadwal.addCell(cellContentJadwal);
      
      cellContentJadwal = new PdfPCell();
      cellContentJadwal.setPadding(5);
      paragraph1 = new Paragraph();
      
      paragraph1.add(new Phrase("10.00-11.45",
    		  FontFactory.getFont(FontFactory.COURIER,8)));
      cellContentJadwal.setPhrase(paragraph1);
      paragraph1.setAlignment(Element.ALIGN_CENTER);
      
      tableContentJadwal.addCell(cellContentJadwal);
      
      cellContentJadwal = new PdfPCell();
      cellContentJadwal.setPadding(5);
      paragraph1 = new Paragraph();
      
      paragraph1.add(new Phrase("TES KEMAMPUAN DAN POTENSI AKADEMIK (TKPA)",
    		  FontFactory.getFont(FontFactory.COURIER,8)));
      cellContentJadwal.setPhrase(paragraph1);
      paragraph1.setAlignment(Element.ALIGN_CENTER);
      
      tableContentJadwal.addCell(cellContentJadwal);
      
      cellContentJadwal = new PdfPCell();
      cellContentJadwal.setPadding(5);
      paragraph1 = new Paragraph();
      
      paragraph1.add(new Phrase("11.45-12.45",
    		  FontFactory.getFont(FontFactory.COURIER,8)));
      cellContentJadwal.setPhrase(paragraph1);
      paragraph1.setAlignment(Element.ALIGN_CENTER);
      
      tableContentJadwal.addCell(cellContentJadwal);
      
      cellContentJadwal = new PdfPCell();
      cellContentJadwal.setPadding(5);
      paragraph1 = new Paragraph();
      
      paragraph1.add(new Phrase("ISTIRAHAT",
    		  FontFactory.getFont(FontFactory.COURIER,8)));
      cellContentJadwal.setPhrase(paragraph1);
      paragraph1.setAlignment(Element.ALIGN_CENTER);
      
      tableContentJadwal.addCell(cellContentJadwal);
      
      cellContentJadwal = new PdfPCell();
      cellContentJadwal.setPadding(5);
      paragraph1 = new Paragraph();
      
      paragraph1.add(new Phrase("12.45-13.00",
    		  FontFactory.getFont(FontFactory.COURIER,8)));
      cellContentJadwal.setPhrase(paragraph1);
      paragraph1.setAlignment(Element.ALIGN_CENTER);
      
      tableContentJadwal.addCell(cellContentJadwal);
      
      cellContentJadwal = new PdfPCell();
      cellContentJadwal.setPadding(5);
      paragraph1 = new Paragraph();
      
      paragraph1.add(new Phrase("MASUK RUANG UJIAN & MENGISI BIODATA",
    		  FontFactory.getFont(FontFactory.COURIER,8)));
      cellContentJadwal.setPhrase(paragraph1);
      paragraph1.setAlignment(Element.ALIGN_CENTER);
      
      tableContentJadwal.addCell(cellContentJadwal);
      
      cellContentJadwal = new PdfPCell();
      cellContentJadwal.setPadding(5);
      paragraph1 = new Paragraph();
      
      paragraph1.add(new Phrase("13.00-14.00",
    		  FontFactory.getFont(FontFactory.COURIER,8)));
      cellContentJadwal.setPhrase(paragraph1);
      paragraph1.setAlignment(Element.ALIGN_CENTER);
      
      tableContentJadwal.addCell(cellContentJadwal);
      
      cellContentJadwal = new PdfPCell();
      cellContentJadwal.setPadding(5);
      paragraph1 = new Paragraph();
      
      paragraph1.add(new Phrase("TES KEMAMPUAN DASAR (TKD) ",
    		  FontFactory.getFont(FontFactory.COURIER,8)));
      cellContentJadwal.setPhrase(paragraph1);
      paragraph1.setAlignment(Element.ALIGN_CENTER);
      
      tableContentJadwal.addCell(cellContentJadwal);
      
      
      tableContent.addCell(tableContentJadwal);
      
    //Add Table
      doc.add(tableContent);
      
      selector1 = new FontSelector();
      
      selector1.addFont(fontTableID);
      ph = selector1.process("\nPILIHAN PROGRAM STUDI : ");
      
      paragraph1 = new Paragraph();
      paragraph1.add(ph);
      doc.add(paragraph1);
      
      Font fontProdi = FontFactory.getFont(FontFactory.COURIER_BOLD,11);
      
      selector1 = new FontSelector();
      selector1.addFont(fontProdi);
      ph = selector1.process("1. "+data.getPilihan_jurusan_1());
      
      paragraph1 = new Paragraph();
      paragraph1.add(ph);
      doc.add(paragraph1);
      
      selector1 = new FontSelector();
      selector1.addFont(fontProdi);
      ph = selector1.process("2. "+data.getPilihan_jurusan_2());
      
      paragraph1 = new Paragraph();
      paragraph1.add(ph);
      doc.add(paragraph1);
     
      selector1 = new FontSelector();
      
      selector1.addFont(fontTableID);
      ph = selector1.process("\nLOKASI UJIAN : ");
      
      paragraph1 = new Paragraph();
      paragraph1.add(ph);
      doc.add(paragraph1);
      
      selector1 = new FontSelector();
      selector1.addFont(fontProdi);
      ph = selector1.process("1. "+data.getLokasi_ujian().toUpperCase());
      
      paragraph1 = new Paragraph();
      paragraph1.add(ph);
      doc.add(paragraph1);
      
//         
    }
 
}
