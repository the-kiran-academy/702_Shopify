package com.jbk.shopify.dao;

import java.util.List;

import com.jbk.shopify.model.Product;

public interface ProductDao {

	public String saveProduct(Product product);

	public Product getProductById(long productId);

	public List<Product> getAllProduct();

	public boolean deleteProductById(long productId);

	public boolean updateProduct(Product product);

	public List<Product> getAllProductByOrder_Field(String orderType, String name);

	public List<Product> getMaxPriceProducts();

	public List<Product> getProductsContainsWith(String subStringOfProduct);

	public int getTotalCountOfProducts();

	public List<Product> getProductsMoreThanGivenPrice(double price);

	public Product getProductByName(String productName);
}
