package com.lti.resource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.BuyProductDto;
import com.lti.dto.CardStatus;
import com.lti.dto.FaqDto;
import com.lti.dto.ForgotPasswordDto;
import com.lti.dto.LoginDto;
import com.lti.dto.LoginStatus;
import com.lti.dto.PayJoiningFeeStatus;
import com.lti.dto.RegisterStatus;
import com.lti.dto.Status;
import com.lti.dto.Status.StatusType;
import com.lti.entity.Admin;
import com.lti.entity.Card;
import com.lti.entity.FrequentlyAskedQuestion;
import com.lti.entity.Product;
import com.lti.entity.ProductPurchased;
import com.lti.entity.Transaction;
import com.lti.entity.User;
import com.lti.service.UserAndAdminService;

@RestController
@CrossOrigin
public class UserAndAdminResource {

	@Autowired
	UserAndAdminService userAndAdminService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	RegisterStatus registerUser(@RequestBody User user) {
		try {
			long userId = userAndAdminService.register(user);
			RegisterStatus status = new RegisterStatus();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Registration Successful!");
			status.setUserId(userId);
			return status;
		} catch (Exception e) {
			RegisterStatus status = new RegisterStatus();
			status.setStatus(StatusType.FAILED);
			status.setMessage(e.getMessage());
			return status;
		}

	}

	@GetMapping(value = "/findUserById")
	public User findUserById(@RequestParam("userId") long userId) {
		System.out.println(userId);
		User user = userAndAdminService.findUserById(userId);
		System.out.println(user.getFirstName());
		return user;

	}

	@GetMapping(value = "/payJoiningFee")
	public PayJoiningFeeStatus payJoiningFee(@RequestParam("userId") long userId) {
		PayJoiningFeeStatus status = new PayJoiningFeeStatus();
		User user = userAndAdminService.findUserById(userId);
		if (user.getRegistrationFee() == 0) {
			user.setRegistrationFee(1);
			User newUser = userAndAdminService.addOrUpdate(user);
			status.setUserId(newUser.getUserId());
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Payment done successfully!!");
			return status;
		} else {
			status.setStatus(StatusType.FAILED);
			status.setMessage("Payment done already!!");
			return status;
		}
	}

	@PostMapping(value = "/login")
	public LoginStatus userLogin(@RequestBody LoginDto login) {
		String password = Base64.getEncoder().encodeToString(login.getPassword().getBytes());
		LoginStatus status = new LoginStatus();
		try {
			User user = userAndAdminService.login(login.getEmailId(), password);
			status.setUserId(user.getUserId());
			status.setUserName(user.getFirstName() + " " + user.getLastName());
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Login successful!!");
			return status;
		} catch (Exception e) {
			status.setStatus(StatusType.FAILED);
			status.setMessage(e.getMessage());
			return status;
		}
	}

	@PostMapping(value = "/adminLogin")
	public LoginStatus adminLogin(@RequestBody LoginDto login) {
		LoginStatus status = new LoginStatus();
		try {
			Admin admin = userAndAdminService.adminLogin(login.getEmailId(), login.getPassword());
			status.setUserId(admin.getAdminId());
			status.setUserName(admin.getAdminName());
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Login successful!!");
			return status;

		} catch (Exception e) {
			status.setStatus(StatusType.FAILED);
			status.setMessage(e.getMessage());
			return status;
		}
	}

	@GetMapping(value = "/generateCard")
	public CardStatus verifyAndGenerateCard(@RequestParam("userId") long userId) {
		User user = userAndAdminService.findUserById(userId);
		CardStatus status = new CardStatus();
		System.out.println(user.getFirstName());
		if (user.getRegistrationFee() > 0 && user.getCardStatus() < 1) {
			Card card = new Card();
			card.setUser(user);
			card.setValidity(LocalDate.now().plusYears(1));
			String cardType = user.getCardType();
			if (cardType.equalsIgnoreCase("Gold")) {
				card.setTotalCredit(150000);
				card.setCreditRemaining(card.getTotalCredit());
			} else if (cardType.equalsIgnoreCase("Titanium")) {
				card.setTotalCredit(250000);
				card.setCreditRemaining(card.getTotalCredit());
			}
			card.setCreditUsed(0);
			Card newCard = userAndAdminService.addorUpdateCard(card);
			user.setCardStatus(1);
			userAndAdminService.addOrUpdate(user);
			status.setCardId(newCard.getCardId());
			status.setMessage("Card generated successfully!!");
			status.setStatus(StatusType.SUCCESS);
			return status;
		} else if (user.getCardStatus() > 0) {
			status.setMessage("user has card already!!");
			status.setStatus(StatusType.FAILED);
			return status;
		} else {
			status.setMessage("User has not paid joining fee");
			status.setStatus(StatusType.FAILED);
			return status;
		}
	}

	@GetMapping(value = "/findCardbyUserId")
	public Card findCardByUserId(@RequestParam("userId") long userId) {
		return userAndAdminService.findCardByUserId(userId);
	}

	@GetMapping("/viewAllUsers")
	public List<User> viewAllUsers() {
		return userAndAdminService.viewAllUsers();
	}

	@GetMapping("/viewAllNotCardHolders")
	public List<User> viewAllNotCardHolders() {
		return userAndAdminService.viewAllNotCardHolders();
	}

	@GetMapping("/viewAllCardHolders")
	public List<User> viewAllCardHolders() {
		return userAndAdminService.viewAllCardHolders();
	}

	@GetMapping("/viewCardHoldersByType")
	public List<User> viewCardHoldersByType(@RequestParam("cardType") String cardType) {
		return userAndAdminService.viewCardHoldersByType(cardType);
	}

	@PostMapping(value = "/addProduct")
	public Product addOrUpdateProduct(@RequestBody Product product) {
		product.setProductType(product.getProductType().toLowerCase());
		return userAndAdminService.addorUpdateProduct(product);
	}

	@GetMapping("/viewAllProducts")
	public List<Product> viewAllProducts() {
		return userAndAdminService.viewAllProducts();
	}

	@GetMapping(value = "/findProductById")
	public Product findProductById(@RequestParam("productId") long productId) {
		return userAndAdminService.findProductById(productId);
	}

	@GetMapping(value = "/viewProductsByFilter")
	public List<Product> viewProductsByFilter(@RequestParam("productType") String productType) {
		String newProductType = productType.toLowerCase();
		return userAndAdminService.viewProductsByFilter(newProductType);
	}

	@PostMapping(value = "/addFaqByProductId")
	public FrequentlyAskedQuestion addOrUpdateFaq(@RequestBody FaqDto faq) {
		Product product = userAndAdminService.findProductById(faq.getProductId());
		FrequentlyAskedQuestion faqNew = new FrequentlyAskedQuestion();
		faqNew.setFaqId(faq.getFaqId());
		faqNew.setQuestions(faq.getQuestions());
		faqNew.setAnswer(faq.getAnswer());
		faqNew.setProduct(product);
		return userAndAdminService.addOrUpdateFaq(faqNew);
	}

	@GetMapping(value = "/viewFrequentlyAskedQuestionsByProductId")
	public List<FrequentlyAskedQuestion> viewFrequentlyAskedQuestionsByProductId(@RequestParam long productId) {
		return userAndAdminService.viewFrequentlyAskedQuestionsByProductId(productId);
	}

	@PostMapping(value = "/buyProduct")
	public BuyProductDto buyProduct(@RequestBody BuyProductDto buyProductDto) {
		BuyProductDto dto = new BuyProductDto();
		double amount_to_be_paid = 0;
		ProductPurchased productPurchased = new ProductPurchased();
		User user = userAndAdminService.findUserById(buyProductDto.getUserId());
		Product product = userAndAdminService.findProductById(buyProductDto.getProductId());
		Card card = userAndAdminService.findCardByUserId(user.getUserId());
		List<ProductPurchased> list = user.getProductsPurchased();
		for (ProductPurchased p : list) {
			System.out.println(p.getProductPurchasedId());
			amount_to_be_paid += p.getAmount_remaining();
		}
		if ((card.getCreditRemaining() - amount_to_be_paid) >= (product.getPrice() * 1.05)) {
			double emi = ((product.getPrice() * 1.05) / buyProductDto.getEmiScheme());
			productPurchased.setUser(user);
			productPurchased.setProduct(product);
			productPurchased.setEmiScheme(buyProductDto.getEmiScheme());
			productPurchased.setProductPurchasedDate(LocalDate.now());
			productPurchased.setAmount_remaining((product.getPrice() * 1.05) - emi);
			ProductPurchased newProductPurchased = userAndAdminService.addOrUpdateProductPurchased(productPurchased);

			Transaction transaction = new Transaction();
			transaction.setUser(user);
			transaction.setCard(findCardByUserId(user.getUserId()));
			transaction.setProductPurchased(newProductPurchased);
			transaction.setTransactionDate(LocalDate.now());
			transaction.setAmountPaid(emi);
			Transaction newTransaction = userAndAdminService.addOrUpdateTransaction(transaction);

			card.setCreditRemaining(card.getCreditRemaining() - emi);
			card.setCreditUsed(card.getCreditUsed() + emi);
			card.setUser(user);
			userAndAdminService.addorUpdateCard(card);

			dto.setAmount_paid(newTransaction.getAmountPaid());
			dto.setAmount_remaining(newProductPurchased.getAmount_remaining());
			dto.setEmiScheme(newProductPurchased.getEmiScheme());
			dto.setProduct_price(product.getPrice());
			dto.setProductName(product.getProductName());
			dto.setProductId(product.getProductId());
			dto.setProductPurchasedId(newProductPurchased.getProductPurchasedId());
			dto.setUserId(user.getUserId());
			dto.setMessage("Product purchase Successful");
			dto.setStatus(StatusType.SUCCESS);
            
			dto.setTransaction_date(newTransaction.getTransactionDate()); // added
			dto.setTransaction_time(LocalTime.now()); // added
			
			userAndAdminService.productPurchaseCompletion(user, product.getProductName());
			return dto;
		} else {
			dto.setMessage("Not enough money in the card");
			dto.setStatus(StatusType.FAILED);
			return dto;
		}

	}

	@PostMapping(value = "/payEmi")
	BuyProductDto payEmi(@RequestBody BuyProductDto dtoR) {
		BuyProductDto dto = new BuyProductDto();
		ProductPurchased productPurchased = userAndAdminService.findProductPurchasedById(dtoR.getProductPurchasedId());
		User user = userAndAdminService.findUserById(dtoR.getUserId());
		// List<Transaction> transactions = user.getTransactions(); not required
		Card card = user.getCard();

		if (productPurchased.getAmount_remaining() > 0) {
			double emi = ((productPurchased.getProduct().getPrice() * 1.05) / productPurchased.getEmiScheme());// added
																												// *1.05

			if ((productPurchased.getAmount_remaining() - emi) == 0) {
				dto.setEmiCompleted(true);
				userAndAdminService.EmiCompletion(user, productPurchased.getProduct().getProductName());
			}

			card.setCreditRemaining(card.getCreditRemaining() - emi);
			card.setCreditUsed(card.getCreditUsed() + emi); // added
			userAndAdminService.addorUpdateCard(card);

			productPurchased.setAmount_remaining(productPurchased.getAmount_remaining() - emi);
			userAndAdminService.addOrUpdateProductPurchased(productPurchased);

			Transaction transaction = new Transaction();
			transaction.setUser(user);
			transaction.setCard(card);
			transaction.setProductPurchased(productPurchased);
			transaction.setTransactionDate(LocalDate.now());
			transaction.setAmountPaid(emi);
			Transaction newTransaction = userAndAdminService.addOrUpdateTransaction(transaction);
			// transactions.add(transaction); not required

			// user.setTransactions(transactions); not required
			// userAndAdminService.addOrUpdate(user); not required

			dto.setAmount_paid(emi);
			dto.setAmount_remaining(productPurchased.getAmount_remaining());
			dto.setEmiScheme(productPurchased.getEmiScheme());
			dto.setEmi(emi);
			dto.setProductName(productPurchased.getProduct().getProductName());
			dto.setMessage("EMI payment Successful");
			dto.setStatus(StatusType.SUCCESS);

			// added
			dto.setProduct_price(productPurchased.getProduct().getPrice());
			dto.setProductId(productPurchased.getProduct().getProductId());
			dto.setProductPurchasedId(productPurchased.getProductPurchasedId());
			dto.setTransaction_date(newTransaction.getTransactionDate());
			dto.setTransaction_time(LocalTime.now());

			return dto;
		} else {
			dto.setMessage(
					"Your EMI transaction for product : " + productPurchased.getProduct().getProductName() + " is completed successfully!!");
			dto.setStatus(StatusType.FAILED);
			dto.setEmiCompleted(true);
			return dto;
		}
	}

	@GetMapping("/viewAllTransactionsByUserId")
	public List<BuyProductDto> viewAllTransactionsByUserId(@RequestParam("userId") long userId) {
		List<Transaction> transactions = userAndAdminService.viewTransactionsOfAnUser(userId);
		if (transactions != null) {
			List<BuyProductDto> dtoList = new ArrayList<BuyProductDto>();

			for (Transaction transaction : transactions) {
				BuyProductDto dto = new BuyProductDto();
				dto.setAmount_paid(transaction.getAmountPaid());
				dto.setAmount_remaining(transaction.getProductPurchased().getAmount_remaining());
				dto.setEmiScheme(transaction.getProductPurchased().getEmiScheme());
				dto.setProduct_price(transaction.getProductPurchased().getProduct().getPrice());
				dto.setProductId(transaction.getProductPurchased().getProduct().getProductId());
				dto.setProductName(transaction.getProductPurchased().getProduct().getProductName());
				dto.setProductPurchasedId(transaction.getProductPurchased().getProductPurchasedId());
				dto.setTransaction_date(transaction.getTransactionDate());
				dto.setUserId(transaction.getUser().getUserId());

				dtoList.add(dto);
			}
			return dtoList;
		} else {
			return null;
		}

	}
	
	@GetMapping("/productsPurchasedByUser")
	public List<BuyProductDto> productsPurchasedByUserId(@RequestParam("userId") long userId){
		List<ProductPurchased> pp = userAndAdminService.viewAllProductsPurchasedByUser(userId);
		if(pp != null) {
			List<BuyProductDto> dtoList = new ArrayList<BuyProductDto>();
			for(ProductPurchased p:pp) {
				BuyProductDto dto = new BuyProductDto();
				dto.setAmount_remaining(p.getAmount_remaining());
				dto.setEmiScheme(p.getEmiScheme());
				dto.setProductId(p.getProduct().getProductId());
				dto.setProductName(p.getProduct().getProductName());
				
				dtoList.add(dto);
			}
			return dtoList;
		}else {
			return null;
		}
	}
	
	@PostMapping(value="/verifyUser")
	ForgotPasswordDto verifyUser(@RequestBody LoginDto dto) {
		ForgotPasswordDto status=new ForgotPasswordDto();
		try {
		User user = userAndAdminService.findUserById(dto.getUserId());
		
		if(user.getEmailId().equals(dto.getEmailId())) { //equals method
			status = userAndAdminService.verifyUser(user);
			return status;
		}
		else {
			status.setStatus(StatusType.FAILED);
			status.setMessage("User is Invalid");
			return status;
		}
		}
		catch(Exception e) {
			status.setStatus(StatusType.FAILED);
			status.setMessage("Exception");
			return status;
		}
	}
	
	@PostMapping("/changePassword")
	public Status changePassword(@RequestBody LoginDto dto) {
		return userAndAdminService.changePassword(dto);
		
	}

}
