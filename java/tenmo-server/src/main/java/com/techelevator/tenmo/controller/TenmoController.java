package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;

@RestController
/*@PreAuthorize("isAuthenticated()")*/

public class TenmoController {

    @Autowired
    AccountDao dao;
    @Autowired
    UserDao userDao;

    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public Balance getBalance(Principal principal) {
        System.out.println(principal.getName());
        return dao.getBalance(principal.getName());
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        List<User> allUsers = userDao.findAll();
        return allUsers;
    }

    @RequestMapping(path = "/user/{username}", method = RequestMethod.GET)
    public int getUserId(@PathVariable String username) {
        int userId = userDao.findIdByUsername(username);
        return userId;
    }

    @RequestMapping(path = "/{username}", method = RequestMethod.GET)
    public User getUser(@PathVariable String username) {
        return userDao.findByUsername(username);
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public boolean createUser(@RequestBody User user) {
        return userDao.create(user.getUsername(), user.getPassword());
    }

    @RequestMapping(path = "/balance/transfer", method = RequestMethod.PUT)
    public void transferMoney(@RequestBody TransferUser transferUser){
        dao.transferMoney(transferUser);
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public void createTransfer(@RequestBody TransferUser transferUser) {
        dao.createTransfer(transferUser);
    }

    @RequestMapping(path = "/transfer/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int transferId) {
        return dao.getTransferById(transferId);
    }

    @RequestMapping(path = "/users/{userId}", method = RequestMethod.GET)
    public int getAccountIdByUserId(@PathVariable int userId) {
        return userDao.getAccountIdByUserId(userId);
    }

    @RequestMapping(path = "/transfers/{accountId}", method = RequestMethod.GET)
    public Transfer[] getAllTransfersByAccountId(@PathVariable int accountId) {
        return dao.getAllTransfersByAccountId(accountId);
    }


}
