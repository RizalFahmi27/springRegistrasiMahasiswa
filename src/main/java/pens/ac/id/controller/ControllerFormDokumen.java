package pens.ac.id.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.MediaType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;



import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.io.FilenameUtils;
import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.ResponseBody;

import pens.ac.id.model.DataDokumen;
import pens.ac.id.model.KartuUjian;
import pens.ac.id.service.ServiceUsers;
import pens.ac.id.service.ServiceDokumen;
import pens.ac.id.service.ServiceLokasiUjian;
import pens.ac.id.model.Users;

import java.io.File;
import java.io.FileInputStream;

import pens.ac.id.pdf_generator.Book;


@Controller
public class ControllerFormDokumen {
	
	
	@Autowired
	ServiceUsers serviceUsers;
	
	@Autowired
	ServiceDokumen serviceDokumen;
	
	@Autowired
	ServiceLokasiUjian ServiceLokasiUjian;
	
	@RequestMapping(value="/user/upload_berkas/", method=RequestMethod.GET)
	public String dokumen(Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute("is_loggedIn")!=null){
		
		String KAP = (String) session.getAttribute("KAP");	
			
		Users user = serviceUsers.getByKAP(KAP);
		System.out.println("id : "+user.getId());
		if(user.getDataDokumen()==null){
			//DataDokumen newDataDokumen = serviceDokumen.save(new DataDokumen());
			//System.out.println(""+dataDokumen.getId_dataDokumen());
			//serviceUsers.updateIdDokumen(newDataDokumen.getId_dataDokumen(), user);
			serviceUsers.updateIdDokumen(user);
			DataDokumen dataDokumen = serviceUsers.getByKAP(KAP).getDataDokumen();
			System.out.println("id dokumen : "+dataDokumen.getId_dataDokumen());
			model.addAttribute("dokumen",dataDokumen);
			return "/dashboard/upload_berkas";
		}
		else {
			DataDokumen dataDokumenFound = serviceDokumen.getById(user.getDataDokumen().getId_dataDokumen());
			model.addAttribute("dokumen",dataDokumenFound);
			return "/dashboard/upload_berkas";
		}
		}
		else {
			return "redirect:/login";
		}
		
	}
	
	@RequestMapping(value="/fileUpload", method=RequestMethod.POST)
	
	public String upload(@RequestParam("id") String id, @RequestParam("file_foto") MultipartFile file_foto, @RequestParam("file_SKL") MultipartFile file_SKL
			, @RequestParam("file_KK") MultipartFile file_KK, @RequestParam("file_bukti") MultipartFile file_bukti ,HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute("is_loggedIn")!=null){
		String no_maba = (String) session.getAttribute("KAP");
		System.out.println("masuk Upload");
		String FILE_PATH = "C:/Users/Rizal Fahmi/workspace2/mySpring/src/main/resources/static/files/mahasiswa_baru/";

		File name_foto = new File( FILE_PATH + no_maba + "/"); 
		
		if(!name_foto.exists())
			name_foto.mkdirs();
		
		
		DataDokumen dataDokumen = serviceDokumen.getById(Long.valueOf(id));
			
			try{
				
				if(!file_foto.isEmpty()){
			
					System.out.println("nama_foto : " +file_foto.getOriginalFilename());
					
					searchFile(name_foto, no_maba,"foto_");
					String extFoto = "." + FilenameUtils.getExtension(file_foto.getOriginalFilename());
					File foto_dir = new File(FILE_PATH + no_maba + "/foto_" + no_maba + extFoto);
					byte[] bytes_foto = file_foto.getBytes();
					BufferedOutputStream bos_foto = new BufferedOutputStream(new FileOutputStream(foto_dir));
					bos_foto.write(bytes_foto);
					bos_foto.close();
					System.out.println("path : " + foto_dir.getAbsolutePath());
					
					if(dataDokumen!=null)
						dataDokumen.setNamaFileFoto("foto_"+no_maba+extFoto);
					
			
				}
			
				if(!file_SKL.isEmpty()){
			
					searchFile(name_foto, no_maba,"SKL_");
					String extSKL = "." + FilenameUtils.getExtension(file_SKL.getOriginalFilename());
					File SKL_dir = new File(FILE_PATH + no_maba + "/SKL_" + no_maba + extSKL);
					byte[] bytes_SKL = file_SKL.getBytes();
					BufferedOutputStream bos_SKL = new BufferedOutputStream(new FileOutputStream(SKL_dir));
					bos_SKL.write(bytes_SKL);
					bos_SKL.close();
					
					if(dataDokumen!=null)
						dataDokumen.setNamaFileSKL("SKL_"+no_maba+extSKL);
						
			
				}
				
				if(!file_KK.isEmpty()){
			
					searchFile(name_foto, no_maba,"KK_");
					String extKK = "." + FilenameUtils.getExtension(file_KK.getOriginalFilename());
					File KK_dir = new File(FILE_PATH + no_maba + "/KK_" + no_maba + extKK);
					byte[] bytes_KK = file_KK.getBytes();	
					BufferedOutputStream bos_KK = new BufferedOutputStream(new FileOutputStream(KK_dir));
					bos_KK.write(bytes_KK);
					bos_KK.close();
					
					if(dataDokumen!=null)
						dataDokumen.setNamaFileKK("KK_"+no_maba+extKK);
				}
				
				if(!file_bukti.isEmpty()){
					
					searchFile(name_foto, no_maba,"Bukti_");
					String extBukti = "." + FilenameUtils.getExtension(file_bukti.getOriginalFilename());
					File Bukti_dir = new File(FILE_PATH + no_maba + "/Bukti_" + no_maba + extBukti);
					byte[] bytes_bukti = file_bukti.getBytes();	
					BufferedOutputStream bos_bukti = new BufferedOutputStream(new FileOutputStream(Bukti_dir));
					bos_bukti.write(bytes_bukti);
					bos_bukti.close();
					
					if(dataDokumen!=null)
						dataDokumen.setNamaFileBuktiPendaftaran("Bukti_"+no_maba+extBukti);
				}
				
				
						
				if(dataDokumen!=null){
					if(serviceDokumen.updateDokumen(dataDokumen)){}
					else {serviceDokumen.save(dataDokumen);}
				}
				else {
					return "redirect:/user/upload_berkas/";
				}
				
				serviceDokumen.save(dataDokumen);
				
				return "redirect:/user/upload_berkas/";
				
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return "redirect:/user/upload_berkas/";
			}
		}
			
		else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value="/download/files/10000212", method = RequestMethod.GET)
	
	public void downloadFileDaftarUlang(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") long id, @RequestParam("type") String type, @RequestParam("no_daftar") String no_daftar) throws IOException{
		
		HttpSession session = request.getSession();
		if(session.getAttribute("is_loggedIn")!=null){
		String PATH = "C:/Users/Rizal Fahmi/workspace2/mySpring/src/main/resources/static/files/mahasiswa_baru/"+ no_daftar + "/";
		
		DataDokumen dokumen = serviceDokumen.getById(id);
		
		File file = null;
		
		if(type.equalsIgnoreCase("foto"))
			file = new File(PATH + dokumen.getNamaFileFoto());
		else if(type.equalsIgnoreCase("skl"))
			file = new File(PATH + dokumen.getNamaFileSKL());
		else if(type.equalsIgnoreCase("kk"))
			file = new File(PATH + dokumen.getNamaFileKK());
		else if(type.equals("bukti"))
			file = new File(PATH + dokumen.getNamaFileBuktiPendaftaran());
		
		if(!file.exists()){
			String errorMessage = "File tidak ditemukan";
			System.out.println(errorMessage);
			OutputStream os = response.getOutputStream();
			os.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			os.close();
			return;
		}
		
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if(mimeType==null){
			System.out.println("mimeType tidak terdeteksi, gunakan ekstensi default");
			mimeType = "application/octet-stream";
		}
		
		System.out.println("mimeType : " + mimeType);
		
		response.setContentType(mimeType);
			
		/* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
        while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
		response.setHeader("Content-disposition", String.format("inline; filename=\"" + file.getName() + "\""));
		
		response.setContentLength((int)file.length());
		
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		
		FileCopyUtils.copy(is, response.getOutputStream());
		
		}
	}
	
	
	
	
	@RequestMapping(value="/cetak/2200001/kartu-ujian", method = RequestMethod.GET)
	public ModelAndView downloadExcel(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		Users user = serviceUsers.getByKAP((String) session.getAttribute("KAP"));
		
		
		if(session.getAttribute("is_loggedIn")!=null && user.isStatus() ){
		
		KartuUjian data = new KartuUjian();
        // create some sample data
        	data.setAlamat(user.getDataDiri().getAlamat_asal());
        	data.setFoto(user.getDataDokumen().getNamaFileFoto());
        	System.out.println(user.getLokasiUjian());
        	//data.setLokasi_ujian(user.getLokasiUjian().getNama_lokasi()+"\n"+user.getLokasiUjian().getAlamat());
        	System.out.println("demo" + ServiceLokasiUjian.getById(user.getLokasiUjian()).getNama_lokasi()+"\n"+ServiceLokasiUjian.getById(user.getLokasiUjian()).getAlamat());
        	data.setLokasi_ujian(ServiceLokasiUjian.getById(user.getLokasiUjian()).getNama_lokasi()+"\n"+ServiceLokasiUjian.getById(user.getLokasiUjian()).getAlamat());
        	data.setNama(user.getNamalengkap());
        	if(user.getNamalengkap().length()>10)
        	data.setNama_lembar_ujian(user.getNamalengkap().substring(0, 10));
        	else data.setNama_lembar_ujian(user.getNamalengkap());
        	data.setNo_peserta(user.getNoUjian());
        	data.setNo_telepon(user.getDataDiri().getNo_telepon());
        	data.setPilihan_jurusan_1(user.getDataDiri().getJurusan1());
        	data.setPilihan_jurusan_2(user.getDataDiri().getJurusan2());
        	data.setTahun_lulus(user.getDataSekolah().getTahun_lulus());
 
        // return a view which will be resolved by an excel view resolver
        	return new ModelAndView("pdfView", "data", data);
		}
		
		else return new ModelAndView("redirect:/login");
        
    }
	

	
	
	private void searchFile(File file, String no_maba, String contextFile){
		File realPath = new File(file.getAbsolutePath() + "/");
		File[] files = realPath.listFiles();
		System.out.println("file : "+realPath.getAbsolutePath());
		if(files!=null){
			for(File f : files){
				
				String fileName = f.getName().split("\\.")[0];
				System.out.println("went to files loop : "+fileName);
				if(fileName.equals(contextFile+no_maba)){
					f.delete();
					System.out.println("dihapus : "+contextFile);
				}
				
			}
		}
	}
	
	@RequestMapping(value="/delete/files/221110", method = RequestMethod.GET)
	public String deleteFile(@RequestParam("id") long id, @RequestParam("type") String type, @RequestParam("no_daftar") String no_daftar, HttpServletRequest request){
		DataDokumen dataDokumen = serviceDokumen.getById(id);
		
		String PATH = "C:/Users/Rizal Fahmi/workspace2/mySpring/src/main/resources/static/files/mahasiswa_baru/"+ no_daftar + "/";
		String fileName = "";
		
		if(type.equalsIgnoreCase("foto"))
			fileName = dataDokumen.getNamaFileFoto();
		else if(type.equalsIgnoreCase("kk"))
			fileName = dataDokumen.getNamaFileKK();
		else if(type.equalsIgnoreCase("skl"))
			fileName = dataDokumen.getNamaFileSKL();
		else if(type.equalsIgnoreCase("bukti"))
			fileName = dataDokumen.getNamaFileBuktiPendaftaran();
		
		File file = new File(PATH + fileName );
		
		if(file.exists())
			file.delete();
		
		System.out.println("Masuk delete : "+type);
		serviceDokumen.deleteDocument(dataDokumen, type);
		return "redirect:/user/upload_berkas/";
		
	}
}
