package com.vw.virtualwallet.services.impl;

import com.vw.virtualwallet.api.exceptions.customs.NotFoundException;
import com.vw.virtualwallet.api.models.responses.AccountDataResponse;
import com.vw.virtualwallet.api.models.responses.CheckBallanceResponse;
import com.vw.virtualwallet.persistence.entities.UserApp;
import com.vw.virtualwallet.persistence.repositories.AccountRepository;
import com.vw.virtualwallet.services.contracts.AccountService;
import com.vw.virtualwallet.utils.logs.WriteLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AccountServiceImpl implements the AccountService interface
 * to handle account-related operations such as checking balance.
 * It interacts with the AccountRepository to perform database operations.
 *
 * @author caito
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    /**
     * Checks the balance of the user's account.
     * Retrieves the account balance for the authenticated user.
     *
     * @param auth the authentication object containing user details
     * @return CheckBallance object containing the current balance
     */
    @Override
    @Transactional(readOnly = true)
    public CheckBallanceResponse checkBalance(Authentication auth) {
        log.info(WriteLog.logInfo("checkBalance"));
        UserApp user = (UserApp) auth.getPrincipal();
        return accountRepository.findByUserId(user.getId())
                .map(account -> new CheckBallanceResponse(account.getBalance()))
                .orElseThrow(() -> new RuntimeException("Account not found for user: " + user.getUsername()));
    }

    /**
     * Retrieves account data for the authenticated user.
     * Provides detailed information about the user's account.
     *
     * @param auth the authentication object containing user details
     * @return AccountDataResponse containing account details
     * @throws Exception if the account is not found
     */
    @Override
    @Transactional(readOnly = true)
    public AccountDataResponse getData(Authentication auth) {
        log.info(WriteLog.logInfo("getData"));
        UserApp user = (UserApp) auth.getPrincipal();
        return accountRepository.findByUserId(user.getId())
                .map(account -> new AccountDataResponse(account.getAccountNumber(), account.getAlias(),
                        account.getUser().getFullName(), account.getUser().getEmail(), account.getUser().getTelephone(),
                        account.getUser().getAddress(), account.getUser().getDni()))
                .orElseThrow(() -> new NotFoundException("Account not found for user: " + user.getUsername()));
    }

    /**
     * Retrieves account data by account number or alias.
     * Searches for an account using either the account number or alias.
     *
     * @param search the account number or alias to search for
     * @return AccountDataResponse containing account details, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    public AccountDataResponse getByAccountOrAlias(String search) {
        log.info(WriteLog.logInfo("getByAccountOrAlias"));
    return accountRepository.findByAccountNumberOrAliasEqualsIgnoreCase(search, search)
            .map(account -> new AccountDataResponse(account.getAccountNumber(), account.getAlias(),
                    account.getUser().getFullName(), account.getUser().getEmail(), account.getUser().getTelephone(),
                    account.getUser().getAddress(), account.getUser().getDni()))
            .orElseThrow(() -> new NotFoundException("Account not found for user: " + search));
    }
}
