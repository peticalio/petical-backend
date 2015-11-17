package org.majimena.petz.web.api.ticket;

import com.codahale.metrics.annotation.Timed;
import org.majimena.petz.domain.Ticket;
import org.majimena.petz.domain.ticket.TicketCriteria;
import org.majimena.petz.security.SecurityUtils;
import org.majimena.petz.service.TicketService;
import org.majimena.petz.web.utils.ErrorsUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * クリニックチケットコントローラ.
 */
@RestController
@RequestMapping("/api/v1")
public class ClinicTicketController {

    /**
     * チケットサービス.
     */
    @Inject
    private TicketService ticketService;

    /**
     * チケットバリデータ.
     */
    @Inject
    private TicketValidator ticketValidator;

    /**
     * チケットサービスを設定する.
     *
     * @param ticketService チケットサービス
     */
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * チケットバリデータを設定する.
     *
     * @param ticketValidator チケットバリデータ
     */
    public void setTicketValidator(TicketValidator ticketValidator) {
        this.ticketValidator = ticketValidator;
    }

    /**
     * クリニックのチケットを取得する.
     *
     * @param clinicId クリニックID
     * @return 指定月の全てのチケット
     */
    @Timed
    @RequestMapping(value = "/clinics/{clinicId}/tickets", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> get(@PathVariable String clinicId, @Valid TicketCriteria criteria) {
        // クリニックの権限チェック
        SecurityUtils.throwIfDoNotHaveClinicRoles(clinicId);

        // 月単位でチケットを取得する
        criteria.setClinicId(clinicId);
        List<Ticket> tickets = ticketService.getTicketsByTicketCriteria(criteria);
        return ResponseEntity.ok().body(tickets);
    }

    /**
     * クリニックのチケットを取得する. 存在しない場合は、404エラーにする.
     *
     * @param clinicId クリニックID
     * @param ticketId チケットID
     * @return 指定月の全てのチケット
     */
    @Timed
    @RequestMapping(value = "/clinics/{clinicId}/tickets/{ticketId}", method = RequestMethod.GET)
    public ResponseEntity<Ticket> get(@PathVariable String clinicId, @PathVariable String ticketId) {
        // クリニックの権限チェック
        SecurityUtils.throwIfDoNotHaveClinicRoles(clinicId);
        // TODO チケットのチェックをすること

        // 該当するチケットを取得する
        Optional<Ticket> schedule = ticketService.getTicketByTicketId(ticketId);
        return schedule
                .map(p -> ResponseEntity.ok().body(p))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * クリニックのチケットを新規作成する.
     *
     * @param clinicId クリニックID
     * @param ticket   登録するチケット情報
     * @param errors   エラーオブジェクト
     * @return 登録したチケット
     * @throws BindException 入力内容に不備がある場合
     */
    @Timed
    @RequestMapping(value = "/clinics/{clinicId}/tickets", method = RequestMethod.POST)
    public ResponseEntity<Ticket> post(@PathVariable String clinicId, @RequestBody @Valid Ticket ticket, BindingResult errors) throws BindException {
        // クリニックの権限チェック
        SecurityUtils.throwIfDoNotHaveClinicRoles(ticket.getClinic().getId());

        // カスタムバリデーションを行う
        ticketValidator.validate(ticket, errors);
        ErrorsUtils.throwIfHaveErrors(errors);

        // チケットを保存する
        Ticket created = ticketService.saveTicket(ticket);
        return ResponseEntity.created(
                URI.create("/api/v1/clinics/" + clinicId + "/tickets/" + created.getId())).body(created);
    }

    /**
     * クリニックのチケットを更新する.
     *
     * @param clinicId クリニックID
     * @param ticketId チケットID
     * @param ticket   更新するチケット情報
     * @param errors   エラーオブジェクト
     * @return 更新したチケット
     * @throws BindException 入力内容に不備がある場合
     */
    @Timed
    @RequestMapping(value = "/clinics/{clinicId}/tickets/{ticketId}", method = RequestMethod.PUT)
    public ResponseEntity<Ticket> put(@PathVariable String clinicId, @PathVariable String ticketId, @RequestBody @Valid Ticket ticket, BindingResult errors) throws BindException {
        // クリニックの権限チェックとIDのコード体系チェック
        SecurityUtils.throwIfDoNotHaveClinicRoles(clinicId);
        SecurityUtils.throwIfDoNotHaveClinicRoles(ticket.getClinic().getId());
        ErrorsUtils.throwIfNotIdentify(ticketId);

        // カスタムバリデーションを行う
        ticketValidator.validate(ticket, errors);
        ErrorsUtils.throwIfHaveErrors(errors);

        // チケットを更新する
        Ticket created = ticketService.updateTicket(ticket);
        return ResponseEntity.ok().body(created);
    }

    /**
     * クリニックのチケットを削除する.
     *
     * @param clinicId クリニックID
     * @param ticketId チケットID
     * @return レスポンスステータス（200）
     */
    @Timed
    @RequestMapping(value = "/clinics/{clinicId}/tickets/{ticketId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> put(@PathVariable String clinicId, @PathVariable String ticketId) {
        // クリニックの権限チェックとIDのコード体系チェック
        SecurityUtils.throwIfDoNotHaveClinicRoles(clinicId);
        ErrorsUtils.throwIfNotIdentify(ticketId);

        // チケットを更新する
        ticketService.deleteTicketByTicketId(ticketId);
        return ResponseEntity.ok().build();
    }
}
