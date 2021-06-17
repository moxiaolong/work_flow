package com.zjx.workflow.repository;

import com.zjx.workflow.entity.Conditions;
import com.zjx.workflow.entity.node.Node;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 条件库
 *
 * @author cgn
 * @date 2021/06/15
 */
public interface ConditionsRepository extends JpaRepository<Conditions, Integer> {
}
