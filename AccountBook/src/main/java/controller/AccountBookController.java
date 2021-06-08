package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dao.AccountBookDao;
import dto.AccountBook;
import dto.DtoWithHttpCode;

@RestController
@RequestMapping("/book")
public class AccountBookController {
	@Autowired
	private AccountBookDao dao;
	
	@GetMapping("/{yearMonth}")
	public DtoWithHttpCode<AccountBook> accountBookByYearMonth(@PathVariable("yearMonth") String yearMonth) {
		DtoWithHttpCode<AccountBook> dto;
		AccountBook book ;
		try{
			book = dao.selectByYearMonth(yearMonth);
			dto = new DtoWithHttpCode<AccountBook>(200, book, new String[1]);
		}catch(Exception e) {
			dto = new DtoWithHttpCode<AccountBook>(404, new AccountBook(), new String[1]);
		}
		return dto;
	}
	@PostMapping("/{yearMonth}")
	public DtoWithHttpCode<AccountBook> newAccountBook(@PathVariable("yearMonth") String yearMonth) {
		DtoWithHttpCode<AccountBook> dto;
		AccountBook book;
		try {
			book = dao.newAccountBook(yearMonth);
			dto = new DtoWithHttpCode<AccountBook>(200,book,new String[1]);
		}catch(Exception e) {
			dto = new DtoWithHttpCode<AccountBook>(404, new AccountBook(), new String[1]);
		}
		return dto;
	}
}
