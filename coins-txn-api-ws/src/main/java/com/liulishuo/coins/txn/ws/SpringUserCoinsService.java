package com.liulishuo.coins.txn.ws;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.liulishuo.coins.txn.boot.StartUp;
import com.vf.fsn.txn.service.CoinsTxnService;


@RestController
public class SpringUserCoinsService {
	@Resource(name = "coinsTxnService")
	private CoinsTxnService coinsTxnService;
	private static final Logger logger = LoggerFactory.getLogger(StartUp.class);
	
	@RequestMapping(value = "/coins/user/{userId}", method = RequestMethod.GET)
	public int getUserCoins(@PathVariable("userId") Long userId) throws Exception{
		logger.debug("查询userID=[{}]", userId);
		Integer coins = coinsTxnService.getCoinsByUserId(userId);
		return coins;
	} 
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public int addUserCoins(HttpServletRequest request,HttpServletResponse Response) throws Exception{
		Long userId = Long.valueOf(request.getParameter("user_id"));
	    Integer coins = Integer.valueOf(request.getParameter("coins"));
	    logger.debug("增加账户userID=[{}]", userId);
	    logger.debug("增加金额coins=[{}]", coins);
	    coinsTxnService.addCoinsForUser(userId, coins);
		return coins;
	}
	@RequestMapping(value = "/transaction/transfer", method = RequestMethod.POST)
	public int transferUserCoins(HttpServletRequest request,HttpServletResponse Response) throws Exception{
		Long fromUserId = Long.valueOf(request.getParameter("from_user_id"));
		Long toUserId = Long.valueOf(request.getParameter("to_user_id"));
	    Integer coins = Integer.valueOf(request.getParameter("coins"));
	    logger.debug("转出账户fromUserId=[{}]", fromUserId);
	    logger.debug("转入账户toUserId=[{}]", toUserId);
	    logger.debug("转账金额coins=[{}]", coins);
	    try {
			coinsTxnService.transfer(fromUserId, toUserId, coins);
		} catch (Exception e) {
			logger.error("转账失败coins=[{}]", coins);
			throw new Exception("转账失败");
		}
	    logger.debug("转账成功coins=[{}]", coins);
		return coins;
	}

}
