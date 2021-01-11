package com.lti.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.lti.entity.Admin;
import com.lti.entity.Card;
import com.lti.entity.FrequentlyAskedQuestion;
import com.lti.entity.Product;
import com.lti.entity.ProductPurchased;
import com.lti.entity.Transaction;
import com.lti.entity.User;
import com.lti.exception.UserAndAdminServiceException;

@Repository
@Transactional
public class UserAndAdminRepositoryImpl implements UserAndAdminRepository {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public User addOrUpdate(User user) {
		try {
			return em.merge(user);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public User findUserById(long userId) {
		try {
			return em.find(User.class, userId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<User> viewAllUsers() {

		String jpql = "from User u";
		try {
			TypedQuery<User> query = em.createQuery(jpql, User.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<User> viewAllCardHolders() {
		String jpql = "select u from User u where u.cardStatus=1";
		try {
			TypedQuery<User> query = em.createQuery(jpql, User.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<User> viewAllNotCardHolders() {
		String jpql = "select u from User u where u.cardStatus=0";
		try {
			TypedQuery<User> query = em.createQuery(jpql, User.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<User> viewCardHoldersByType(String cardType) {
		String jpql = "select u from User u where u.cardType=:ct";
		try {
			TypedQuery<User> query = em.createQuery(jpql, User.class);
			query.setParameter("ct", cardType);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public Product addorUpdateProduct(Product product) {
		try {
			return em.merge(product);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Product findProductById(long productId) {
		try {
			return em.find(Product.class, productId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Product> viewAllProducts() {
		try {
			String jpql = "select p from Product p";
			TypedQuery<Product> query = em.createQuery(jpql, Product.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<Product> viewProductsByFilter(String productType) {
		String jpql = "select p from Product p where p.productType=:pt";
		try {
			TypedQuery<Product> query = em.createQuery(jpql, Product.class);
			query.setParameter("pt", productType);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public Transaction addOrUpdateTransaction(Transaction transaction) {
		try {
			return em.merge(transaction);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Transaction findTransactionByTransactionId(long transactionId) {
		try {
			return em.find(Transaction.class, transactionId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Transaction> viewTransactionsOfAnUser(long userId) {
		String jpql = "select t from Transaction t where t.user.userId=:uid order by t.transactionDate";
		try {
			TypedQuery<Transaction> query = em.createQuery(jpql, Transaction.class);
			query.setParameter("uid", userId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Transactional
	public ProductPurchased addOrUpdateProductPurchased(ProductPurchased productPurchased) {

		try {
			return em.merge(productPurchased);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ProductPurchased findProductPurchasedById(long productPurchasedId) {

		try {
			return em.find(ProductPurchased.class, productPurchasedId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<ProductPurchased> viewAllProductsPurchasedByUser(long userId) {
		String jpql = "select pp from ProductPurchased pp where pp.user.userId=:uid order by pp.productPurchasedDate";
		try {
			TypedQuery<ProductPurchased> query = em.createQuery(jpql, ProductPurchased.class);
			query.setParameter("uid", userId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Transactional
	public Card addorUpdateCard(Card card) {
		try {
			return em.merge(card);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Card findCardById(long cardId) {

		try {
			return em.find(Card.class, cardId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Card> viewAllCards() {
		String jpql = "select c from Card c";
		try {
			TypedQuery<Card> query = em.createQuery(jpql, Card.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

//	public List<Card> viewAllValidCards() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public List<Card> viewAllCardsByCardType(String cardType) {
		String jpql = "select c from Card c where c.cardType=:ct";
		try {
			TypedQuery<Card> query = em.createQuery(jpql, Card.class);
			query.setParameter("ct", cardType);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

//	public List<Card> viewAllValidCardsByCardType() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public FrequentlyAskedQuestion addOrUpdateFaq(FrequentlyAskedQuestion faq) {
		try {
			return em.merge(faq);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public FrequentlyAskedQuestion frequentlyAskedQuestionbyId(long faqId) {
		try {
			return em.find(FrequentlyAskedQuestion.class, faqId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<FrequentlyAskedQuestion> viewFrequentlyAskedQuestionsByProductId(long productId) {
		String jpql = "select faq from FrequentlyAskedQuestion faq where faq.product.productId=:uid";
		try {
			TypedQuery<FrequentlyAskedQuestion> query = em.createQuery(jpql, FrequentlyAskedQuestion.class);
			query.setParameter("uid", productId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

//	public boolean login(String emailId, String password) {
//		String sql = "select u from User u where u.emailId=:email and u.password=:userPassword";
//		try {
//			TypedQuery<User> query = em.createQuery(sql, User.class);
//			query.setParameter("email", emailId);
//			query.setParameter("userPassword", password);
//			if (query.getSingleResult() != null) {
//				return true;
//			} else {
//				return false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
	

//	@Transactional
//	public boolean adminLogin(String emailId, String password) {		
//		String sql = "select a from Admin a where a.emailId=:aid and a.password=:aPassword";
//		try {
////			Admin admin=new Admin();
////			admin.setAdminName("Tim");
////			admin.setEmailId("tim@gmail.com");
////			admin.setPassword("Tim@123");
////			em.merge(admin);
//			
//			TypedQuery<Admin> query = em.createQuery(sql, Admin.class);
//			query.setParameter("aid", emailId);
//			query.setParameter("aPassword", password);
//			if ( query.getSingleResult()!= null) {
//				return true;
//			} else {
//				return false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}

	public Card findCardByUserId(long userId) {
		String jpql = "select c from Card c";
		try {
			TypedQuery<Card> query = em.createQuery(jpql,Card.class);
		    List<Card> cards=query.getResultList();
			for(Card card:cards) {
				if(card.getUser().getUserId() == userId) {
					return card;
				}
			}
			return null;			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Transaction> viewTransactionsOfAnUserByDate(LocalDate fromDate, LocalDate toDate, long userId) {
		String sql = "select t from Transaction t where t.transactionDate between :fromDate and :toDate and t.userId=:uid";
		try {
			TypedQuery<Transaction> query = em.createQuery(sql, Transaction.class);
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
			query.setParameter("uid", userId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<ProductPurchased> viewProductPurchasedByAnUserByDate(LocalDate fromDate, LocalDate toDate,
			long userId) {
		String sql = "select pp from ProductPurchased pp where pp.productPurchasedDate between :fromDate and :toDate and pp.userId=:uid";
		try {
			TypedQuery<ProductPurchased> query = em.createQuery(sql, ProductPurchased.class);
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
			query.setParameter("uid", userId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Transaction> viewTransactionsByDate() {
		String sql = "select t from Transaction t order by t.transactionDate";
		try {
			TypedQuery<Transaction> query = em.createQuery(sql, Transaction.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<ProductPurchased> viewAllProductsPurchasedByDate() {
		String sql = "select pp from ProductPurchased pp order by pp.productPurchasedDate";
		try {
			TypedQuery<ProductPurchased> query = em.createQuery(sql, ProductPurchased.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public User deleteUser(long userId) {
		try {
		User user = em.find(User.class, userId);
		em.remove(user);
		return user;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Product deleteProduct(long productId) {
		try {
			Product product = em.find(Product.class, productId);
			em.remove(product);
			return product;
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	public Card deleteCard(long cardId) {
		try {
			Card card = em.find(Card.class, cardId);
			em.remove(card);
			return card;
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	public ProductPurchased deleteProductPurchased(long productPurchasedId) {
		try {
			ProductPurchased productPurchased = em.find(ProductPurchased.class, productPurchasedId);
			em.remove(productPurchased);
			return productPurchased;
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	public Transaction deleteTransaction(long transactionId) {
		try {
			Transaction transaction = em.find(Transaction.class, transactionId);
			em.remove(transaction);
			return transaction;
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	public FrequentlyAskedQuestion deleteFrequentlyAskedQuestion(long faqId) {
		try {
			FrequentlyAskedQuestion faq = em.find(FrequentlyAskedQuestion.class, faqId);
			em.remove(faq);
			return faq;
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}
	
	//added extra
	
	public boolean isUserEmailPresent(String emailId) {
		System.out.println("email");
		return (Long)
				em
				.createQuery("select count(c.userId) from User c where c.emailId=:email")
				.setParameter("email", emailId)
				.getSingleResult() >= 1 ? true : false;
	}
	
	public boolean isUserBankPresent(long accountNo) {
		return (Long)
				em
				.createQuery("select count(c.userId) from User c where c.accountNo=:accountNo")
				.setParameter("accountNo", accountNo)
				.getSingleResult() >= 1 ? true : false;
	}
	
	public boolean isUserPancardPresent(String pancardNumber) {
		return (Long)
				em
				.createQuery("select count(c.userId) from User c where c.pancardNumber=:pancardNumber")
				.setParameter("pancardNumber", pancardNumber)
				.getSingleResult() >= 1 ? true : false;
	}
	public boolean isUserAadharcardPresent(long aadharNumber) {
		return (Long)
				em
				.createQuery("select count(c.userId) from User c where c.aadharNumber=:aadharNumber")
				.setParameter("aadharNumber", aadharNumber)
				.getSingleResult() >= 1 ? true : false;
	}
	
	public User login(String emailId, String password) {
		String sql = "select u from User u where u.emailId=:email and u.password=:userPassword";
		try {
			TypedQuery<User> query = em.createQuery(sql, User.class);
			query.setParameter("email", emailId);
			query.setParameter("userPassword", password);
			User user = query.getSingleResult();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserAndAdminServiceException("Invalid emailId/password");
		}
	}
	
	
	public Admin adminLogin(String emailId, String password) {		
		String sql = "select a from Admin a where a.emailId=:aid and a.password=:aPassword";
		try {
			TypedQuery<Admin> query = em.createQuery(sql, Admin.class);
			query.setParameter("aid", emailId);
			query.setParameter("aPassword", password);
			Admin admin = query.getSingleResult();
			return admin;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserAndAdminServiceException("Invalid emailId/password");
		}
	}
	
	public boolean isAdminPresent(String emailId) {
		return (Long)
				em
				.createQuery("select count(c.adminId) from Admin c where c.emailId = :email")
				.setParameter("email", emailId)
				.getSingleResult() == 1 ? true : false;
	}

}
//String sql = "select e from Enrollment e where e.regDate between :fromDate and :toDate";
//try {
//	TypedQuery<Enrollment> query = em.createQuery(sql, Enrollment.class);
//	query.setParameter("fromDate", fromDate);
//	query.setParameter("toDate", toDate);
//	return query.getResultList();
//} catch (Exception e) {
//	e.printStackTrace();
//	return null;
//}