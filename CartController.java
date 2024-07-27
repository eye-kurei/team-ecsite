package jp.co.internous.team2405.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.team2405.model.domain.TblCart;
import jp.co.internous.team2405.model.domain.dto.CartDto;
import jp.co.internous.team2405.model.form.CartForm;
import jp.co.internous.team2405.model.mapper.TblCartMapper;
import jp.co.internous.team2405.model.session.LoginSession;

/**
 * カート情報に関する処理のコントローラー
 * @author インターノウス
 *
 */
@Controller
@RequestMapping("/team2405/cart")
public class CartController {

	/*
	 * フィールド定義
	 */
	@Autowired
	private TblCartMapper cartMapper;

	@Autowired
	private LoginSession loginSession;

	/**
	 * カート画面を初期表示する。
	 * @param m 画面表示用オブジェクト
	 * @return カート画面
	 */
	@RequestMapping("/")
	public String index(Model m) {
		
		int userId = loginSession.getLogined() ? loginSession.getUserId() : loginSession.getTmpUserId();
		List<CartDto> carts = cartMapper.findByUserId(userId);
		m.addAttribute("carts", carts);
		m.addAttribute("loginSession", loginSession);
		
		return "cart";

	}

	/**
	 * カートに追加処理を行う
	 * @param f カート情報のForm
	 * @param m 画面表示用オブジェクト
	 * @return カート画面
	 */
	@RequestMapping("/add")
	public String addCart(CartForm f, Model m) {
		
		int userId = loginSession.getLogined() ? loginSession.getUserId() : loginSession.getTmpUserId();

		TblCart cart = new TblCart();
		cart.setUserId(userId);
		cart.setProductId(f.getProductId());
		cart.setProductCount(f.getProductCount());
		
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		cart.setCreatedAt(timeStamp);
		cart.setUpdatedAt(timeStamp);

		int count = cartMapper.findCountByUserIdAndProuductId(userId, f.getProductId());
		int result = 0;
		if (count != 0) {
			result = cartMapper.update(cart);
		} else {
			result = cartMapper.insert(cart);
		}
		
		if (result > 0) {
			List<CartDto> carts = cartMapper.findByUserId(userId);
			m.addAttribute("carts", carts);
			m.addAttribute("loginSession", loginSession);
		}
		
		return "redirect:/team2405/cart/";

	}

	/**
	 * カート情報を削除する
	 * @param checkedIdList 選択したカート情報のIDリスト
	 * @return true:削除成功、false:削除失敗
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/delete")
	@ResponseBody
	public boolean deleteCart(@RequestBody String checkedIdList) {

		Gson gson = new Gson();
		CartForm cartForm = gson.fromJson(checkedIdList, CartForm.class);
		List<Integer> deleteRequestIdList = cartForm.getCheckIdList();
		int deletedCount = cartMapper.deleteById(deleteRequestIdList);

		return deletedCount == deleteRequestIdList.size();

	}
}
