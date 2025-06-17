package com.vw.virtualwallet.services.impl;

import com.vw.virtualwallet.api.exceptions.customs.BadRequestException;
import com.vw.virtualwallet.api.exceptions.customs.NotFoundException;
import com.vw.virtualwallet.api.models.requests.TransactionRequest;
import com.vw.virtualwallet.api.models.requests.TransferRequest;
import com.vw.virtualwallet.persistence.entities.Account;
import com.vw.virtualwallet.persistence.entities.Transaction;
import com.vw.virtualwallet.persistence.entities.UserApp;
import com.vw.virtualwallet.persistence.repositories.AccountRepository;
import com.vw.virtualwallet.persistence.repositories.TransactionRepository;
import com.vw.virtualwallet.services.contracts.TransactionService;
import com.vw.virtualwallet.utils.enums.TransactionType;
import com.vw.virtualwallet.utils.logs.WriteLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * TransactionServiceImpl implements the TransactionService interface
 * to handle transaction-related operations such as deposits.
 * It interacts with the TransactionRepository and AccountRepository
 * to perform database operations.
 *
 * @author caito
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    /**
     * Deposits an amount into the user's account.
     * Validates the request and updates the account balance accordingly.
     *
     * @param request the transaction request containing amount and description
     * @param auth the authentication object containing user details
     * @throws BadRequestException if the deposit amount is not greater than zero
     */
    @Override
    @Transactional
    public void deposit(TransactionRequest request, Authentication auth) {
        log.info(WriteLog.logInfo("deposit"));
        UserApp user = (UserApp) auth.getPrincipal();
        Account account = accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Account not found for user: " + user.getUsername()));
        if (request.getAmount().compareTo( BigDecimal.ZERO) <= 0) {
            log.error(WriteLog.logError("Deposit amount must be greater than zero"));
            throw new BadRequestException(List.of("Deposit amount must be greater than zero"));
        }
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.DEPOSITO);
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setDateTime(LocalDateTime.now());
        transaction.setAccount(account);
        transactionRepository.save(transaction);
        account.setBalance(account.getBalance().add(transaction.getAmount()));
        accountRepository.save(account);
    }

    /**
     * Transfers an amount from the user's account to another account.
     * Validates the request, checks balances, and updates both accounts accordingly.
     *
     * @param request the transfer request containing destination account, amount, and description
     * @param auth the authentication object containing user details
     * @throws BadRequestException if the transfer amount is not greater than zero,
     *                             if the description is empty, or if there are insufficient funds
     * @throws NotFoundException if the source or destination account is not found
     */
    @Override
    @Transactional
    public void trasnfer(TransferRequest request, Authentication auth) {
        log.info(WriteLog.logInfo("trasnfer"));
        UserApp user = (UserApp) auth.getPrincipal();
        Account sourceAccount = accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("Source account not found for user: " + user.getUsername()));
        Account destinationAccount = accountRepository.findByAccountNumberOrAliasEqualsIgnoreCase(
                request.getDestinationAccount(), request.getDestinationAccount())
                .orElseThrow(() -> new NotFoundException("Destination account not found: "
                        + request.getDestinationAccount()));
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException(List.of("Amount must be greater than zero"));
        }
        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            throw new BadRequestException(List.of("Description cannot be empty"));
        }
        if (sourceAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new BadRequestException(List.of("Insufficient balance in source account"));
        }
        Transaction sourceTransaction = Transaction.builder()
                .type(TransactionType.TRANSFERENCIA_ENVIADA)
                .amount(request.getAmount())
                .description(request.getDescription())
                .dateTime(LocalDateTime.now())
                .account(sourceAccount)
                .build();
        Transaction destinationTransaction = Transaction.builder()
                .type(TransactionType.TRANSFERENCIA_RECIBIDA)
                .amount(request.getAmount())
                .description(request.getDescription())
                .dateTime(LocalDateTime.now())
                .account(destinationAccount)
                .build();
        transactionRepository.save(sourceTransaction);
        transactionRepository.save(destinationTransaction);
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(request.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(request.getAmount()));
        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);
    }


}
