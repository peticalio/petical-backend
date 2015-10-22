package org.majimena.petz.web.api.examination;

import com.codahale.metrics.annotation.Timed;
import org.majimena.petz.domain.Schedule;
import org.majimena.petz.domain.examination.ScheduleCriteria;
import org.majimena.petz.security.SecurityUtils;
import org.majimena.petz.service.ScheduleService;
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
 * クリニックスケジュールコントローラ.
 */
@RestController
@RequestMapping("/api/v1")
public class ClinicScheduleController {

    /**
     * スケジュールサービス.
     */
    @Inject
    private ScheduleService scheduleService;

    /**
     * スケジュールバリデータ.
     */
    @Inject
    private ScheduleValidator scheduleValidator;

    /**
     * スケジュールサービスを設定する.
     *
     * @param scheduleService スケジュールサービス
     */
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * スケジュールバリデータを設定する.
     *
     * @param scheduleValidator スケジュールバリデータ
     */
    public void setScheduleValidator(ScheduleValidator scheduleValidator) {
        this.scheduleValidator = scheduleValidator;
    }

    /**
     * クリニックのスケジュールを取得する.
     *
     * @param clinicId クリニックID
     * @return 指定月の全てのスケジュール
     */
    @Timed
    @RequestMapping(value = "/clinics/{clinicId}/schedules", method = RequestMethod.GET)
    public ResponseEntity<List<Schedule>> get(@PathVariable String clinicId, @Valid ScheduleCriteria criteria) {
        // クリニックの権限チェック
        SecurityUtils.throwIfDoNotHaveClinicRoles(clinicId);

        // 月単位でスケジュールを取得する
        criteria.setClinicId(clinicId);
        List<Schedule> schedules = scheduleService.getSchedulesByScheduleCriteria(criteria);
        return ResponseEntity.ok().body(schedules);
    }

    /**
     * クリニックのスケジュールを取得する. 存在しない場合は、404エラーにする.
     *
     * @param clinicId   クリニックID
     * @param scheduleId スケジュールID
     * @return 指定月の全てのスケジュール
     */
    @Timed
    @RequestMapping(value = "/clinics/{clinicId}/schedules/{scheduleId}", method = RequestMethod.GET)
    public ResponseEntity<Schedule> get(@PathVariable String clinicId, @PathVariable String scheduleId) {
        // クリニックの権限チェック
        SecurityUtils.throwIfDoNotHaveClinicRoles(clinicId);
        // TODO スケジュールのチェックをすること

        // 該当するスケジュールを取得する
        Optional<Schedule> schedule = scheduleService.getScheduleByScheduleId(scheduleId);
        return schedule
                .map(p -> ResponseEntity.ok().body(p))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * クリニックのスケジュールを新規作成する.
     *
     * @param clinicId クリニックID
     * @param schedule 登録するスケジュール情報
     * @param errors   エラーオブジェクト
     * @return 登録したスケジュール
     * @throws BindException 入力内容に不備がある場合
     */
    @Timed
    @RequestMapping(value = "/clinics/{clinicId}/schedules", method = RequestMethod.POST)
    public ResponseEntity<Schedule> post(@PathVariable String clinicId, @RequestBody @Valid Schedule schedule, BindingResult errors) throws BindException {
        // クリニックの権限チェック
        SecurityUtils.throwIfDoNotHaveClinicRoles(schedule.getClinic().getId());

        // カスタムバリデーションを行う
        scheduleValidator.validate(schedule, errors);
        ErrorsUtils.throwIfHaveErrors(errors);

        // スケジュールを保存する
        Schedule created = scheduleService.saveSchedule(schedule);
        return ResponseEntity.created(
                URI.create("/api/v1/clinics/" + clinicId + "/schedules/" + created.getId())).body(created);
    }

    /**
     * クリニックのスケジュールを更新する.
     *
     * @param clinicId   クリニックID
     * @param scheduleId スケジュールID
     * @param schedule   更新するスケジュール情報
     * @param errors     エラーオブジェクト
     * @return 更新したスケジュール
     * @throws BindException 入力内容に不備がある場合
     */
    @Timed
    @RequestMapping(value = "/clinics/{clinicId}/schedules/{scheduleId}", method = RequestMethod.PUT)
    public ResponseEntity<Schedule> put(@PathVariable String clinicId, @PathVariable String scheduleId, @RequestBody @Valid Schedule schedule, BindingResult errors) throws BindException {
        // クリニックの権限チェックとIDのコード体系チェック
        SecurityUtils.throwIfDoNotHaveClinicRoles(clinicId);
        SecurityUtils.throwIfDoNotHaveClinicRoles(schedule.getClinic().getId());
        ErrorsUtils.throwIfNotIdentify(scheduleId);

        // カスタムバリデーションを行う
        scheduleValidator.validate(schedule, errors);
        ErrorsUtils.throwIfHaveErrors(errors);

        // スケジュールを更新する
        Schedule created = scheduleService.updateSchedule(schedule);
        return ResponseEntity.ok().body(created);
    }

    /**
     * クリニックのスケジュールを削除する.
     *
     * @param clinicId   クリニックID
     * @param scheduleId スケジュールID
     * @return レスポンスステータス（200）
     */
    @Timed
    @RequestMapping(value = "/clinics/{clinicId}/schedules/{scheduleId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> put(@PathVariable String clinicId, @PathVariable String scheduleId) {
        // クリニックの権限チェックとIDのコード体系チェック
        SecurityUtils.throwIfDoNotHaveClinicRoles(clinicId);
        ErrorsUtils.throwIfNotIdentify(scheduleId);

        // スケジュールを更新する
        scheduleService.deleteScheduleByScheduleId(scheduleId);
        return ResponseEntity.ok().build();
    }
}
