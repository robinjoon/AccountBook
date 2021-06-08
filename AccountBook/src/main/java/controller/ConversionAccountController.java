package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dao.ConversionAccountDao;
import dto.ConversionAccount;
import dto.DtoWithHttpCode;

import java.util.List;

@RestController
@RequestMapping("/accounts/conversion")
public class ConversionAccountController {
	@Autowired
	private ConversionAccountDao dao;
	
	@GetMapping("/{yearMonth}")
	public DtoWithHttpCode<List<ConversionAccount>> conversionAccountsByYearMonth(@PathVariable("yearMonth")String yearMonth){
		List<ConversionAccount> list = null;
		DtoWithHttpCode<List<ConversionAccount>> dto;
		try {
			list = dao.selectByYearMonth(yearMonth);
			dto = new DtoWithHttpCode<List<ConversionAccount>>(200, list, new String[1]);
		}catch(Exception e){
			dto = new DtoWithHttpCode<List<ConversionAccount>>(404, list, new String[1]);
		}
		return dto;
	}
	
	@PostMapping("")
	public DtoWithHttpCode<ConversionAccount> insert(@RequestBody ConversionAccount account){
		DtoWithHttpCode<ConversionAccount> dto;
		ConversionAccount data = null;
		try {
			data = dao.insert(account);
			dto = new DtoWithHttpCode<ConversionAccount>(200, data, new String[1]);
		}catch (Exception e) {
			dto =  new DtoWithHttpCode<ConversionAccount>(500, data, new String[1]);
		}
		return dto;
	}
}
