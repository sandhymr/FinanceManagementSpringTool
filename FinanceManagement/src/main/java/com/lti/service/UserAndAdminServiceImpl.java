package com.lti.service;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.dto.ForgotPasswordDto;
import com.lti.dto.LoginDto;
import com.lti.dto.Status;
import com.lti.dto.Status.StatusType;
import com.lti.entity.Admin;
import com.lti.entity.Card;
import com.lti.entity.FrequentlyAskedQuestion;
import com.lti.entity.Product;
import com.lti.entity.ProductPurchased;
import com.lti.entity.Transaction;
import com.lti.entity.User;
import com.lti.exception.UserAndAdminServiceException;
import com.lti.repository.UserAndAdminRepository;

@Service
public class UserAndAdminServiceImpl implements UserAndAdminService {

	@Autowired
	UserAndAdminRepository userAndAdminRepository;
	
	@Autowired
	EmailService emailService;
	
	
	@Override
	public Status changePassword(LoginDto dto) {
		Status status=new Status();
		try {
		String BasicBase64format 
        = Base64.getEncoder() 
         .encodeToString(dto.getPassword().getBytes()); 
		User user = userAndAdminRepository.findUserById(dto.getUserId());
	    user.setPassword(BasicBase64format);
	    userAndAdminRepository.addOrUpdate(user);
	    
	    status.setStatus(StatusType.SUCCESS);
	    status.setMessage("Password Changed Successfully");
	    return status;
		}
		catch(Exception e) {
			status.setMessage("Password update failed");
			status.setStatus(StatusType.FAILED);
			return status;
		}
	}
	
	
	@Override
	public ForgotPasswordDto verifyUser(User user) {
		Random rand = new Random();
		ForgotPasswordDto fpDto = new ForgotPasswordDto();
		int otp = rand.nextInt((9999 - 100) + 1) + 10;
		
		
		String subject="OTP for changing the password";
		String email= user.getEmailId();
		String text = "Hi" + user.getFirstName()+ " "+ user.getLastName() +"Your OTP is: " + otp ;
		emailService.sendEmailForNewRegistration(email,text,subject);
		System.out.println("Email Sent");
		
		fpDto.setOtp(otp);
		fpDto.setMessage("User verified and OTP generated");
		fpDto.setStatus(StatusType.SUCCESS);
		
		return fpDto;
		
	}
	
	public void EmiCompletion(User user,String productName) {
		String subject ="EMI successful";
		String email = user.getEmailId();
		String text = "Hi "+user.getFirstName()+" "+user.getLastName()+"!! Your EMI transaction for product : " + productName +" is completed successfully!!";
		emailService.sendEmailForNewRegistration(email, text, subject);
		System.out.println("Email Sent.");
	}
	
	public void productPurchaseCompletion(User user,String productName) {
		String subject ="Product purchased successfully";
		String email = user.getEmailId();
		String text = "Hi "+user.getFirstName()+" "+user.getLastName()+"!! Your product : " + productName +" purchase is successful!!";
		emailService.sendEmailForNewRegistration(email, text, subject);
		System.out.println("Email Sent.");
	}
	
	public long register(User user) {
		System.out.println(userAndAdminRepository.isUserEmailPresent(user.getEmailId()));
//		if(userAndAdminRepository.isUserEmailPresent(user.getEmailId()) || 
//				userAndAdminRepository.isUserAadharcardPresent(user.getAadharNumber()) ||
//				userAndAdminRepository.isUserBankPresent(user.getAccountNo()) ||
//				userAndAdminRepository.isUserPancardPresent(user.getPancardNumber())) {
//			throw new UserAndAdminServiceException("User already registered!");
//		}
		if(userAndAdminRepository.isUserEmailPresent(user.getEmailId())) {
			throw new UserAndAdminServiceException("EmailId already registered!");
		}else if(userAndAdminRepository.isUserAadharcardPresent(user.getAadharNumber()) ){
			throw new UserAndAdminServiceException("Aadhar number already registered!");
		}else if(userAndAdminRepository.isUserBankPresent(user.getAccountNo())) {
			throw new UserAndAdminServiceException("Account number already registered!");
		}else if(userAndAdminRepository.isUserPancardPresent(user.getPancardNumber())) {
			throw new UserAndAdminServiceException("Pancard number already registered!");
		}
		else {
			String BasicBase64format 
                 = Base64.getEncoder() 
                  .encodeToString(user.getPassword().getBytes()); 
			user.setPassword(BasicBase64format);
			User newUser=userAndAdminRepository.addOrUpdate(user);
			String subject ="Registration successful";
			String email = newUser.getEmailId();
			String text = "Hi "+newUser.getFirstName()+" "+newUser.getLastName()+"!! Your registration Id is :"+newUser.getUserId();
			emailService.sendEmailForNewRegistration(email, text, subject);
			System.out.println("Email Sent.");
			return newUser.getUserId();
		}
	}
	
	@Override
	public User addOrUpdate(User user) {
		return userAndAdminRepository.addOrUpdate(user);
	}

	@Override
	public User findUserById(long userId) {
		return userAndAdminRepository.findUserById(userId);
	}

	@Override
	public List<User> viewAllUsers() {
		return userAndAdminRepository.viewAllUsers();
	}

	@Override
	public List<User> viewAllCardHolders() {
		return userAndAdminRepository.viewAllCardHolders();
	}

	@Override
	public List<User> viewAllNotCardHolders() {
		return userAndAdminRepository.viewAllNotCardHolders();
	}

	@Override
	public List<User> viewCardHoldersByType(String cardType) {
		return userAndAdminRepository.viewCardHoldersByType(cardType);
	}

	@Override
	public Product addorUpdateProduct(Product product) {
		return userAndAdminRepository.addorUpdateProduct(product);
	}

	@Override
	public Product findProductById(long productId) {
		return userAndAdminRepository.findProductById(productId);
	}

	@Override
	public List<Product> viewAllProducts() {
		return userAndAdminRepository.viewAllProducts();
	}

	@Override
	public List<Product> viewProductsByFilter(String productType) {
		return userAndAdminRepository.viewProductsByFilter(productType);
	}

	@Override
	public Transaction addOrUpdateTransaction(Transaction transaction) {
		return userAndAdminRepository.addOrUpdateTransaction(transaction);
	}

	@Override
	public Transaction findTransactionByTransactionId(long transactionId) {
		return userAndAdminRepository.findTransactionByTransactionId(transactionId);
	}

	@Override
	public List<Transaction> viewTransactionsOfAnUser(long userId) {
		return userAndAdminRepository.viewTransactionsOfAnUser(userId);
	}

	@Override
	public List<Transaction> viewTransactionsOfAnUserByDate(LocalDate fromDate, LocalDate toDate, long userId) {
		return userAndAdminRepository.viewTransactionsOfAnUserByDate(fromDate, toDate, userId);
	}

	@Override
	public List<Transaction> viewTransactionsByDate() {
		return userAndAdminRepository.viewTransactionsByDate();
	}

	@Override
	public ProductPurchased addOrUpdateProductPurchased(ProductPurchased productPurchased) {
		return userAndAdminRepository.addOrUpdateProductPurchased(productPurchased);
	}

	@Override
	public ProductPurchased findProductPurchasedById(long productPurchasedId) {
		return userAndAdminRepository.findProductPurchasedById(productPurchasedId);
	}

	@Override
	public List<ProductPurchased> viewAllProductsPurchasedByUser(long userId) {
		return userAndAdminRepository.viewAllProductsPurchasedByUser(userId);
	}

	@Override
	public List<ProductPurchased> viewProductPurchasedByAnUserByDate(LocalDate fromDate, LocalDate toDate,
			long userId) {
		return userAndAdminRepository.viewProductPurchasedByAnUserByDate(fromDate, toDate, userId);
	}

	@Override
	public List<ProductPurchased> viewAllProductsPurchasedByDate() {
		return userAndAdminRepository.viewAllProductsPurchasedByDate();
	}

	@Override
	public Card addorUpdateCard(Card card) {
		return userAndAdminRepository.addorUpdateCard(card);
	}

	@Override
	public Card findCardById(long cardId) {
		return userAndAdminRepository.findCardById(cardId);
	}

	@Override
	public List<Card> viewAllCards() {
		return userAndAdminRepository.viewAllCards();
	}

	@Override
	public Card findCardByUserId(long userId) {
		return userAndAdminRepository.findCardByUserId(userId);
	}

	@Override
	public List<Card> viewAllCardsByCardType(String cardType) {
		return userAndAdminRepository.viewAllCardsByCardType(cardType);
	}

	@Override
	public FrequentlyAskedQuestion addOrUpdateFaq(FrequentlyAskedQuestion faq) {
		return userAndAdminRepository.addOrUpdateFaq(faq);
	}

	@Override
	public FrequentlyAskedQuestion frequentlyAskedQuestionbyId(long faqId) {
		return userAndAdminRepository.frequentlyAskedQuestionbyId(faqId);
	}

	@Override
	public List<FrequentlyAskedQuestion> viewFrequentlyAskedQuestionsByProductId(long productId) {
		return userAndAdminRepository.viewFrequentlyAskedQuestionsByProductId(productId);
	}
//
//	@Override
//	public boolean login(String emailId, String password) {
//		return userAndAdminRepository.login(emailId, password);
//	}
	
	

//	@Override
//	public boolean adminLogin(String emailId, String password) {
//		return userAndAdminRepository.adminLogin(emailId, password);
//	}
//	

	@Override
	public User login(String emailId, String password) {
		
		try {
			if(!userAndAdminRepository.isUserEmailPresent(emailId)) {
				throw new UserAndAdminServiceException("User not registered!");
			}
			User user = userAndAdminRepository.login(emailId, password);
			return user;
		}
		//catch(EmptyResultDataAccessException e) {
		catch(Exception e) {
			throw new UserAndAdminServiceException(e.getMessage());
		}
	}
	
	@Override
	public Admin adminLogin(String emailId, String password) {
		
	try {
		if(!userAndAdminRepository.isAdminPresent(emailId)) {
			throw new UserAndAdminServiceException("Admin not registered!");
		}
		Admin admin = userAndAdminRepository.adminLogin(emailId, password);
		return admin;
	}catch(Exception e) {
		throw new UserAndAdminServiceException(e.getMessage());
	}
		
	}
	

}
