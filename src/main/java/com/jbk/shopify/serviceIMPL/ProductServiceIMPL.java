package com.jbk.shopify.serviceIMPL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jbk.shopify.dao.ProductDao;
import com.jbk.shopify.model.Category;
import com.jbk.shopify.model.Charges;
import com.jbk.shopify.model.FinalProduct;
import com.jbk.shopify.model.Product;
import com.jbk.shopify.model.Supplier;
import com.jbk.shopify.service.ProductService;
import com.jbk.shopify.utility.ValidateObject;

@Service
public class ProductServiceIMPL implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private FinalProduct finalProduct;
	@Autowired
	private Charges charges;

	@Override
	public Object saveProduct(Product product) {

		String productId = new SimpleDateFormat("yyyyMMddHHmmsss").format(new java.util.Date());

		product.setProductId(Long.parseLong(productId));

		return productDao.saveProduct(product);

//		Map<String, String> errorMap = ValidateObject.validateProduct(product);
//		
//		if(errorMap.isEmpty()) {
//			String status = productDao.saveProduct(product);
//			return status;
//		}else {
//			return errorMap;
//		}

	}

	@Override
	public Product getProductById(long productId) {

		return productDao.getProductById(productId);
	}

	@Override
	public List<Product> getAllProduct() {

		return productDao.getAllProduct();
	}

	@Override
	public boolean deleteProductById(long productId) {

		return productDao.deleteProductById(productId);
	}

	@Override
	public boolean updateProduct(Product product) {

		return productDao.updateProduct(product);
	}

	@Override
	public List<Product> getAllProductByOrder_Field(String orderType, String name) {

		return productDao.getAllProductByOrder_Field(orderType, name);
	}

	@Override
	public List<Product> getMaxPriceProducts() {

		return productDao.getMaxPriceProducts();
	}

	@Override
	public List<Product> getProductsContainsWith(String subStringOfProduct) {

		return productDao.getProductsContainsWith(subStringOfProduct);
	}

	@Override
	public int getTotalCountOfProducts() {

		return productDao.getTotalCountOfProducts();
	}

	@Override
	public List<Product> getProductsMoreThanGivenPrice(double price) {

		return productDao.getProductsMoreThanGivenPrice(price);
	}

	@Override
	public FinalProduct getFinalProductById(long id) {

		Product product = getProductById(id);

		if (product != null) {
			finalProduct.setProductId(id);
			finalProduct.setProductName(product.getProductName());
			finalProduct.setProductQty(product.getProductQty());
			finalProduct.setProductPrice(product.getProductPrice());
			finalProduct.setSupplier(product.getSupplier());
			finalProduct.setCategory(product.getCategory());

			double productPrice = product.getProductPrice();
			int gst = product.getCategory().getGst();
			float deliveryCharges = product.getCategory().getDeliveryCharge();
			int discount = product.getCategory().getDiscount();

			double gstAmount = productPrice * gst / 100;
			charges.setGstAmount(gstAmount);
			charges.setDeliveryCharges(deliveryCharges);
			finalProduct.setCharges(charges);

			double discountAmout = productPrice * discount / 100;
			finalProduct.setDiscountAmount(discountAmout);

			finalProduct.setFinalProductPrice((productPrice + deliveryCharges + gstAmount) - discountAmout);

		}

		return finalProduct;
	}

	@Override
	public Product getProductByName(String productName) {

		return productDao.getProductByName(productName);
	}

	public List<Product> readFile(String filePath) {
		Product product = null;
		List<Product> list = new ArrayList<Product>();
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis);) {

			Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rows = sheet.rowIterator();

			while (rows.hasNext()) {
				Row row = rows.next();

				if (row.getRowNum() == 0) {
					continue;
				}

				product = new Product();
				String productId = new SimpleDateFormat("yyyyMMddHHmm").format(new java.util.Date());

				Random random = new Random();
				int num = random.nextInt(899) + 100;
				productId = productId + num;
				product.setProductId(Long.parseLong(productId));

				Iterator<Cell> cells = row.cellIterator();

				while (cells.hasNext()) {
					Cell cell = (Cell) cells.next();

					int columnIndex = cell.getColumnIndex();

					switch (columnIndex) {
					case 0: {
						product.setProductName(cell.getStringCellValue());
						break;
					}

					case 1: {
						Supplier supplier = new Supplier();
						supplier.setSupplierId((long) cell.getNumericCellValue());
						product.setSupplier(supplier);
						break;
					}

					case 2: {
						Category category = new Category();
						category.setCategoryId((long) cell.getNumericCellValue());
						product.setCategory(category);
						break;
					}

					case 3: {
						product.setProductQty((int) cell.getNumericCellValue());
						break;
					}

					case 4: {
						product.setProductPrice(cell.getNumericCellValue());
						break;
					}

					}

				}
				list.add(product);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	@Override
	public Object uploadSheet(MultipartFile file) {
		String path = "src/main/resources/";
		String fileName = file.getOriginalFilename();
		List<Product> list = null;
		int status = 0;
		int uploadedRecords = 0;
		int existsRecords = 0;
		try (FileOutputStream fos = new FileOutputStream(path + fileName);) {

			byte[] data = file.getBytes();
			fos.write(data);

			list = readFile(path + fileName);

			for (Product product : list) {
				status = productDao.saveProduct(product);

				if (status == 1) {
					uploadedRecords = uploadedRecords + 1;
				} else if (status == 2) {
					existsRecords = existsRecords + 1;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Uploaded Records", uploadedRecords);
		map.put("Already Exists Record", existsRecords);

		return map;
	}

}
