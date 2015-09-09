package org.majimena.petz.service;

import org.majimena.petz.common.exceptions.ApplicationException;
import org.majimena.petz.domain.Customer;
import org.majimena.petz.domain.customer.CustomerAuthorizationToken;
import org.majimena.petz.domain.customer.CustomerCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 顧客サービス.
 */
public interface CustomerService {

    Page<Customer> getCustomersByCustomerCriteria(CustomerCriteria criteria, Pageable pageable);

    /**
     * 認証トークンの情報で登録情報と合致しているかチェックして認証する.
     *
     * @param token 顧客認証トークン
     * @return 顧客情報
     * @throws ApplicationException 認証に失敗した場合に発生する例外
     */
    Customer authorize(CustomerAuthorizationToken token) throws ApplicationException;

    /**
     * 顧客情報を保存する. 入力があればユーザー情報も一緒に更新する.
     *
     * @param clinicId 顧客として登録するクリニックのID
     * @param customer 顧客情報
     * @return 保存した顧客情報
     * @throws ApplicationException
     */
    Customer saveCustomer(String clinicId, Customer customer) throws ApplicationException;
}
