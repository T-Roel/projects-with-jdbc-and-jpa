package org.troel.application;

import java.util.Date;
import java.util.List;

import org.troel.model.dao.DaoFactory;
import org.troel.model.dao.SellerDao;
import org.troel.model.entities.Department;
import org.troel.model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("=== TEST 1: seller findById ===");
		Seller seller = sellerDao.findById(3); 
		System.out.println(seller);
		
		System.out.println("\n=== TEST 2: seller findByDepartment ===");
		Department dep = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(dep);
		list.forEach(x -> {
			System.out.println(x);
		});
		
		System.out.println("\n=== TEST 3: seller findAll ===");
		List<Seller> listAll = sellerDao.findAll();
		listAll.forEach(x -> {
			System.out.println(x);
		});
		
		System.out.println("\n=== TEST 4: seller insert ===");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, dep);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id = " + newSeller.getId());
	}
}