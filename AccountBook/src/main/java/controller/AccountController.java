package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dao.AccountDao;
import dto.Account;
import dto.DtoWithHttpCode;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	@Autowired
	private AccountDao accountDao;
	@GetMapping("/{date}")
	private DtoWithHttpCode<List<Account>> accountsByDate(@PathVariable("date") String date){
		List<Account> accounts = null;
		DtoWithHttpCode<List<Account>> dto;
		try {
			accounts = accountDao.selectByDate(date);
			dto = new DtoWithHttpCode<List<Account>>(200, accounts, new String[1]);
		}catch(Exception e) {
			dto = new DtoWithHttpCode<List<Account>>(404, null, new String[1]);
		}
		return dto;
	}
	@GetMapping("{type}/{yearMonth}")
	private DtoWithHttpCode<List<Account>> accountsByYearMonth(@PathVariable("yearMonth") String yearMonth, @PathVariable("type")String type) {
		List<Account> accounts = null;
		DtoWithHttpCode<List<Account>> dto;
		if(type.equalsIgnoreCase("income") || type.equalsIgnoreCase("expenditure")) {
			try {
				accounts = accountDao.selectByYearMonth(yearMonth, type);
				dto = new DtoWithHttpCode<List<Account>>(200, accounts, new String[1]);
			}catch(Exception e) {
				dto = new DtoWithHttpCode<List<Account>>(404, null, new String[1]);
			}
		}else {
			dto = new DtoWithHttpCode<List<Account>>(500, accounts, new String[1]);
		}
		return dto;
	}
	@PostMapping("")
	private DtoWithHttpCode<Account> newAccount(@RequestBody Account account) {
		long aid;
		DtoWithHttpCode<Account> dto;
		try {
			aid = accountDao.insert(account);
			dto = new DtoWithHttpCode<Account>(200, accountDao.selectByAid(aid), new String[1]);
		}catch(Exception e) {
			dto = new DtoWithHttpCode<Account>(404, new Account(), new String[1]);
			e.printStackTrace();
		}
		return dto;
	}
	@DeleteMapping("/{aid}")
	private DtoWithHttpCode<Account> deleteAccount(@PathVariable("aid") long aid) {
		Account account;
		DtoWithHttpCode<Account> dto;
		try{
			account = accountDao.deleteAccount(aid);	
			dto = new DtoWithHttpCode<Account>(200, account, new String[1]);
		}catch(Exception e) {
			dto = new DtoWithHttpCode<Account>(500, new Account(), new String[1]);
		}
		return dto;
	}
	@PutMapping("/{aid}")
	private DtoWithHttpCode<Account> editAccount(@RequestBody Account account) {
		DtoWithHttpCode<Account> dto;
		try {
			Account backup = accountDao.deleteAccount(account.getAid());
			try {
				Account account2 = accountDao.insertWithAid(account);
				dto = new DtoWithHttpCode<Account>(200, account2, new String[1]);
			}catch(Exception e){
				accountDao.insertWithAid(backup);
				dto = new DtoWithHttpCode<Account>(500, new Account(), new String[1]);
			}
		}catch(Exception e){
			dto = new DtoWithHttpCode<Account>(500, new Account(), new String[1]);
		}
		return dto;
	}
}
