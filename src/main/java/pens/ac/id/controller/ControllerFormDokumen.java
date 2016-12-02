package pens.ac.id.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.ResponseBody;

import pens.ac.id.model.DataDokumen;

import pens.ac.id.service.ServiceUsers;
import pens.ac.id.service.ServiceDokumen;
import pens.ac.id.model.Users;

import java.io.File;
import java.io.FileInputStream;


@Controller
public class ControllerFormDokumen {
	
	
	
	@Autowired
	ServiceUsers serviceUsers;
	
	@Autowired
	ServiceDokumen serviceDokumen;
	
	@RequestMapping(value="/dashboard/user/{email}", method=RequestMethod.GET)
	public String dokumen(@PathVariable String email, Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute("is_loggedIn")!=null){
		
		Users user = serviceUsers.getByEmail(email);
		System.out.println("id : "+user.getId());
		if(user.getDataDokumen()==null){
			//DataDokumen newDataDokumen = serviceDokumen.save(new DataDokumen());
			//System.out.println(""+dataDokumen.getId_dataDokumen());
			//serviceUsers.updateIdDokumen(newDataDokumen.getId_dataDokumen(), user);
			serviceUsers.updateIdDokumen(user);
			DataDokumen dataDokumen = serviceUsers.getByEmail(email).getDataDokumen();
			System.out.println("id dokumen : "+dataDokumen.getId_dataDokumen());
			model.addAttribute("dokumen",dataDokumen);
			return "/dashboard/upload_dokumen";
		}
		else {
			DataDokumen dataDokumenFound = serviceDokumen.getById(user.getDataDokumen().getId_dataDokumen());
			
//			if(dataDokumenFound.getNamaFileFoto()!=null)
//				dataDokumenFound.setStatusFoto("found");
//			else dataDokumenFound.setStatusFoto("not_found");
//			
//			if(dataDokumenFound.getNamaFileKK()!=null)
//				dataDokumenFound.setStatusKK("found");
//			else dataDokumenFound.setStatusKK("no_found);
			
			System.out.println(dataDokumenFound.getStatusFoto());
			model.addAttribute("dokumen",dataDokumenFound);
			return "/dashboard/upload_dokumen";
		}
		}
		else {
			return "redirect:/login";
		}
		
	}
	
	@RequestMapping(value="/fileUpload", method=RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam("id") String id, @RequestParam("file_foto") MultipartFile file_foto, @RequestParam("file_SKL") MultipartFile file_SKL
			, @RequestParam("file_KK") MultipartFile file_KK ,HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute("is_loggedIn")!=null){
		String no_maba = (String) session.getAttribute("email");
		System.out.println("masuk Upload");
		if(file_foto!=null && file_SKL!=null && file_KK!=null){
			
			try{

			
			String FILE_PATH = "C:/Users/Rizal Fahmi/workspace2/mySpring/src/main/resources/static/files/mahasiswa_baru/";
			
			
			System.out.println("nama_foto : " +file_foto.getOriginalFilename());
			
			String extFoto = "." + file_foto.getOriginalFilename().split("\\.")[1];
			String extSKL = "." + file_SKL.getOriginalFilename().split("\\.")[1];
			String extKK = "." + file_KK.getOriginalFilename().split("\\.")[1];
			
			File name_foto = new File( FILE_PATH + no_maba + "/"); 
			
			if(!name_foto.exists())
				name_foto.mkdirs();
			
			searchFile(name_foto, no_maba);
			
			File foto_dir = new File(FILE_PATH + no_maba + "/foto_" + no_maba + extFoto);
			File SKL_dir = new File(FILE_PATH + no_maba + "/SKL_" + no_maba + extSKL);
			File KK_dir = new File(FILE_PATH + no_maba + "/KK_" + no_maba + extKK);
			
			

			
				byte[] bytes_foto = file_foto.getBytes();
				byte[] bytes_SKL = file_SKL.getBytes();
				byte[] bytes_KK = file_KK.getBytes();
				BufferedOutputStream bos_foto = new BufferedOutputStream(new FileOutputStream(foto_dir));
				BufferedOutputStream bos_SKL = new BufferedOutputStream(new FileOutputStream(SKL_dir));
				BufferedOutputStream bos_KK = new BufferedOutputStream(new FileOutputStream(KK_dir));
				
				bos_foto.write(bytes_foto);
				bos_KK.write(bytes_KK);
				bos_SKL.write(bytes_SKL);
				
				bos_foto.close();
				bos_KK.close();
				bos_SKL.close();
				
				DataDokumen dataDokumen = serviceDokumen.getById(Long.valueOf(id));
				System.out.println("path : " + foto_dir.getAbsolutePath());
				
				if(dataDokumen!=null){
					dataDokumen.setNamaFileFoto("foto_"+no_maba+extFoto);
					dataDokumen.setNamaFileKK("KK_"+no_maba+extKK);
					dataDokumen.setNamaFileSKL("SKL_"+no_maba+extSKL);
					if(serviceDokumen.updateDokumen(dataDokumen)){}
					else {serviceDokumen.save(dataDokumen);}
				}
				else {
					return "Simpan Data Gagal";
				}
				
				serviceDokumen.save(dataDokumen);
				
				return "Upload Berhasil";
				
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return "Upload Gagal, coba lagi";
			}
		}
		
		return "Terdapat field upload yang masih kosong";
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
	
	private void searchFile(File file, String no_maba){
		File realPath = new File(file.getAbsolutePath() + "/");
		File[] files = realPath.listFiles();
		System.out.println("file : "+realPath.getAbsolutePath());
		if(files!=null){
			for(File f : files){
				
				String fileName = f.getName().split("\\.")[0];
				System.out.println("went to files loop : "+fileName);
				if(fileName.equals("foto_"+no_maba)){
					f.delete();
					System.out.println("Foto dihapus");
				}
				else if(fileName.equals("KK_"+no_maba)){
					f.delete();
					System.out.println("KK dihapus");
				}
				else if(fileName.equals("SKL_"+no_maba)){
					f.delete();
					System.out.println("SKL dihapus");
				}
			}
		}
	}
}
