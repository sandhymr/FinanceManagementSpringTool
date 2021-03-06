package com.lti.service;

import java.time.LocalDate;
import java.util.List;

import com.lti.dto.CardStatus;
import com.lti.dto.ForgotPasswordDto;
import com.lti.dto.LoginDto;
import com.lti.dto.Status;
import com.lti.entity.Admin;
import com.lti.entity.Card;
import com.lti.entity.FrequentlyAskedQuestion;
import com.lti.entity.Product;
import com.lti.entity.ProductPurchased;
import com.lti.entity.Transaction;
import com.lti.entity.User;

public interface UserAndAdminService {

	// user
	
	
	User addOrUpdate(User user);
	User findUserById(long userId);
	List<User> viewAllUsers();  // All registered Users
	List<User> viewAllCardHolders();
	List<User> viewAllNotCardHolders(); // Only Registered but not haviing Card
	List<User> viewCardHoldersByType(String cardType);
	
	//product
	
	 Product addorUpdateProduct(Product product);
	 Product findProductById(long productId);
	 List<Product> viewAllProducts();
	 List<Product> viewProductsByFilter(String productType);
	 
	 //transaction
	 
	 Transaction addOrUpdateTransaction(Transaction transaction);
	 Transaction findTransactionByTransactionId(long transactionId);
	 List<Transaction> viewTransactionsOfAnUser(long userId);
	 List<Transaction> viewTransactionsOfAnUserByDate(LocalDate fromDate,LocalDate toDate, long userId);
//	 Transaction payEmi(long productPurchasedId, long userId);      discuss
	 List<Transaction> viewTransactionsByDate();
	 
	 
	 //productPurchased
	 
	 ProductPurchased addOrUpdateProductPurchased(ProductPurchased productPurchased);
	 ProductPurchased findProductPurchasedById(long productPurchasedId);
	 List<ProductPurchased> viewAllProductsPurchasedByUser(long userId);
	 List<ProductPurchased> viewProductPurchasedByAnUserByDate(LocalDate fromDate,LocalDate toDate, long userId);	 
	 List<ProductPurchased> viewAllProductsPurchasedByDate();
	 //card
	 
	 Card addorUpdateCard(Card card);
	 Card findCardById(long cardId);
	 List<Card> viewAllCards();					//Admin Functionality
	 Card findCardByUserId(long userId);
//	 List<Card> viewAllValidCards();			//Admin Functionality
	 List<Card> viewAllCardsByCardType(String cardType);   	//Admin Functionality
//	 List<Card> viewAllValidCardsByCardType(); 	//Admin Functionality
	
 
	 
	 //FAQ
	 
	 FrequentlyAskedQuestion addOrUpdateFaq(FrequentlyAskedQuestion faq);
	 FrequentlyAskedQuestion frequentlyAskedQuestionbyId(long faqId);
	 List<FrequentlyAskedQuestion> viewFrequentlyAskedQuestionsByProductId(long productId);
 

//	boolean login(String emailId, String password);
	
//	boolean adminLogin(String emailId, String password);
//	
	 //added extra
	long register(User user); 
	User login(String emailId, String password);
	Admin adminLogin(String emailId, String password);
	void EmiCompletion(User user,String productName);
	void productPurchaseCompletion(User user,String productName);
	public ForgotPasswordDto verifyUser(User user);
	public Status changePassword(LoginDto dto);
		
//    forgotPassword(String email)
//	User changePassword(long userId);  USE ADDORUPDATE USER FOR THIS
//	User payJoiningFee(long userId); userResource
	//Extra

}
