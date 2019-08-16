package com.springGgg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller
public class MainController {
	
	private String localHost;
	private Path path;
	private File file;
	private Stream<String> lines;
	private Optional<String> opStr;
	
	@RequestMapping("/main")
	public String main() {
		// 1. 초기값 셋팅
		localHost = "http://localhost:8080/";
		path = Paths.get("C:/temp", "simpleUrlVm");
		file = new File("C:\\temp\\simpleUrlVm");
		
		// 2. 폴더 생성
		File Folder = new File("C:\\temp");
		if(!Folder.exists()) {
			try {
				Folder.mkdir();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return "main";
	}
	
	@RequestMapping(value = "/changeUrl.do", method = RequestMethod.POST)
	public ModelAndView changeUrl(Model model, HttpServletRequest request) throws IOException {
		
		Map<String,Object> modelMap = new HashMap<String,Object>();
		String orgUrl = request.getParameter("textUrl") !=""? request.getParameter("textUrl"):"";
		if(StringUtils.isEmpty(orgUrl)){
			modelMap.put("err","true");
	    	modelMap.put("msg","URL이 올바르지 않습니다");
	    	return new ModelAndView(new MappingJackson2JsonView(),modelMap);
	    }
		
		// 1. 파라미터값 읽어와서 랜덤 8 Character 생성
		String str = "";
		String strNew = "";
		List<String> strList = new ArrayList<String>();
		
		// 2. 단축URL 저장 및 생성
		// 2.1 파일 있는지 확인
		if(!file.exists()) {
			// 2.1 파일 없을 경우 생성 후 저장
			file.createNewFile();
			IntStream rndStrm = new Random().ints(8,65,90);
			rndStrm.mapToObj(item -> (char)item).forEach(item -> strList.add(String.valueOf((char)item)));
			for(String s:strList){ str += s; }
			
			try (BufferedWriter writer = Files.newBufferedWriter(path)){
				writer.write(orgUrl+","+str+"\n");
			}
			
			modelMap.put("url",localHost+str);
	    	modelMap.put("msg","단축URL이 생성되었습니다.");
	    	return new ModelAndView(new MappingJackson2JsonView(),modelMap);
			
		} else {
			// 2.2 이미 생성된 파일일 경우, 동일 URL 확인
			lines = Files.lines(path);
			opStr = lines.filter(item -> item.contains(orgUrl)).findFirst();
			
			if(opStr.isPresent()) {
	        	// 2.3 동일 URL 존재할 경우 단축URL 알려줌 리턴
				String[] strArray = opStr.get().split(",");
				
				modelMap.put("url",localHost + strArray[1]);
	        	modelMap.put("msg","이미 등록된 URL 입니다.\n요청URL: "+ strArray[0] + "\n단축URL: " + localHost + strArray[1]);
	    		return new ModelAndView(new MappingJackson2JsonView(),modelMap);
	        }else {
	        	// 2.4 동일 URL 없을 경우, 동일 단축URL 확인 중복되는 단축URL 없을때 까지 loop
	        	strNew = checkDuplicateRandomStr();
	        	BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath(),true));
	        	writer.append(orgUrl+","+strNew+"\n");
	        	writer.flush();
	        	writer.close();
	        }
		}

		modelMap.put("url",localHost + strNew);
    	modelMap.put("msg","단축URL이 생성되었습니다.");
		return new ModelAndView(new MappingJackson2JsonView(),modelMap);
	}
	
	private String checkDuplicateRandomStr() throws IOException {
		
		String strRndFinal = "";
		List<String> rndList= new ArrayList<String>();
		while(true) {
			// 1. 난수번호 10개 생성
			for( int i=0 ; i < 10 ; i++) {
				String str = "";
				List<String> strList = new ArrayList<String>();
				
				IntStream rndStrm = new Random().ints(8,65,90);
				rndStrm.mapToObj(item -> (char)item).forEach(item -> strList.add(String.valueOf((char)item)));
				for(String s:strList){ str += s; }
				
				rndList.add(str);			
			}

			// 2. 난수번호 10개 중 일치하는게 있는지 파일 확인, 없으면 그 번호로 난수번호 생성하고 끝.			
			for(String s : rndList) {
				lines = Files.lines(path);
				opStr = lines.filter(item -> item.contains(s)).findFirst();
				if(!opStr.isPresent()) {
					strRndFinal = s;
					break;
				}
			}
			
			if(!StringUtils.isEmpty(strRndFinal)) {
				break;
			}
		}
		return strRndFinal;
	}

	@RequestMapping(value ="/{idx}",method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView redirectUrl(@PathVariable("idx") String idx,Model model, HttpServletRequest request, HttpServletResponse response) {		

		if(file.exists()) {
			Stream<String> lines;
			try {
				lines = Files.lines(path);
				opStr = lines.filter(item -> item.contains(idx)).findFirst();
				
				if(opStr.isPresent()) {
					String[] strArray = opStr.get().split(",");
					return new ModelAndView("redirect:" + strArray[0]);
		        }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			return new ModelAndView("redirect:/main");
		}
		return new ModelAndView("redirect:/main");
	}
}
