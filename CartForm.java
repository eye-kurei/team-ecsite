package jp.co.internous.team2405.model.form;

import java.io.Serializable;
import java.util.List;

/**
 * カートフォーム
 * @author インターノウス
 *
 */
public class CartForm implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int userId;
	private int productId;
	private int productCount;
	
	private List<Integer> checkedIdList;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	
	public List<Integer> getCheckIdList(){
		return checkedIdList;
	}
	public void setCheckedIdList(List<Integer> checkedIdList) {
		this.checkedIdList = checkedIdList;
	}
	
}
