package com.jsp.DAO;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.jsp.entity.UserInformation;

public class BankDAOImpl implements BankDAO {
	
	private final static String update = "update UserInformation userInfo set userInfo.amount =:amount where userInfo.email_id=:email and userInfo.password=:password";

	@Override
	public int insertBankCustomerDetails(UserInformation userInformation) {
		Scanner scan = new Scanner(System.in);
		
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("BankingApplication");
		EntityManager manager = managerFactory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
	
		manager.persist(userInformation);
		transaction.commit();
			
		manager.close();
		managerFactory.close();
		return userInformation.getAccount_number();
		
	}

	@Override
	public boolean creditAmountInAccount(UserInformation userInformation, double creditAmount) {
		
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("BankingApplication");
		EntityManager manager = managerFactory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		
			Query query = manager.createQuery(update);
			String email =userInformation.getEmail_id();
			String password = userInformation.getPassword();
			
			query.setParameter("email", email);
			query.setParameter("password", password);
			query.setParameter("amount", creditAmount);
			
			int result = query.executeUpdate();
			transaction.commit();
			manager.close();
			managerFactory.close();
			
			if(result != 0)
			{
				userInformation.setAmount(creditAmount);
				return true;
			}
			else
			{
				return false;
			}
			
		
	}

	@Override
	public boolean debitAmountFromAccount(UserInformation userInformation, double debitAmount) {

		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("BankingApplication");
		EntityManager manager = managerFactory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		
			Query query = manager.createQuery(update);
			String email =userInformation.getEmail_id();
			String password = userInformation.getPassword();
			
			query.setParameter("email", email);
			query.setParameter("password", password);
			query.setParameter("amount", debitAmount);
			
			int result = query.executeUpdate();
			transaction.commit();
			manager.close();
			managerFactory.close();
			
			if(result != 0)
			{
				userInformation.setAmount(debitAmount);
				return true;
			}
			else
			{
				return false;
			}
		
	}

	@Override
	public boolean changePasswordByUsingId(UserInformation userInformation, String password) {
		
		
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("BankingApplication");
		EntityManager manager = managerFactory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		
		String update_password = "update UserInformation userInfo set userInfo.password =:newpassword where userInfo.email_id=:email and userInfo.password=:password";
	
		
		Query query = manager.createQuery(update_password);
		String email =userInformation.getEmail_id();
		String oldPassword = userInformation.getPassword();
		
		query.setParameter("email", email);
		query.setParameter("password", oldPassword);
		query.setParameter("newpassword", password);
		
		int result = query.executeUpdate();
		transaction.commit();
		manager.close();
		managerFactory.close();
		
		if(result != 0)
		{
			userInformation.setPassword(password);
			return true;
		}
		else
		{
			return false;
		}
		
	}

	@Override
	public UserInformation searchUserAccountDetailsByUsingMobileAndPassword(String email, String password) {

		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("BankingApplication");
		EntityManager manager = managerFactory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		
		Query query = manager.createNamedQuery(password);
		
		query.setParameter("email", email);
		query.setParameter("password", password);
		
		try {
			UserInformation userInformation = (UserInformation) query.getSingleResult();
			
			manager.close();
			managerFactory.close();
			
			return userInformation;
		}
		catch(Exception e)
		{
			return null;
		}
	}

}
