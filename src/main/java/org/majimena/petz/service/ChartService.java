package org.majimena.petz.service;

import org.majimena.petz.common.exceptions.ApplicationException;
import org.majimena.petz.domain.Chart;
import org.majimena.petz.domain.chart.ChartCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * カルテサービス.
 */
public interface ChartService {

    /**
     * カルテを検索する. ページングに対応するため, ページ情報が必要.
     *
     * @param criteria カルテ検索条件
     * @param pageable ページ情報
     * @return 検索条件に該当するカルテの一覧
     */
    Page<Chart> findChartsByChartCriteria(ChartCriteria criteria, Pageable pageable);

    /**
     * カルテIDをもとに, カルテを取得する.
     *
     * @param chartId カルテID
     * @return カルテ
     */
    Optional<Chart> getChartByChartId(String chartId);

    /**
     * カルテを保存する. 未登録のペット情報があれば, それも一緒に登録する.
     *
     * @param clinicId カルテを登録するクリニックのID
     * @param chart    カルテ情報
     * @return 保存しカルテ情報
     * @throws ApplicationException
     */
    Chart saveChart(String clinicId, Chart chart) throws ApplicationException;
}
