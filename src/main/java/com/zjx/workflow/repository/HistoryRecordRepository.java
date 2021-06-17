package com.zjx.workflow.repository;

import com.zjx.workflow.entity.HistoryRecord;
import com.zjx.workflow.entity.node.Node;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 历史记录库
 *
 * @author cgn
 * @date 2021/06/15
 */
public interface HistoryRecordRepository extends JpaRepository<HistoryRecord, Integer> {
}
