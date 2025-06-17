package com.vw.virtualwallet.services.contracts;

import com.vw.virtualwallet.api.models.responses.AccountDataResponse;
import com.vw.virtualwallet.api.models.responses.CheckBallanceResponse;
import org.springframework.security.core.Authentication;

/**
 * AccountService interface defines the contract for account-related operations.
 * It provides a method to check the balance of a user's account.
 *
 * @author caito
 *
 */
public interface AccountService {
    CheckBallanceResponse checkBalance(Authentication auth);
    AccountDataResponse getData(Authentication auth) ;
    AccountDataResponse getByAccountOrAlias(String search);
}
