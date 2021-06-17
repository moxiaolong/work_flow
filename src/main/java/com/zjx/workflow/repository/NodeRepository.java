package com.zjx.workflow.repository;

import com.zjx.workflow.entity.node.Node;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 测试库
 *
 * @author cgn
 * @date 2021/06/15
 */
public interface NodeRepository extends JpaRepository<Node, Integer> {
}
