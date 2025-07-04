package com.vw.virtualwallet.services.contracts;

import com.vw.virtualwallet.api.models.requests.TransactionRequest;
import com.vw.virtualwallet.api.models.requests.TransferRequest;
import com.vw.virtualwallet.api.models.responses.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

/**
 * Service interface for handling transactions in the Virtual Wallet application.
 * This interface defines methods for processing transactions such as deposits.
 *
 * @author caito
 *
 */
public interface TransactionService {
  void deposit(TransactionRequest request, Authentication auth);
  void trasnfer(TransferRequest request, Authentication auth);
  Page<TransactionResponse> getTransactions(Authentication auth, int page, int size);
}
