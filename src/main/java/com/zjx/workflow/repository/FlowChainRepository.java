package com.zjx.workflow.repository;

import com.zjx.workflow.entity.node.FlowChain;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 测试库
 *
 * @author cgn
 * @date 2021/06/15
 */
public interface FlowChainRepository extends JpaRepository<FlowChain, Integer> {
}
